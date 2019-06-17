import {Category} from "./Category";

export class Product {

    idProduct: number;
    description: string;
    price: number;
    units: number;
    category: Category;


    constructor(idProduct: number, description: string, price: number, units: number, category: Category) {
        this.idProduct = idProduct;
        this.description = description;
        this.price = price;
        this.units = units;
        this.category = category;
    }

}
