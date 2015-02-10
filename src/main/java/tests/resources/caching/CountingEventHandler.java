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
    public boolean handleResourceDump(R resource) {
        dumpHandledCount++;
        return true;
    }

    @Override
    public boolean handleResourceRead(R resource) {
        readHandledCount++;
        return true;
    }

    @Override
    public void handleResourcePurged(R resource) {
        purgeHandledCount++;
    }
}
