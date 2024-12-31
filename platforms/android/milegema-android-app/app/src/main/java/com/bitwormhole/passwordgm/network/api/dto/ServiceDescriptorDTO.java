package com.bitwormhole.passwordgm.network.api.dto;

import com.bitwormhole.passwordgm.network.api.DTO;

public class ServiceDescriptorDTO extends DTO {

    private String namespace;
    private String name;
    private String version;
    private String description;
    private String interfaces;

    public ServiceDescriptorDTO() {
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }
}
