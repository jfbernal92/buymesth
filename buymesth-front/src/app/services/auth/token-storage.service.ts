import {Injectable} from '@angular/core';

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'Username';
const USER_SESS_ID = 'UserSessID';
const TOKE_EXP_DATE = 'TokenExpiration';

@Injectable({
    providedIn: 'root'
})
export class TokenStorageService {
    constructor() {
    }

    signOut() {
        window.localStorage.clear();
    }

    public saveToken(token: string) {
        window.localStorage.removeItem(TOKEN_KEY);
        window.localStorage.setItem(TOKEN_KEY, token);
    }

    public getToken(): string {
        return localStorage.getItem(TOKEN_KEY);
    }

    public saveUsername(username: string) {
        window.localStorage.removeItem(USERNAME_KEY);
        window.localStorage.setItem(USERNAME_KEY, username);
    }

    public getUsername(): string {
        return localStorage.getItem(USERNAME_KEY);
    }


    public saveUserID(id: string) {
        window.localStorage.removeItem(USER_SESS_ID);
        window.localStorage.setItem(USER_SESS_ID, id);
    }

    public getUserID(): string {
        return localStorage.getItem(USER_SESS_ID);
    }

    public saveDateExpiration(date: Date) {
        console.log(date);
        date.setHours(date.getHours() + 24);
        console.log(date);

        window.localStorage.removeItem(TOKE_EXP_DATE);
        window.localStorage.setItem(TOKE_EXP_DATE, date.toString());
    }

    public getDateExpiration(): Date {
        return new Date(localStorage.getItem(TOKE_EXP_DATE));
    }
}