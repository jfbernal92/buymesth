import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {TokenStorageService} from "../auth/token-storage.service";
import {Observable} from "rxjs";
import {Pageable} from "../../models/Pageable";
import {Constants} from "../../utils/Constants";
import {Product} from "../../models/Product";
import {Sort} from "../../models/Sort";

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};


@Injectable({
    providedIn: 'root'
})
export class ProductService {

    constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {
    }

    getProducts(page?: Pageable): Observable<PageDtoResponse> {
        const params = this.getHttpParams(page);
        return this.http.get<PageDtoResponse>(Constants.Product.ROOT, {headers: httpOptions.headers, params});
    }

    createProduct(products: Product[]): Observable<PageDtoResponse> {
        return this.http.post<PageDtoResponse>(Constants.Product.ROOT, products, httpOptions);
    }

    getProduct(id: number): Observable<Product> {
        return this.http.get<Product>(Constants.Product.ROOT + '/' + id, httpOptions);
    }

    editProduct(id: number, product: Product): Observable<Product> {
        return this.http.put<Product>(Constants.Product.ROOT + '/' + id, product, httpOptions)
    }

    delete(id: number): Observable<any> {
        return this.http.delete(Constants.Product.ROOT + '/' + id, httpOptions);
    }

    getProductCountByCategory(): Observable<Map<string, number>> {
        return this.http.get<Map<string, number>>(Constants.Product.BY_CATEGORY, httpOptions);
    }

    searchProduct(filter: any): Observable<any> {
        let params = new HttpParams({fromObject: filter});
        return this.http.get<any>(Constants.Product.SEARCH, {
            headers: httpOptions.headers,
            params: params
        })
    }


    getHttpParams(page: Pageable): HttpParams {
        if (page === undefined) {
            page = Pageable.defaultPageable();
            page.sort = [new Sort('description')];
        }
        return new HttpParams()
            .set('page', page.page.toString())
            .set('size', page.size.toString())
            .set('sort', page.sort[0].property);
    }
}
