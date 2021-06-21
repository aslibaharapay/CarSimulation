package com.mercedes.vehicle.api.car.simulator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Request ve Response bilgilerinin loglamasını sağlayan interceptor.
 */
@Component
public class CustomHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(CustomHandlerInterceptorAdapter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("1.Pre Handle method is Calling");

		ServletRequest servletRequest = new ContentCachingRequestWrapper(request);
		servletRequest.getParameterMap();
		logRequest(request, handler);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		log.info("[postHandle][" + request + "]");

		System.out.println("2.Post Handle method is Calling");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {

		logResponse(request, response, handler);
		log.info("[afterCompletion][" + request + "][exception: " + exception + "]");

		System.out.println("3.Request and Response is completed");
	}

	public void logRequest(HttpServletRequest httpServletRequest, Object body) {
		StringBuilder stringBuilder = new StringBuilder();
		Map<String, String> parameters = buildParametersMap(httpServletRequest);

		stringBuilder.append("\n REQUEST ");
		stringBuilder.append("\n method=[").append(httpServletRequest.getMethod()).append("] ");
		stringBuilder.append("\n path=[").append(httpServletRequest.getRequestURI()).append("] ");
		stringBuilder.append("\n headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");

		if (!parameters.isEmpty()) {
			stringBuilder.append("\n parameters=[").append(parameters).append("] ");
		}

		if (body != null) {
			stringBuilder.append("\n body=[" + body + "]");
		}

		log.info(stringBuilder.toString());
	}


	public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("\n RESPONSE ");
		stringBuilder.append("\n method=[").append(httpServletRequest.getMethod()).append("] ");
		stringBuilder.append("\n path=[").append(httpServletRequest.getRequestURI()).append("] ");
		stringBuilder.append("\n responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
		stringBuilder.append("\n responseBody=[").append(body).append("] ");

		log.info(stringBuilder.toString());
	}

	private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
		Map<String, String> resultMap = new HashMap<>();
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String key = parameterNames.nextElement();
			String value = httpServletRequest.getParameter(key);
			resultMap.put(key, value);
		}

		return resultMap;
	}

	private Map<String, String> buildHeadersMap(HttpServletRequest request) {
		List<String> headerNames = Collections.list(request.getHeaderNames());

		Map<String, String> map = new HashMap<>(headerNames.size());
		for (String key : headerNames) {
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}

	private Map<String, String> buildHeadersMap(HttpServletResponse response) {
		Map<String, String> map = new HashMap<>(response.getHeaderNames().size());

		Collection<String> headerNames = response.getHeaderNames();
		for (String header : headerNames) {
			map.put(header, response.getHeader(header));
		}

		return map;
	}
}
