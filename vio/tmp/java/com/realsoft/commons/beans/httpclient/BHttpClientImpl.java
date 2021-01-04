package com.realsoft.commons.beans.httpclient;

import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.utils.system.SystemUtils;

public class BHttpClientImpl implements IBHttpClient {

	private static Logger log = Logger.getLogger(BHttpClientImpl.class);

	private int connectionTimeOutMillis = 0;

	private String proxyHost = null;

	private int proxyPort = -1;

	private HttpClient client = null;

	private String keystoreFile = null;

	private String keystorePassword = null;

	public BHttpClientImpl() {
	}

	private InputStream execute(HttpMethod method) throws BHttpClientException {
		String url = null;
		try {
			url = method.getURI().toString();
			int code = client.executeMethod(method);
			log.debug("Method of url " + url + " executed with code = " + code);
			return method.getResponseBodyAsStream();
		} catch (Exception e) {
			log.error("Could not execute get method", e);
			throw new BHttpClientException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.http-client.executeMethod.error",
					"Could not execute get method of url = " + url, e);
		}
	}

	public InputStream executeGetMethod(String url) throws BHttpClientException {
		HttpMethod get = new GetMethod(url);
		return execute(get);
	}

	public InputStream executePostMethod(String url)
			throws BHttpClientException {
		HttpMethod get = new PostMethod(url);
		return execute(get);
	}

	public int getConnectionTimeOutMillis() {
		return connectionTimeOutMillis;
	}

	public void setConnectionTimeOutMillis(int connectionTimeOut) {
		this.connectionTimeOutMillis = connectionTimeOut;
	}

	public void initialize() throws URISyntaxException {
		if (keystoreFile != null) {
			SystemUtils.setPropertyValue("java.protocol.handler.pkgs",
					"com.sun.net.ssl.internal.www.protocol");
			SystemUtils.setPropertyValue("javax.net.ssl.trustStore", getClass()
					.getClassLoader().getResource(keystoreFile).getPath());
			SystemUtils.setPropertyValue("javax.net.ssl.trustStorePassword",
					keystorePassword);
		}
		client = new HttpClient(new MultiThreadedHttpConnectionManager());
		if (proxyHost != null && proxyPort != -1) {
			client.getHostConfiguration().setProxy(proxyHost, proxyPort);
			// client.getState().setProxyCredentials(new AuthScope(host, port,
			// AuthScope.ANY_REALM), new UsernamePasswordCredentials(proxyUser,
			// proxyPassword));
		}
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				connectionTimeOutMillis);
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getKeystoreFile() {
		return keystoreFile;
	}

	public void setKeystoreFile(String keystore) {
		this.keystoreFile = keystore;
	}

	public String getKeystorePassword() {
		return keystorePassword;
	}

	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

}
