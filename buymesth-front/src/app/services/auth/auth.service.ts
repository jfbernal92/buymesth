import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

import {JwtResponse} from './jwt-response';
import {Constants} from '../../utils/Constants';
import {TokenStorageService} from './token-storage.service';
import {LoginFormData} from '../../models/LoginFormData';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    }

    login(loginForm: LoginFormData): Observable<JwtResponse> {
        return this.http.post<JwtResponse>(Constants.Main.LOGIN, loginForm, httpOptions);
    }

    signUp(info: LoginFormData): Observable<object> {
        return this.http.post(Constants.Main.SIGNUP, info, httpOptions);
    }

    emailConfirmation(url: string): Observable<object> {
        return this.http.get(url, httpOptions);
    }

    reSendEmailConfirmation(email: string, token: string): Observable<object> {
        return this.http.post(Constants.Main.SEND_ANOTHER_LINK + token, email, httpOptions);
    }

    passwordForgotten(email: string): Observable<object> {
        return this.http.post(Constants.Main.PASSWORD_FORGOTTEN, email, httpOptions);
    }

    passwordRecover(url: string, newPassword: string, secondPassword: string): Observable<object> {
        return this.http.put(url, JSON.stringify({password: newPassword, password2: secondPassword}), httpOptions);
    }

    checkLoggedUser(): boolean {
      //  console.log("loged", !!(new Date() < this.tokenStorage.getDateExpiration() && this.tokenStorage.getToken() && this.tokenStorage.getUsername() && this.tokenStorage.getUserID()));
        return !!(this.tokenStorage.getToken() && this.tokenStorage.getUsername() && this.tokenStorage.getUserID())
    }

    logOut() {
        this.tokenStorage.signOut();
    }

    isAdmin(): Promise<any> {
        return this.http.get(Constants.User.USER_ROLES + this.tokenStorage.getUserID(), httpOptions).toPromise();
    }

    saveToken(data) {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUsername(data.email);
        this.tokenStorage.saveUserID(data.id);
        this.tokenStorage.saveDateExpiration(new Date());
    }

}
