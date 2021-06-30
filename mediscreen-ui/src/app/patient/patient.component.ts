import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { Patient } from 'src/app/models/patient';
import {Router} from "@angular/router";

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {

  constructor(private patientService: PatientService, private router: Router) { }

  patients: Patient[] = [];

  ngOnInit(): void {
    this.patientService.getPatients();
    this.patientService.patientSubject.subscribe((patient: Patient[]) => {
        this.patients = patient;
      }
    );
    this.patientService.emitPatientSubject();
  }

  onDelete(id: number) {
    if( ! confirm("Are you sure?")){
      return;
    }
    this.patientService.deletePatient(id).subscribe((result) => {
        for(let i = 0; i < this.patientService.patients.length; i++){
          if ( this.patientService.patients[i].id === id) {
            this.patientService.patients.splice(i, 1);
          }
        }
        this.patientService.emitPatientSubject();

      }, (error) => {

      }
      );
  }

  onEdit(id: number) {
    this.router.navigate(['/patientForm/' + id]);
  }


  onDetails(pid: number) {
    this.router.navigate(['/details/' + pid])
  }
}
