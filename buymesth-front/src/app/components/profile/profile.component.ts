import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UserService} from '../../services/user/user.service';
import {MenuItem, MessageService} from 'primeng/api';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserDetails} from '../../models/UserDetails';
import {AuthService} from '../../services/auth/auth.service';
import {Router} from '@angular/router';
import {User} from '../../models/User';
import {DataShareService} from '../../services/data-share/data-share.service';
import {OperationService} from '../../services/operation/operation.service';
import {Operation} from '../../models/Operation';
import {OperationStatus} from '../../models/OperationStatus';
import {OperationType} from '../../models/OperationType';
import {Pageable} from '../../models/Pageable';
import {Sort} from "../../models/Sort";


@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css'],
    providers: [MessageService],
    encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {
    BG_COLOR = 'bg-warning';
    errorMessage: string;
    items: MenuItem[];
    activeIndex = 0;
    form: FormGroup;
    user: User;
    userDetailForm: UserDetails;
    activeItem: MenuItem;
    operations: Operation[];
    response: PageDtoResponse;
    page: Pageable;

    constructor(private datShareService: DataShareService, private fb: FormBuilder, private userService: UserService,
                private messageService: MessageService, private authService: AuthService, private router: Router,
                private operationService: OperationService) {
    }

    ngOnInit() {

        if (!this.authService.checkLoggedUser()) {
            this.router.navigateByUrl('login');
        }
        this.datShareService.changeBgColor(this.BG_COLOR);

        this.form = this.fb.group({
            name: new FormControl('', Validators.required),
            firstSurname: new FormControl('', Validators.required),
            secondSurname: new FormControl(''),
            country: new FormControl('', Validators.required),
            state: new FormControl(''),
            region: new FormControl('', Validators.required),
            province: new FormControl(''),
            postalCode: new FormControl('', Validators.required),
            street: new FormControl('', Validators.required),
            number: new FormControl(''),
            door: new FormControl('')
        });

        this.userService.getUserProfile().subscribe(data => {
            this.user = data;
            console.log(this.user);
            if (this.user.userDetail === null) {
                this.initPanelToCompleteProfile();
            } else {
                this.initPanelProfileCompleted();
                this.datShareService.showUserName(this.user.userDetail.name);
                this.datShareService.showBank(this.user.userDetail.bank);
            }
        }, error => {
            console.log(error)
            this.authService.logOut();
            this.router.navigateByUrl('login');
        });

    }

    initPanelToCompleteProfile() {
        this.items = [{
            label: 'Personal Information',
            command: (event: any) => {
                this.activeIndex = 0;

            }
        },
            {
                label: 'Address',
                command: (event: any) => {
                    this.activeIndex = 1;
                }
            },
            {
                label: 'Confirmation',
                command: (event: any) => {
                    this.activeIndex = 2;
                }
            }
        ];
    }


    initPanelProfileCompleted() {
        this.items = [
            {label: 'History', icon: 'fa fa-fw fa-bar-chart'},
            {label: 'Orders', icon: 'fa fa-fw fa-calendar'},
            {label: 'Deposits', icon: 'fa fa-fw fa-book'}
        ];
        this.activeItem = this.items[0];
    }

    next() {
        switch (this.activeIndex) {
            case 0:
                if (this.form.controls.name.valid && this.form.controls.firstSurname.valid) {
                    this.activeIndex++;
                    break;
                }
                this.message();
                break;
            case 1:
                if (this.form.controls.country.valid && this.form.controls.region.valid && this.form.controls.postalCode.valid && this.form.controls.number.valid) {
                    this.activeIndex++;
                    break;
                }
                this.message();
                break;
            case 2:
                break;
            default:
        }
    }

    previous() {
        this.activeIndex--;
    }


    getHistory($event?) {
        if ($event || this.activeIndex !== 0) {
            this.activeIndex = 0;
            this.operationService.getUserOperations(this.user.id, this.getPage($event)).subscribe(
                data => {
                    this.response = data;
                    this.operations = data.result;
                    console.log(data);
                },
                err => {
                    console.log(err.error.message);
                });
        }
    }

    getOrders($event?) {

        if ($event || this.activeIndex !== 1) {
            this.activeIndex = 1;
            this.operationService.getUserOperations(this.user.id, this.getPage($event), OperationType.BUY).subscribe(
                data => {
                    this.response = data;
                    this.operations = data.result;
                    console.log(data);
                },
                err => {
                    console.log(err.error.message);
                });
        }
    }

    getDeposits($event?) {
        if ($event || this.activeIndex !== 2) {
            this.activeIndex = 2;
            this.operationService.getUserOperations(this.user.id, this.getPage($event), OperationType.DEPOSIT).subscribe(
                data => {
                    this.response = data;
                    this.operations = data.result;
                },
                err => {
                    console.log(err.error.message);
                });
        }
    }

    message() {
        this.messageService.add({
            severity: 'warn',
            summary: 'Attention!',
            detail: 'Please, complete the required fields',
            life: 5000
        });
    }

    onSubmit() {
        this.userDetailForm = new UserDetails(this.form.controls.name.value,
            this.form.controls.firstSurname.value,
            this.form.controls.secondSurname.value,
            this.form.controls.country.value,
            this.form.controls.state.value,
            this.form.controls.region.value,
            this.form.controls.province.value,
            this.form.controls.postalCode.value,
            this.form.controls.street.value,
            this.form.controls.number.value,
            this.form.controls.door.value);

        if (this.user.userDetail === null) {
            this.userService.completeProfile(this.userDetailForm).subscribe(data => {
                this.user.userDetail = data;
                this.user.enabled = true;
            }, error => {
                console.log(error);
            });
        } else {
            this.userService.editUserProfile(this.userDetailForm).subscribe(data => {
                this.user.userDetail = data;
                this.messageService.addAll([{
                    severity: 'success',
                    summary: 'Success!',
                    detail: 'Your profile has been updated.'
                }]);
            }, error => {
                this.messageService.addAll([{
                    severity: 'error',
                    summary: 'Error',
                    detail: error.error.message
                }]);
                console.log(error);
            });
        }
    }

    hasAdminRole(): boolean {
        return this.user.roles.some(r => r.name === 'ADMIN');
    }

    getColorLabel(status: OperationStatus): string {
        switch (status) {
            case OperationStatus.PENDING:
                return 'label-info';
            case OperationStatus.COMPLETED:
                return 'label label-success';
            case OperationStatus.TRANSIT:
                return 'label label-warning';
            default:
                return 'label label-default';

        }
    }

    getColorType(type: OperationType): string {
        switch (type) {
            case OperationType.BUY:
                return 'text-danger';
            case OperationType.DEPOSIT:
                return 'text-success';
            default:
                return 'text-default';
        }
    }

    private getPage(event): Pageable {
        if (event) {
            this.page = new Pageable(event.rows, event.page);
        } else {
            this.response = undefined;
            this.page = Pageable.defaultPageable();
        }
        this.page.sort = [new Sort('date', 'desc')];
        return this.page;
    }
}
