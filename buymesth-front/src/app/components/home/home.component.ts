import {Component, OnInit} from '@angular/core';
import {DataShareService} from "../../services/data-share/data-share.service";
import {AuthService} from "../../services/auth/auth.service";
import {UserService} from "../../services/user/user.service";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    BG_COLOR = 'bg-info';

    constructor(private dataSharedService: DataShareService, private authService: AuthService, private userService: UserService) {
    }

    ngOnInit() {
        this.dataSharedService.changeBgColor(this.BG_COLOR);

        if (this.authService.checkLoggedUser()) {
            this.userService.getUserProfile().toPromise().then(data => {
                this.dataSharedService.showBank(data.userDetail.bank);
                this.dataSharedService.showUserName(data.userDetail.name);
            }).catch(() => {
                this.authService.logOut();
            });
        }
    }

}