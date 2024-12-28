package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.utils.FileOptions;
import com.bitwormhole.passwordgm.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

public class FileLayer implements DataAccessLayer {


    public FileLayer() {
    }

    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);

        Path file = request.getFile();
        byte[] raw = FileUtils.readBinary(file);
        request.setRaw(raw);

        // Logs.debug(this + ".read() : end");
    }

    @Override
    public void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException {
        // Logs.debug(this + ".write() : begin");

        Path file = request.getFile();
        DataAccessMode mode = request.getDam();
        byte[] raw = request.getRaw();
        FileOptions opt = new FileOptions();

        if (DataAccessMode.APPEND.equals(mode)) {
            opt.append = true;
        } else {
            opt.truncate = true;
        }
        opt.mkdirs = true;
        opt.create = true;
        opt.write = true;

        FileUtils.writeBinary(raw, file, opt);

        next.write(request);
        // Logs.debug(this + ".write() : end");
    }
}
