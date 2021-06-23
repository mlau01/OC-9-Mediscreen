import { Component, OnInit } from '@angular/core';
import { NoteService } from '../services/note.service';

@Component({
  selector: 'app-patient-note',
  templateUrl: './patient-note.component.html',
  styleUrls: ['./patient-note.component.css']
})
export class PatientNoteComponent implements OnInit {

  constructor(private noteService: NoteService) { }

  patientId!: String;

  ngOnInit(): void {
    this.patientId = this.noteService.patientId;
  }

}
