import { HttpClient } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import { Note } from 'src/app/models/note'

@Injectable({
  providedIn: 'root'
})

export class NoteService {
  apiUrl = 'http://localhost:8082';

  constructor(private httpClient: HttpClient) { }

  public getPatientNote(pid: number): Observable<Note[]> {
    return this.httpClient.get<Note[]>(this.apiUrl + '/notes/patient/' + pid);
  }
}
