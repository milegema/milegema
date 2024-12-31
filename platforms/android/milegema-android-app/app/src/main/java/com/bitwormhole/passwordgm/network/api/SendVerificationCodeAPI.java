package com.bitwormhole.passwordgm.network.api;

import com.bitwormhole.passwordgm.network.web.WebMethod;

public final class SendVerificationCodeAPI extends APITemplate<SendVerificationCodeAPI.Request, SendVerificationCodeAPI.Response> {

    private SendVerificationCodeAPI() {
    }

    public static class Request extends VO {

        private String email;
        private String action;

        public Request() {
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
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


    public static SendVerificationCodeAPI api() {
        SendVerificationCodeAPI a = new SendVerificationCodeAPI();
        a.setMethod(WebMethod.POST);
        a.setLocation("/api/v1/example");
        return a;
    }
}
