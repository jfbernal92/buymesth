import {Component, OnInit} from '@angular/core';
import {OperationService} from "../../../../services/operation/operation.service";
import {MessageService} from "primeng/api";
import {Operation} from "../../../../models/Operation";
import {OperationStatus} from "../../../../models/OperationStatus";
import {Pageable} from "../../../../models/Pageable";
import {OperationType} from "../../../../models/OperationType";
import {OperationStatusForm} from "../../../../models/OperationStatusForm";
import {Sort} from "../../../../models/Sort";

@Component({
    selector: 'app-operation-management',
    templateUrl: './operation-management.component.html',
    styleUrls: ['./operation-management.component.css'],
    providers: [MessageService]
})
export class OperationManagementComponent implements OnInit {

    operations: Operation[];
    selectedOperation: Operation;
    response: PageDtoResponse;
    selectedType: string[] = [OperationType.BUY, OperationType.DEPOSIT];
    selectedStatus: string[] = [OperationStatus.COMPLETED, OperationStatus.PENDING, OperationStatus.TRANSIT];
    page = Pageable.defaultPageable();
    display = false;
    availableStatus = [];
    newStatus: OperationStatus;
    operationsByType: any;
    date: Date = new Date();


    constructor(private operationService: OperationService, private messageService: MessageService) {
    }

    ngOnInit() {
        this.page.sort = [new Sort('date')];
        this.getOperations(this.page);
        this.getOperationCountByType();
    }

    getOperationCountByType() {
        let labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
        let dataBuy = [];
        let buyAdd = false;
        let depositAdd = false;
        let dataDeposit = [];
        let date = new Date();
        this.operationService.getOperationsCountByType().toPromise().then(res => {
            for (let i = 0; i <= date.getMonth(); i++) {
                res.forEach(o => {
                    if (o.month === labels[i]) {
                        switch (o.type) {
                            case OperationType.BUY:
                                dataBuy.push(o.total);
                                buyAdd = true;
                                break;
                            case OperationType.DEPOSIT:
                                dataDeposit.push(o.total);
                                depositAdd = true;
                                break;
                        }
                    }
                });
                if (!buyAdd) {
                    dataBuy.push(0);
                }
                if (!depositAdd) {
                    dataDeposit.push(0)
                }
                buyAdd = false;
                depositAdd = false;
            }

            this.operationsByType = {
                labels: labels,
                datasets: [
                    {
                        label: 'Buys',
                        backgroundColor: '#42A5F5',
                        borderColor: '#1E88E5',
                        data: dataBuy
                    },
                    {
                        label: 'Deposits',
                        backgroundColor: '#9CCC65',
                        borderColor: '#7CB342',
                        data: dataDeposit
                    }
                ]
            };
            this.date = new Date();
        }).catch(() => {
            this.messageService.addAll([{
                severity: "warn",
                summary: "Warning!",
                detail: "Error retrieving operation stats"
            }]);
        })
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

    changeStatus() {

        if (this.newStatus === undefined || this.newStatus === null) {
            this.messageService.addAll([{
                severity: "warn",
                summary: "Warning!",
                detail: "You must select a status"
            }]);
            return;
        }
        console.log(this.newStatus);

        this.display = false;

        this.operationService.changeOperationStatus(new OperationStatusForm(this.selectedOperation.idOperation, this.selectedOperation.idUser, this.newStatus)).subscribe(data => {
            this.messageService.addAll([{
                severity: "success",
                summary: "Success",
                detail: 'Operation status changed successfully'
            }]);
            this.getPage();
        }, error => {
            this.messageService.addAll([{
                severity: "error",
                summary: "Error",
                detail: error.error.message
            }]);
        })
    }

    getPage(event?) {
        if (event) {
            this.page.size = event.rows;
            this.page.page = event.page;
        }
        this.search();
    }

    search() {
        let type;
        let status;
        if (this.selectedType.length === 1) {
            type = this.selectedType[0];
        }
        if (this.selectedStatus.length === 1) {
            status = this.selectedStatus[0];
        }

        if (this.selectedStatus.length === 2) {
            this.messageService.addAll([{
                severity: "warn",
                summary: "Warning!",
                detail: "Only one or all status can be selected at the same time",
                closable: true,
                life: 8000
            }]);
            return;
        }
        this.getOperations(this.page, type, status);
    }

    showDialog(operation: Operation) {
        this.newStatus = null;
        if (operation.status !== OperationStatus.COMPLETED) {
            this.availableStatus = [];
            this.availableStatus.push({label: 'Select Status', value: null});
            if (operation.type === OperationType.DEPOSIT) {
                this.availableStatus.push({label: OperationStatus.COMPLETED, value: OperationStatus.COMPLETED})
            } else {
                Object.keys(OperationStatus).map(f => {
                    if (f !== operation.status) {
                        this.availableStatus.push({label: f, value: f});
                    }
                });
            }
            this.selectedOperation = operation;
            this.display = true;
        }
    }

    getOperations(page?: Pageable, type?: OperationType, status?: OperationStatus) {
        this.operationService.getOperations(page, type, status).toPromise().then(data => {
            this.response = data;
            this.operations = data.result;
            console.log(data)
        })
    }
}
