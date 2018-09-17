import { Component,OnInit, OnDestroy, ViewChild } from '@angular/core';
import { ConfigModel, DocumentDefinition } from '@atlasmap/atlasmap-data-mapper';
import { ErrorHandlerService } from '@atlasmap/atlasmap-data-mapper';
import { DocumentManagementService } from '@atlasmap/atlasmap-data-mapper';
import { MappingManagementService } from '@atlasmap/atlasmap-data-mapper';
import { InitializationService } from '@atlasmap/atlasmap-data-mapper';
import { DocumentInitializationModel } from '@atlasmap/atlasmap-data-mapper';
import { DataMapperAppComponent } from '@atlasmap/atlasmap-data-mapper';
import { InspectionType } from '@atlasmap/atlasmap-data-mapper';
import { DocumentType } from '@atlasmap/atlasmap-data-mapper';
import { Subscription } from 'rxjs';
import { jsonResources } from './json.resources';
import { xmlResources } from './xml.resources';
@Component({
  selector: 'app-root',
  template: '<data-mapper #dataMapperComponent></data-mapper>',
  providers: [MappingManagementService, ErrorHandlerService, DocumentManagementService, InitializationService],
})

export class AppComponent implements OnInit, OnDestroy {

  @ViewChild('dataMapperComponent')
  dataMapperComponent: DataMapperAppComponent;

  private saveMappingSubscription: Subscription;
  configModel: ConfigModel;

  constructor(private initializationService: InitializationService) { }

  ngOnInit() {
      // initialize config information before initializing services
      this.configModel = this.initializationService.cfg;
      //store references to our services in our config model
      //initialize base urls for our service calls
      this.configModel.initCfg.baseJavaInspectionServiceUrl = 'http://localhost:8585/v2/atlas/java/';
      this.configModel.initCfg.baseXMLInspectionServiceUrl = 'http://localhost:8585/v2/atlas/xml/';
      this.configModel.initCfg.baseJSONInspectionServiceUrl = 'http://localhost:8585/v2/atlas/json/';
      this.configModel.initCfg.baseMappingServiceUrl = 'http://localhost:8585/v2/atlas/';
      this.configModel.initCfg.disableMappingPreviewMode = false;
      
      //initialize data for our class path service call
      //note that quotes, newlines, and tabs are escaped

      this.configModel.initCfg.xsrfHeaderName = 'ATLASMAP-XSRF-TOKEN';
      this.configModel.initCfg.xsrfCookieName = 'ATLASMAP-XSRF-TOKEN';
      this.configModel.initCfg.xsrfDefaultTokenValue = 'awesome';


     // configModel.initCfg.pomPayload = InitializationService.createExamplePom();
     this.configModel.initCfg.classPathFetchTimeoutInMilliseconds = 30000;
      // if classPath is specified, maven call to resolve pom will be skipped
      this.configModel.initCfg.classPath = null;

      this.configModel.initCfg.debugDocumentServiceCalls = true;
      this.configModel.initCfg.debugDocumentParsing = true;
      this.configModel.initCfg.debugMappingServiceCalls = true;
      this.configModel.initCfg.debugClassPathServiceCalls = true;
      this.configModel.initCfg.debugValidationServiceCalls = true;
      this.configModel.initCfg.debugFieldActionServiceCalls = true;
  
      // enable mock mappings loading, example code is shown in the InitializationService for this
  
      // enable mock source/target documents as needed
  
      this.addJavaDocument('io.atlasmap.qe.test.SourceMappingTestClass', true);
     this.addJavaDocument('io.atlasmap.qe.test.DatesObject', true);
     this.addJavaDocument('io.atlasmap.qe.test.SourceListsClass', true);
      this.addJavaDocument('io.atlasmap.qe.test.SmallMappingTestClass', true);
      this.addJavaDocument('io.atlasmap.qe.test.TargetMappingTestClass', false);
      this.addJavaDocument('io.atlasmap.qe.test.StringObject', false);
     this.addJavaDocument('io.atlasmap.qe.test.TargetListsClass', false);

     this.addTextDocument('sourceJson', DocumentType.JSON, InspectionType.SCHEMA, jsonResources.sourceSchema, true)
     this.addTextDocument('targetJson', DocumentType.JSON, InspectionType.SCHEMA, jsonResources.targetSchema, false);

     this.addTextDocument('targetXmlSchema', DocumentType.XML, InspectionType.SCHEMA, xmlResources.targetXMLSchema, false);
     this.addTextDocument('sourceXmlInstance', DocumentType.XML, InspectionType.INSTANCE, xmlResources.sourceInstance, true);
     this.addTextDocument('sourceXmlSchema', DocumentType.XML, InspectionType.SCHEMA, xmlResources.sourceXMLSchema, true);

      this.initializationService.initialize();

      //save the mappings when the ui calls us back asking for save
      this.configModel.mappingService.saveMappingOutput$.subscribe((saveHandler: Function) => {
          //NOTE: the mapping definition being saved is currently stored in "this.cfg.mappings" until further notice.

          //This is an example callout to save the mapping to the mock java service
          this.configModel.mappingService.saveMappingToService();

          //After you've sucessfully saved you *MUST* call this (don't call on error)
          this.configModel.mappingService.handleMappingSaveSuccess(saveHandler);
      });
      if (!this.configModel.sourceDocs || this.configModel.sourceDocs.length === 0) {
        this.configModel.errorService.error('No source document was found', '');
        }
        if (!this.configModel.targetDocs || this.configModel.targetDocs.length === 0) {
          this.configModel.errorService.error('No target document was found', '');
        }
  }

  private addJavaDocument(className: string, isSource: boolean):DocumentDefinition {
      const model: DocumentInitializationModel = new DocumentInitializationModel();
      model.id = className;
      model.type = DocumentType.JAVA;
      model.inspectionType = InspectionType.JAVA_CLASS;
      model.inspectionSource = className;
      model.isSource = isSource;
      return this.configModel.addDocument(model);
  }

  private addTextDocument(name: string, type: DocumentType, inspectionType: InspectionType, source: string, isSource: boolean) {
      const model: DocumentInitializationModel = new DocumentInitializationModel();
      model.id = name;
      model.type = type;
      model.inspectionType = inspectionType;
      model.inspectionSource = source;
      model.isSource = isSource;
      this.configModel.addDocument(model);
  }

  ngOnDestroy() {
      this.saveMappingSubscription.unsubscribe();
    }
}