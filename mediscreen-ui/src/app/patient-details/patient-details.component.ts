import { Component, OnInit } from '@angular/core';
import {PatientService} from "../services/patient.service";
import {ActivatedRoute} from "@angular/router";
import {NoteService} from "../services/note.service";
import {Patient} from "../models/patient";
import {Note} from "../models/note";

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

  pid!: number;
  notes!: Note[];

  constructor(private patientService: PatientService, private route: ActivatedRoute, private noteService: NoteService) { }

  ngOnInit(): void {
    this.pid = this.route.snapshot.params['pid'];
    this.noteService.getPatientNote(this.pid).subscribe((notes) => {
      this.notes = notes;
    }, (error) => {
      console.log(error);
    });
  }

}
