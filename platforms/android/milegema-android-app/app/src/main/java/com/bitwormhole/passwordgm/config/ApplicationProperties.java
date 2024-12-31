package com.bitwormhole.passwordgm.config;

import com.bitwormhole.passwordgm.data.properties.PropertyTable;

public interface ApplicationProperties {

    void customize(PropertyTable p);

    String APPLICATION_PROFILES_ACTIVE = "application.profiles.active";
    String DEBUG_ENABLED = "debug.enabled";

}
