export class UserDetails {
    name: string;
    firstSurname: string;
    secondSurname: string;
    country: string;
    state: string;
    region: string;
    province: string;
    postalCode: number;
    street: string;
    number: number;
    door: string;
    bank: number;

    constructor(name: string, firstSurname: string, secondSurname: string, country: string, state: string, region: string, province: string, postalCode: number, street: string, number: number, door: string) {
        this.name = name;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.country = country;
        this.state = state;
        this.region = region;
        this.province = province;
        this.postalCode = postalCode;
        this.street = street;
        this.number = number;
        this.door = door;
    }
}