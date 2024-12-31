package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.data.properties.PropertyTable;

import java.io.IOException;

public interface TableReader {

    PropertyTable read() throws IOException;

}
