import { BrowserModule } from '@angular/platform-browser';
import { NgModule,ChangeDetectorRef} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { DataMapperModule } from '@atlasmap/atlasmap.data.mapper';
import { AtlasMapHostQEComponent } from './atlasmap.host.qe.component';

@NgModule({
  declarations: [
    AtlasMapHostQEComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot([]),
    DataMapperModule
  ],
  exports: [
    AtlasMapHostQEComponent
  ],
  providers: [],
  bootstrap: [AtlasMapHostQEComponent]
})
export class AppModule { }
