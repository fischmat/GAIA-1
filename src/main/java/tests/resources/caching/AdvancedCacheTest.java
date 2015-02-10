package tests.resources.caching;

import org.junit.Test;
import sep.gaia.resources.DataResource;
import sep.gaia.resources.caching.AdvancedCache;
import sep.gaia.resources.caching.CacheRemovalStrategy;
import sep.gaia.resources.caching.ResourceCachingHandler;
import sep.gaia.resources.poi.PointOfInterest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class AdvancedCacheTest {


    private static int timesCacheManaged;

    @Test
    public void testDumpAndRead() throws Exception {

        // Prepare some POIs to be cached:
        PointOfInterest poi1 = new PointOfInterest("POI_1", 0.0f, 0.0f);
        PointOfInterest poi2 = new PointOfInterest("POI_2", 1.0f, 1.0f);

        // TODO: Test compression when added.
        // Create a cache without compression/strategy:
        AdvancedCache<String, PointOfInterest> orgCache = new AdvancedCache<>();
        orgCache.addResource(poi1.getKey(), poi1);
        orgCache.addResource(poi2.getKey(), poi2);

        CountingEventHandler<PointOfInterest> countingHandler = new CountingEventHandler<>();
        orgCache.addCachingPreprocessor(countingHandler);

        // Lets stream to a buffer in memory:
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        orgCache.dump(out);
        out.flush();

        assertEquals(2, countingHandler.dumpHandledCount);

        // Prepare for streaming data back:
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

        // The new cache-instance that will read the data (same settings):
        AdvancedCache<String, PointOfInterest> newCache = new AdvancedCache<>();
        newCache.addCachingPreprocessor(countingHandler);

        newCache.readFromDump(in);

        assertEquals(2, countingHandler.readHandledCount);
        assertEquals(poi1.getName(), newCache.getResourceByKey(poi1.getKey()).getName());
    }

    @Test
    public void testGetResourceByKey() throws Exception {
        // Prepare some POIs to be cached:
        PointOfInterest poi1 = new PointOfInterest("POI_1", 0.0f, 0.0f);

        // Create a cache without compression/strategy:
        AdvancedCache<String, PointOfInterest> cache = new AdvancedCache<>();
        cache.addResource(poi1.getKey(), poi1);

        // No misses and hits in a new cache?
        assertEquals(0, cache.getCacheHitCount() + cache.getCacheMissCount());

        // Fetch the resource in cache, should succeed:
        assertEquals(poi1, cache.getResourceByKey(poi1.getKey()));
        assertEquals(1, cache.getCacheHitCount());

        // Fetch something not in cache, should fail:
        assertEquals(null, cache.getResourceByKey("Not in cache"));
        assertEquals(1, cache.getCacheMissCount());
    }

    @Test
    public void testAddResource() throws Exception {
        // Prepare some POIs to be cached:
        PointOfInterest poi1 = new PointOfInterest("POI_1", 0.0f, 0.0f);
        PointOfInterest poi2 = new PointOfInterest("POI_2", 1.0f, 1.0f);

        // Reset the counter how often cache was managed:
        timesCacheManaged = 0;

        // Create a cache without compression and with a dummy removal strategy:
        AdvancedCache<String, PointOfInterest> cache = new AdvancedCache<>(new CacheRemovalStrategy() {
            @Override
            public void manageCache(AdvancedCache cache) {
                timesCacheManaged++;
            }
        });

        // Store the first POI, should succeed and cache should be managed once:
        assertEquals(true, cache.addResource(poi1.getKey(), poi1));
        assertEquals(1, cache.getCachedResourcesCount());
        assertEquals(1, timesCacheManaged);

        // Try to store it again, should fail but should be managed never the less:
        assertEquals(false, cache.addResource(poi1.getKey(), poi1));
        assertEquals(1, cache.getCachedResourcesCount());
        assertEquals(2, timesCacheManaged);

        // Now add another resource:
        assertEquals(true, cache.addResource(poi2.getKey(), poi2));
        assertEquals(2, cache.getCachedResourcesCount());
        assertEquals(3, timesCacheManaged);
    }

    @Test
    public void testRemoveResource() throws Exception {
        // Prepare some POIs to be cached:
        PointOfInterest poi1 = new PointOfInterest("POI_1", 0.0f, 0.0f);
        PointOfInterest poi2 = new PointOfInterest("POI_2", 1.0f, 1.0f);

        // Create a cache without compression/strategy:
        AdvancedCache<String, PointOfInterest> cache = new AdvancedCache<>();
        cache.addResource(poi1.getKey(), poi1);
        cache.addResource(poi2.getKey(), poi2);

        // Add an counter, so we can check if events were fired:
        CountingEventHandler<PointOfInterest> countingHandler = new CountingEventHandler<>();
        cache.addCachingPreprocessor(countingHandler);

        // Remove first POI:
        assertEquals(true, cache.removeResource(poi1.getKey()));
        assertEquals(1, cache.getCachedResourcesCount());
        assertEquals(1, countingHandler.purgeHandledCount);

        // Try the same again:
        assertEquals(false, cache.removeResource(poi1.getKey()));
        assertEquals(1, countingHandler.purgeHandledCount);

        // Remove the second resource:
        assertEquals(true, cache.removeResource(poi2.getKey()));
        assertEquals(0, cache.getCachedResourcesCount());
        assertEquals(2, countingHandler.purgeHandledCount);
    }
}