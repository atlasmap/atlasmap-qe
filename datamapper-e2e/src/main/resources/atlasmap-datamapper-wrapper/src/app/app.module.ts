import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { DataMapperModule } from '@atlasmap/atlasmap-data-mapper';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot([]),
    DataMapperModule.withInterceptor(),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
