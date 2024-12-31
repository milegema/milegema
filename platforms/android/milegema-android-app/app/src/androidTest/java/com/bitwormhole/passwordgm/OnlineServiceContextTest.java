package com.bitwormhole.passwordgm;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.network.api.ApiDiscoverer;
import com.bitwormhole.passwordgm.network.api.OnlineServiceContext;
import com.bitwormhole.passwordgm.network.api.dto.ApiDescriptorDTO;
import com.bitwormhole.passwordgm.network.web.DefaultWebClientFactory;
import com.bitwormhole.passwordgm.network.web.WebClient;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;
import java.util.Map;


@RunWith(AndroidJUnit4.class)
public class OnlineServiceContextTest {

    @Test
    public void useOnlineServiceContext() throws IOException {

        URL url = new URL("http://192.168.0.104:8080/index.json");

        // prepare WebClient
        WebClient client = DefaultWebClientFactory.getInstance().create(null);


        // prepare OSC
        OnlineServiceContext osc = new OnlineServiceContext();
        osc.setClient(client);
        osc.setCurrentLocation(url);
        osc.setOriginLocation(url);

        // discover
        ApiDiscoverer discoverer = new ApiDiscoverer(osc);
        discoverer.discover();


        Map<String, ApiDescriptorDTO> items = osc.getInterfaces();
        for (ApiDescriptorDTO item : items.values()) {
            Logs.debug("api:" + item.getMethod() + ' ' + item.getUrl());
        }
    }
}
