package com.afkl.travel.exercise;

import org.junit.Test;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@EnableAutoConfiguration(exclude = {MetricsEndpointAutoConfiguration.class, MetricsAutoConfiguration.class})
public class ApplicationTest {


    @Test
    public void contextLoads() {
    }

}
