import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-patient-note',
  templateUrl: './patient-note.component.html',
  styleUrls: ['./patient-note.component.css']
})
export class PatientNoteComponent implements OnInit {

  @Input() author!: string;
  @Input() created!: Date;
  @Input() content!: string;

  constructor() { }

  ngOnInit(): void {
  }

}
