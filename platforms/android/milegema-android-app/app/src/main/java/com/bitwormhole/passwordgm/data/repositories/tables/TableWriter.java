package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.data.properties.PropertyTable;

import java.io.IOException;

public interface TableWriter {

    void write(PropertyTable pt) throws IOException;

    void flush() throws IOException;

}
