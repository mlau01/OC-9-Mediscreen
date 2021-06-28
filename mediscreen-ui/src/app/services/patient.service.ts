import { HttpClient } from '@angular/common/http';
import {Inject, Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import { Patient } from 'src/app/models/patient'
import {DatePipe, formatDate} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  apiUrl = 'http://localhost:8081';
  patientSubject = new Subject<Patient[]>()
  patients: Patient[] = [];

  constructor(private httpClient: HttpClient) { }

  getPatients() {
    this.httpClient.get<Patient[]>(this.apiUrl + '/patients').subscribe((result) =>
    {
      this.patients = result;
      this.emitPatientSubject();
    }, (error) => {

    })
  }

  createPatient(patient: Patient) : Observable<string>{
    return this.httpClient.post<string>(this.apiUrl + '/patients', patient);
  }

  deletePatient(id: number): Observable<string> {
    return this.httpClient.delete<string>(this.apiUrl + '/patients/' + id);
  }

  public emitPatientSubject() {
    this.patientSubject.next(this.patients.slice());
  }

  getById(id: string) : Patient | undefined {
    for(let patient of this.patients){
      if(patient.id === Number.parseInt(id)){
        return patient;
      }
    }
    return undefined;
  }

  editPatient(id: string, patient: Patient) {
    patient.id = Number.parseInt(id);
    return this.httpClient.put<string>(this.apiUrl + '/patients', patient);
  }
}
