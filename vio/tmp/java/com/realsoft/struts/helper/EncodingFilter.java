/*
 * Created on 04.05.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: EncodingFilter.java,v 1.2 2016/04/15 10:37:26 dauren_home Exp $
 */
package com.realsoft.struts.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

/**
 * Encoding filter.
 * 
 * @author Oleg Babintsev
 * @version 1.0
 * 
 */
public class EncodingFilter implements Filter {

	private static Logger log = Logger.getLogger(EncodingFilter.class);

	private String encoding = null;

	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
		log.debug("determine character encoding : " + encoding);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (encoding != null) {
			try {
				log.debug("request character encoding = "
						+ request.getCharacterEncoding());
				request.setCharacterEncoding(encoding);
			} catch (UnsupportedEncodingException uee) {
				log.error(uee);
			}
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}