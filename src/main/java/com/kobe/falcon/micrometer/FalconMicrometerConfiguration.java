package com.kobe.falcon.micrometer;

import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(FeignClientsConfiguration.class)
public class FalconMicrometerConfiguration {

    @Value("${falcon.api.url}")
    private String path;


    @Bean
    public FalconClient falconClient(Contract contract, Decoder decoder, Encoder encoder){


        return Feign.builder().contract(contract).encoder(encoder).decoder(decoder).target(FalconClient.class, "http://"+path);
    }


    @Bean
    public FalconMicrometer falconMicrometer(){
        return new FalconMicrometer();
    }


}
