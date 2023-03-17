package com.metechvn.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtil {
    public static <T> List<List<T>> partition(Collection<T> data, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        List<T> batch = new ArrayList<>();
        for (T record : data) {
            batch.add(record);
            if (batch.size() == batchSize) {
                batches.add(batch);
                batch = new ArrayList<>();
            }
        }
        batches.add(batch);
        return batches;
    }
}
