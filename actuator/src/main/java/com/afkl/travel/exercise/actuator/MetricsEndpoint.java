package com.afkl.travel.exercise.actuator;

import com.afkl.travel.exercise.model.Metrics;
import com.afkl.travel.exercise.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "metrics")
public class MetricsEndpoint {

    @Autowired
    MetricsService metricsService;

    /**
     * End point that return metrics for the application.
     * It will override the existing actuator end point /actuator/metrics
     * @return
     */
    @ReadOperation
    public Metrics metricsDetails() {
        return metricsService.getStatusMetric();
    }
}
