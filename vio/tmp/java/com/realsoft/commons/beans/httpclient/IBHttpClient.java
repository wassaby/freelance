package com.realsoft.commons.beans.httpclient;

import java.io.InputStream;

public interface IBHttpClient {
	InputStream executeGetMethod(String url) throws BHttpClientException;

	InputStream executePostMethod(String url) throws BHttpClientException;
}
