package tests.resources.caching.strategies;

import org.junit.Test;
import sep.gaia.resources.caching.AdvancedCache;
import sep.gaia.resources.caching.strategies.LRURemovalStrategy;
import sep.gaia.resources.poi.PointOfInterest;
import tests.resources.caching.CountingEventHandler;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * This is a test for {@link sep.gaia.resources.caching.strategies.LRURemovalStrategy}.
 */
public class LRURemovalStrategyTest {

    @Test
    public void testManageCache() throws Exception {
        // First test the fixed-amount mode:
        LRURemovalStrategy<String, PointOfInterest> strategy = new LRURemovalStrategy<>(100, 0.5f, true);

        // Create a cache without compression and use LRU-strategy:
        AdvancedCache<String, PointOfInterest> cache = new AdvancedCache<>(strategy);
        CountingEventHandler<PointOfInterest> counter = new CountingEventHandler<>();
        cache.addCachingPreprocessor(counter);

        // Now create 100 POIs. Removal should not be done yet:
        for(int i = 1; i <= 100; i++) {
            PointOfInterest poi = new PointOfInterest("POI_" + i, i, i);
            cache.addResource(poi.getKey(), poi); // Add the resource
            // And 'use' it:
            cache.getResourceByKey(poi.getKey());
        }
        assertEquals(0, counter.purgeHandledCount);
        assertEquals(100, cache.getCachedResourcesCount());

        PointOfInterest poi101 = new PointOfInterest("POI_101", 101, 101);
        cache.addResource(poi101.getKey(), poi101);

        assertEquals(1, counter.purgeHandledCount);
        assertEquals(100, cache.getCachedResourcesCount());

        // Now test removal by share:
        strategy.setKeepFixedAmount(false);
        PointOfInterest poi102 = new PointOfInterest("POI_102", 102, 102);
        cache.addResource(poi102.getKey(), poi102);

        // There should be 50 removed by percentage and one from fixed-amount test:
        assertEquals(51, counter.purgeHandledCount);
        assertEquals(51, cache.getCachedResourcesCount());
    }
}