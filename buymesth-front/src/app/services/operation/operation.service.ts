import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {TokenStorageService} from '../auth/token-storage.service';
import {Pageable} from '../../models/Pageable';
import {Observable} from 'rxjs';
import {Operation} from '../../models/Operation';
import {Constants} from '../../utils/Constants';
import {OperationType} from '../../models/OperationType';
import {OperationStatus} from '../../models/OperationStatus';
import {OperationStatusForm} from "../../models/OperationStatusForm";
import {BuyForm} from "../../models/BuyForm";
import {OperationTypeCount} from "../../models/OperationTypeCount";
import {Sort} from "../../models/Sort";

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class OperationService {

    constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {
    }


    getOperations(page?: Pageable, type?: OperationType, status?: OperationStatus): Observable<PageDtoResponse> {
        console.log(page);
        const params = this.getHttpParams(page, type, status);

        return this.http.get<PageDtoResponse>(Constants.Operation.ROOT,
            {headers: httpOptions.headers, params});
    }

    getUserOperations(id: number, page?: Pageable, type?: OperationType, status?: OperationStatus): Observable<PageDtoResponse> {
        const params = this.getHttpParams(page, type, status);
        return this.http.get<PageDtoResponse>(Constants.Operation.USER + id,
            {headers: httpOptions.headers, params});
    }

    changeOperationStatus(data: OperationStatusForm): Observable<Operation> {
        return this.http.put<Operation>(Constants.Operation.ROOT, data, httpOptions);
    }

    buy(data: BuyForm): Observable<Operation> {
        return this.http.post<Operation>(Constants.Operation.BUY + this.tokenStorageService.getUserID(), data, httpOptions);
    }

    deposit(amount: number): Observable<Operation> {
        return this.http.post<Operation>(Constants.Operation.DEPOSIT + this.tokenStorageService.getUserID(), amount, httpOptions);
    }

    getOperationsCountByType(): Observable<OperationTypeCount[]> {
        return this.http.get<OperationTypeCount[]>(Constants.Operation.BY_TYPE, httpOptions);
    }

    getHttpParams(page: Pageable, type: OperationType, status: OperationStatus): HttpParams {
        if (page === undefined) {
            page = Pageable.defaultPageable();
            page.sort = [new Sort('date', 'asc')];
        }
        return new HttpParams()
            .set('page', page.page.toString())
            .set('size', page.size.toString())
            .set('sort', page.getSortParamQuery())
            .set('status', status === undefined ? '' : status.toString())
            .set('type', type === undefined ? '' : type.toString());

    }
}
