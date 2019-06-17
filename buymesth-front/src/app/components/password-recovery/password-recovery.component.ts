import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user/user.service";
import {AuthService} from "../../services/auth/auth.service";
import {Constants} from "../../utils/Constants";
import {DataShareService} from "../../services/data-share/data-share.service";
import {MessageService} from "primeng/api";

@Component({
    selector: 'app-password-recovery',
    templateUrl: './password-recovery.component.html',
    styleUrls: ['./password-recovery.component.css'],
    providers: [MessageService]
})
export class PasswordRecoveryComponent implements OnInit {

    BG_COLOR = 'bg-default';

    constructor(private router: Router, private fb: FormBuilder, private userService: UserService, private activatedRoute: ActivatedRoute,
                private authService: AuthService, private dataSharedService: DataShareService, private messageService: MessageService) {
    }

    form: FormGroup;
    token: string;
    d: string;
    id: string;
    fullParams = false;
    submitted: boolean = false;

    ngOnInit() {

        this.dataSharedService.changeBgColor(this.BG_COLOR);

        this.activatedRoute.paramMap.subscribe(p => {
            this.token = p.get(Constants.Params.TOKEN);
            this.d = p.get(Constants.Params.DATE);
            this.id = p.get(Constants.Params.ID);
        });
        this.form = this.fb.group({
            email: new FormControl('', Validators.compose([Validators.required, Validators.email])),
            password: new FormControl('', Validators.compose([Validators.required, Validators.minLength(6)])),
            password2: new FormControl('', Validators.compose([Validators.required, Validators.minLength(6)]))
        });
        if (this.token != null && this.d != null) {
            this.fullParams = true;
        }
    }

    onSubmit() {
        this.submitted = true;
        if (this.fullParams) {
            //Modifify password
            this.authService.passwordRecover(Constants.Main.PASSWORD_FORGOTTEN + this.token + '/' +
                Constants.Params.DATE + '/' + this.d + '/' + this.id, this.form.controls.password.value, this.form.controls.password2.value).subscribe(data => {
                console.log(data);
                this.messageService.addAll([{
                    severity: 'success',
                    detail: 'Your password has been changed sucessfuly',
                    sticky: true,
                    closable: true
                }]);
            }, error => {
                this.messageService.addAll([{
                    severity: 'warn',
                    detail: error.error.message
                }])
            });
        } else {
            //Send link to email
            this.authService.passwordForgotten(this.form.controls.email.value).subscribe(data => {
                this.messageService.addAll([{
                    severity: 'success',
                    detail: 'An email has been sent to restore your password',
                    sticky: true,
                    closable: true
                }])
            }, error => {
                this.messageService.addAll([{
                    severity: 'warn',
                    detail: error.error.message
                }])
            });
        }
        this.submitted = false;

    }


}
