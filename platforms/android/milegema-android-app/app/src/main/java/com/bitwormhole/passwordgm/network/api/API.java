package com.bitwormhole.passwordgm.network.api;

import com.bitwormhole.passwordgm.network.web.WebMethod;
import com.bitwormhole.passwordgm.utils.ByteSlice;

public interface API {

    WebMethod getMethod();

    String getLocation();

    Object getRequestObject();

    Class<?> getRequestType();

    Object getResponseObject();

    Class<?> getResponseType();

    void setResponseObject(Object obj);

}
