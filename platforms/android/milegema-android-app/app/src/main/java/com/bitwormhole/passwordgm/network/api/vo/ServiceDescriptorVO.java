package com.bitwormhole.passwordgm.network.api.vo;

import com.bitwormhole.passwordgm.network.api.VO;
import com.bitwormhole.passwordgm.network.api.dto.ServiceDescriptorDTO;

import java.util.List;

public class ServiceDescriptorVO extends VO {

    private List<ServiceDescriptorDTO> services;

    public ServiceDescriptorVO() {
    }

    public List<ServiceDescriptorDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDescriptorDTO> services) {
        this.services = services;
    }
}
