
package com.afkl.travel.exercise.configuration;


import com.afkl.travel.exercise.filter.MetricsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.user.name}")
    private String userName;

    @Value("${spring.security.user.password}")
    private String userPassword;

    @Value("${spring.security.user.role}")
    private String userRole;

    @Value("${spring.security.actuator.name}")
    private String actuatorUserName;

    @Value("${spring.security.actuator.password}")
    private String actuatorUserPassword;

    @Value("${spring.security.actuator.role}")
    private String actuatorUserRole;

    @Autowired
    private MetricsFilter metricsFilter;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/travel/**").hasRole(userRole)
                .antMatchers("/actuator/**").hasRole(actuatorUserRole)
                .antMatchers("/h2_console/**").permitAll()
                .anyRequest().permitAll().and().httpBasic();
        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication()
                .withUser(userName).password("{noop}" + userPassword).roles(userRole)
                .and()
                .withUser(actuatorUserName).password("{noop}" + actuatorUserPassword).roles(actuatorUserRole);
    }

    @Bean
    public FilterRegistrationBean metricFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(metricsFilter);
        registration.addUrlPatterns("/travel/*");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}

