package com.bitwormhole.passwordgm.config;

import com.bitwormhole.passwordgm.data.properties.PropertyTable;

public class ApplicationReleaseProperties implements ApplicationProperties {

    @Override
    public void customize(PropertyTable p) {
        p.put(ApplicationProperties.APPLICATION_PROFILES_ACTIVE, "release");
    }
}
