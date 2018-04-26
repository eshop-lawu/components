package com.lawu.media.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lawu.utils.RandomUtil;

/**
 * 视频截图工具类
 * 
 * @author zhangrc
 * @date 2017/05/19
 *
 */
public class VideoCutImgUtil {
	
	 private static Logger logger = LoggerFactory.getLogger(VideoCutImgUtil.class);
	
	/**
	 * 视频截图图片
	 * @param veido_path
	 * @param ffmpeg_path
	 * @return
	 */
	public static String processImg(String veido_path,String dir,String baseImageDir,String ffmpegUrl) {
		File file = new File(veido_path);
		if (!file.exists()) {
			logger.debug("路径[" + veido_path + "]对应的视频文件不存在!");
			return null;
		}
		String newfileName =RandomUtil.buildFileName(".jpg");
		File fileImg = new File(baseImageDir, dir);
        if (!fileImg.exists()) {
        	fileImg.mkdirs();
        }
		String AD_IMG_VIDEO=baseImageDir+"/"+dir+"/"+newfileName;
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpegUrl);
		commands.add("-i");
		commands.add(veido_path);
		commands.add("-y");
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add("1");// 这个参数是设置截取视频多少秒时的画面
		commands.add("-s");
		commands.add("500x300");
		commands.add(AD_IMG_VIDEO);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			Process p = builder.start();
			// 等待20秒
			boolean exitValue = p.waitFor(20, TimeUnit.SECONDS);
			if (!exitValue) {
				logger.error("截取图片失败");
				//认定截图失败，销毁进程
				p.destroy();
				return null;
			}
            logger.info("截取图片成功");
			String video_img=dir+"/"+newfileName;
			return video_img;
		} catch (Exception e) {
			logger.error("截取图片失败");
			return null;
		}
	}
	
	
    /** 
     * 获取视频总时间 
     * @param viedo_path    视频路径 
     * @param ffmpeg_path   ffmpeg路径 
     * @return 
     */  
    public static String getVideoTime(String video_path, String ffmpeg_path) {  
        List<String> commands = new java.util.ArrayList<String>();  
        commands.add(ffmpeg_path);  
        commands.add("-i");  
        commands.add(video_path);  
        try {  
            ProcessBuilder builder = new ProcessBuilder();  
            builder.command(commands);  
            final Process p = builder.start();  
              
            //从输入流中读取视频信息  
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));  
            StringBuffer sb = new StringBuffer();  
            String line = "";  
            while ((line = br.readLine()) != null) {  
                sb.append(line);  
            }  
            br.close();  
              
            //从视频信息中解析时长  
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";  
            Pattern pattern = Pattern.compile(regexDuration);  
            Matcher m = pattern.matcher(sb.toString());  
            if (m.find()) {  
            	String time=m.group(1);  
            	time=time.substring(0,time.length()-3);
                return time;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
        return "0";  
    }  
    
    /**
     * 对视频格式进行转换
     * @param video_path
     * @param dir
     * @param baseImageDir
     * @param ffmpegUrl
     * @return
     */
    public static String processMP4(String video_path,String dir,String baseImageDir,String ffmpegUrl) { 
    	
   	    File file = new File(video_path);  
        if (!file.isFile()) {  
            return video_path;  
        }  
        
        String type = video_path.substring(video_path.lastIndexOf(".") + 1, video_path.length())  
                .toLowerCase();  
        // 目前只判断3gp格式文件
        if (!type.equals("3gp")) {  
            return video_path;  
        } 
        
        String newfileName =RandomUtil.buildFileName(".mp4");
		File fileImg = new File(baseImageDir, dir);
        if (!fileImg.exists()) {
        	fileImg.mkdirs();
        }
		String AD_VIDEO=baseImageDir+"/"+dir+"/"+newfileName;
         
        List<String> commend = new ArrayList<String>();  
        commend.add(ffmpegUrl);  
        commend.add("-i");  
        commend.add(video_path);  
        commend.add("-ab");  
        commend.add("56");  
        commend.add("-ar");  
        commend.add("22050");  
        commend.add("-qscale");  
        commend.add("8");  
        commend.add("-r");  
        commend.add("15");  
        commend.add("-s");  
        commend.add("600x500");  
        commend.add(AD_VIDEO);
 
        try {  
           ProcessBuilder builder = new ProcessBuilder(commend);  
           builder.command(commend);  
           Process p= builder.start();  
           // 等待20秒
		   boolean exitValue = p.waitFor(20, TimeUnit.SECONDS);
		   if (!exitValue) {
				logger.error("格式转换失败");
				//认定格式转换失败，销毁进程，依然返回原视频路径
				p.destroy();
				return video_path;
		   }
           logger.info("视频转换成功");
		   String video_url=baseImageDir+File.separator+dir+"/"+newfileName;
		   return video_url; 
        } catch (Exception e) {  
           e.printStackTrace();  
           logger.info("视频转换失败");
           return video_path;  
        }  
   }  
    
    
    /**
     * 获取视频编码信息
     * 
     * @param video_path
     * @param ffmpeg_path
     * @return
     */
    public static Boolean getVideoInfo(String video_path, String ffmpeg_path) {  
        List<String> commands = new java.util.ArrayList<String>();  
        commands.add(ffmpeg_path);  
        commands.add("-i");  
        commands.add(video_path);  
        try {  
            ProcessBuilder builder = new ProcessBuilder();  
            builder.command(commands);  
            final Process p = builder.start();  
              
            //从输入流中读取视频信息  
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));  
            StringBuffer sb = new StringBuffer();  
            String line = "";  
            while ((line = br.readLine()) != null) {  
                sb.append(line);  
            }  
            br.close();  
              
            String regexDuration ="Video: (.*?), (.*?), (.*?)[,\\s]"; 
            Pattern pattern = Pattern.compile(regexDuration);  
            Matcher m = pattern.matcher(sb.toString());  
            if (m.find()) {  
            	String code=m.group(1);
				if (code.length() >= 4) {
					code=code.substring(0, 4);
				}
            	logger.error("-----------视频编码为："+code+"       "+code.getClass().getName()+"       "+code.equals("h264"));
            	if(!code.equals("h264")){
            		return false;
            	}
                return true;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return true;
    } 
      
	/*public static void main(String[] args) {
		Boolean str = getVideoInfo("D:\\ffmpeg\\sas11b.mp4","D:\\ffmpeg\\ffmpeg.exe");
		System.out.println(str);
	}*/

}
