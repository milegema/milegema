package com.bitwormhole.passwordgm.network.api.vo;

import com.bitwormhole.passwordgm.network.api.VO;
import com.bitwormhole.passwordgm.network.api.dto.ApiDescriptorDTO;

import java.util.List;

public class ApiDescriptorVO extends VO {

    private List<ApiDescriptorDTO> interfaces;

    public ApiDescriptorVO() {
    }

    public List<ApiDescriptorDTO> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<ApiDescriptorDTO> interfaces) {
        this.interfaces = interfaces;
    }
}
