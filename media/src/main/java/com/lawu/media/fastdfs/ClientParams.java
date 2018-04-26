/**
 * 
 */
package com.lawu.media.fastdfs;

/**
 * @author lihj <br/>
 *         connect_timeout = 2 <br/>
 *         network_timeout = 30 <br/>
 *         charset = ISO8859-1 <br/>
 *         http.tracker_http_port = 80 <br/>
 *         http.anti_steal_token = true <br/>
 *         http.secret_key = FastDFS1234567890 <br/>
 *         tracker_server = 192.168.1.180:22122,192.168.1.181:22122 <br/>
 * @date 2017年7月24日
 */
public class ClientParams {

	/**
	 * 连接超时2s
	 */
	private int connectTimeout = 2;
	/**
	 * 网络超时30s
	 */
	private int networkTimeout = 30;
	/**
	 * 设置字符编码 ISO8859-1
	 */
	private String charset = "ISO8859-1";
	/**
	 * tracker 和storage 服务地址以及端口逗号隔开
	 */
	private String trackerServer;
	/**
	 * 设置http访问端口
	 */
	private int trackerHttpPort;
	/**
	 * 开启token
	 */
	private boolean antiStealToken = true;
	/**
	 * 秘钥
	 */
	private String secretKey = "FastDFS1234567890";

	/**
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param connectTimeout
	 *            the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the networkTimeout
	 */
	public int getNetworkTimeout() {
		return networkTimeout;
	}

	/**
	 * @param networkTimeout
	 *            the networkTimeout to set
	 */
	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset
	 *            the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the trackerServer
	 */
	public String getTrackerServer() {
		return trackerServer;
	}

	/**
	 * @param trackerServer
	 *            the trackerServer to set
	 */
	public void setTrackerServer(String trackerServer) {
		this.trackerServer = trackerServer;
	}

	public int getTrackerHttpPort() {
		return trackerHttpPort;
	}

	public void setTrackerHttpPort(int trackerHttpPort) {
		this.trackerHttpPort = trackerHttpPort;
	}

	/**
	 * @return the antiStealToken
	 */
	public boolean getAntiStealToken() {
		return antiStealToken;
	}

	/**
	 * @param antiStealToken
	 *            the antiStealToken to set
	 */
	public void setAntiStealToken(boolean antiStealToken) {
		this.antiStealToken = antiStealToken;
	}

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * @param secretKey
	 *            the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
