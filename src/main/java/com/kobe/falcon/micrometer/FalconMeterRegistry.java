
package com.kobe.falcon.micrometer;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.step.StepMeterRegistry;
import io.micrometer.core.instrument.util.*;
import io.micrometer.core.ipc.http.HttpSender;
import io.micrometer.core.ipc.http.HttpUrlConnectionSender;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;



public class FalconMeterRegistry extends StepMeterRegistry {
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new NamedThreadFactory("falcon-metrics-publisher");
    private final FalconConfig config;
    private final HttpSender httpClient;
    private final Logger logger = LoggerFactory.getLogger(FalconMeterRegistry.class);
    private boolean databaseExists = false;

    public FalconMeterRegistry(FalconConfig config, Clock clock) {
        this(config, clock, DEFAULT_THREAD_FACTORY, new HttpUrlConnectionSender(config.connectTimeout(), config.readTimeout()));
    }


    @Deprecated
    public FalconMeterRegistry(FalconConfig config, Clock clock, ThreadFactory threadFactory) {
        this(config, clock, threadFactory, new HttpUrlConnectionSender(config.connectTimeout(), config.readTimeout()));
    }

    private FalconMeterRegistry(FalconConfig config, Clock clock, ThreadFactory threadFactory, HttpSender httpClient) {
        super(config, clock);
        config().namingConvention(new FalconNamingConvention());
        this.config = config;
        this.httpClient = httpClient;
        start(threadFactory);
    }

    public static Builder builder(FalconConfig config) {
        return new Builder(config);
    }

    @Override
    public void start(ThreadFactory threadFactory) {
        if (config.enabled()) {
            logger.info("publishing metrics to falcon every " + TimeUtils.format(config.step()));
        }
        super.start(threadFactory);
    }


    @Autowired
    private FalconMicrometer falconMicrometer;

    @Override
    protected void publish() {

        try {
            for (List<Meter> batch : MeterPartition.partition(this, config.batchSize())) {

                Iterator<Meter> iterator = batch.iterator();
                while (iterator.hasNext()){
                    Meter next = iterator.next();
                    Meter.Id id = next.getId();
                    String name = id.getName();

                    if (next instanceof Counter) {
                        Counter counter = (Counter) next;
                        List<Tag> tags = id.getTags();
                        List<String> collect1 = tags.stream().filter(t -> StringUtils.isNotBlank(t.getValue())).map(t -> t.getKey() + "=" + t.getValue()).collect(Collectors.toList());
                        falconMicrometer.sendGaugeMetric(name, (float)counter.count(), collect1);
                    }
                }
            }
        } catch (Throwable e) {
            logger.error("failed to send metrics to falcon", e);
        }
    }

    @Override
    protected final TimeUnit getBaseTimeUnit() {
        return TimeUnit.MILLISECONDS;
    }

    public static class Builder {
        private final FalconConfig config;

        private Clock clock = Clock.SYSTEM;
        private ThreadFactory threadFactory = DEFAULT_THREAD_FACTORY;
        private HttpSender httpClient;

        @SuppressWarnings("deprecation")
        Builder(FalconConfig config) {
            this.config = config;
            this.httpClient = new HttpUrlConnectionSender(config.connectTimeout(), config.readTimeout());
        }

        public Builder clock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Builder threadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            return this;
        }

        public Builder httpClient(HttpSender httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public FalconMeterRegistry build() {
            return new FalconMeterRegistry(config, clock, threadFactory, httpClient);
        }
    }

    class Field {
        final String key;
        final double value;

        Field(String key, double value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + DoubleFormat.decimalOrNan(value);
        }
    }

}
