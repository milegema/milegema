package com.bitwormhole.passwordgm.network.api;

import com.bitwormhole.passwordgm.network.api.dto.ApiDescriptorDTO;
import com.bitwormhole.passwordgm.network.api.dto.ServiceDescriptorDTO;
import com.bitwormhole.passwordgm.network.web.WebClient;

import java.net.URL;
import java.util.Map;

public class OnlineServiceContext {

    private URL originLocation;
    private URL currentLocation;
    private String[] trustedDomains;
    private WebClient client;
    private Map<String, ServiceDescriptorDTO> services;
    private Map<String, ApiDescriptorDTO> interfaces;

    public OnlineServiceContext() {
    }

    public Map<String, ServiceDescriptorDTO> getServices() {
        return services;
    }

    public void setServices(Map<String, ServiceDescriptorDTO> services) {
        this.services = services;
    }

    public Map<String, ApiDescriptorDTO> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Map<String, ApiDescriptorDTO> interfaces) {
        this.interfaces = interfaces;
    }

    public WebClient getClient() {
        return client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }

    public String[] getTrustedDomains() {
        return trustedDomains;
    }

    public void setTrustedDomains(String[] trustDomains) {
        this.trustedDomains = trustDomains;
    }

    public URL getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(URL originLocation) {
        this.originLocation = originLocation;
    }

    public URL getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(URL currentLocation) {
        this.currentLocation = currentLocation;
    }
}
