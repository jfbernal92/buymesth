import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import {LoginFormData} from '../../models/LoginFormData';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MessageService} from 'primeng/api';
import {DataShareService} from "../../services/data-share/data-share.service";


@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    providers: [MessageService]
})
export class LoginComponent implements OnInit {

    BG_COLOR = 'bg-success';
    errorMessage = '';
    loginInfo: LoginFormData;
    loginForm: FormGroup;
    submitted: boolean;


    constructor(private router: Router, private fb: FormBuilder, private authService: AuthService, private messageService: MessageService, private dataSharedService: DataShareService) {
    }

    ngOnInit() {

        if (this.authService.checkLoggedUser()) {
            this.router.navigateByUrl('profile');
        }
        this.dataSharedService.changeBgColor(this.BG_COLOR);
        this.loginForm = this.fb.group({
            email: new FormControl('', Validators.compose([Validators.required, Validators.email])),
            password: new FormControl('', Validators.compose([Validators.required, Validators.minLength(6)])),
        });

    }

    onSubmit() {
        if (this.loginForm.valid) {
            this.submitted = true;

            this.loginInfo = new LoginFormData(
                this.loginForm.controls.email.value,
                this.loginForm.controls.password.value);

            this.authService.login(this.loginInfo).subscribe(
                data => {
                    this.authService.saveToken(data);
                    this.router.navigateByUrl('profile');
                },
                error => {
                    this.submitted = false;
                    let severity = 'warn';
                    switch (error.status) {
                        case 400:
                            this.errorMessage = error.error.message;
                            break;
                        case 401:
                            this.errorMessage = 'Your account has not been activated yet';
                            break;
                        case 403:
                            this.errorMessage = 'Your account has been blocked. Please contact with de administrator';
                            severity = 'error';
                            break;
                        case 404:
                            this.errorMessage = 'Incorrect email or password';
                            break;
                    }
                    this.messageService.add({severity: severity, summary: 'Attention!', detail: this.errorMessage});
                }
            );
        } else {
            this.messageService.add({severity: 'error', summary: 'Attention!', detail: 'complete the fields'});
        }
    }
}
