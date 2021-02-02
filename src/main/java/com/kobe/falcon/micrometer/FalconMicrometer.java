package com.kobe.falcon.micrometer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FalconMicrometer implements InitializingBean {

    @Autowired
    private FalconClient falconClient;


    private String hostName = "localhost";


    @Async
    public Response sendGaugeMetric(String metric, Float value, List<String> tags) {

        if(Objects.isNull(metric) || Objects.isNull(value) || CollectionUtils.isEmpty(tags)){
            return Response.FAIL;
        }

        Response response = null;
        try{
            FalconMicrometerDto falconMicrometerDto = new FalconMicrometerDto();
            falconMicrometerDto.setMetric(metric);

//            Map<String, String> getenv = System.getenv();
//            String hostname = getenv.get("HOSTNAME");
//            if(StringUtils.isBlank(hostname)){
//                hostname = getenv.get("COMPUTERNAME");
//            }
//            if(StringUtils.isBlank(hostname)){
//                hostname = InetAddress.getLocalHost().getHostName();
//            }

            falconMicrometerDto.setEndpoint(hostName);
            falconMicrometerDto.setTimestamp(System.currentTimeMillis()/1000-3600);
            falconMicrometerDto.setValue(value);
            falconMicrometerDto.setStep(60);
            falconMicrometerDto.setTags(StringUtils.join(tags,","));
            falconMicrometerDto.setCounterType(CounterTypeEnum.GAUGE.name());

            if(log.isDebugEnabled()){
                log.debug("FalconMicrometer.sendGaugeMetric falconMicrometerDto:{}",falconMicrometerDto);
            }

            String result = falconClient.sendMicrometer(Arrays.asList(falconMicrometerDto));

            if(log.isDebugEnabled()){
                log.debug("FalconMicrometer.sendGaugeMetric result:{}",result);
            }

            if(StringUtils.equals("000000",result)){
                response = Response.makeResponse(ErrorCode.FAIL.getCode(),result,result);
            } else {
                response = Response.makeResponse(ErrorCode.SUCCESS.getCode(),result,result);
            }


        } catch (Exception e){
            log.error("FalconMicrometer.sendGaugeMetric error msg:{}",e.getMessage(),e);
            response = Response.makeResponse(ErrorCode.FAIL.getCode(),e.getMessage(),"");
        }

        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("FalconMicrometer.afterPropertiesSet");
        Process hostname = Runtime.getRuntime().exec("hostname");
        try(BufferedReader stdInput = new BufferedReader(new InputStreamReader(hostname.getInputStream()));){
            String s;
            while ((s = stdInput.readLine()) != null) {
                log.info("FalconMicrometer.afterPropertiesSet hostName:{}",s);
                hostName = s;
            }
        }
    }
}
