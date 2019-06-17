import {Role} from "./Role";
import {UserDetails} from "./UserDetails";

export class User {
    id: number;
    email: string;
    enabled: boolean;
    locked: boolean;
    roles: Role[];
    signupDate: string;
    userDetail: UserDetails;

    constructor(email: string, enabled: boolean, locked: boolean, roles: Role[], signupDate: string, userDetail: UserDetails, id?: number) {
        this.email = email;
        this.enabled = enabled;
        this.locked = locked;
        this.roles = roles;
        this.signupDate = signupDate;
        this.userDetail = userDetail;
        this.id = id;
    }

    isAdmin(): boolean {
        return this.roles.includes(new Role('ADMIN'));
    }
}