package com.bitwormhole.passwordgm.network.api;

import com.bitwormhole.passwordgm.network.api.vo.ServiceDescriptorVO;
import com.bitwormhole.passwordgm.network.web.WebMethod;

public final class ServiceDiscoveryAPI extends APITemplate<ServiceDiscoveryAPI.Request, ServiceDiscoveryAPI.Response> {

    private ServiceDiscoveryAPI() {
    }

    public static class Request extends ServiceDescriptorVO {
    }

    public static class Response extends ServiceDescriptorVO {
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

    public static ServiceDiscoveryAPI api() {
        ServiceDiscoveryAPI a = new ServiceDiscoveryAPI();
        a.setMethod(WebMethod.GET);
        a.setLocation("todo:fetch_service_info");
        return a;
    }
}
