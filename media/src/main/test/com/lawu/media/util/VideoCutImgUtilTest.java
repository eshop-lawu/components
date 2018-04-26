package com.lawu.media.util;

import org.junit.Ignore;
import org.junit.Test;

import com.lawu.media.util.VideoCutImgUtil;

/**
 * 
 * @author jiangxinjun
 * @date 2017年9月13日
 */
public class VideoCutImgUtilTest {
	
	//private final static String VEIDO_PATH = "C:\\Users\\Administrator\\Desktop\\VID_20170807_160132.mp4";
	private final static String VEIDO_PATH = "E:\\迅雷下载\\南极料理人BD日语中字[电影天堂www.dy2018.com].mp4";
	
	private final static String DIR = "cut_images";
	
	private final static String BASEIMAGEDIR = "D:\\Develop Tools\\ffmpeg";
	
	private final static String FFMPEGURL = "D:\\Develop Tools\\ffmpeg\\ffmpeg";
	
	@Ignore
	@Test
	public void processImg() {
		String filePath = VideoCutImgUtil.processImg(VEIDO_PATH, DIR, BASEIMAGEDIR, FFMPEGURL);
		System.out.println(filePath);
	}
	
}
