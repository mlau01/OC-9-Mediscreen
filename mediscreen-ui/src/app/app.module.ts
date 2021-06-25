import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientComponent } from './patient/patient.component';
import { PatientService } from './services/patient.service';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import {PatientFormComponent} from "./patient-form/patient-form.component";
import {FormsModule} from "@angular/forms";
import {DatePipe} from "@angular/common";

//ROUTES CONFIGURATION
const appRoutes: Routes = [
  { path: 'patient', component: PatientComponent},
  { path: 'patientForm', component: PatientFormComponent},
  { path: '', component: PatientComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    PatientComponent,
    PatientFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    //APPLY ROUTE CONFIG
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    PatientService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
