package com.bitwormhole.passwordgm.boot;

import android.content.Context;

import com.bitwormhole.passwordgm.components.ComLife;
import com.bitwormhole.passwordgm.components.ComLifecycle;
import com.bitwormhole.passwordgm.contexts.AppContext;
import com.bitwormhole.passwordgm.contexts.ContextFactory;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.contexts.RootContext;
import com.bitwormhole.passwordgm.data.blocks.AppBlock;
import com.bitwormhole.passwordgm.data.blocks.AppBlockLS;
import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.data.repositories.Repository;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectHolder;
import com.bitwormhole.passwordgm.data.repositories.refs.RefHolder;
import com.bitwormhole.passwordgm.data.repositories.refs.RefName;
import com.bitwormhole.passwordgm.network.web.WebClient;
import com.bitwormhole.passwordgm.utils.Logs;
import com.bitwormhole.passwordgm.utils.Time;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoAppContextLoader implements ComLifecycle {

    private ContextHolder contextHolder;
    private WebClient webClient;

    public AutoAppContextLoader() {
    }


    public WebClient getWebClient() {
        return webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ContextHolder getContextHolder() {
        return contextHolder;
    }

    public void setContextHolder(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }


    @Override
    public ComLife life() {
        ComLife l = new ComLife();
        l.setOrder(BootOrder.APP_CONTEXT);
        l.setOnCreate(this::loadAll);
        l.setLoop(this::run_loop);
        return l;
    }


    // private


    private interface PrivateLoaderFunc {
        AppContext invoke(AppContext ac) throws IOException;
    }

    private void run_loop() {
        AppContext app = this.contextHolder.getApp();
        for (; ; ) {
            if (app.isStopping()) {
                break;
            }
            if (app.isStopped()) {
                break;
            }
            Time.sleep(5000);
        }
    }


    private void loadAll() throws IOException {

        Logs.info(this + "::loadAll");

        final ContextHolder ch = this.contextHolder;
        final List<PrivateLoaderFunc> steps = new ArrayList<>();
        AppContext ac = ch.getApp();

        steps.add(this::loadAppContext);
        steps.add(this::loadAppBlock);
        //     steps.add(this::loadDeveloperMode);

        for (PrivateLoaderFunc fn : steps) {
            ac = fn.invoke(ac);
        }
        ch.setApp(ac);
    }


    private AppContext loadAppContext(AppContext ac) throws IOException {

        final ContextHolder ch = this.contextHolder;
        final RootContext rc = ch.getRoot();
        final Context android_app_ctx = ch.getAndroid().getApplicationContext();
        final String android_app_name = android_app_ctx.getClass().getName();

        if (ac == null) {
            ac = ContextFactory.createAppContext(ch);
            ch.setApp(ac);
        }

        ac.setRepository(rc.getRepository());
        ac.setFolder(rc.getFolder().resolve(android_app_name));
        ac.setKeyPair(rc.getKeyPair());
        ac.setSecretKey(rc.getSecretKey());
        ac.setName(android_app_name);
        ac.setLabel("password-gm-app");
        ac.setAlias(android_app_ctx.getClass().getSimpleName());
        ac.setDescription("" + ac);
        ac.setWebClient(this.webClient);

        return ac;
    }

    private AppContext loadAppBlock(AppContext ac) throws IOException {
        AppBlock block = this.inner_loadAppBlock(ac);
        if (!AppBlock.isAvailable(block)) {
            this.inner_initNewAppBlock(ac);
            block = this.inner_loadAppBlock(ac);
        }
        ac.setBlock(block);
        return ac;
    }

    private void inner_initNewAppBlock(AppContext ac) throws IOException {
    }


    private AppBlock inner_loadAppBlock(AppContext ac) throws IOException {
        final RootContext rc = this.contextHolder.getRoot();
        final Repository repo = ac.getRepository();
        final RefName ref_name = rc.getConfig().getRefsBlocksApp();
        final RefHolder ref = repo.refs().get(ref_name);
        if (!ref.exists()) {
            return null;
        }
        ObjectID obj_id = ref.read();
        ObjectHolder obj_holder = repo.objects().get(obj_id);
        if (obj_holder.exists()) {
            return AppBlockLS.load(obj_id, repo);
        }
        return null;
    }
}
