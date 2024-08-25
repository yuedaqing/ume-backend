package com.yue.netty.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequestMessage extends Message {
    @Override
    public String toString() {
        return "LoginRequestMessage{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    private String username;
    private String password;

    public LoginRequestMessage() {
    }

    public LoginRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
