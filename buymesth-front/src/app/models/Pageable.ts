import {Sort} from "./Sort";

export class Pageable {

    size: number;
    page: number;
    sort: Sort[];


    constructor(size: number, page: number, sort?: Sort[]) {
        this.size = size;
        this.page = page;
        this.sort = sort;
    }

    static defaultPageable(): Pageable {
        return new Pageable(10, 0);
    }

    getUrlQuery() {
        return '?page=' + this.page + '&size=' + this.size + this.getSortQuery();
    }

    private getSortQuery(): string {
        const sort = '&sort=';
        const order = '%2C';
        if (this.sort === null || this.sort === undefined) {
            return '';
        }
        let params = '';
        this.sort.forEach(s => {
            params += sort + s.property + order + s.direction;
        });
        return params;
    }

    getSortParamQuery(): string {
        const order = ',';
        const sort = '&sort=';
        if (this.sort === null || this.sort === undefined) {
            return '';
        }
        let params = '';
        this.sort.forEach((s, i) => {
            params += i !== 0 ? sort : '' + s.property + order + s.direction;
        });
        return params;
    }
}

