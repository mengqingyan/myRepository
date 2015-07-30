/**
 * 
 */
package com.revencoft.validation.utils.matcher;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

/**
 * @author mengqingyan
 * @version 
 */
public final class RegexRequestMatcher implements RequestMatcher {

	private final Pattern pattern;
    private final HttpMethod httpMethod;
	private static final Logger log = Logger.getLogger(RegexRequestMatcher.class);
	
    
    public RegexRequestMatcher(String pattern, String httpMethod) {
        this(pattern, httpMethod, false);
    }

    public RegexRequestMatcher(String pattern, String httpMethod, boolean caseInsensitive) {
        if (caseInsensitive) {
            this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        } else {
            this.pattern = Pattern.compile(pattern);
        }
        this.httpMethod = StringUtils.hasText(httpMethod) ? HttpMethod.valueOf(httpMethod) : null;
    }
    
    
    public boolean matches(HttpServletRequest request) {
        if (httpMethod != null && request.getMethod() != null && httpMethod != valueOf(request.getMethod())) {
            return false;
        }

        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String query = request.getQueryString();

        if (pathInfo != null || query != null) {
            StringBuilder sb = new StringBuilder(url);

            if (pathInfo != null) {
                sb.append(pathInfo);
            }

            if (query != null) {
                sb.append('?').append(query);
            }
            url = sb.toString();
        }

        if (log .isDebugEnabled()) {
            log.debug("Checking match of request : '" + url + "'; against '" + pattern + "'");
        }

        return pattern.matcher(url).matches();
    }


    private static HttpMethod valueOf(String method) {
        try {
            return HttpMethod.valueOf(method);
        } catch(IllegalArgumentException e) {}

        return null;
    }

}
