import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {PatientService} from "../services/patient.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.css']
})
export class PatientFormComponent implements OnInit {

  error: any;
  showError: boolean = false;

  constructor(private patientService: PatientService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(f: NgForm) {
    this.patientService.createPatient(f.value).subscribe((patientCreated) => {
      this.router.navigate(['/patient']);
    }, (error) =>
      {
        if(error instanceof HttpErrorResponse) {
          this.error = error.error;
          this.showError = true;
        }
      });
  }
}
