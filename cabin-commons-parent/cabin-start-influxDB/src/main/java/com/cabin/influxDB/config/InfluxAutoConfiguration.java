package com.cabin.influxDB.config;

import com.cabin.influxDB.empty.InfluxDBProperties;
import com.cabin.influxDB.util.InfluxDBManager;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.influxdb.client.InfluxDBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 伍六七
 * @date 2023/7/1 22:34
 */
@Configuration
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxAutoConfiguration {
    @Autowired
    private InfluxDBProperties properties;

    @Bean(name = "influxByToken")
    public InfluxDBClient getInfluxDBClient() {
        if (InfluxDBManager.getClient() == null) {
            return InfluxDBManager.connectByToken(
                    properties.getUrl(),
                    properties.getToken(),
                    properties.getBucket(),
                    properties.getOrg());
        }
        return InfluxDBManager.getClient();
    }


    @Bean(name = "InfluxDBTemplate")
    public InfluxDBTemplate getInfluxDBTemplate() {
        return InfluxDBTemplate.getSingleton();
    }
}
