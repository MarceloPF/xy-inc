import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreatePointComponent } from './create-point/create-point.component';
import { PointDetailsComponent } from './point-details/point-details.component';
import { PointListComponent } from './point-list/point-list.component';
import { EditPointComponent } from './edit-point/edit-point.component';

@NgModule({
  declarations: [
    AppComponent,
    CreatePointComponent,
    PointDetailsComponent,
    PointListComponent,
    EditPointComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
