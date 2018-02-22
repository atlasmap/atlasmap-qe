/*
    Copyright (C) 2017 Red Hat, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import { Component, ViewChild, OnInit } from '@angular/core';
import { ConfigModel } from '@atlasmap/atlasmap.data.mapper';
import { ChangeDetectorRef } from "@angular/core";
import { ErrorHandlerService } from '@atlasmap/atlasmap.data.mapper';
import { DocumentManagementService } from '@atlasmap/atlasmap.data.mapper';
import { MappingManagementService } from '@atlasmap/atlasmap.data.mapper';
import { InitializationService } from '@atlasmap/atlasmap.data.mapper';
import {DocumentInitializationModel} from '@atlasmap/atlasmap.data.mapper';
import { DataMapperAppComponent } from '@atlasmap/atlasmap.data.mapper';
import { InspectionType } from '@atlasmap/atlasmap.data.mapper';
import { DocumentType } from '@atlasmap/atlasmap.data.mapper';


@Component({
    selector: 'data-mapper-qe',
    template: `
        <data-mapper #dataMapperComponent></data-mapper>
    `,
    providers: [MappingManagementService, ErrorHandlerService, DocumentManagementService],
    
})

export class AtlasMapHostQEComponent implements OnInit {
    
      @ViewChild('dataMapperComponent')
      dataMapperComponent: DataMapperAppComponent;
      configModel: ConfigModel;
      
      constructor(private  initializationService: InitializationService) { }
    
        ngOnInit() {
            // initialize config information before initializing services
            this.configModel = this.initializationService.cfg;
    
            //store references to our services in our config model
    
            //initialize base urls for our service calls
            this.configModel.initCfg.baseJavaInspectionServiceUrl = 'http://localhost:8585/v2/atlas/java/';
            this.configModel.initCfg.baseXMLInspectionServiceUrl = 'http://localhost:8585/v2/atlas/xml/';
            this.configModel.initCfg.baseJSONInspectionServiceUrl = 'http://localhost:8585/v2/atlas/json/';
            this.configModel.initCfg.baseMappingServiceUrl = 'http://localhost:8585/v2/atlas/';
    
            //initialize data for our class path service call
            //note that quotes, newlines, and tabs are escaped
            this.configModel.initCfg.pomPayload = InitializationService.createExamplePom();
            this.configModel.initCfg.classPathFetchTimeoutInMilliseconds = 30000;
            // if classPath is specified, maven call to resolve pom will be skipped
            this.configModel.initCfg.classPath = null;
                this.addJavaDocument('io.atlasmap.qe.test.SourceMappingTestClass', true);
                this.addJavaDocument('io.atlasmap.qe.test.SourceListsClass', true);
                this.addJavaDocument('io.atlasmap.qe.test.SmallMappingTestClass', true);
                this.addJavaDocument('io.atlasmap.qe.test.TargetMappingTestClass', false);
                this.addJavaDocument('io.atlasmap.qe.test.StringObject', false);
                this.addJavaDocument('io.atlasmap.qe.test.TargetListsClass', false);
    

            this.initializationService.initialize();
    
            //save the mappings when the ui calls us back asking for save
        this.configModel.mappingService.saveMappingOutput$.subscribe((saveHandler: Function) => {
                //NOTE: the mapping definition being saved is currently stored in "this.cfg.mappings" until further notice.
    
                //This is an example callout to save the mapping to the mock java service
                this.configModel.mappingService.saveMappingToService();
    
                //After you've sucessfully saved you *MUST* call this (don't call on error)
                this.configModel.mappingService.handleMappingSaveSuccess(saveHandler);
            });
        }

        private addJavaDocument(className: string, isSource: boolean) {
            const model: DocumentInitializationModel = new DocumentInitializationModel();
            model.id = className;
            model.type = DocumentType.JAVA;
            model.inspectionType = InspectionType.JAVA_CLASS;
            model.inspectionSource = className;
            model.isSource = isSource;
             this.configModel.addDocument(model);
        }
    }