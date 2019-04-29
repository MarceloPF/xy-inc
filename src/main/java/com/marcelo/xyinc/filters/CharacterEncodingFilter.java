package com.marcelo.xyinc.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

/** all request force encoder UTF-8 */

@Configurable
@WebFilter(filterName = "encoding", urlPatterns = { "/*" }, dispatcherTypes = { DispatcherType.ASYNC,
		DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.ERROR }, initParams = {
				@WebInitParam(name = "encoding", value = "UTF-8"),
				@WebInitParam(name = "forceEncoding", value = "true") })
public class CharacterEncodingFilter implements Filter {

	private static final Log LOG = LogFactory.getLog(CharacterEncodingFilter.class);

	private String encoding;

	@Override
	public void destroy() {
		LOG.info("destroy");
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) {
		try {
			req.setCharacterEncoding(encoding);
			resp.setCharacterEncoding(encoding);
			chain.doFilter(req, resp);
		} catch (final ServletException e) {
			LOG.error("ServletException", e);
		} catch (final IOException e) {
			LOG.error("IOException", e);
		}
	}

	@Override
	public void init(final FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
		LOG.info("init");
	}

	public String getEncoding() {
		return encoding;
	}

}
