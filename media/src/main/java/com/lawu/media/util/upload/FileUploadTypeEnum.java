package com.lawu.media.util.upload;


public enum FileUploadTypeEnum {
	IMG("IMG", "图片"),
	VIDEO("VIDEO", "视频"), 
	OTHER("OTHER", "其他");

	public String val;
	public String name;

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	FileUploadTypeEnum(String val, String name) {
		this.val = val;
		this.name = name;
	}
	
	public static FileUploadTypeEnum getEnum(String val) {
		FileUploadTypeEnum[] values = FileUploadTypeEnum.values();
		for (FileUploadTypeEnum object : values) {
			if (object.val.equals(val)){
				return object;
			}
		}
		return null;
	}

}
