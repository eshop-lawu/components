/**
 * 
 */
package com.lawu.media.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.lawu.media.fastdfs.ClientParams;
import com.lawu.media.util.upload.FastDFSClient;
import com.lawu.media.util.upload.FastDFSResult;
import com.lawu.media.util.upload.FastDFSResultEnum;
import com.lawu.media.util.upload.FileUploadConstant;
import com.lawu.media.util.upload.FileUploadTypeEnum;
import com.lawu.utils.RandomUtil;

/**
 * FastDFS上传工具
 * 
 * @author lihj
 * @date 2017年7月25日
 */
public class FastDFSUploadUtils {

	private static Logger log = LoggerFactory.getLogger(FastDFSUploadUtils.class);

	public static FastDFSResult upload(HttpServletRequest request, UploadParam uparam) {
		FastDFSResult result = new FastDFSResult();
		Collection<Part> parts = null;
		try {
			parts = request.getParts();
		} catch (Exception e) {
			log.error("获取文件信息异常" + e.getMessage());
			result.setFenum(FastDFSResultEnum.FD_FILE_ERROR);
			return result;
		}
		for (Part part : parts) {
			InputStream in = null;
			if (part.getContentType() != null) {
				long fileSize = part.getSize();
				if (fileSize > 0 && fileSize < FileUploadConstant.VIDEO_MAX_SIZE) {
					String fileName = part.getSubmittedFileName();
					String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
					try {
						in = part.getInputStream();
						ClientParams param = uparam.getCparam();
						if (uparam.getFileUploadTypeEnum().equals(FileUploadTypeEnum.IMG)) {// 图片文件
							if (fileSize > FileUploadConstant.IMG_MAX_SIZE) {
								result.setFenum(FastDFSResultEnum.FD_FILE_IMG_BIG);
								return result;
							}
							FastDFSClient client =new FastDFSClient();
							String fileUrl =client.uploadFile(FastDFSClient.getFileBuffer(in, fileSize), extName,param);
							result.setFileUrl(fileUrl);
						} else if (uparam.getFileUploadTypeEnum().equals(FileUploadTypeEnum.VIDEO)) {// 视频文件
							if (StringUtils.isEmpty(uparam.getFfmpegUrl())) {
								result.setFenum(FastDFSResultEnum.FD_FILE_ERROR);
								return result;
							}
							String newName = RandomUtil.buildFileName(fileName);
							saveLocalFile(part, uparam, newName);
							FastDFSClient client =new FastDFSClient();
							String tmpVideoUrl = uparam.getBaseImageDir() + File.separator + uparam.getDir()
							+ File.separator + newName;
							
							//判断视频编码
							Boolean isH264 = VideoCutImgUtil.getVideoInfo(tmpVideoUrl, uparam.getFfmpegUrl());
							log.error("isH264-------"+isH264);
							log.error("isH264-------"+!isH264);
							if(!isH264){
								result.setFenum(FastDFSResultEnum.FD_FILE_VIDEO_CODE_ERROR);
								return result;
							}
							
							String imgUrl = VideoCutImgUtil.processImg(tmpVideoUrl, uparam.getDir(), uparam.getBaseImageDir(), uparam.getFfmpegUrl());
							String cutUrl = uparam.getBaseImageDir() + File.separator + imgUrl;
							
							//视频转换，如果为3gp则转换，否则直接返回
							tmpVideoUrl = VideoCutImgUtil.processMP4(tmpVideoUrl, uparam.getDir(), uparam.getBaseImageDir(), uparam.getFfmpegUrl());
							String fileUrl = client.uploadFile(tmpVideoUrl,param);
							result.setFileUrl(fileUrl);
							FastDFSClient clientImg =new FastDFSClient();
							if(imgUrl != null){
								File file = new File(cutUrl);
								log.debug("截图文件大小为："+file.length());
								String cutImgUrl = clientImg.uploadFile(cutUrl,param);
								if (null == cutImgUrl) {
									result.setFenum(FastDFSResultEnum.FD_FILE_CUT_ERROR);
									return result;
								}
								result.setCutImgUrl(cutImgUrl);
							}
							File redeoFile = new File(tmpVideoUrl);
							if (redeoFile.exists()) {
								redeoFile.delete();
							}
							File cutImgFile = new File(cutUrl);
							if (cutImgFile.exists()) {
								cutImgFile.delete();
							}
						} else {// 其他文件
							FastDFSClient client =new FastDFSClient();
							String fileUrl = client.uploadFile(FastDFSClient.getFileBuffer(in, fileSize), extName,param);
							result.setFileUrl(fileUrl);
						}
						result.setFenum(FastDFSResultEnum.FD_UPLOAD_SUCCESS);
						return result;
					} catch (Exception e) {
						log.error("FastDFS上传文件异常" + e.getMessage()+e.getLocalizedMessage());
						try {
							if (null != in) {
								in.close();
							}
						} catch (IOException e1) {
							log.error("关闭流异常" + e.getMessage());
						}
						result.setFenum(FastDFSResultEnum.FD_FILE_ERROR);
						return result;
					} finally {
						try {
							if (null != in) {
								in.close();
							}
						} catch (Exception e) {
							log.error("关闭流异常" + e.getMessage());
						}
					}
				} else {
					log.error("上传文件大于" + FileUploadConstant.VIDEO_MAX_SIZE + "M");
					result.setFenum(FastDFSResultEnum.UPLOAD_SIZE_BIGER);
					return result;
				}
			}
		}
		result.setFenum(FastDFSResultEnum.FD_FILE_ERROR);
		return result;
	}

	/**
	 * 存储本地文件
	 * 
	 * @param part
	 * @throws IOException
	 */
	private static void saveLocalFile(Part part, UploadParam uparam, String newName) throws IOException {
		File file = new File(uparam.getBaseImageDir(), uparam.getDir());
		if (!file.exists()) {
			file.mkdirs();
		}
		byte[] b = new byte[1024 * 1024];
		try (InputStream in = part.getInputStream();
				FileOutputStream out = new FileOutputStream(new File(file, newName));) {
			int index = 0;
			while ((index = in.read(b)) != -1) {
				out.write(b, 0, index);
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
