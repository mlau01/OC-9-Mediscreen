import { HttpClient } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import { Note } from 'src/app/models/note'
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})

export class NoteService {
  apiUrl = 'http://localhost:8082';
  noteEditionSubject = new Subject<Note>();
  noteSubject = new Subject<Note[]>();
  notes! : Note[];
  constructor(private httpClient: HttpClient) { }

  public getPatientNote(pid: number){
    return this.httpClient.get<Note[]>(this.apiUrl + '/notes/patient/' + pid).subscribe((notes) => {
      this.notes = notes;
      this.emitNote();
    });
  }

  public emitNoteEdition(note: Note) {
    this.noteEditionSubject.next(note);

  }

  create(note: Note, pid: number){
    note.patientId = pid;
    this.httpClient.post<Note>(this.apiUrl + '/notes', note).subscribe((noteCreated) => {
      this.notes.unshift(noteCreated);
      this.emitNote();
    }, (error) => {
      console.log(error);
    })
  }

  edit(note: Note, form: FormGroup){
    note.author = form.value['author'];
    note.note = form.value['note'];
    return this.httpClient.put<Note>(this.apiUrl + '/notes', note).subscribe((noteUpdated) => {
      for(let i = 0; i < this.notes.length; i++){
        if ( this.notes[i].id === noteUpdated.id) {
          this.notes[i] = noteUpdated;
          this.emitNote();
        }
      }
    });
  }

  delete(id: string) {
    this.httpClient.delete(this.apiUrl + '/notes/' + id).subscribe(() => {
      for(let i = 0; i < this.notes.length; i++) {
        if (this.notes[i].id === id) {
          this.notes.splice(i, 1);
          this.emitNote();
        }
      }
    }, (error) => {
      console.log(error);
    });
  }

  emitNote(){
    this.noteSubject.next(this.notes);
  }
}
