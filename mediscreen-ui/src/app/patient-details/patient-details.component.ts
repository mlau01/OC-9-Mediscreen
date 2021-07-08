import { Component, OnInit } from '@angular/core';
import {PatientService} from "../services/patient.service";
import {ActivatedRoute} from "@angular/router";
import {NoteService} from "../services/note.service";
import {Note} from "../models/note";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {Patient} from "../models/patient";
import {DiabetesService} from "../services/diabetes.service";
import {PatientAssessDto} from "../models/patientAssessDto";

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

  pid!: number;
  patient: Patient | undefined;
  notes!: Note[];
  noteForm!: FormGroup;
  isEditing = false;
  editNote!: Note;
  error!: string;
  patientAssessDto! : PatientAssessDto;

  constructor(private patientService: PatientService,
              private diabetesService: DiabetesService,
              private route: ActivatedRoute,
              private noteService: NoteService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.initNote();
    this.initForm()
    this.getPatient();
    this.getDiabetesAssess();

  }

  getDiabetesAssess(){
    this.diabetesService.getPatientAssessDtoById(this.pid);
    this.diabetesService.patientAssessDtoSubject.subscribe((patientAssessDto) => {
      this.patientAssessDto = patientAssessDto;
    })
  }

  getPatient(){
    this.patient = this.patientService.getById(''+this.pid);
  }

  initNote() {
    this.pid = this.route.snapshot.params['pid'];
    this.noteService.getPatientNote(this.pid);

    this.noteService.noteSubject.subscribe((notes) => {
      this.notes = notes;
    })
  }

  initForm(){
    this.noteForm = this.formBuilder.group({
      author: ['', [Validators.required, Validators.maxLength(40), Validators.minLength(2)]],
      note: ['', [Validators.required, Validators.minLength(5)]]});

    this.noteService.noteEditionSubject.subscribe((note) => {
      this.isEditing = true;
      this.noteForm.patchValue(note);
      this.editNote = note;
    })
  }

  onSubmit() {
    if( ! this.isEditing) {
      this.noteService.create(this.noteForm?.value, this.pid);
    } else {
      this.noteService.edit(this.editNote, this.noteForm);
    }
    this.cancelEdit();

  }

  cancelEdit() {
    this.isEditing = false;
    this.noteForm.reset();
  }
}
