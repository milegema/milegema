package com.bitwormhole.passwordgm.network.web.filters;

import com.bitwormhole.passwordgm.network.web.WebFilter;
import com.bitwormhole.passwordgm.network.web.WebFilterChain;
import com.bitwormhole.passwordgm.network.web.WebFilterRegistration;
import com.bitwormhole.passwordgm.network.web.WebFilterRegistry;
import com.bitwormhole.passwordgm.network.web.WebRequest;
import com.bitwormhole.passwordgm.network.web.WebResponse;

import java.io.IOException;

public final class ExampleFilter implements WebFilterRegistry, WebFilter {


    public ExampleFilter() {
    }

    @Override
    public WebResponse execute(WebRequest request, WebFilterChain next) throws IOException {
        return next.execute(request);
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
