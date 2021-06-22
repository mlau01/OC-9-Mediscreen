import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  patientId!: String;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.patientId = this.route.snapshot.params['id'];
  }
}
