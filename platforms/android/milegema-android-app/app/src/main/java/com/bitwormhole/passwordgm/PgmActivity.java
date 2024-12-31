package com.bitwormhole.passwordgm;

import android.app.Activity;
import android.app.Application;

import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.ui.FrontContext;

public class PgmActivity extends Activity implements PgmAppInterface {

    private final FrontContext frontContext;

    public PgmActivity() {
        this.frontContext = new FrontContext(this);
    }

    public FrontContext getFrontContext() {
        return frontContext;
    }

    @Override
    public ContextHolder getContexts() {
        PgmApplication app = (PgmApplication) this.getApplicationContext();
        return app.getContexts();
    }
}
