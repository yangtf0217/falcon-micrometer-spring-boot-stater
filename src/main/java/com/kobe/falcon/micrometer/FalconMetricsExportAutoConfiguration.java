package com.kobe.falcon.micrometer;

import io.micrometer.core.instrument.Clock;
import io.micrometer.spring.autoconfigure.CompositeMeterRegistryAutoConfiguration;
import io.micrometer.spring.autoconfigure.MetricsAutoConfiguration;
import io.micrometer.spring.autoconfigure.export.StringToDurationConverter;
import io.micrometer.spring.autoconfigure.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@AutoConfigureBefore({CompositeMeterRegistryAutoConfiguration.class, SimpleMetricsExportAutoConfiguration.class})
@AutoConfigureAfter({MetricsAutoConfiguration.class})
@ConditionalOnBean({Clock.class})
@ConditionalOnClass({FalconMeterRegistry.class})
@ConditionalOnProperty(
        prefix = "management.metrics.export.falcon",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = false
)
@EnableConfigurationProperties({FalconProperties.class})
@Import({StringToDurationConverter.class})
public class FalconMetricsExportAutoConfiguration {


    public FalconMetricsExportAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({FalconConfig.class})
    public FalconConfig falconConfig(FalconProperties props) {
        return new FalconPropertiesConfigAdapter(props);
    }

    @Bean
    @ConditionalOnMissingBean
    public FalconMeterRegistry falconMeterRegistry(FalconConfig config, Clock clock) {
        return new FalconMeterRegistry(config, clock);
    }
}
