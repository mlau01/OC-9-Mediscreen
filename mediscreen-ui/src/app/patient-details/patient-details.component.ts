import { Component, OnInit } from '@angular/core';
import {PatientService} from "../services/patient.service";
import {ActivatedRoute} from "@angular/router";
import {NoteService} from "../services/note.service";
import {Note} from "../models/note";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

  pid!: number;
  notes!: Note[];
  noteForm!: FormGroup;
  isEditing = false;
  editNote!: Note;
  error!: string;

  constructor(private patientService: PatientService,
              private route: ActivatedRoute,
              private noteService: NoteService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.initNote();
    this.initForm()

  }

  initNote() {
    this.pid = this.route.snapshot.params['pid'];
    this.noteService.getPatientNote(this.pid).subscribe((notes) => {
      this.notes = notes;
    }, (error) => {
      console.log(error);
    });
  }

  initForm(){
    this.noteForm = this.formBuilder.group({
      author: ['', [Validators.required, Validators.maxLength(20), Validators.minLength(2)]],
      note: ['', [Validators.required, Validators.maxLength(1000), Validators.minLength(5)]]});

    this.noteService.noteSubject.subscribe((note) => {
      this.isEditing = true;
      this.noteForm.patchValue(note);
      this.editNote = note;
    })
  }

  onSubmit() {
    let method!: Observable<string>;
    if( ! this.isEditing) {
      method = this.noteService.create(this.noteForm?.value, this.pid);
    } else {
      method = this.noteService.edit(this.editNote, this.noteForm);
    }
    method.subscribe((patientCreated) => {

    }, (error) =>
    {
      if(error instanceof HttpErrorResponse) {
        console.log(error);
        this.error = error.error;
      }
    });
  }

  cancelEdit() {
    this.isEditing = false;
    this.noteForm.reset();
  }
}
