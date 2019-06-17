package com.buymesth.app.mail;

import com.buymesth.app.utils.routes.RestRoutes;
import lombok.Data;

@Data
public class EmailPasswordRecoveryTemplate {
    private String token;
    private String date;
    private String id;

    public EmailPasswordRecoveryTemplate(String token, String date, String id) {
        this.token = token;
        this.date = date;
        this.id = id;
    }

    public String getTemplate() {
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
                "    <h1>You have request a password reset</h1>\n" +
                "    <p>If you haven't do that, maybe someone is trying to stole your account, please, check it out.\n" +
                "        <br><br>Copy the URL in the browser or click in the button bellow:\n" +
                "        <br><br>https://buymesth.herokuapp.com" + RestRoutes.Main.PASSWORD_FORGOTTEN_POST + this.token + "/d/" + this.date + "/" + this.id +
                "    </p>\n" +
                "    <a class=\"button\"href=\"https://buymesth.herokuapp.com" + RestRoutes.Main.PASSWORD_FORGOTTEN_POST + this.token + "/d/" + this.date + "/" + this.id + " \">Reset Your Password</a>\n" +
                "</body>\n" +
                "</html>";
    }

}
