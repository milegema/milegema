package com.bitwormhole.passwordgm;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.network.api.APIAgent;
import com.bitwormhole.passwordgm.network.api.SendVerificationCodeAPI;
import com.bitwormhole.passwordgm.network.web.WebClient;
import com.bitwormhole.passwordgm.tasks.Promise;
import com.bitwormhole.passwordgm.tasks.Result;
import com.bitwormhole.passwordgm.ui.elements.VerificationCodeBox;
import com.bitwormhole.passwordgm.utils.Errors;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;

public class LoginActivity extends PgmActivity {

    private EditText mEditEmailAddress;
    private VerificationCodeBox mVCodeBox;


    private String mStringEmailAddress;
    private String mStringVCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mEditEmailAddress = findViewById(R.id.edit_text_email);
        mVCodeBox = findViewById(R.id.input_v_code);

        mVCodeBox.setOnClickSendListener(this::handleClickSendCode);
        this.setupButtonHandler(R.id.button_login, this::handleClickLogin);
        this.setupButtonHandler(R.id.button_next_step, this::handleClickNextStep);
    }

    private void setupButtonHandler(int id, View.OnClickListener l) {
        this.findViewById(id).setOnClickListener(l);
    }


    private void handleClickNextStep(View v) {
        this.mStringEmailAddress = this.mEditEmailAddress.getText().toString();
    }

    private void handleClickLogin(View v) {
    }

    private void handleClickSendCode() {
        final ContextHolder ch = this.getContexts();
        final WebClient client = ch.getApp().getWebClient();
        final String email_address = this.mEditEmailAddress.getText().toString();
        final SendVerificationCodeAPI api = APIAgent.getSendVerificationCodeAPI();
        final SendVerificationCodeAPI.Request req = new SendVerificationCodeAPI.Request();

        req.setAction("login");
        req.setEmail(email_address);
        api.setRequest(req);

        Promise.init(this, SendVerificationCodeAPI.class).Try(() -> {
            APIAgent.execute(api, client);
            return new Result<>(api);
        }).Then((res) -> {
            Logs.info("send-verification-code:ok");
            return res;
        }).Catch((res) -> {
            Errors.handle(this, res.getError());
            return res;
        }).start();
        Logs.info("send-verification-code:done");
    }
}
