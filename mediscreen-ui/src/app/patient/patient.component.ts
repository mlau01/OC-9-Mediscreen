import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { Patient } from 'src/app/models/patient';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {

  constructor(private patientService: PatientService) { }

  patients: Patient[] = [];

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

    }, (error) => {

      }
      );
  }
}
