/**
 * 
 */
package com.lawu.media.util.upload;

/**
 * 上传文件返回枚举
 * @author lihj
 * @date 2017年7月27日
 */
public enum FastDFSResultEnum {

	FD_UPLOAD_SUCCESS(1),
	FD_FILE_ERROR(2),
	FD_FILE_IMG_BIG(3),
	UPLOAD_SIZE_BIGER(4),
	FD_FILE_CUT_ERROR(5),
	FD_FILE_VIDEO_CODE_ERROR(6);
	
	private int val;
	
	FastDFSResultEnum(int val){
		this.val=val;
	}

	/**
	 * @return the val
	 */
	public int getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(int val) {
		this.val = val;
	}
	
	
	
}
