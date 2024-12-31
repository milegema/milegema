package com.bitwormhole.passwordgm.network.api;

import com.bitwormhole.passwordgm.network.api.dto.ApiDescriptorDTO;
import com.bitwormhole.passwordgm.network.api.dto.ServiceDescriptorDTO;
import com.bitwormhole.passwordgm.network.web.WebClient;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiDiscoverer {

    private final OnlineServiceContext context;

    public ApiDiscoverer(OnlineServiceContext ctx) {
        this.context = ctx;
    }

    private void innerDoDiscover() throws IOException {
        this.context.setServices(new HashMap<>());
        this.context.setInterfaces(new HashMap<>());
        URL url = this.context.getOriginLocation();
        this.innerFetchServiceDescriptors(url);
    }

    private void innerFetchServiceDescriptors(URL url) throws IOException {
        WebClient client = this.context.getClient();
        ServiceDiscoveryAPI api = ServiceDiscoveryAPI.api();
        api.setLocation(url.toString());
        APIAgent.execute(api, client);
        ServiceDiscoveryAPI.Response resp = api.getResponse();
        List<ServiceDescriptorDTO> src = resp.getServices();
        Map<String, ServiceDescriptorDTO> dst = this.context.getServices();
        for (ServiceDescriptorDTO item : src) {
            String api_url = item.getInterfaces();
            dst.put(api_url, item);
            this.innerFetchApiDescriptors(api_url);
        }
    }

    private void innerFetchApiDescriptors(String url) throws IOException {
        WebClient client = this.context.getClient();
        InterfaceDiscoveryAPI api = InterfaceDiscoveryAPI.api();
        api.setLocation(url);
        APIAgent.execute(api, client);
        InterfaceDiscoveryAPI.Response resp = api.getResponse();
        List<ApiDescriptorDTO> src = resp.getInterfaces();
        Map<String, ApiDescriptorDTO> dst = this.context.getInterfaces();
        for (ApiDescriptorDTO item : src) {
            String key = item.getUrl();
            dst.put(key, item);
        }
    }

    private boolean innerNeedToLoad() {
        Map<String, ApiDescriptorDTO> api_set = this.context.getInterfaces();
        if (api_set == null) {
            return true;
        }
        return api_set.isEmpty();
    }

    public void discover() throws IOException {
        if (this.innerNeedToLoad()) {
            this.innerDoDiscover();
        }
    }

    public void reload() throws IOException {
        this.innerDoDiscover();
    }
}
