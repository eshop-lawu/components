/**
 * 
 */
package com.lawu.media.util;

import com.lawu.media.fastdfs.ClientParams;
import com.lawu.media.util.upload.FileUploadTypeEnum;

/**
 * @author lihj
 * @date 2017年7月26日
 */
public class UploadParam {

	private String dir;
	private String baseImageDir;
	/**
	 * 上传文件类型 图片 img 视频 video 其他 随便传
	 */
	private FileUploadTypeEnum fileUploadTypeEnum;

	/**
	 * ffmpeg 文件的路径
	 */
	private String ffmpegUrl;

	private ClientParams cparam;

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the baseImageDir
	 */
	public String getBaseImageDir() {
		return baseImageDir;
	}

	/**
	 * @param baseImageDir the baseImageDir to set
	 */
	public void setBaseImageDir(String baseImageDir) {
		this.baseImageDir = baseImageDir;
	}

	public FileUploadTypeEnum getFileUploadTypeEnum() {
		return fileUploadTypeEnum;
	}

	public void setFileUploadTypeEnum(FileUploadTypeEnum fileUploadTypeEnum) {
		this.fileUploadTypeEnum = fileUploadTypeEnum;
	}

	/**
	 * @return the ffmpegUrl
	 */
	public String getFfmpegUrl() {
		return ffmpegUrl;
	}

	/**
	 * @param ffmpegUrl the ffmpegUrl to set
	 */
	public void setFfmpegUrl(String ffmpegUrl) {
		this.ffmpegUrl = ffmpegUrl;
	}

	/**
	 * @return the cparam
	 */
	public ClientParams getCparam() {
		return cparam;
	}

	/**
	 * @param cparam the cparam to set
	 */
	public void setCparam(ClientParams cparam) {
		this.cparam = cparam;
	}
	
	
}
