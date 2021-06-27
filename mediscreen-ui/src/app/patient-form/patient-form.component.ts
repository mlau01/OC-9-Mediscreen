import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, NgForm} from "@angular/forms";
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
  patientForm!: FormGroup;

  constructor(private patientService: PatientService, private router: Router, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.patientForm = this.formBuilder.group({
      firstName: '',
      lastName: '',
      sex: '',
      dateOfBirth: '',
      address: '',
      city: '',
      phone: ''});
  }

  onSubmit() {
    this.patientService.createPatient(this.patientForm?.value).subscribe((patientCreated) => {
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
