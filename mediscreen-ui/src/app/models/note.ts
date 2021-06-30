export class Note {
  public id: string;
  public patientId: number;
  public created: Date;
  public author: string;
  public note: string;

  constructor(id: string, patientId: number, created: Date, author: string, note: string) {
    this.id = id;
    this.patientId = patientId;
    this.created = created;
    this.author = author;
    this.note = note;
  }
}
