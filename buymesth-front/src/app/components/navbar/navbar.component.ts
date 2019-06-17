import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {Router} from "@angular/router";
import {DataShareService} from "../../services/data-share/data-share.service";
import {OperationService} from "../../services/operation/operation.service";
import {MessageService} from "primeng/api";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css'],
    providers: [MessageService]
})
export class NavbarComponent implements OnInit {


    private bg_color: string;
    private bank: number;
    private userName: string;
    amount: number = 0;

    constructor(private authService: AuthService, private router: Router, private dataSharedService: DataShareService,
                private operationService: OperationService, private messageService: MessageService) {
        this.dataSharedService.current_bg_color.subscribe(color => this.bg_color = color);
        this.dataSharedService.current_bank.subscribe(bank => this.bank = bank);
        this.dataSharedService.current_userName.subscribe(name => this.userName = name);
    }

    ngOnInit() {
    }

    isLogged(): boolean {
        return this.authService.checkLoggedUser();
    }

    logOut(): void {
        this.authService.logOut();
        window.location.href = 'home';
    }

    isInAdminPanel(): boolean {
        return window.location.href.search('admin') !== -1;
    }

    deposit() {
        this.operationService.deposit(this.amount).toPromise().then(() => {
            this.messageService.addAll([{
                severity: 'success',
                summary: 'Congrats!',
                detail: 'Your account will be accredited when the administrator confirm the transaction',
                sticky: true,
                closable: true
            }])
        }).catch(() => {
            this.messageService.addAll([{
                severity: 'error',
                summary: 'Error',
                detail: 'Error in transaction. No money will be substracted from your wallet',
                sticky: true,
                closable: true
            }])
        });
    }

}
