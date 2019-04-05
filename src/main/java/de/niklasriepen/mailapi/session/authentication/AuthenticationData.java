package de.niklasriepen.mailapi.session.authentication;

import de.niklasriepen.mailapi.session.util.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AuthenticationData {

    private static final Charset charset = StandardCharsets.UTF_8;

    private String username;
    private String password;

    public AuthenticationData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsernameBase64() {
        return Base64.encode(username, charset);
    }

    public String getPasswordBase64() {
        return Base64.encode(password, charset);
    }
}
