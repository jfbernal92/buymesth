export class BuyForm {

    userId: number;
    productId: number;
    visible: boolean;

    constructor(userId: number, productId: number, visible: boolean) {
        this.userId = userId;
        this.productId = productId;
        this.visible = visible;
    }
}