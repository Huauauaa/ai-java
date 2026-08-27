package com.huauauaa.syntax.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Common Map usage patterns in Java.
 */
public final class MapUsage {

    private MapUsage() {}

    public static void main(String[] args) {
        creation();
        basicOperations();
        computeMethods();
        iteration();
        mapMerge();
        streamOperations();
        orderGuarantees();
    }

    /** Ways to create maps. */
    static void creation() {
        // Mutable map
        Map<String, Integer> mutable = new HashMap<>();
        mutable.put("a", 1);

        // Immutable map (up to 10 pairs), no null keys/values allowed
        Map<String, Integer> immutable = Map.of("one", 1, "two", 2);

        // Immutable copy of an existing map
        Map<String, Integer> copy = Map.copyOf(mutable);

        // Growing an immutable map: creates a new map
        Map<String, Integer> grown = Map.copyOf(immutable);
        System.out.println("creation: " + mutable + " " + immutable + " " + copy.size() + " " + grown.size());
    }

    /** Basic get/put/remove patterns. */
    static void basicOperations() {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("alice", 90);
        scores.put("bob", 85);

        // getOrDefault avoids null checks
        int carol = scores.getOrDefault("carol", 0);

        // putIfAbsent only writes if the key is absent
        scores.putIfAbsent("alice", 100); // no effect, key exists

        // replace only writes if the key is present
        scores.replace("bob", 88);

        // remove with a value condition (returns false if value mismatches)
        boolean removed = scores.remove("bob", 999);

        // containsKey / containsValue
        boolean hasAlice = scores.containsKey("alice");
        boolean hasScore90 = scores.containsValue(90);

        System.out.println("basic: carol=" + carol + " removed=" + removed
                + " hasAlice=" + hasAlice + " hasScore90=" + hasScore90 + " " + scores);
    }

    /** computeIfAbsent / computeIfPresent / compute. */
    static void computeMethods() {
        Map<String, List<Integer>> groups = new HashMap<>();

        // The classic "multimap" idiom: initialize the list on first access
        groups.computeIfAbsent("even", k -> new java.util.ArrayList<>()).add(2);
        groups.computeIfAbsent("even", k -> new java.util.ArrayList<>()).add(4);

        Map<String, Integer> counters = new HashMap<>();
        counters.put("hit", 1);

        // compute: recalculate the value unconditionally (key must map to non-null)
        counters.compute("hit", (k, v) -> v + 1);

        // computeIfPresent: run only if the key currently has a value
        counters.computeIfPresent("hit", (k, v) -> v * 10);

        // compute with a null return removes the entry
        counters.compute("hit", (k, v) -> v > 100 ? null : v);

        System.out.println("compute: groups=" + groups + " counters=" + counters);
    }

    /** Ways to iterate a map. */
    static void iteration() {
        Map<String, Integer> map = Map.of("x", 1, "y", 2, "z", 3);

        // Entry set (full key-value access)
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.printf("entry: %s=%d%n", e.getKey(), e.getValue());
        }

        // Keys only
        for (String key : map.keySet()) {
            System.out.println("key: " + key);
        }

        // Values only
        for (Integer value : map.values()) {
            System.out.println("value: " + value);
        }

        // forEach with a BiConsumer
        map.forEach((k, v) -> System.out.println("forEach: " + k + "->" + v));
    }

    /** merge: per-key aggregation. */
    static void mapMerge() {
        Map<String, Integer> totals = new HashMap<>();

        String[] words = {"a", "b", "a", "c", "b", "a"};
        for (String w : words) {
            // If absent -> 1, otherwise add 1 to the existing value
            totals.merge(w, 1, Integer::sum);
        }

        // Merge two maps
        Map<String, Integer> other = Map.of("b", 10, "d", 5);
        other.forEach((k, v) -> totals.merge(k, v, Integer::sum));

        System.out.println("merge: " + totals);
    }

    /** Stream-based map operations. */
    static void streamOperations() {
        Map<String, Integer> prices = Map.of("apple", 3, "banana", 5, "cherry", 8);

        // Filter entries into a new map
        Map<String, Integer> expensive = prices.entrySet().stream()
                .filter(e -> e.getValue() > 4)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Build a map from a stream
        Map<String, Integer> fromList = List.of("alpha", "beta").stream()
                .collect(Collectors.toMap(s -> s, String::length));

        // Reverse a map (careful: duplicate values will throw)
        Map<Integer, String> reversed = prices.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        // Grouping
        Map<Integer, List<String>> byLength = List.of("a", "bb", "cc", "ddd").stream()
                .collect(Collectors.groupingBy(String::length));

        System.out.println("stream: expensive=" + expensive + " fromList=" + fromList
                + " reversed=" + reversed + " byLength=" + byLength);
    }

    /** HashMap vs LinkedHashMap vs TreeMap. */
    static void orderGuarantees() {
        // HashMap: no defined order
        Map<String, Integer> hash = new HashMap<>();
        hash.put("b", 2);
        hash.put("a", 1);
        hash.put("c", 3);

        // LinkedHashMap: insertion order (or access order with the 3-arg constructor)
        Map<String, Integer> linked = new LinkedHashMap<>();
        linked.put("b", 2);
        linked.put("a", 1);
        linked.put("c", 3);

        // TreeMap: sorted by natural key order (or a Comparator)
        Map<String, Integer> tree = new TreeMap<>();
        tree.put("b", 2);
        tree.put("a", 1);
        tree.put("c", 3);

        // TreeMap navigation
        TreeMap<String, Integer> nav = new TreeMap<>(tree);
        System.out.println("order: hash=" + hash + " linked=" + linked + " tree=" + tree
                + " firstKey=" + nav.firstKey() + " floorKey(b)=" + nav.floorKey("b"));
    }
}
