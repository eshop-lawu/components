package com.lawu.media.util.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lawu.media.common.MyException;
import com.lawu.media.common.NameValuePair;
import com.lawu.media.fastdfs.*;

import java.io.IOException;
import java.io.InputStream;

public class FastDFSClient {

    private static Logger log = LoggerFactory.getLogger(FastDFSClient.class);

 /*   private static TrackerClient trackerClient = null;
    private static TrackerServer trackerServer = null;
    private static StorageServer storageServer = null;
    private static StorageClient1 storageClient = null;*/

//    private static FastDFSClient client;

/*    public static FastDFSClient getInstance(ClientParams param) throws Exception {
        if (client == null) {
            synchronized (FastDFSClient.class) {
                if (client == null) {
                    client = new FastDFSClient(param);
                }
            }
        }
        return client;
    }
*/
/*    private FastDFSClient(ClientParams param) throws Exception {
        ClientGlobal.init(param);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }*/

    /**
     * 上传文件方法
     * <p>
     * Title: uploadFile
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @param metas    文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas,ClientParams param) throws Exception {
        String result = null;
        try {
        	 ClientGlobal.init(param);
        	 TrackerClient trackerClient = new TrackerClient();
        	 TrackerServer trackerServer = trackerClient.getConnection();
        	 StorageServer storageServer = null;
        	 StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            result = storageClient.upload_file1(fileName, extName, metas);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        } catch (MyException e) {
            log.error(e.getMessage());
            throw e;
        }
        return result;
    }

    /**
     * 上传文件,传fileName
     *
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @return null为失败
     * @throws Exception
     */
    public String uploadFile(String fileName,ClientParams param) throws Exception {
        return uploadFile(fileName, null, null, param);
    }

    /**
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @param extName  文件的扩展名 如 txt jpg等
     * @return null为失败
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName,ClientParams param) throws Exception {
        return uploadFile(fileName, extName, null,param);
    }

    /**
     * 上传文件方法
     * <p>
     * Title: uploadFile
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param fileContent 文件的内容，字节数组
     * @param extName     文件扩展名
     * @param metas       文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas,ClientParams param) throws Exception {
        String result = null;
        try {
        	 ClientGlobal.init(param);
        	 TrackerClient trackerClient = new TrackerClient();
        	 TrackerServer trackerServer = trackerClient.getConnection();
        	 StorageServer storageServer = null;
        	 StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
            result = storageClient.upload_file1(fileContent, extName, metas);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        } catch (MyException e) {
            log.error(e.getMessage());
            throw e;
        }
        return result;
    }

    /**
     * 上传文件
     *
     * @param fileContent 文件的字节数组
     * @return null为失败
     * @throws Exception
     */
   public String uploadFile(byte[] fileContent,ClientParams param) throws Exception {
        return uploadFile(fileContent, null, null,param);
    }

    /**
     * 上传文件
     *
     * @param fileContent 文件的字节数组
     * @param extName     文件的扩展名 如 txt jpg png 等
     * @return null为失败
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName,ClientParams param) throws Exception {
        return uploadFile(fileContent, extName, null,param);
    }

    /**
     * InputStream 转byte[]
     *
     * @param inStream
     * @param fileLength
     * @return
     * @throws IOException
     */
    public static byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {

        byte[] buffer = new byte[256 * 1024];
        byte[] fileBuffer = new byte[(int) fileLength];

        int count = 0;
        int length = 0;

        while ((length = inStream.read(buffer)) != -1) {
            for (int i = 0; i < length; ++i) {
                fileBuffer[count + i] = buffer[i];
            }
            count += length;
        }
        return fileBuffer;
    }
}
