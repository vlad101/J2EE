package com.serendipity.csrf;

import com.google.common.cache.Cache;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author vladimir
 */

public class ValidateSalt implements Filter  {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        // Assume its HTTP
        HttpServletRequest httpReq = (HttpServletRequest) request;

        // Get the salt sent with the request
        String salt = (String) httpReq.getParameter("csrfPreventionSalt");

        // Validate that the salt is in the cache
        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>)
            httpReq.getSession().getAttribute("csrfPreventionSaltCache");

        if (csrfPreventionSaltCache != null &&
                salt != null &&
                csrfPreventionSaltCache.getIfPresent(salt) != null){

            // If the salt is in the cache, we move on
            chain.doFilter(request, response);
        } else {
            // Otherwise we throw an exception aborting the request flow
            throw new ServletException("Potential CSRF detected!! Inform a scary sysadmin ASAP.");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}