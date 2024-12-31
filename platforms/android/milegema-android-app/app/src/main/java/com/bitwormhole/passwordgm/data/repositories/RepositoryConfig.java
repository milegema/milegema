package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.data.properties.PropertyTable;

import java.io.IOException;
import java.nio.file.Path;

public interface RepositoryConfig {

    Path file();

    PropertyTable loadProperties() throws IOException;

    void store(PropertyTable pt) throws IOException;

}
