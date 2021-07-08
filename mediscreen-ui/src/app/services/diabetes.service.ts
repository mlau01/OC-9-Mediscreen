import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import { Note } from 'src/app/models/note'
import {FormGroup} from "@angular/forms";
import {PatientAssessDto} from "../models/patientAssessDto";

@Injectable({
  providedIn: 'root'
})

export class DiabetesService {

  apiUrl = 'http://localhost:8083';
  patientAssessDtoSubject = new Subject<PatientAssessDto>();
  patientAssessDto!: PatientAssessDto;

  constructor(private httpClient: HttpClient) { }

  getPatientAssessDtoById(id: number){
    let params = new HttpParams().set('patId', id);
    this.httpClient.get<PatientAssessDto>(this.apiUrl + '/assess/id', {params}).subscribe((patientAssessDto) => {
      this.patientAssessDto = patientAssessDto;
      this.emitPatientAssessDto();
    }, (error) => {
      console.log(error);
    });
  }

  private emitPatientAssessDto() {
    this.patientAssessDtoSubject.next(this.patientAssessDto);
  }
}
