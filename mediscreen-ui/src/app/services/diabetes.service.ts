import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import { Subject} from 'rxjs';
import {PatientAssessDto} from "../models/patientAssessDto";

@Injectable({
  providedIn: 'root'
})

export class DiabetesService {

  apiUrl = 'http://localhost:8083';
  patientAssessDtoSubject = new Subject<PatientAssessDto>();
  patientAssessDto!: PatientAssessDto;
  lastPatientId!: number;

  constructor(private httpClient: HttpClient)  { }

  getPatientAssessDtoById(id: number){
    let params = new HttpParams().set('patId', id);
    this.httpClient.get<PatientAssessDto>(this.apiUrl + '/assess/id', {params}).subscribe((patientAssessDto) => {
      this.patientAssessDto = patientAssessDto;
      this.lastPatientId = id;
      this.emitPatientAssessDto();
    }, (error) => {
      console.log(error);
    });
  }

  public emitPatientAssessDto() {
    this.patientAssessDtoSubject.next(this.patientAssessDto);
  }

  public refresh(){
    this.getPatientAssessDtoById(this.lastPatientId);
  }
}
