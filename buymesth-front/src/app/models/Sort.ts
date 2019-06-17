export class Sort {

    direction: string;
    property: string;

    constructor(property: string, direction?: string) {
        this.direction = direction === undefined ? 'asc' : direction;
        this.property = property;
    }

    changeDirection() {
        this.direction = this.direction === 'asc' ? 'desc' : 'asc';
    }

}