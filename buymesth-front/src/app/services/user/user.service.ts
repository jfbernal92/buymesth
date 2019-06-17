import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Constants} from '../../utils/Constants';
import {TokenStorageService} from '../auth/token-storage.service';
import {UserDetails} from "../../models/UserDetails";
import {User} from "../../models/User";
import {UserCount} from "../../models/UserCount";
import {Pageable} from "../../models/Pageable";

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {
    }

    getUserProfile(): Observable<User> {
        return this.http.get<User>(Constants.User.PROFILE + this.tokenStorageService.getUserID(), httpOptions);
    }

    completeProfile(userDetails: UserDetails): Observable<UserDetails> {
        return this.http.post<UserDetails>(Constants.User.PROFILE + this.tokenStorageService.getUserID(), userDetails, httpOptions);
    }

    getUserCount(): Observable<UserCount> {
        return this.http.get<UserCount>(Constants.User.USER_ALL, httpOptions);
    }

    getUsers(page: Pageable): Observable<PageDtoResponse> {
        return this.http.get<PageDtoResponse>(Constants.User.ROOT + page.getUrlQuery(), httpOptions);
    }

    editUserProfile(userDetail: UserDetails): Observable<UserDetails> {
        return this.http.put<UserDetails>(Constants.User.PROFILE + this.tokenStorageService.getUserID(), userDetail, httpOptions);
    }

    deleteUser(id: number): Observable<any> {
        return this.http.delete(Constants.User.ROOT + '/' + id, httpOptions);
    }

    getUserCountByCountry(): Observable<Map<string, number>> {
        return this.http.get<Map<string, number>>(Constants.User.BY_COUNTRY, httpOptions);
    }

    getUserCountBySignupDate(): Observable<Map<string, number>> {
        return this.http.get<Map<string, number>>(Constants.User.BY_SINGUP_DATE, httpOptions);
    }

}
