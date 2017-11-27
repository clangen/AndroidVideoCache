package com.danikula.videocache.headers;

import java.util.List;
import java.util.Map;

/**
 * Allows user to inspect the server's response headers.
 *
 * @author clangen
 */
public interface HeaderReceiver {
    /**
     * Called with headers sent back from the server
     *
     * @param url an url headers are associated with
     * @param headers the headers themselves
     */
    void receiveHeaders(String url, Map<String, List<String>> headers);
}
