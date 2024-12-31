package com.bitwormhole.passwordgm.network.api;

import com.bitwormhole.passwordgm.network.web.WebMethod;

public abstract class AbstractAPI implements API {

    private WebMethod method;
    private String location;

    public AbstractAPI() {
    }

    public WebMethod getMethod() {
        return method;
    }

    public void setMethod(WebMethod method) {
        this.method = method;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
