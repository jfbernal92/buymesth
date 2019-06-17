const MainRoute = {
    MAIN: 'https://buymesth-back.herokuapp.com',
    USER: 'https://buymesth-back.herokuapp.com/user',
    OPERATION: 'https://buymesth-back.herokuapp.com/operation',
    PRODUCT: 'https://buymesth-back.herokuapp.com/product'
};

export const Constants = {

    Main: {
        LOGIN: MainRoute.MAIN + '/login',
        SIGNUP: MainRoute.MAIN + '/signup',
        EMAIL_CONFIRMATION: MainRoute.MAIN + '/emailconfirmation/',
        SEND_ANOTHER_LINK: MainRoute.MAIN + '/sendlink/',
        PASSWORD_FORGOTTEN: MainRoute.MAIN + '/passwordrecovery/',
    },

    User: {
        ROOT: MainRoute.USER,
        PROFILE: MainRoute.USER + '/profile/',
        OPERATIONS: MainRoute.USER + '/operations/',
        CHANGE_YOUR_PASSWORD: MainRoute.USER + '/passwordchange/',
        USER_ROLES: MainRoute.USER + '/roles/',
        USER_ALL: MainRoute.USER + '/all',
        BY_COUNTRY: MainRoute.USER + '/bycountry',
        BY_SINGUP_DATE: MainRoute.USER + '/bysignupdate'
    },

    Operation: {
        ROOT: MainRoute.OPERATION,
        USER: MainRoute.OPERATION + '/user/',
        BUY: MainRoute.OPERATION + '/buy/',
        DEPOSIT: MainRoute.OPERATION + '/deposit/',
        BY_TYPE: MainRoute.OPERATION + '/bytype'
    },

    Product: {
        ROOT: MainRoute.PRODUCT,
        BY_CATEGORY: MainRoute.PRODUCT + '/bycategory',
        SEARCH: MainRoute.PRODUCT + '/search'
    },

    Params: {
        TOKEN: 'token',
        DATE: 'd',
        ID: 'id'
    }
};

