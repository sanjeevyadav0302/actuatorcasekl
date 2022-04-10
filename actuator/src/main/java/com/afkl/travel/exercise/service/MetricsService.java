package com.afkl.travel.exercise.service;

import com.afkl.travel.exercise.model.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class MetricsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsService.class);
    private ConcurrentMap<String, Integer> statusMetric;
    private ConcurrentMap<String, Long> timerMetric;
    private static int count = 0;
    private Metrics metrics = new Metrics();

    public MetricsService() {
        statusMetric = new ConcurrentHashMap<String, Integer>();
        timerMetric = new ConcurrentHashMap<String, Long>();

    }

    public void increaseCount(final int status, long elapsedTime) {
        LOGGER.info("increase count called for request");
        increaseStatusMetric(status);
        calculateTimeForRequest(elapsedTime);
    }


    public Metrics getStatusMetric() {
        LOGGER.info("get metrics for all request");
        if (!CollectionUtils.isEmpty(statusMetric)) {
            int totalNumberofRequest = 0;
            statusMetric.put("total_number_of_request", totalNumberofRequest);
            for (int value : statusMetric.values()) {
                totalNumberofRequest += value;
            }
            //int temp = statusMetric.get("total_number_of_request");
            statusMetric.put("total_number_of_request", totalNumberofRequest);
            metrics.setCounter(statusMetric);
            metrics.setTimer(timerMetric);
            return metrics;
        }
        return metrics;
    }

    private void increaseStatusMetric(final int status) {
        LOGGER.info("increaseStatusMetric for request");
        final Integer statusCount = statusMetric.get("total_"+ Integer.toString(status) + "_request");
        if (null != statusCount) {
            statusMetric.put("total_" + Integer.toString(status) + "_request", statusCount + 1);
        } else {
            statusMetric.put("total_" + Integer.toString(status) + "_request", 1);
        }
    }

    private void calculateTimeForRequest(long elapsedTime) {
        LOGGER.info("calculate time for request");
        LOGGER.debug("elapsed time for request" + elapsedTime);
        final Long averageTime = timerMetric.get("averageTime");
        if (averageTime == null) {
            timerMetric.put("averageTime", elapsedTime);
            timerMetric.put("maxTime", elapsedTime);
            count++;
        } else {
            count++;
            int requestCompleted = count -1;
            final long totalTime = requestCompleted * timerMetric.get("averageTime") + elapsedTime;
            LOGGER.info("totalTime  for request " + totalTime);
            LOGGER.info("Counter number " + count);
            final Long averagTime = totalTime / count;
            timerMetric.put("averageTime", averagTime);
            final Long maxTim = timerMetric.get("maxTime");
            if (elapsedTime > maxTim) {
                timerMetric.put("maxTime", elapsedTime);
            }
        }
    }


}
