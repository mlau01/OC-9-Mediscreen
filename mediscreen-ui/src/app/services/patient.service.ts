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
    return this.httpClient.get<Patient[]>(this.apiUrl + '/patient/list');
  }

  createPatient(patient: Patient) : Observable<Patient>{
    let formData = new FormData();
    formData.append("firstName", patient.firstName);
    formData.append("lastName", patient.lastName);
    formData.append("sex", patient.sex);
    formData.append("dob", formatDate(patient.dateOfBirth, 'yyyy-MM-dd', this.locale));
    formData.append("address", patient.address);
    formData.append("city", patient.city);
    formData.append("phone", patient.phone);
    console.log('post formdata object:' + formData);
    return this.httpClient.post<any>(this.apiUrl + '/patient/add', formData);
  }

  deletePatient(id: number) {
    return this.httpClient.delete(this.apiUrl + '/patient/delete/' + id);
  }
}
