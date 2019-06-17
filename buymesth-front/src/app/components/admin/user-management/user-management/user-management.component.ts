import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../../services/user/user.service';
import {Pageable} from '../../../../models/Pageable';
import {Sort} from '../../../../models/Sort';
import {User} from '../../../../models/User';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MessageService} from "primeng/api";


@Component({
    selector: 'app-user-management',
    templateUrl: './user-management.component.html',
    styleUrls: ['./user-management.component.css'],
    providers: [MessageService]
})
export class UserManagementComponent implements OnInit {

    response: PageDtoResponse;
    pageable = Pageable.defaultPageable();
    NAME = 'userDetail.name';
    EMAIL = 'email';
    SIGNUP_DATE = 'signupDate';
    BANK = 'userDetail.bank';
    ENABLED = 'enabled';
    LOCKED = 'locked';
    activeUser: User;
    activeIndexUser: number;
    lastElement: Element;
    form: FormGroup;


    constructor(private userService: UserService, private fb: FormBuilder, private messageService: MessageService) {
    }

    ngOnInit() {
        this.getUsers(this.pageable);

        this.form = this.fb.group({
            country: new FormControl('', Validators.required),
            state: new FormControl(),
            region: new FormControl('', Validators.required),
            province: new FormControl(),
            postalCode: new FormControl('', Validators.required),
            street: new FormControl('', Validators.required),
            number: new FormControl(),
            door: new FormControl()
        });
    }

    order(property: string) {
        if (this.pageable.sort === undefined || this.pageable.sort[0].property !== property) {
            this.pageable.sort = [new Sort(property)];
        } else {
            this.pageable.sort[0].changeDirection();
        }
        this.getPage();
    }

    getUsers(pageable: Pageable) {
        this.userService.getUsers(pageable).toPromise().then(data => {
            this.response = data;
            this.activeUser = this.response.result[0];
            this.changeStatusFormControl(this.activeUser.enabled);
        }, error => {
            this.messageService.addAll([{
                severity: 'error',
                summary: 'Error!',
                detail: error.error.message
            }])
        })
    }

    getPage(event?) {
        if (event) {
            this.pageable.size = event.rows;
            this.pageable.page = event.page;
        }
        this.getUsers(this.pageable);
    }

    viewRow(rowIndex, row) {
        this.activeIndexUser = rowIndex;
        if (rowIndex !== 0) {
            let e = document.getElementById('first');
            e.classList.remove('bg-hover')
        }
        if (this.lastElement !== undefined && this.lastElement !== row.path[1]) {
            this.lastElement.classList.remove('bg-hover');
        }
        this.lastElement = row.path[1];
        this.lastElement.classList.add('bg-hover');
        this.activeUser = this.response.result[rowIndex];
        this.changeStatusFormControl(this.activeUser.enabled);


    }

    onSubmit() {
        this.activeUser.userDetail.country = this.form.controls.country.value;
        this.activeUser.userDetail.state = this.form.controls.state.value;
        this.activeUser.userDetail.region = this.form.controls.region.value;
        this.activeUser.userDetail.province = this.form.controls.province.value;
        this.activeUser.userDetail.postalCode = this.form.controls.postalCode.value;
        this.activeUser.userDetail.street = this.form.controls.street.value;
        this.activeUser.userDetail.number = this.form.controls.number.value;
        this.activeUser.userDetail.door = this.form.controls.door.value;

        console.log('valores a guardar');
        console.log(this.activeUser);
        this.userService.editUserProfile(this.activeUser.userDetail).subscribe(data => {
            this.activeUser.userDetail = data;
            this.response.result[this.activeIndexUser] = this.activeUser;
            console.log('respuesta individual');
            console.log(this.activeUser);
            console.log('actualizado en respuesta colectiva');
            console.log(this.response.result[this.activeIndexUser]);
            console.log('resultado entero');
            console.log(this.response.result);
        }, error => {
            this.messageService.addAll([{
                severity: 'error',
                summary: 'Error!',
                detail: error.error.message
            }])
        });
    }

    delete() {
        this.userService.deleteUser(this.activeUser.id).toPromise().then(() => {
            this.messageService.addAll([{
                severity: 'success',
                summary: 'Success!',
                detail: 'User removed succesfully'
            }])
        }, error => {
            this.messageService.addAll([{
                severity: 'error',
                summary: 'Error!',
                detail: error.error.message
            }])
        })
    }

    changeStatusFormControl(enable: boolean) {
        if (enable) {
            this.form.patchValue(this.activeUser.userDetail);
            this.form.controls.country.enable();
            this.form.controls.state.enable();
            this.form.controls.region.enable();
            this.form.controls.province.enable();
            this.form.controls.postalCode.enable();
            this.form.controls.street.enable();
            this.form.controls.number.enable();
            this.form.controls.door.enable();
        } else {
            this.form.reset();
            this.form.controls.country.disable();
            this.form.controls.state.disable();
            this.form.controls.region.disable();
            this.form.controls.province.disable();
            this.form.controls.postalCode.disable();
            this.form.controls.street.disable();
            this.form.controls.number.disable();
            this.form.controls.door.disable();
        }

    }
}
