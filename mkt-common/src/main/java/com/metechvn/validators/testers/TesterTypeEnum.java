package com.metechvn.validators.testers;


import com.metechvn.validators.testers.impl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum TesterTypeEnum {
    REQUIRED("required", new RequiredTester()),
    STRING_LENGTH_MIN("string_length_min", new StringLengthMinTester()),
    STRING_PATTERN("string_pattern", new StringPatternTester()),
    STRING_LENGTH_MAX("string_length_max", new StringLengthMaxTester()),
    LONG_MIN("long_min", new LongMinTester()),
    LONG_MAX("long_max", new LongMaxTester()),
    LONG_NEGATIVE("long_negative", new LongNegativeTester()),
    DOUBLE_MIN("double_min", new DoubleMinTester()),
    DOUBLE_MAX("double_max", new DoubleMaxTester()),
    DOUBLE_POINT("floating_point", new FloatingPointTester()),
    BOOLEAN("boolean", new BooleanTester()),
    DATETIME_MIN("datetime_min", new DatetimeMinTester()),
    DATETIME_MAX("datetime_max", new DatetimeMaxTester()),
    LIST_SIZE("list_size", new ListSizeTester()),
    LIST_MAX_CHOICE("max_choice", new MaxChoiceTester()),
    ;
    private String type;
    private ITester tester;

    private static final Map<String, TesterTypeEnum> MAP_TYPE_ENUM = new ConcurrentHashMap<>();

    TesterTypeEnum(String type, ITester tester) {
        this.type = type;
        this.tester = tester;
    }

    public ITester getTester() {
        return tester;
    }

    public synchronized static TesterTypeEnum getTesterByType(String type) {
        if (MAP_TYPE_ENUM.size() == 0) {
            for (TesterTypeEnum value : TesterTypeEnum.values()) {
                MAP_TYPE_ENUM.put(value.type, value);
            }
        }
        return MAP_TYPE_ENUM.get(type);
    }
}
