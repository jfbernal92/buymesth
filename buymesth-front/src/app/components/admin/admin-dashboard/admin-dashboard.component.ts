import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../services/user/user.service";
import {UserCount} from "../../../models/UserCount";
import {Category} from "../../../models/Category";
import {ProductService} from "../../../services/product/product.service";
import {MessageService} from "primeng/api";

@Component({
    selector: 'app-admin-dashboard',
    templateUrl: './admin-dashboard.component.html',
    styleUrls: ['./admin-dashboard.component.css'],
    providers: [MessageService]
})
export class AdminDashboardComponent implements OnInit {

    constructor(private userService: UserService, private productService: ProductService, private messageService: MessageService) {
    }

    userCount = new UserCount(0, 0, 0,);
    userSignup: any;
    userCountry: any;
    preferredCategories: any;
    dateCount: Date = new Date();
    dateSignup: Date = new Date();
    dateCountry: Date = new Date();
    dateCategory: Date = new Date();

    ngOnInit() {
        this.getUserCount();
        this.getUsersBySingup();
        this.getUsersByCountry();
        this.getProductsByCategory();
    }

    getUserCount() {
        this.userService.getUserCount().toPromise().then(result => {
            this.userCount = result;
            this.dateCount = new Date();
        }).catch(() =>
            this.messageService.addAll([{
                severity: 'warning',
                summary: 'Warning!',
                detail: 'Could not retrieve info for user stats'
            }]));
    }

    getUsersBySingup() {
        let labels = [];
        let data = [];
        this.userService.getUserCountBySignupDate().toPromise().then(res => {
            for (let key in res) {
                labels.push(key);
                data.push(res[key]);
            }
            this.userSignup = {
                labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                datasets: [
                    {
                        label: 'New accounts',
                        data: data,
                        fill: false,
                        borderColor: '#4bc0c0',
                        backgroundColor: '#4bc0c0'
                    }
                ]
            };
            this.dateSignup = new Date();
        }, () => {
            this.messageService.addAll([{
                severity: 'error',
                summary: 'Error',
                detail: 'Error retrieving users by sign up date'
            }])
        })
    }

    getUsersByCountry() {
        let labels = [];
        let data = [];
        let backgroundColor = "#FF6384 #36A2EB #FFCE56";//TODO AGREGAR SUFICIENTES COLORES
        let hoverBackgroundColor = "#FF6384 #36A2EB #FFCE56";

        this.userService.getUserCountByCountry().toPromise().then(res => {
            for (let key in res) {
                labels.push(key);
                data.push(res[key]);
            }
            this.userCountry = {
                labels: labels,
                datasets: [{
                    data: data,
                    backgroundColor: backgroundColor.split(' ', data.length),
                    hoverBackgroundColor: hoverBackgroundColor.split(' ', data.length)
                }]
            };
            this.dateCountry = new Date();
        }, () => {
            this.messageService.addAll([{
                severity: 'error',
                summary: 'Error',
                detail: 'Error retrieving users by country'
            }])
        });


    }

    getProductsByCategory() {
        let labels = [Category.HOME, Category.KITCHEN, Category.OTHER, Category.OUTDOOR, Category.STATIONERY, Category.TECHNOLOGY];
        let data = [0, 0, 0, 0, 0, 0];

        this.productService.getProductCountByCategory().toPromise().then(res => {
            for (let key in res) {
                switch (key) {
                    case Category.HOME:
                        data[0] = res[key];
                        break;
                    case Category.KITCHEN:
                        data[1] = res[key];
                        break;
                    case Category.OTHER:
                        data[2] = res[key];
                        break;
                    case Category.OUTDOOR:
                        data[3] = res[key];
                        break;
                    case Category.STATIONERY:
                        data[4] = res[key];
                        break;
                    case Category.TECHNOLOGY:
                        data[5] = res[key];
                        break;
                }
            }
            this.preferredCategories = {
                labels: labels,
                datasets: [
                    {
                        label: 'Buys',
                        backgroundColor: 'rgba(255,99,132,0.2)',
                        borderColor: 'rgba(255,99,132,1)',
                        pointBackgroundColor: 'rgba(255,99,132,1)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgba(255,99,132,1)',
                        data: data
                    }
                ]
            };
            this.dateCategory = new Date();
        }, () => {
            this.messageService.addAll([{
                severity: 'error',
                summary: 'Error',
                detail: 'Error retrieving products by category'
            }])
        });

    }

}
