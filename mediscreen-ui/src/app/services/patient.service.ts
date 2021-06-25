import { HttpClient } from '@angular/common/http';
import {Inject, Injectable, LOCALE_ID} from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from 'src/app/models/patient'
import {DatePipe, formatDate} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  apiUrl = 'http://localhost:8081';

  constructor(private httpClient: HttpClient, @Inject(LOCALE_ID) private locale: string) { }

  getPatients() : Observable<Patient[]> {
    return this.httpClient.get<Patient[]>(this.apiUrl + '/patient');
  }

  createPatient(patient: Patient) : Observable<Patient>{
    return this.httpClient.post<Patient>(this.apiUrl + '/patient', patient);
  }

  deletePatient(id: number): Observable<string> {
    return this.httpClient.delete<string>(this.apiUrl + '/patient/' + id);
  }
}
