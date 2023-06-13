package com.paradise.code.util;

import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : PARADISE
 * @ClassName : MapUtils
 * @description :
 * @since : 2023/6/13 21:34
 */
public class MapUtils {

    public static <K, V> Map<K, V> mergeSourceMaps(List<Map<K, V>> sources) {
        if (CollectionUtils.isEmpty(sources)) {
            return Maps.newHashMap();
        }
        return sources.stream()
                .flatMap(map -> map.entrySet().stream().filter(Objects::nonNull))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));
    }
}
