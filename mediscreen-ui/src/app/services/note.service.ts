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
  notes! : Note[];
  constructor(private httpClient: HttpClient) { }

  public getPatientNote(pid: number): Observable<Note[]> {
    return this.httpClient.get<Note[]>(this.apiUrl + '/notes/patient/' + pid);
  }

  public emitNoteEdition(note: Note) {
    this.noteEditionSubject.next(note);

  }

  create(note: Note, pid: number): Observable<Note> {
    note.patientId = pid;
    return this.httpClient.post<Note>(this.apiUrl + '/notes', note);
  }

  edit(note: Note, form: FormGroup): Observable<Note> {
    note.author = form.value['author'];
    note.note = form.value['note'];
    return this.httpClient.put<Note>(this.apiUrl + '/notes', note);
  }

  delete(id: string) {
    return this.httpClient.delete(this.apiUrl + '/notes/' + id);
  }
}
