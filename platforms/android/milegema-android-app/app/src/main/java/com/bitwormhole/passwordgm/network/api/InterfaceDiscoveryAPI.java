package com.bitwormhole.passwordgm.network.api;

import com.bitwormhole.passwordgm.network.api.vo.ApiDescriptorVO;
import com.bitwormhole.passwordgm.network.web.WebMethod;

public final class InterfaceDiscoveryAPI extends APITemplate<InterfaceDiscoveryAPI.Request, InterfaceDiscoveryAPI.Response> {

    private InterfaceDiscoveryAPI() {
    }

    public static class Request extends ApiDescriptorVO {
    }

    public static class Response extends ApiDescriptorVO {
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

    public static InterfaceDiscoveryAPI api() {
        InterfaceDiscoveryAPI a = new InterfaceDiscoveryAPI();
        a.setMethod(WebMethod.GET);
        a.setLocation("todo:fetch_api_info");
        return a;
    }
}
