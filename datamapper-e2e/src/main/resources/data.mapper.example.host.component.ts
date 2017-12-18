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

import { Component, ViewChild } from '@angular/core';
import { ConfigModel } from '../models/config.model';

import { ErrorHandlerService } from '../services/error.handler.service';
import { DocumentManagementService } from '../services/document.management.service';
import { MappingManagementService } from '../services/mapping.management.service';
import { InitializationService } from '../services/initialization.service';

import { DataMapperAppComponent } from './data.mapper.app.component';

@Component({
    selector: 'data-mapper-example-host',
    template: `
        <data-mapper #dataMapperComponent></data-mapper>
    `,
    providers: [MappingManagementService, ErrorHandlerService, DocumentManagementService],
})

export class DataMapperAppExampleHostComponent {

    @ViewChild('dataMapperComponent')
    public dataMapperComponent: DataMapperAppComponent;

    constructor(private initializationService: InitializationService) {
        // initialize config information before initializing services
        const c: ConfigModel = initializationService.cfg;

        //store references to our services in our config model

        //initialize base urls for our service calls
        c.initCfg.baseJavaInspectionServiceUrl = 'http://localhost:8585/v2/atlas/java/';
        c.initCfg.baseXMLInspectionServiceUrl = 'http://localhost:8585/v2/atlas/xml/';
        c.initCfg.baseJSONInspectionServiceUrl = 'http://localhost:8585/v2/atlas/json/';
        c.initCfg.baseMappingServiceUrl = 'http://localhost:8585/v2/atlas/';

        //initialize data for our class path service call
        //note that quotes, newlines, and tabs are escaped
        c.initCfg.pomPayload = InitializationService.createExamplePom();
        c.initCfg.classPathFetchTimeoutInMilliseconds = 30000;
        // if classPath is specified, maven call to resolve pom will be skipped
        c.initCfg.classPath = null;
        c.addJavaDocument('io.atlasmap.qe.test.SourceMappingTestClass', true);
        c.addJavaDocument('io.atlasmap.qe.test.TargetMappingTestClass', false);
        /*
         * The following examples demonstrate adding source/target documents to the Data Mapper's configuration.
         * Note that multiple source documents are supported, but multiple target documents are not supported.
         *
         * example java source document configuration:
         *
         * var documentIsSourceDocument: boolean = true;
         * c.addJavaDocument("io.atlasmap.java.test.SourceOrder", documentIsSourceDocument);
         *
         * example xml instance document:
         *
         * c.addXMLInstanceDocument("XMLInstanceSource", DocumentManagementService.generateMockInstanceXML(), documentIsSourceDocument);
         *
         * example xml schema document:
         *
         * c.addXMLSchemaDocument("XMLSchemaSource", DocumentManagementService.generateMockSchemaXML(), documentIsSourceDocument);
         *
         * example json document:
         *
         * c.addJSONDocument("JSONTarget", DocumentManagementService.generateMockJSON(), documentIsSourceDocument);
         *
         */

        //enable debug logging options as needed

        //initialize system
        initializationService.initialize();

        //save the mappings when the ui calls us back asking for save
        c.mappingService.saveMappingOutput$.subscribe((saveHandler: Function) => {
            //NOTE: the mapping definition being saved is currently stored in "this.cfg.mappings" until further notice.

            //turn this on to print out example json
            const makeExampleJSON = false;
            if (makeExampleJSON) {
                const jsonObject: any = c.mappingService.serializeMappingsToJSON();
                const jsonVersion = JSON.stringify(jsonObject);
                const jsonPretty = JSON.stringify(JSON.parse(jsonVersion), null, 2);
            }

            //This is an example callout to save the mapping to the mock java service
            c.mappingService.saveMappingToService();

            //After you've sucessfully saved you *MUST* call this (don't call on error)
            c.mappingService.handleMappingSaveSuccess(saveHandler);
        });
    }
}
