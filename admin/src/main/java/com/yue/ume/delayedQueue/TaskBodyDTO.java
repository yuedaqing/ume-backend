package com.yue.ume.delayedQueue;

import java.io.Serializable;

public class TaskBodyDTO implements Serializable {
    private String name;
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}