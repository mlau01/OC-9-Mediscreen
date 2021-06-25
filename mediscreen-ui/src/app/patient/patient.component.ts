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
  noteShown: boolean = false;

  ngOnInit(): void {
    this.getPatients();
  }

  getPatients() {
    this.patientService.getPatients().subscribe(
      result => {
        if(result && result != null) {
          this.patients = result;
        }
      }
    )
  }

  onDelete(id: number) {
    this.patientService.deletePatient(id).subscribe((result) => {
        for(let i = 0; i < this.patients.length; i++){
          if ( this.patients[i].id === id) {
            this.patients.splice(i, 1);
          }
        }

      }, (error) => {

      }
      );
  }
}
