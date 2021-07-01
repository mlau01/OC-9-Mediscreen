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
  noteSubject = new Subject<Note>();
  constructor(private httpClient: HttpClient) { }

  public getPatientNote(pid: number): Observable<Note[]> {
    return this.httpClient.get<Note[]>(this.apiUrl + '/notes/patient/' + pid);
  }

  public emitNote(note: Note) {
    this.noteSubject.next(note);

  }

  create(note: Note, pid: number): Observable<string> {
    note.patientId = pid;
    return this.httpClient.post<string>(this.apiUrl + '/notes', note);
  }

  edit(note: Note, form: FormGroup): Observable<string> {
    note.author = form.value['author'];
    note.note = form.value['note'];
    return this.httpClient.put<string>(this.apiUrl + '/notes', note);
  }
}
