package com.lawu.autotest.tool.handle.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author meishuquan
 * @date 2017/10/25
 */
@Service
public class HttpRequestHandleFactory {

    @Autowired
    private GetHttpRequestHandle getHttpRequestHandle;

    @Autowired
    private PostHttpRequestHandle postHttpRequestHandle;

    @Autowired
    private DeleteHttpRequestHandle deleteHttpRequestHandle;

    @Autowired
    private PutHttpRequestHandle putHttpRequestHandle;

    public HttpRequestHandle getHandle(String method) {
        switch (method) {
            case "GET":
                return getHttpRequestHandle;
            case "POST":
                return postHttpRequestHandle;
            case "DELETE":
                return deleteHttpRequestHandle;
            case "PUT":
                return putHttpRequestHandle;
        }
        return null;
    }
}
