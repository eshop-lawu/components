/**
 * 
 */
package com.lawu.media.util.upload;

/**
 * 上传返回对象
 * 
 * @author lihj
 * @date 2017年7月25日
 */
public class FastDFSResult {

	/**
	 * 上传状态枚举
	 */
	private FastDFSResultEnum fenum;
	/**
	 * 上传文件返回的路径
	 */
	private String fileUrl;

	/**
	 * 视频截图路径
	 */
	private String cutImgUrl;

	/**
	 * @return the fenum
	 */
	public FastDFSResultEnum getFenum() {
		return fenum;
	}

	/**
	 * @param fenum
	 *            the fenum to set
	 */
	public void setFenum(FastDFSResultEnum fenum) {
		this.fenum = fenum;
	}

	/**
	 * @return the fileUrl
	 */
	public String getFileUrl() {
		return fileUrl;
	}

	/**
	 * @param fileUrl
	 *            the fileUrl to set
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	/**
	 * @return the cutImgUrl
	 */
	public String getCutImgUrl() {
		return cutImgUrl;
	}

	/**
	 * @param cutImgUrl
	 *            the cutImgUrl to set
	 */
	public void setCutImgUrl(String cutImgUrl) {
		this.cutImgUrl = cutImgUrl;
	}

}
