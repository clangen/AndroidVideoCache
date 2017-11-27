package com.danikula.videocache.headers;

import java.util.List;
import java.util.Map;

/**
 * Empty implementation of HeaderReceiver
 *
 * @author clangen
 */
public class EmptyHeaderReceiver implements HeaderReceiver {
    @Override
    public void receiveHeaders(String url, Map<String, List<String>> headers) {

    }
}
