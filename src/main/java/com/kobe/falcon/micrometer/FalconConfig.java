/**
 * Copyright 2017 Pivotal Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kobe.falcon.micrometer;

import io.micrometer.core.instrument.step.StepRegistryConfig;
import io.micrometer.core.lang.Nullable;


public interface FalconConfig extends StepRegistryConfig {
    /**
     * Accept configuration defaults
     */
    FalconConfig DEFAULT = k -> null;

    @Override
    default String prefix() {
        return "falcon";
    }

    /**
     * @return The db to send metrics to. Defaults to "mydb".
     */
    default String db() {
        String v = get(prefix() + ".db");
        return v == null ? "mydb" : v;
    }

    /**
     * @return Sets the write consistency for each point. The falcon default is 'one'. Must
     * be one of 'any', 'one', 'quorum', or 'all'. Only available for falconEnterprise clusters.
     */
    default FalconConsistency consistency() {
        String v = get(prefix() + ".consistency");
        if (v == null)
            return FalconConsistency.ONE;
        return FalconConsistency.valueOf(v.toUpperCase());
    }

    /**
     * @return Authenticate requests with this user. By default is {@code null}, and the registry will not
     * attempt to present credentials to falcon.
     */
    @Nullable
    default String userName() {
        return get(prefix() + ".userName");
    }

    /**
     * @return Authenticate requests with this password. By default is {@code null}, and the registry will not
     * attempt to present credentials to falcon.
     */
    @Nullable
    default String password() {
        return get(prefix() + ".password");
    }

    /**
     * @return falcon writes to the DEFAULT retention policy if one is not specified.
     */
    @Nullable
    default String retentionPolicy() {
        return get(prefix() + ".retentionPolicy");
    }

    /**
     * @return Time period for which falcon should retain data in the current database (e.g. 2h, 52w).
     */
    @Nullable
    default String retentionDuration() {
        return get(prefix() + ".retentionDuration");
    }

    /**
     * @return How many copies of the data are stored in the cluster. Must be 1 for a single node instance.
     */
    @Nullable
    default Integer retentionReplicationFactor() {
        String v = get(prefix() + ".retentionReplicationFactor");
        return v == null ? null : Integer.valueOf(v);
    }

    /**
     * @return The time range covered by a shard group (e.g. 2h, 52w).
     */
    @Nullable
    default String retentionShardDuration() {
        return get(prefix() + ".retentionShardDuration");
    }


    /**
     * @return The URI for the falcon backend. The default is {@code http://localhost:8086}.
     */
    default String uri() {
        String v = get(prefix() + ".uri");
        return (v == null) ? "http://localhost:8086" : v;
    }

    /**
     * @return {@code true} if metrics publish batches should be GZIP compressed, {@code false} otherwise.
     */
    default boolean compressed() {
        String v = get(prefix() + ".compressed");
        return v == null || Boolean.valueOf(v);
    }

    /**
     * @return {@code true} if Micrometer should check if {@link #db()} exists before attempting to publish
     * metrics to it, creating it if it does not exist.
     */
    default boolean autoCreateDb() {
        String v = get(prefix() + ".autoCreateDb");
        return v == null || Boolean.valueOf(v);
    }
}
