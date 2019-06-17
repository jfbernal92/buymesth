package com.buymesth.app.mail;

import com.buymesth.app.utils.routes.RestRoutes;
import lombok.Data;

@Data
public class EmailConfirmationTemplate {
    private String token;
    private String date;
    private String id;

    public EmailConfirmationTemplate(String token, String date, String id) {
        this.token=token;
        this.date=date;
        this.id=id;
    }

    public String getTemplate(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <style>\n" +
                "        .button {\n" +
                "            background-color: #008CBA;\n" +
                "            border: none;\n" +
                "            color: white;\n" +
                "            padding: 15px 32px;\n" +
                "            text-align: center;\n" +
                "            text-decoration: none;\n" +
                "            display: inline-block;\n" +
                "            font-size: 16px;\n" +
                "        }\n" +
                "        body{\n" +
                "            font-family: sans-serif;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Welcome to buymesth.com!</h1>\n" +
                "    <p>We are glad that you have chosen to use our portal to never think about a gift again :)\n" +
                "        <br><br>Please confirm your account by clicking on the button or by copying the URL in the browser:\n" +
                "        <br><br>https://buymesth.herokuapp.com"+ RestRoutes.EMAIL_TEMPLATE+this.token+"/d/"+this.date+"/"+this.id+
                "    </p>\n" +
                "    <a class=\"button\"href=\"https://buymesth.herokuapp.com"+ RestRoutes.EMAIL_TEMPLATE+this.token+"/d/"+this.date+"/"+this.id+" \">Confirm your Account</a>\n" +
                "</body>\n" +
                "</html>";
    }

    public String getTemplateAnotherLink(){
       return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <style>\n" +
                "        .button {\n" +
                "            background-color: #008CBA;\n" +
                "            border: none;\n" +
                "            color: white;\n" +
                "            padding: 15px 32px;\n" +
                "            text-align: center;\n" +
                "            text-decoration: none;\n" +
                "            display: inline-block;\n" +
                "            font-size: 16px;\n" +
                "        }\n" +
                "        body{\n" +
                "            font-family: sans-serif;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>You have request a new confirmation link.</h1>\n" +
                "    <p>Remember that this link will expire in 24h\n" +
                "        <br><br>Confirm your account by clicking on the button or by copying the URL in the browser:\n" +
                "        <br><br>https://buymesth.herokuapp.com"+ RestRoutes.EMAIL_TEMPLATE+this.token+"/d/"+this.date+"/"+this.id+
                "    </p>\n" +
                "    <a class=\"button\"href=\"https://buymesth.herokuapp.com"+ RestRoutes.EMAIL_TEMPLATE+this.token+"/d/"+this.date+"/"+this.id+" \">Confirm your Account</a>\n" +
                "</body>\n" +
                "</html>";
    }
}
