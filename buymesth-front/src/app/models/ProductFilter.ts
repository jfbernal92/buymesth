import {Category} from "./Category";

export class ProductFilter {

    maxPrice: number;
    minPrice: number;
    categories: Category[];


    constructor(maxPrice: number, minPrice: number, categories: Category[]) {
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.categories = categories;
    }
}