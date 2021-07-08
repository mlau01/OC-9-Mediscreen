import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientComponent } from './patient/patient.component';
import { PatientService } from './services/patient.service';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import {PatientFormComponent} from "./patient-form/patient-form.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DatePipe} from "@angular/common";
import { PatientDetailsComponent } from './patient-details/patient-details.component';
import { PatientNoteComponent } from './patient-note/patient-note.component';
import {NoteService} from "./services/note.service";
import {DiabetesService} from "./services/diabetes.service";

//ROUTES CONFIGURATION
const appRoutes: Routes = [
  { path: 'patient', component: PatientComponent},
  { path: 'patientForm', component: PatientFormComponent},
  { path: 'patientForm/:id', component: PatientFormComponent},
  { path: 'details/:pid', component: PatientDetailsComponent},
  { path: '', component: PatientComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    PatientComponent,
    PatientFormComponent,
    PatientDetailsComponent,
    PatientNoteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    //APPLY ROUTE CONFIG
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    PatientService,
    NoteService,
    DiabetesService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
