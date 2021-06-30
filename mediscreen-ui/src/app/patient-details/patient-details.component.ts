import { Component, OnInit } from '@angular/core';
import {PatientService} from "../services/patient.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

  pid!: Number;

  constructor(private patientService: PatientService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.pid = this.route.snapshot.params['pid'];

  }

}
