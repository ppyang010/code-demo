package com.code.config;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 范围查询所使用的分片算法
 */
public class TblRangeShardAlgo implements RangeShardingAlgorithm<Long> {
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> rangeShardingValue) {
        Collection<String> collect = new LinkedHashSet<>();
        Range<Long> valueRange = rangeShardingValue.getValueRange();
        for (Long i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
            // 不分表
            for (String each : availableTargetNames) {
                collect.add(each);
/*                if (each.endsWith(i % availableTargetNames.size() + "")) {
                    collect.add(each);
                }*/
            }
        }
        return collect;
    }

}