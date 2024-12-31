package com.bitwormhole.passwordgm.config;

import android.content.Context;

import com.bitwormhole.passwordgm.contexts.AppContext;
import com.bitwormhole.passwordgm.contexts.ContextCustomizer;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.data.properties.PropertyTable;
import com.bitwormhole.passwordgm.utils.FileOptions;
import com.bitwormhole.passwordgm.utils.FileUtils;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigProperties implements ContextCustomizer {

    @Override
    public void customize(ContextHolder ch) {
        AppContext app = ch.getApp();
        PropertyTable  p = app.getProperties();
        try {
            this.loadProfile(ch);
        } catch (IOException e) {
            Logs.error("cannot load profile", e);
        }
        String profile = app.getProfile();
        this.loadForDefault(p);
        if ("debug".equalsIgnoreCase(profile)) {
            this.loadForDebug(p);
        } else {
            this.loadForRelease(p);
        }
    }


    private void loadProfile(ContextHolder ch) throws IOException {
        final Context ctx = ch.getAndroid();
        final Path file = ctx.getDataDir().toPath().resolve("profile");
        String profile = "release";
        if (Files.exists(file)) {
            // load
            String text = FileUtils.readText(file);
            profile = text.trim().toLowerCase();
        } else {
            // init new
            FileOptions flags = new FileOptions();
            flags.create = true;
            flags.write = true;
            flags.truncate = true;
            FileUtils.writeText(profile, file, flags);
        }
        ch.getApp().setProfile(profile);
    }


    private void loadForDebug(PropertyTable  p) {
        (new ApplicationDebugProperties()).customize(p);
    }

    private void loadForRelease(PropertyTable  p) {
        (new ApplicationReleaseProperties()).customize(p);
    }

    private void loadForDefault(PropertyTable p) {
        (new ApplicationDefaultProperties()).customize(p);
    }
}
