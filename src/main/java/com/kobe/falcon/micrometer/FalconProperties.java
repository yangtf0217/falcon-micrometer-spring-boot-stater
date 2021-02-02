package com.kobe.falcon.micrometer;

import io.micrometer.spring.autoconfigure.export.StepRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "management.metrics.export.falcon")
public class FalconProperties extends StepRegistryProperties {

    private String db;
    private FalconConsistency consistency;
    private String userName;
    private String password;
    private String retentionPolicy;
    private String uri;
    private Boolean compressed;
    private Boolean autoCreateDb;
    private String retentionDuration;
    private Integer retentionReplicationFactor;
    private String retentionShardDuration;

    public FalconProperties() {
    }

    public String getDb() {
        return this.db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public FalconConsistency getConsistency() {
        return this.consistency;
    }

    public void setConsistency(FalconConsistency consistency) {
        this.consistency = consistency;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetentionPolicy() {
        return this.retentionPolicy;
    }

    public void setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Boolean getCompressed() {
        return this.compressed;
    }

    public void setCompressed(Boolean compressed) {
        this.compressed = compressed;
    }

    public Boolean getAutoCreateDb() {
        return this.autoCreateDb;
    }

    public void setAutoCreateDb(Boolean autoCreateDb) {
        this.autoCreateDb = autoCreateDb;
    }

    public String getRetentionDuration() {
        return this.retentionDuration;
    }

    public void setRetentionDuration(String retentionDuration) {
        this.retentionDuration = retentionDuration;
    }

    public Integer getRetentionReplicationFactor() {
        return this.retentionReplicationFactor;
    }

    public void setRetentionReplicationFactor(Integer retentionReplicationFactor) {
        this.retentionReplicationFactor = retentionReplicationFactor;
    }

    public String getRetentionShardDuration() {
        return this.retentionShardDuration;
    }

    public void setRetentionShardDuration(String retentionShardDuration) {
        this.retentionShardDuration = retentionShardDuration;
    }
}
