package com.lawu.service.monitor.handle.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.service.monitor.config.api.RequestType;

/**
 * @author Leach
 * @date 2017/10/17
 */
@Service
public class HttpRequestHandleFactory {

    @Autowired
    private GetHttpRequestHandle getHttpRequestHandle;

    public HttpRequestHandle getHandle(RequestType requestType) {
        switch (requestType) {
            case GET:
                return getHttpRequestHandle;
        }
        return null;
    }
}
