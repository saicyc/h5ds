package com.chinait.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.chinait.utils.Constance;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录验证码验证过滤器
 *
 * @author cyc
 */
public class UserFilter extends GenericFilterBean {
    private RequestMatcher loginRequestMatcher;
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "j_captcha";
    // 验证码
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
    // 验证码开关，默认false
    private boolean openCaptcha = false;
    private boolean postOnly = true;

    public UserFilter() {
        setFilterProcessesUrl("/j_spring_security_check");
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.loginRequestMatcher = new FilterProcessUrlRequestMatcher(filterProcessesUrl);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!requiresLogin(request, response)) {
            chain.doFilter(request, response);

            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process captcha authentication");
        }

        try {
            attemptAuthentication(request);
        } catch (InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            unsuccessfulAuthentication(request, response, failed);

            return;
        } catch (AuthenticationException failed) {
            // Authentication failed
            unsuccessfulAuthentication(request, response, failed);

            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * 属性验证
     *
     * @param request so that request attributes can be retrieved
     *
     * @throws AuthenticationException
     */
    public void attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 验证验证码
        if (isOpenCaptcha())
            checkCaptcha(request);
    }

    /**
     * 验证验证码
     *
     * @param request so that request attributes can be retrieved
     */
    private void checkCaptcha(HttpServletRequest request) {
        String captcha = obtainCaptcha(request).toLowerCase();

        String code = (String) request.getSession().getAttribute(Constance.CHECK_CODE);

        if (!captcha.equals(code))
            throw new UsernameNotFoundException("Captcha has error");
    }

    /**
     * Default behaviour for unsuccessful authentication.
     * <ol>
     * <li>Clears the {@link SecurityContextHolder}</li>
     * <li>Stores the exception in the session (if it exists or <tt>allowSesssionCreation</tt> is set to <tt>true</tt>)</li>
     * <li>Delegates additional behaviour to the {@link AuthenticationFailureHandler}.</li>
     * </ol>
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication request failed: " + failed.toString());
            logger.debug("Updated SecurityContextHolder to contain null Authentication");
            logger.debug("Delegating to authentication failure handler " + failureHandler);
        }

        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    protected AuthenticationFailureHandler getFailureHandler() {
        return failureHandler;
    }

    /**
     * Enables subclasses to override the composition of the captcha
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the captcha
     */
    protected String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(captchaParameter);
    }

    public void setCaptchaParameter(String captchaParameter) {
        Assert.hasText(captchaParameter, "Captcha parameter must not be empty or null");
        this.captchaParameter = captchaParameter;
    }

    public void setOpenCaptcha(boolean openCaptcha) {
        this.openCaptcha = openCaptcha;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter.
     * If set to true, and an authentication request is received which is not a POST request, an exception will
     * be raised immediately and authentication will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
     * will be called as if handling a failed authentication.
     * <p/>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public boolean isOpenCaptcha() {
        return openCaptcha;
    }

    public final String getCaptchaParameter() {
        return captchaParameter;
    }

    /**
     * Allow subclasses to modify when a login should take place.
     *
     * @param request the request
     * @param response the response
     *
     * @return <code>true</code> if logout should occur, <code>false</code> otherwise
     */
    protected boolean requiresLogin(HttpServletRequest request, HttpServletResponse response) {
        return loginRequestMatcher.matches(request);
    }

    private static final class FilterProcessUrlRequestMatcher implements RequestMatcher {
        private final String filterProcessesUrl;

        private FilterProcessUrlRequestMatcher(String filterProcessesUrl) {
            Assert.hasLength(filterProcessesUrl, "filterProcessesUrl must be specified");
            Assert.isTrue(UrlUtils.isValidRedirectUrl(filterProcessesUrl), filterProcessesUrl +
                                                                           " isn't a valid redirect URL");
            this.filterProcessesUrl = filterProcessesUrl;
        }

        public boolean matches(HttpServletRequest request) {
            String uri = request.getRequestURI();
            int pathParamIndex = uri.indexOf(';');

            if (pathParamIndex > 0) {
                // strip everything from the first semi-colon
                uri = uri.substring(0, pathParamIndex);
            }

            int queryParamIndex = uri.indexOf('?');

            if (queryParamIndex > 0) {
                // strip everything from the first question mark
                uri = uri.substring(0, queryParamIndex);
            }

            if ("".equals(request.getContextPath())) {
                return uri.endsWith(filterProcessesUrl);
            }

            return uri.endsWith(request.getContextPath() + filterProcessesUrl);
        }
    }
}
