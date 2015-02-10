package sep.gaia.resources.caching;

import sep.gaia.resources.DataResource;

import java.io.Serializable;

/**
 * Implementation of this interface should remove entries of the caches its assigned to using a
 * certain strategy.
 * This must be done by implementing {@link #manageCache(AdvancedCache)} which is called
 * on each insertion to a cache.
 */
public interface CacheRemovalStrategy<K extends Serializable, R extends DataResource> {

    /**
     * Implementations manage the caches size by removing elements according to a certain strategy.
     * Called by a cache when a resource is added to it.
     * The method has exclusive usage rights to all of the caches concurrency-critical members (except preprocessors)
     * and should thus not further block execution as required.
     * @param cache The cache to be managed.
     */
    public void manageCache(AdvancedCache<K, R> cache);
}
