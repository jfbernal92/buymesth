import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Constants} from '../../utils/Constants';
import {AuthService} from '../../services/auth/auth.service';
import {UserService} from '../../services/user/user.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
    selector: 'app-email-confirmation',
    templateUrl: './email-confirmation.component.html',
    styleUrls: ['./email-confirmation.component.css'],
    providers: [AuthService]
})
export class EmailConfirmationComponent implements OnInit {

    constructor(private router: Router, private fb: FormBuilder, private userService: UserService, private activatedRoute: ActivatedRoute,
                private authService: AuthService) {
    }

    confirmForm: FormGroup;
    token: string;
    d: string;
    id: string;
    valid = false;
    er = false;

    ngOnInit() {
        this.activatedRoute.paramMap.subscribe(p => {
            this.token = p.get(Constants.Params.TOKEN);
            this.d = p.get(Constants.Params.DATE);
            this.id = p.get(Constants.Params.ID);
        });
        this.confirmForm = this.fb.group({
            email: new FormControl('', Validators.compose([Validators.required, Validators.email]))
        });

        if (this.token != null && this.d != null) {
            console.log('token:' + this.token + ' d:' + this.d);
            this.authService.emailConfirmation(Constants.Main.EMAIL_CONFIRMATION + this.token + '/' +
                Constants.Params.DATE + '/' + this.d + '/' + this.id).subscribe(data => {
                console.log(data);
                setTimeout(() => {
                    this.emailConfirmed();
                }, 2000);
            }, error => {
                setTimeout(() => {
                    this.error();
                }, 2000);
                console.log(error);
            });
        }
    }

    onSubmit() {
        this.authService.reSendEmailConfirmation(this.confirmForm.controls.email.value, this.token).subscribe(data => {
            console.log(data);
        }, error => {
            console.log(error);
        });
    }

    emailConfirmed() {
        this.valid = !this.valid;
        setTimeout(() =>
            this.router.navigateByUrl('login'), 5000
        );
    }

    error() {
        this.er = !this.er;
    }

}
