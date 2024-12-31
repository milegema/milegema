package com.bitwormhole.passwordgm.network.api;

import com.bitwormhole.passwordgm.network.web.WebMethod;

public final class ExampleAPI extends APITemplate<ExampleAPI.Request, ExampleAPI.Response> {

    private ExampleAPI() {
    }

    public static class Request extends VO {

    }

    public static class Response extends VO {


    }

    @Override
    public Class<?> getRequestType() {
        return Request.class;
    }

    @Override
    public Class<?> getResponseType() {
        return Response.class;
    }

    @Override
    public void setResponseObject(Object obj) {
        if (obj instanceof Response) {
            this.setResponse((Response) obj);
        }
    }

    public static ExampleAPI api() {
        ExampleAPI a = new ExampleAPI();
        a.setMethod(WebMethod.POST);
        a.setLocation("/api/v1/example");
        return a;
    }
}
