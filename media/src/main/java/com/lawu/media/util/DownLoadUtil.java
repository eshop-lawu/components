package com.lawu.media.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author zhangyong
 * @date 2017/5/22.
 */
public class DownLoadUtil {
    private static Logger logger = LoggerFactory.getLogger(DownLoadUtil.class);

    private DownLoadUtil(){

    }
    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     *
     * @param urlStr 原文件路径
     * @param fileName 文件名称
     * @param savePath 保存路径
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath){
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        } catch (MalformedURLException e) {
            logger.info("MalformedURLException {} ",e);
        } catch (IOException e) {
            logger.info("IOException {} ",e);
        }
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        File file = new File(saveDir + File.separator + fileName);
        //得到输入流
        if (conn == null) {
            return;
        }
        try (InputStream inputStream = conn.getInputStream();
             FileOutputStream fos = new FileOutputStream(file)) {
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            fos.write(getData);
            fos.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            logger.info("FileNotFoundException {} ",e);
        } catch (IOException e) {
            logger.info("IOException {}",e);
        }

        logger.info("info:url={} download success",url);

    }
}
