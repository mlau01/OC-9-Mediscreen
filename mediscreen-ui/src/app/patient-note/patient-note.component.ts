import {Component, Input, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {Note} from "../models/note";
import {NoteService} from "../services/note.service";

@Component({
  selector: 'app-patient-note',
  templateUrl: './patient-note.component.html',
  styleUrls: ['./patient-note.component.css']
})
export class PatientNoteComponent implements OnInit {

  @Input() id!: string;
  @Input() note! : Note;

  constructor(private noteService: NoteService) { }

  ngOnInit(): void {
  }

  onEdit() {
    this.noteService.emitNoteEdition(this.note);
  }

  onDelete(id: string) {
    this.noteService.delete(this.note.id);
  }
}
