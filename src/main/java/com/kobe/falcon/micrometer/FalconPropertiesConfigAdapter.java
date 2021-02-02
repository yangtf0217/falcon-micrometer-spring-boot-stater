
package com.kobe.falcon.micrometer;

import io.micrometer.spring.autoconfigure.export.StepRegistryPropertiesConfigAdapter;

class FalconPropertiesConfigAdapter extends StepRegistryPropertiesConfigAdapter<FalconProperties> implements FalconConfig {

    FalconPropertiesConfigAdapter(FalconProperties properties) {
        super(properties);
    }

    @Override
    public String db() {
        return get(FalconProperties::getDb, FalconConfig.super::db);
    }

    @Override
    public FalconConsistency consistency() {
        return get(FalconProperties::getConsistency, FalconConfig.super::consistency);
    }

    @Override
    public String userName() {
        return get(FalconProperties::getUserName, FalconConfig.super::userName);
    }

    @Override
    public String password() {
        return get(FalconProperties::getPassword, FalconConfig.super::password);
    }

    @Override
    public String retentionPolicy() {
        return get(FalconProperties::getRetentionPolicy, FalconConfig.super::retentionPolicy);
    }

    @Override
    public Integer retentionReplicationFactor() {
        return get(FalconProperties::getRetentionReplicationFactor, FalconConfig.super::retentionReplicationFactor);
    }

    @Override
    public String retentionDuration() {
        return get(FalconProperties::getRetentionDuration, FalconConfig.super::retentionDuration);
    }

    @Override
    public String retentionShardDuration() {
        return get(FalconProperties::getRetentionShardDuration, FalconConfig.super::retentionShardDuration);
    }

    @Override
    public String uri() {
        return get(FalconProperties::getUri, FalconConfig.super::uri);
    }

    @Override
    public boolean compressed() {
        return get(FalconProperties::getCompressed, FalconConfig.super::compressed);
    }

    @Override
    public boolean autoCreateDb() {
        return get(FalconProperties::getAutoCreateDb, FalconConfig.super::autoCreateDb);
    }
}
