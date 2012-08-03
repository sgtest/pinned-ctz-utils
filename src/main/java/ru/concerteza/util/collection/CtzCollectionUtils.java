package ru.concerteza.util.collection;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.*;
import ru.concerteza.util.value.Pair;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Collection utilities
 *
 * @author alexey
 * Date: 6/9/11
 * @see CtzCollectionUtilsTest
 */
public class CtzCollectionUtils {
    @Deprecated // use ImmutableMap.of() directly
    public static final Map<String, Object> EMPTY_MAP = ImmutableMap.of();

    /**
     * Fires guava transform chain for iterators, which may produce nullable elements.
     * Will be replaced by {@code Iterators.advance(...)} in newer guava versions
     *
     * @param iter input iterator
     * @return count of transformed elements
     */
    public static long fireTransform(Iterator<?> iter) {
        int counter = 0;
        while (iter.hasNext()) {
            iter.next();
            counter +=1;
        }
        return counter;
    }

    /**
     * Fires guava transform chain for iterables, which may produce nullable elements
     *
     * @param iter input iterable
     * @return count of transformed elements
     */
    public static long fireTransform(Iterable<?> iter) {
        return fireTransform(iter.iterator());
    }

    /**
     * Produces map from key set, values are function products. Inspired by
     * <a href="http://docs.guava-libraries.googlecode.com/git-history/v12.0/javadoc/com/google/common/collect/Maps.html#uniqueIndex%28java.util.Iterator,%20com.google.common.base.Function%29">uniqueIndex</a> method
     *
     * @param keySet input set, contains maps keys
     * @param valueFunction produces map values based on set keys
     * @param <K> map key type
     * @param <V> map value type
     * @return map based on input set, values are function products
     */
    public static <K, V> ImmutableMap<K, V> keySetToMap(Set<K> keySet, Function<? super K, V> valueFunction) {
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        for(K key : keySet) {
            builder.put(key, valueFunction.apply(key));
        }
        return builder.build();
    }

    /**
     * Converts separate keys and values lists into immutable map. Lists must have the same size.
     *
     * @param keys keys list
     * @param values values list
     * @param <K> key type
     * @param <V> value type
     * @return immutable map
     */
    public static <K, V> ImmutableMap<K, V> listsToMap(List<K> keys, List<V> values) {
        checkArgument(keys.size() == values.size(), "Keys and values sizes differs, keys: '%s', values: '%s'", keys.size(), values.size());
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        for(int i = 0; i < keys.size(); i++) {
            builder.put(keys.get(i), values.get(i));
        }
        return builder.build();
    }

    /**
     * Sentinel wrapper for API's that may return null instead of empty list
     *
     * @param input list or null
     * @param <T> list value type
     * @return empty list on null input, provided list otherwise
     */
    public static <T> List<T> defaultList(@Nullable List<T> input) {
        if(null == input) return ImmutableList.of();
        return input;
    }

    /**
     * Concurrent hash set factory method
     *
     * @param <T> value type
     * @return set backed by {@link ConcurrentHashMap}
     */
    public static <T> Set<T> newConcurrentHashSet() {
        return Collections.newSetFromMap(new ConcurrentHashMap<T, Boolean>());
    }
}
