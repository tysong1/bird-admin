package com.tysong.bird.admin.common.bean;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChartBean {
    List<String> labels = Lists.newArrayList();
    List<DataSet> datasets= Lists.newArrayList();

    @Getter
    @Setter
    public static class DataSet{
        String label;
        List<Double> data=Lists.newArrayList();
        Boolean fill;
        String backgroundColor;
        String borderColor;
    }
}
