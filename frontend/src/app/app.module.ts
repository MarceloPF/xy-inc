import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreatePointComponent } from './create-point/create-point.component';
import { PointDetailsComponent } from './point-details/point-details.component';
import { PointListComponent } from './point-list/point-list.component';

@NgModule({
  declarations: [
    AppComponent,
    CreatePointComponent,
    PointDetailsComponent,
    PointListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
