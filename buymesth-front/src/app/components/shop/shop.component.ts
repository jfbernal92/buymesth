import {Component, OnInit} from '@angular/core';
import {Category} from "../../models/Category";
import {UserService} from "../../services/user/user.service";
import {MessageService} from "primeng/api";
import {OperationService} from "../../services/operation/operation.service";
import {ProductService} from "../../services/product/product.service";
import {User} from "../../models/User";
import {DataShareService} from "../../services/data-share/data-share.service";
import {ProductFilter} from "../../models/ProductFilter";
import {BuyForm} from "../../models/BuyForm";
import {Product} from "../../models/Product";

@Component({
    selector: 'app-buy',
    templateUrl: './shop.component.html',
    styleUrls: ['./shop.component.css'],
    providers: [MessageService]
})
export class ShopComponent implements OnInit {

    categories: Category[] = [];
    rangeValues = [0.10];
    visible: boolean = false;
    user: User;
    BG_COLOR = 'bg-primary';
    submitted: boolean;

    VISIBLE_BUY = 'You have just bought: ';
    NO_VISIBLE_BUY = 'You decided to keep the hype! Enjoy! ';


    constructor(private userService: UserService, private operationService: OperationService, private productService: ProductService,
                private messageService: MessageService, private dataSharedService: DataShareService) {
    }

    ngOnInit() {
        this.getUserData(true);
    }


    getUserData(init?: boolean) {
        this.userService.getUserProfile().toPromise()
            .then(data => {
                this.user = data;
                if (init) {
                    this.dataSharedService.showBank(this.user.userDetail.bank);
                    this.dataSharedService.showUserName(this.user.userDetail.name);
                }
                this.dataSharedService.changeBgColor(this.BG_COLOR);
                if (this.rangeValues.length == 1) {
                    this.rangeValues.push(this.user.userDetail.bank)
                }
            })
            .catch(() => {
                this.messageService.addAll([{
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Error retrieving user information'
                }])
            })
    }

    changeSwitch(event, category) {
        let cat = this.categories;
        event.checked ? cat.push(category) : cat = this.categories.filter(cat => cat !== category);
        this.categories = cat;
        console.log(this.categories)
    }

    buy() {
        if (this.categories.length == 0) {
            this.messageService.addAll([{
                severity: 'warn',
                summary: 'Attention',
                detail: 'At least one category must be selected',
                life: 7500
            }])
        } else {
            let selectedProduct: Product;
            this.submitted = true;
            let filter = new ProductFilter(this.rangeValues[1], this.rangeValues[0], this.categories);
            this.productService.searchProduct(filter).toPromise().then(res => {
                selectedProduct = res[Math.floor(Math.random() * res.length)];
                this.operationService.buy(new BuyForm(this.user.id, selectedProduct.idProduct, !this.visible)).toPromise()
                    .then(() => {
                        this.getUserData();
                    }).catch(error => {
                    console.log(error)
                })

            }).catch(err => {

            });

            setTimeout(() => {
                this.submitted = false;
                if (selectedProduct) {
                    this.dataSharedService.showBank(this.user.userDetail.bank);
                    this.rangeValues[1] = this.user.userDetail.bank;
                    this.messageService.addAll([{
                        severity: 'success',
                        summary: 'Congrats!',
                        detail: !this.visible ? this.VISIBLE_BUY + selectedProduct.description : this.NO_VISIBLE_BUY,
                        sticky: true,
                        closable: true
                    }])
                } else {
                    this.messageService.addAll([{
                        severity: 'warn',
                        summary: 'Sorry',
                        detail: 'There is no product matching with that filter',
                        life: 5000
                    }])
                }
            }, 3000)
        }

    }
}
