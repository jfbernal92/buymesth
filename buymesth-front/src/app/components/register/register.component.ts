import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {LoginFormData} from "../../models/LoginFormData";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {DataShareService} from "../../services/data-share/data-share.service";
import {MessageService} from "primeng/api";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css'],
    providers: [MessageService]
})
export class RegisterComponent implements OnInit {
    BG_COLOR = 'bg-info';
    form: FormGroup;
    signupInfo: LoginFormData;
    submitted = false;
    spinner = false;

    constructor(private authService: AuthService, private fb: FormBuilder, private router: Router, private dataSharedService: DataShareService, private messageService: MessageService) {
        if (this.authService.checkLoggedUser()) {
            this.router.navigateByUrl('profile');
        }
        this.dataSharedService.changeBgColor(this.BG_COLOR);
        this.form = this.fb.group({
            email: new FormControl('', Validators.compose([Validators.required, Validators.email])),
            password: new FormControl('', Validators.compose([Validators.required, Validators.minLength(6)])),
        });
    }

    ngOnInit() {
    }

    onSubmit() {
        this.submitted = true;
        if (this.form.valid) {
            this.spinner = true;
            console.log(this.form);
            this.signupInfo = new LoginFormData(this.form.controls.email.value, this.form.controls.password.value);
            this.authService.signUp(this.signupInfo).subscribe(
                () => {
                    this.alert('success', 'Congrats', 'Your account has been created, please check your email to activate it.');
                    this.spinner = false;
                },
                error => {
                    this.submitted = false;
                    this.spinner = false;
                    if (error.status === 500) {
                        this.alert('error', 'Error', 'Internal Server Error');
                    }
                    if (error.status === 409) {
                        this.alert('warn', '', 'This email is already registered');
                    }
                }
            );
        }
    }


    alert(severity: string, summary: string, message: string) {
        this.messageService.addAll([{
            severity: severity,
            summary: summary,
            detail: message,
            sticky: severity !== 'success',
            closable: severity !== 'success'
        }])
    }
}