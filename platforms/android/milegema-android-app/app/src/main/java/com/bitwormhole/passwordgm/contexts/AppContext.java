package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.components.ComponentManager;
import com.bitwormhole.passwordgm.data.blocks.AppBlock;
import com.bitwormhole.passwordgm.data.properties.PropertyTable;
import com.bitwormhole.passwordgm.network.web.WebClient;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.utils.Attributes;

public class AppContext extends ContextBase {

    private ComponentManager components;
    private PropertyTable properties;
    private Attributes attributes;
    private KeyPairManager keyPairManager;
    private AppBlock block;
    private WebClient webClient;
    private String profile; // debug/release/...

    private boolean started;
    private boolean starting;
    private boolean stopped;
    private boolean stopping;
    private boolean debugEnabled;


    public AppContext(RootContext _parent) {
        super(_parent, ContextScope.APP);
        this.attributes = new Attributes();
        this.properties = PropertyTable.Factory.create();
    }

    public AppBlock getBlock() {
        return block;
    }

    public void setBlock(AppBlock block) {
        this.block = block;
    }

    public ComponentManager getComponents() {
        return components;
    }

    public void setComponents(ComponentManager components) {
        this.components = components;
    }

    public KeyPairManager getKeyPairManager() {
        return keyPairManager;
    }

    public void setKeyPairManager(KeyPairManager keyPairManager) {
        this.keyPairManager = keyPairManager;
    }


    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isStopping() {
        return stopping;
    }

    public void setStopping(boolean stopping) {
        this.stopping = stopping;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }


    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable properties) {
        this.properties = properties;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
