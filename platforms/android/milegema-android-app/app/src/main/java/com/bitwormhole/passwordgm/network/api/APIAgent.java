package com.bitwormhole.passwordgm.network.api;

import com.bitwormhole.passwordgm.network.web.WebClient;
import com.bitwormhole.passwordgm.network.web.WebEntity;
import com.bitwormhole.passwordgm.network.web.WebEntityUtils;
import com.bitwormhole.passwordgm.network.web.WebRequest;
import com.bitwormhole.passwordgm.network.web.WebResponse;

import java.io.IOException;

public final class APIAgent {

    public static void execute(API api, WebClient client) throws IOException {

        // prepare
        WebRequest request = new WebRequest();
        request.setMethod(api.getMethod());
        request.setUrl(api.getLocation());
        Object req_pojo = api.getRequestObject();
        if (req_pojo != null) {
            WebEntity entity1 = WebEntityUtils.toJsonEntity(req_pojo);
            request.setEntity(entity1);
        }

        // execute
        WebResponse response = client.execute(request);

        // handle
        WebEntity entity2 = response.getEntity();
        if (isContentTypeOf("json", response)) {
            Class<?> resp_type = api.getResponseType();
            Object resp_pojo = WebEntityUtils.fromJsonEntity(entity2, resp_type);
            api.setResponseObject(resp_pojo);
        } else if (isContentTypeOf("text", response)) {
            String text = WebEntityUtils.fromTextEntity(entity2);
            api.setResponseObject(text);
        } else {
            byte[] data = WebEntityUtils.fromBinaryEntity(entity2);
            api.setResponseObject(data);
        }
    }

    private static boolean isContentTypeOf(String type_want, WebResponse response) {
        String type_have = response.getEntity().getContentType();
        if (type_want == null || type_have == null) {
            return false;
        }
        type_have = type_have.toLowerCase();
        type_want = type_want.toLowerCase();
        return type_have.contains(type_want);
    }


    public static SendVerificationCodeAPI getSendVerificationCodeAPI() {
        return SendVerificationCodeAPI.api();
    }

    public static LoginAPI getLoginAPI() {
        return LoginAPI.api();
    }

}
