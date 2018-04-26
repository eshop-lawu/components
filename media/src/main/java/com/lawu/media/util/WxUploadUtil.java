package com.lawu.media.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lawu.media.util.UploadParam;
import com.lawu.media.util.upload.FastDFSClient;
import com.lawu.utils.RandomUtil;

/**
 * 微信,QQ下载上传到服务器
 * @Description
 * @author zhangrc
 * @date 2018年1月17日
 */
public class WxUploadUtil {
	
	
	public static String upload(String imgurl,UploadParam uparam) throws IOException{
		
		URL url = new URL(imgurl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");  
        conn.setConnectTimeout(5 * 1000);  
        InputStream inStream = conn.getInputStream();
        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close(); 
        
        byte[] btImg = outStream.toByteArray();
        
        String newName = null;
        
        if(null != btImg && btImg.length > 0){ 
        	String fileName = "third.jpg"; 
        	newName = RandomUtil.buildFileName(fileName);
            File file = new File(uparam.getBaseImageDir(), uparam.getDir());  
            FileOutputStream fops = new FileOutputStream(new File(file, newName));  
            fops.write(btImg);  
            fops.flush();  
            fops.close();  
        }
        
		if (newName != null && newName !="") {
			String uploadImg =  uparam.getBaseImageDir() + File.separator + uparam.getDir()
			+ File.separator + newName;
			FastDFSClient upload =new FastDFSClient();
			try {
				String img = upload.uploadFile(uploadImg, uparam.getCparam());
				return img;
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return "";
		
	}
	

}
