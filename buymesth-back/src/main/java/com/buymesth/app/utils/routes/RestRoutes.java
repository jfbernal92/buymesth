package com.buymesth.app.utils.routes;

import java.util.Arrays;
import java.util.List;

public class RestRoutes {


    public static final String EMAIL_TEMPLATE = "/emailconfirmation/";

    public static final class Main {
        public static final String URL = "/";
        public static final String LOGIN = "/login";
        public static final String SIGNUP = "/signup";
        public static final String EMAIL_CONFIRMATION = "/emailconfirmation/{token}/d/{d}/{id}";
        public static final String SEND_ANOTHER_LINK = "/sendlink/{token}";
        public static final String PASSWORD_FORGOTTEN_POST_RECOVER = "/passwordrecovery/{token}/d/{d}/{id}";
        public static final String PASSWORD_FORGOTTEN_POST = "/passwordrecovery/";
    }

    public static final class User {
        public static final String URL = "/user";
        public static final String USER_PROFILE = "/profile/{id}";
        public static final String USER_CHANGE_YOUR_PASSWORD = "/passwordchange/{id}";
        public static final String USER_ROLES = "/roles/{id}";
        public static final String USER_ALL = "/all";
        public static final String USER_BY_COUNTRY = "/bycountry";
        public static final String USER_BY_SINGUP_DATE = "/bysignupdate";
    }


    public static final class Operation {
        public static final String URL = "/operation";
        public static final String BUY = "/buy/{id}";
        public static final String DEPOSIT = "/deposit/{id}";
        public static final String OPERATION = "/{id}";
        public static final String USER = "/user/{id}";
        public static final String OPERATION_BY_TYPE = "/bytype";

    }

    public static final class Product {
        public static final String URL = "/product";
        public static final String PRODUCT = "/{id}";
        public static final String SEARCH = "/search";
        public static final String PRODUCT_BY_CATEGORY = "/bycategory";
    }

    private static List<String> whiteList = Arrays.asList(
            Main.SIGNUP,
            Main.EMAIL_CONFIRMATION,
            Main.LOGIN,
            Main.SEND_ANOTHER_LINK,
            Main.PASSWORD_FORGOTTEN_POST_RECOVER,
            Main.PASSWORD_FORGOTTEN_POST);

    public static String[] getWhiteList() {
        return whiteList.toArray(new String[0]);
    }


}
