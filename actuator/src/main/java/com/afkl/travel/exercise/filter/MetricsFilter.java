package com.afkl.travel.exercise.filter;


import com.afkl.travel.exercise.service.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MetricsFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsFilter.class);
    @Autowired
    private MetricsService metricsService;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws java.io.IOException, ServletException {
        LOGGER.info("request is in MetricsFilter ");
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        final int status = ((HttpServletResponse) response).getStatus();
        LOGGER.debug("response status :" + status);
        long elapsedTime = System.currentTimeMillis() - startTime;
        LOGGER.debug("time taken by request :" + elapsedTime);
        metricsService.increaseCount(status, elapsedTime);

    }

}
