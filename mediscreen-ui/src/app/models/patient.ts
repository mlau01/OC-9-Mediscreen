export class Patient {

    public id: number;
    public firstName: string;
    public lastName: string;
    public sex: string;
    public address: string;
    public city: string;
    public phone: string;
    public dateOfBirth: Date;
    public note: string | undefined;

    constructor(id: number, firstName: string, lastName: string,
                sex: string, address: string, city: string, phone: string, dateOfBirth: Date) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.sex = sex;
      this.address = address;
      this.city = city;
      this.phone = phone;
      this.dateOfBirth = dateOfBirth;
    }


}
