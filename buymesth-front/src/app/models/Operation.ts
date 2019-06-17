import {Product} from './Product';
import {OperationType} from './OperationType';
import {OperationStatus} from './OperationStatus';

export class Operation {

    idUser: number;
    idOperation: number;
    date: Date;
    amount: number;
    type: OperationType;
    status: OperationStatus;
    product: Product;
    visible: boolean;

    constructor(idUser: number, idOperation: number, date: Date, amount: number, type: OperationType,
                status: OperationStatus, product: Product, visible: boolean) {
        this.idUser = idUser;
        this.idOperation = idOperation;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.product = product;
        this.visible = visible;
    }
}

