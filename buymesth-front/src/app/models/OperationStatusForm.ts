import {OperationStatus} from "./OperationStatus";

export class OperationStatusForm {

    idOperation: number;
    idUser: number;
    status: OperationStatus;

    constructor(idOperation: number, idUser: number, status: OperationStatus) {
        this.idOperation = idOperation;
        this.idUser = idUser;
        this.status = status;
    }
}