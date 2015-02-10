package tests.resources.caching;

import sep.gaia.resources.DataResource;
import sep.gaia.resources.caching.ResourceCachingHandler;
import sep.gaia.resources.poi.PointOfInterest;

/**
 * Class counting how often a certain caching event has been invoked.
 */
public class CountingEventHandler<R extends DataResource> implements ResourceCachingHandler<R> {

    public int dumpHandledCount;

    public int readHandledCount;

    public int purgeHandledCount;

    @Override
    public void handleResourceDump(R resource) {
        dumpHandledCount++;
    }

    @Override
    public void handleResourceRead(R resource) {
        readHandledCount++;
    }

    @Override
    public void handleResourcePurged(R resource) {
        purgeHandledCount++;
    }
}
