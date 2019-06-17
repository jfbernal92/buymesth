import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class DataShareService {

    private bg_color = new BehaviorSubject('bg-primary');
    current_bg_color = this.bg_color.asObservable();
    private bank = new BehaviorSubject(0.00);
    current_bank = this.bank.asObservable();
    private userName = new BehaviorSubject('');
    current_userName = this.userName.asObservable();


    constructor() {
    }

    changeBgColor(bgColorClass: string) {
        this.bg_color.next(bgColorClass)
    }

    showBank(bank: number) {
        this.bank.next(bank);
    }

    showUserName(userName: string) {
        this.userName.next(userName);
    }
}
