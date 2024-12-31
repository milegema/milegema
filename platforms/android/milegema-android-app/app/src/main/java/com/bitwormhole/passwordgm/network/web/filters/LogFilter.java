package com.bitwormhole.passwordgm.network.web.filters;

import com.bitwormhole.passwordgm.network.web.WebFilter;
import com.bitwormhole.passwordgm.network.web.WebFilterChain;
import com.bitwormhole.passwordgm.network.web.WebFilterRegistration;
import com.bitwormhole.passwordgm.network.web.WebFilterRegistry;
import com.bitwormhole.passwordgm.network.web.WebRequest;
import com.bitwormhole.passwordgm.network.web.WebResponse;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;

public final class LogFilter implements WebFilterRegistry, WebFilter {


    public LogFilter() {
    }

    private static class LogItem {
        String method;
        String url;
        int code;
        String message;
    }

    @Override
    public WebResponse execute(WebRequest request, WebFilterChain next) throws IOException {
        LogItem item = new LogItem();
        try {
            item.method = request.getMethod().name();
            item.url = request.getUrl();
            WebResponse resp = next.execute(request);
            item.code = resp.getStatus().getCode();
            item.message = resp.getStatus().getMessage();
            return resp;
        } catch (Exception e) {
            item.message = "error:" + e.getMessage();
            item.code = -1;
            throw new RuntimeException(e);
        } finally {
            this.log(item);
        }
    }

    private void log(LogItem item) {
        StringBuilder b = new StringBuilder();
        b.append(item.method);
        b.append(' ');
        b.append(item.url);
        b.append("; Response:HTTP ");
        b.append(item.code);
        b.append(' ');
        b.append(item.message);
        Logs.debug(b.toString());
    }

    @Override
    public WebFilterRegistration registration() {
        WebFilterRegistration reg = new WebFilterRegistration();
        reg.setFilter(this);
        reg.setEnabled(true);
        reg.setOrder(0);
        return reg;
    }
}
