package sep.gaia.resources.caching.strategies;

import sep.gaia.resources.DataResource;
import sep.gaia.resources.caching.AdvancedCache;
import sep.gaia.resources.caching.CacheEntry;
import sep.gaia.resources.caching.CacheRemovalStrategy;

import java.io.Serializable;
import java.util.*;

/**
 * A Least-Recently-Used removal strategy to be used with a {@link sep.gaia.resources.caching.AdvancedCache}.
 * To prioritize entries their timestamp of their last usage will be compared.
 */
public class LRURemovalStrategy<K extends Serializable, R extends DataResource> extends OrderedRemovalStrategy<K, R> {

    /**
     * @param threshold The amount of elements that triggers removal when exceeded.
     * @param removeByPercentage The percentage of elements to be removed when the threshold is exceeded.
     *                           Only used when keepFixedAmount is false.
     * @param keepFixedAmount Set true if the cache should at maximum contain threshold elements or remove by
     *                        percentage if false.
     */
    public LRURemovalStrategy(int threshold, float removeByPercentage, boolean keepFixedAmount) {
        super(threshold, removeByPercentage, keepFixedAmount, -1, new Comparator<Map.Entry<K, CacheEntry<R>>>() {
            @Override
            public int compare(Map.Entry<K, CacheEntry<R>> first, Map.Entry<K, CacheEntry<R>> second) {
                return (int) (first.getValue().getLastUsageTime() - second.getValue().getLastUsageTime());
            }
        });
    }

    /**
     * @param threshold The amount of elements that triggers removal when exceeded.
     * @param removeByPercentage The percentage of elements to be removed when the threshold is exceeded.
     *                           Only used when keepFixedAmount is false.
     * @param keepFixedAmount Set true if the cache should at maximum contain threshold elements or remove by
     *                        percentage if false.
     * @param maxSortingAmount Specifies how many entries at most to be taken in consideration when doing removal.
     *                         Because the entries must be sorted and copied, large values can lead to bad performance.
     *                         If this value is -1, all elements will be inspected every time.
     */
    public LRURemovalStrategy(int threshold, float removeByPercentage, boolean keepFixedAmount, int maxSortingAmount) {
        super(threshold, removeByPercentage, keepFixedAmount, maxSortingAmount, new Comparator<Map.Entry<K, CacheEntry<R>>>() {
            @Override
            public int compare(Map.Entry<K, CacheEntry<R>> first, Map.Entry<K, CacheEntry<R>> second) {
                return (int) (first.getValue().getLastUsageTime() - second.getValue().getLastUsageTime());
            }
        });
    }

    @Override
    public void manageCache(AdvancedCache<K, R> cache) {
        // Get the caches index, all rights already granted:
        Map<K, CacheEntry<R>> index = cache.getIndex();

        // Removal only done if defined number of elements cached is exceeded:
        if(index.size() > getThreshold()) {

            LinkedHashMap<K, CacheEntry<R>> sorted = selectWeakEntries(index);

            // Remove the weak elemnents:
            Iterator<K> iterator = sorted.keySet().iterator();
            while(iterator.hasNext()) {
                // Use the caches remove method as required:
                cache.removeResource(iterator.next());
            }
        }
    }
}
