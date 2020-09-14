package com.tysong.bird.admin.common.config;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class TimeFormatConfig {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM-dd HH:mm");
    public static final DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
