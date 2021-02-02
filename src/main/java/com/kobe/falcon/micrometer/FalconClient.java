package com.kobe.falcon.micrometer;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(name = "falcon",path = "/", url = "127.0.0.1:1988")
//@FeignClient(name = "falcon",path = "/", url = "falcon.api")
//@FeignClient(name = "falcon",path = "/", url = "${falcon.api.url}")
public interface FalconClient {

    @PostMapping(value = "/v1/push", consumes = MediaType.APPLICATION_JSON_VALUE)
    String sendMicrometer(@RequestBody List<FalconMicrometerDto> falconMicrometerList);
}
