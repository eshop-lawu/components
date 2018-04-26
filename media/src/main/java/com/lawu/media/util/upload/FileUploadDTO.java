/**
 * 
 */
package com.lawu.media.util.upload;

/**
 * @author lihj
 * @date 2017年7月27日
 */
public class FileUploadDTO {
	/**
	 * 上传文件返回的路径
	 */
	private String fileUrl;

	/**
	 * 视频截图路径
	 */
	private String cutImgUrl;

	/**
	 * 图片标识
	 */
	private String fileIndex;

	/**
	 * @return the fileUrl
	 */
	public String getFileUrl() {
		return fileUrl;
	}

	public FileUploadDTO() {

	}

	public FileUploadDTO(String fileUrl, String cutImgUrl, String fileIndex) {
		this.fileUrl = fileUrl;
		this.cutImgUrl = cutImgUrl;
		this.fileIndex = fileIndex;
	}
	public FileUploadDTO(String fileUrl, String cutImgUrl) {
		this.fileUrl = fileUrl;
		this.cutImgUrl = cutImgUrl;
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

	public String getFileIndex() {
		return fileIndex;
	}

	public void setFileIndex(String fileIndex) {
		this.fileIndex = fileIndex;
	}

}
