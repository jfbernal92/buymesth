import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../../../services/product/product.service";
import {Product} from "../../../../models/Product";
import {MessageService} from "primeng/api";
import {Pageable} from "../../../../models/Pageable";
import {Sort} from "../../../../models/Sort";
import {Category} from "../../../../models/Category";

@Component({
    selector: 'app-product-management',
    templateUrl: './product-management.component.html',
    styleUrls: ['./product-management.component.css'],
    providers: [MessageService]
})
export class ProductManagementComponent implements OnInit {

    products: Product[];
    productsInitial = [];
    edits = [];
    newProduct = new Product(null, null, null, null, null);
    displayDialog: boolean;
    cols: any[];
    response: PageDtoResponse;
    clonedProducts: { [s: string]: Product; } = {};
    categories: Category[] = [Category.TECHNOLOGY, Category.STATIONERY, Category.OUTDOOR, Category.OTHER, Category.KITCHEN, Category.HOME];

    constructor(private productService: ProductService, private messageService: MessageService) {
    }

    //TODO: AÑADIR DROPDOWN PARA CATEGORY, RESUMEN DE PRODUCTOS ACTUALIZADOS

    ngOnInit() {
        this.retrieveData();
        this.cols = [
            {field: 'description', header: 'Description'},
            {field: 'category', header: 'Category'},
            {field: 'price', header: 'Price'},
            {field: 'units', header: 'Units'}
        ];
    }

    retrieveData(page?: Pageable) {
        this.productService.getProducts(page).toPromise().then(response => {
            this.productsInitial = [];
            this.products = response.result;
            response.result.map(p => this.productsInitial.push(Object.assign({}, p)));
            this.response = response;
        });
    }

    onRowEditInit(product: Product) {
        this.clonedProducts[product.idProduct] = {...product};
    }

    isEnterPressed($event, rowData: Product) {
        if ($event.key === 'Enter') {
            this.onRowEditInit(rowData);
        }
    }

    saveChanges(flag?: boolean) {
        this.edits = [];
        this.productsInitial.forEach(f => {
            if (!this.isEqual(f, this.clonedProducts[f.idProduct])) {
                this.edits.push(this.clonedProducts[f.idProduct]);
                console.log("Product modified");
            }
        });
        console.log(this.edits);
        if (flag && this.edits.length > 0) {
            this.productService.createProduct(this.edits).toPromise().then(res => {
                this.retrieveData();
                this.messageService.addAll([{
                    severity: 'success',
                    summary: 'Success!',
                    detail: res.rows + ' products has been updated'
                }])
            }, err => {
                this.messageService.addAll([{
                    severity: 'error',
                    summary: 'Error',
                    detail: err.error.message
                }])
            })
        }

    }

    getPage(event) {
        if (event) {
            this.retrieveData(new Pageable(event.rows, event.page, [new Sort('description')]));
        }

    }

    showDialogToAdd() {
        this.displayDialog = true;
    }

    save() {
        this.clear();
        if (this.notNull(this.newProduct)) {
            this.productService.createProduct([this.newProduct]).toPromise().then(res => {
                this.retrieveData();
                this.messageService.addAll([{
                    severity: 'success',
                    summary: 'Success!',
                    detail: res.rows + ' products has been added!'
                }])
            }, err => {
                this.messageService.addAll([{
                    severity: 'error',
                    summary: 'Error',
                    detail: err.error.message
                }])
            })
        }
    }

    clear() {
        this.newProduct.description = null;
        this.newProduct.price = null;
        this.newProduct.units = null;
        this.newProduct.category = null;
    }

    notNull(p: Product): boolean {
        return p.price !== null || p.units !== null || p.description !== null || p.category !== null;
    }

    isEqual(p1: Product, p2: Product): boolean {
        if (p1 === undefined || p2 === undefined)
            return true;
        return p1.idProduct === p2.idProduct && p1.description === p2.description && p1.units === p2.units && p1.price === p2.price && p1.category === p2.category;
    }


}
