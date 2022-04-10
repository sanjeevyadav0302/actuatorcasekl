package com.afkl.travel.exercise;

import com.afkl.travel.exercise.client.LocationServiceRestClient;
import com.afkl.travel.exercise.model.Locations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;


@SpringBootApplication(exclude = {MetricsEndpointAutoConfiguration.class, MetricsAutoConfiguration.class})
public class Application implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    @Autowired
    LocationServiceRestClient locationServiceRestClient;

    @Bean
    @Primary
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .setName("travel-api")
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LOGGER.info("application started ");
    }

    @Override
    public void run(String... args) {
        List<Locations> airportsLocations = locationServiceRestClient.getAirportsLocation("US");
        airportsLocations.forEach(airport -> {
            LOGGER.info("Airport code:" + airport.getCode());
            LOGGER.info("Airport type:" + airport.getType().get());
            LOGGER.info("Airport latitude:" + airport.getLatitude());
            LOGGER.info("Airport longitude:" + airport.getLongitude());
        });
    }
}
