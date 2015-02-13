package sep.gaia.resources.caching.strategies;

import sep.gaia.resources.DataResource;
import sep.gaia.resources.caching.CacheEntry;
import sep.gaia.resources.caching.CacheRemovalStrategy;

import java.io.Serializable;
import java.util.*;

/**
 * This strategy supports two modes for calculating to amount of elements to be removed.
 * <ul>
 *     <li>With the {@link #keepFixedAmount} flag set, the number of elements will be at maximum the threshold (maybe more for short time).</li>
 *     <li>With the flag cleared, each time the threshold is exceeded, the least-recently-used {@link #removeByPercentage} elements will be removed.</li>
 * </ul>
 * Also this strategy supports limitation of entries to be inspected at most at each {@link #manageCache(sep.gaia.resources.caching.AdvancedCache)} call in order
 * to avoid performance issues.
 */
public abstract class OrderedRemovalStrategy<K extends Serializable, R extends DataResource> implements CacheRemovalStrategy<K, R> {

    /**
     * If this (postive) number of elements in cache are exceeded, some of them will be removed.
     * If {@link #keepFixedAmount} the cache will always hold at maximum this many elements.
     */
    private int threshold;

    /**
     * If {@link #keepFixedAmount} is false, this percentage of elements will be removed.
     * So this must be a value in range (0, 1].
     * If the named flag is true this value will be ignored.
     */
    private float removeByPercentage;

    /**
     * Whether to keep number of cached elements at maximum {@link #threshold} (true) or
     * to always remove by percentage when the value is exceeded.
     */
    private boolean keepFixedAmount;

    /**
     * Number of elements to inspect at maximum when applying the strategy.
     * If this is -1 all elements will be inspected every time.
     */
    private int maxSortingAmount = -1;

    private Comparator<Map.Entry<K, CacheEntry<R>>> comparator;

    /**
     * @param threshold If this (postive) number of elements in cache are exceeded, some of them will be removed.
     * @param removeByPercentage If <code>keepFixedAmount</code> is false, this percentage of elements will be removed.
     * @param keepFixedAmount Whether to keep number of cached elements at maximum <code>threshold</code> (true) or
     *                        to always remove by percentage when the threshold is exceeded.
     * @param maxSortingAmount Number of elements to inspect at maximum when applying the strategy. If its value is -1
     *                         all elements will be inspected whenever a cache is managed.
     * @param comparator The comparision to use when sorting the elements.
     */
    public OrderedRemovalStrategy(int threshold, float removeByPercentage, boolean keepFixedAmount, int maxSortingAmount,
                                  Comparator<Map.Entry<K, CacheEntry<R>>> comparator) {
        this.threshold = threshold;
        this.removeByPercentage = removeByPercentage;
        this.keepFixedAmount = keepFixedAmount;
        this.maxSortingAmount = maxSortingAmount;
        this.comparator = comparator;
    }

    /**
     * Sorts the index according to the comparison given and respecting the inspection limits.
     * The number of elements N is calculated by the given parameters and the first N elements of the sorted list
     * will be picked.
     * @param index The caches index.
     * @return The weakest (according to given comparison) N elements or less if the size of <code>index</code> is less
     * than N or the inspection limit is less than N.
     */
    protected LinkedHashMap<K, CacheEntry<R>> selectWeakEntries(Map<K, CacheEntry<R>> index) {
        // Calculate number of entries to remove according to current specification:
        int amountToRemove;
        if(keepFixedAmount) {
            amountToRemove = index.size() - threshold;
        } else {
            amountToRemove = (int) Math.floor(index.size() * removeByPercentage);
        }

        // Sort the entries, but at maximum a certain amount of them in order to improve performance:
        int sortingLimit = maxSortingAmount != -1 ? maxSortingAmount : Integer.MAX_VALUE;
        LinkedHashMap<K, CacheEntry<R>> sorted = sortIndexByLastUsage(index, Math.min(sortingLimit, amountToRemove));
        return sorted;
    }

    /**
     * Sorts the given index according to the last usage timestamp of its entries in ascending order and returns a copy.
     * @param index The index to be sorted.
     * @param copyCount In order to improve performance this parameter specifies how many entries to be copied at maximum.
     *                  If the value is -1 or greater than the size of <code>index</code> all entries will be copied.
     *                  In case this value is positve and less than the indexes size, the only the first elements returned
     *                  by <code>index.entrySet()</code> will be copied and sorted.
     * @return A sorted copy of the index.
     */
    private LinkedHashMap<K, CacheEntry<R>> sortIndexByLastUsage(Map<K, CacheEntry<R>> index, int copyCount) {
        List<Map.Entry<K, CacheEntry<R>>> indexEntries = new LinkedList<>();

        // Copy the entries from the index to the list respecting copy-limitations:
        Iterator<Map.Entry<K, CacheEntry<R>>> iterator = index.entrySet().iterator();
        int copiedEntries = 0;
        while(iterator.hasNext() && (copiedEntries < copyCount || copyCount == -1)) {
            indexEntries.add(iterator.next());
            copiedEntries++;
        }

        // Sort by time of last usage (higher values mean later usage):
        Collections.sort(indexEntries, comparator);

        // Copy back into a new map:
        LinkedHashMap<K, CacheEntry<R>> result = new LinkedHashMap<>();
        for (Map.Entry<K, CacheEntry<R>> entry : indexEntries) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public float getRemoveByPercentage() {
        return removeByPercentage;
    }

    public void setRemoveByPercentage(float removeByPercentage) {
        this.removeByPercentage = removeByPercentage;
    }

    public boolean limitsFixedAmount() {
        return keepFixedAmount;
    }

    public void setKeepFixedAmount(boolean keepFixedAmount) {
        this.keepFixedAmount = keepFixedAmount;
    }

    public int getMaxSortingAmount() {
        return maxSortingAmount;
    }

    public void setMaxSortingAmount(int maxSortingAmount) {
        this.maxSortingAmount = maxSortingAmount;
    }

    public Comparator<Map.Entry<K, CacheEntry<R>>> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<Map.Entry<K, CacheEntry<R>>> comparator) {
        this.comparator = comparator;
    }
}
