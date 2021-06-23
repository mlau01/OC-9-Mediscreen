import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from 'src/app/models/patient'

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private httpClient: HttpClient) { }

  getPatients() : Observable<Patient[]> {
    return this.httpClient.get<Patient[]>('http://localhost:8081/patient/list');
  }
}
