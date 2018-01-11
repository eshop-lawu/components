package com.lawu.compensating.transaction;
import java.io.File;
import java.io.IOException;

import org.apache.curator.test.TestingServer;

public final class EmbedZKServer {
    
    private static final int PORT = 2181;
    
    private static volatile TestingServer testingServer;
    
    public static void start() throws Exception {
        if (null != testingServer) {
            return;
        }
        testingServer = new TestingServer(PORT, new File(String.format("target/test_zk_data/%s/", System.nanoTime())));
    }
    
    /**
     * 不需要手动停止
     * @throws IOException
     * @author jiangxinjun
     * @createDate 2018年1月11日
     * @updateDate 2018年1月11日
     */
    public static void stop() throws IOException {
        testingServer.close();
    }
}