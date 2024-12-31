package com.bitwormhole.passwordgm.network.web.filters;

import com.bitwormhole.passwordgm.network.web.WebFilter;
import com.bitwormhole.passwordgm.network.web.WebFilterChain;
import com.bitwormhole.passwordgm.network.web.WebFilterRegistration;
import com.bitwormhole.passwordgm.network.web.WebFilterRegistry;
import com.bitwormhole.passwordgm.network.web.WebRequest;
import com.bitwormhole.passwordgm.network.web.WebResponse;

import java.io.IOException;

public final class LocationHandlerFilter implements WebFilterRegistry, WebFilter {


    public LocationHandlerFilter() {
    }

    @Override
    public WebResponse execute(WebRequest request, WebFilterChain next) throws IOException {

        String url = request.getUrl();
        url = this.normalizeURL(url);
        request.setUrl(url);

        return next.execute(request);
    }

    private String normalizeURL(String url) {

        if (url == null) {
            url = "/";
        }

        if (url.startsWith("http:/") || url.startsWith("https:/")) {
            return url;
        }

        if (url.startsWith("/")) {
            String base = this.getBaseURL();
            return base + url;
        }

        return url;
    }

    private String getBaseURL() {
        return "http://bitwormhole.com:80"; // 这里暂时写死
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
