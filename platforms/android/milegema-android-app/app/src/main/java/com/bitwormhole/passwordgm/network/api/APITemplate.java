package com.bitwormhole.passwordgm.network.api;

public abstract class APITemplate<REQ, RESP> extends AbstractAPI {

    private REQ request;
    private RESP response;

    public APITemplate() {
    }

    @Override
    public Object getRequestObject() {
        return this.request;
    }

    @Override
    public Object getResponseObject() {
        return this.response;
    }

    public REQ getRequest() {
        return request;
    }

    public void setRequest(REQ request) {
        this.request = request;
    }

    public RESP getResponse() {
        return response;
    }

    public void setResponse(RESP response) {
        this.response = response;
    }
}
