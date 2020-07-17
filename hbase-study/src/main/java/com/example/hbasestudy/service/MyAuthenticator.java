package com.example.hbasestudy.service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description ceshi
 * @date 2020/7/13 20:09
 */
public class MyAuthenticator extends Authenticator {

    private String username = "lwx18848956826";

    private String password = "JEHOOGHIEBVEJKCK";

    public MyAuthenticator() {
        super();
    }

    public MyAuthenticator(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication(username, password);
    }
}
