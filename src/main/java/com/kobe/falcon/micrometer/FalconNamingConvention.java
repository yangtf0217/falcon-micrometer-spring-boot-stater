
package com.kobe.falcon.micrometer;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.core.lang.Nullable;

import java.util.regex.Pattern;

public class FalconNamingConvention implements NamingConvention {


    private static final Pattern PATTERN_SPECIAL_CHARACTERS = Pattern.compile("([, =\"])");

    private final NamingConvention delegate;


    public FalconNamingConvention() {
        this(NamingConvention.snakeCase);
    }

    public FalconNamingConvention(NamingConvention delegate) {
        this.delegate = delegate;
    }

    @Override
    public String name(String name, Meter.Type type, @Nullable String baseUnit) {
        return escape(delegate.name(name, type, baseUnit).replace("=", "_"));
    }

    @Override
    public String tagKey(String key) {
        // `time` cannot be a field key or tag key
        if (key.equals("time"))
            throw new IllegalArgumentException("'time' is an invalid tag key in falconDB");
        return escape(delegate.tagKey(key));
    }

    @Override
    public String tagValue(String value) {
        // `time` cannot be a field key or tag key
        if (value.equals("time"))
            throw new IllegalArgumentException("'time' is an invalid tag value in falconDB");
        return escape(this.delegate.tagValue(value));
    }

    private String escape(String string) {
        return PATTERN_SPECIAL_CHARACTERS.matcher(string).replaceAll("\\\\$1");
    }
}
