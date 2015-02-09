package sep.gaia.resources.caching;

import sep.gaia.resources.DataResource;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Defines a basic structure for instances caching resources ({@link sep.gaia.resources.DataResource}).
 * In this implementation it does not matter if the caching is done persistently, only in memory or
 * in any other way.
 * This class is meant to replace {@link sep.gaia.resources.Cache} and provides a more abstract and powerful way
 * to handle cached resources.
 * This class features dynamic compression of serialized data using the LZO compression algorithm (LZO1X),
 * as well as serialized dumping and reading of the caches data.
 * Also note that the classes methods are thread-safe and thus may block when called.
 *
 * @param <K> The type of objects used to identify the cached resources (e.g. {@link String})
 *            Note that this type must implement the <code>hashCode()</code> appropriate.
 * @param <R> The type of resources to handle within the cache.
 *
 * @author Matthias Fisch
 */
public abstract class AbstractCache<K extends Serializable, R extends DataResource> {

    /**
     * The association of the keys to identify the resources with the respective resoures.
     */
    private Map<K, CacheEntry<R>> index = new HashMap<>();

    /**
     * Lock securing {@link #index} from concurrent modification errors.
     */
    private Lock indexInUseLock = new ReentrantLock();

    /**
     * Flag indicating whether to use LZO compression algorithm when writing the cache-index via
     * {@link #dump(java.io.OutputStream)} or overriding methods.
     */
    private boolean useDumpRealtimeCompression;

    /**
     * This is the caches Lamport-time. This means that on each event when the time is requested, it is incremented.
     */
    private long currentTime;

    /**
     * Lock securing {@link #currentTime} from concurrent modification errors.
     */
    private Lock currentTimeInUseLock = new ReentrantLock();

    /**
     * The removal strategy used by this cache.
     */
    private CacheRemovalStrategy removalStrategy;

    /**
     * The handlers to process caching-events of resources, such as dumping, reading and removal.
     */
    private Collection<ResourceCachingHandler<R>> preprocessors = new LinkedList<>();

    /**
     * Lock securing {@link #preprocessors} from concurrent modification errors.
     */
    private Lock preprocessorsInUseLock = new ReentrantLock();

    /**
     * The number of times a request to this cache was successful.
     */
    private long hits;

    /**
     * Lock securing {@link #hits} from concurrent modification errors.
     */
    private Lock hitsInUseLock = new ReentrantLock();

    /**
     * The number of times a request to this cache did not result in a match.
     */
    private long misses;

    /**
     * Lock securing {@link #misses} from concurrent modification errors.
     */
    private Lock missesInUseLock = new ReentrantLock();

    /**
     * Initializes the cache with realtime-compression turned off.
     * @param strategy The removal strategy to manage the caches size.
     */
    public AbstractCache(CacheRemovalStrategy strategy) {
        this.removalStrategy = strategy;
    }

    /**
     * @param removalStrategy The removal strategy to manage the caches size.
     * @param useDumpRealtimeCompression Whether to use LZO compression algorithm when writing the cache-index via
     * {@link #dump(java.io.OutputStream)} or overriding methods.
     */
    public AbstractCache(CacheRemovalStrategy removalStrategy, boolean useDumpRealtimeCompression) {
        this.useDumpRealtimeCompression = useDumpRealtimeCompression;
        this.removalStrategy = removalStrategy;
    }

    /**
     * Serializes and writes the data to the given target specified by <code>out</code>.
     * This can be used to dump an image of the cache persistently.
     * If LZO-realtime compression is used (cf. ), the serialized data will be compressed before being written
     * to the target stream.
     * Note, that the stream will neither be flushed or closed after writing the data, so its in the responsibility
     * of the caller to do so.
     * @param out Where to write serialized data.
     * @throws java.io.IOException Thrown when an error occurs while writing.
     */
    public void dump(OutputStream out) throws IOException {
        // Convert the stream to a LZO-compressed one if needed:
        OutputStream picked = convertOutputStream(out);
        ObjectOutputStream serializedOutput = new ObjectOutputStream(picked);

        // Maybe the resources can not be serialized directly and thus additional work must be done by the preprocessors:
        preprocessorsInUseLock.lock();

        for (ResourceCachingHandler preprocessor : preprocessors) {
            for(K resourceKey : index.keySet()) {
                CacheEntry<R> entry = index.get(resourceKey);
                preprocessor.handleResourceDump(entry.getResource());
            }
        }
        preprocessorsInUseLock.unlock();


        // Lock the index for exclusive use by this thread:
        indexInUseLock.lock();

        // Serialize and write to the stream:
        serializedOutput.writeObject(index);

        // Free the lock again;
        indexInUseLock.unlock();
    }

    /**
     * Serializes and stores the caches data in a file.
     * If the file does not exist, it will be created.
     * For detailed information c.f. {@link #dump(java.io.OutputStream)}
     * @param file The file to store the data in.
     * @throws java.io.IOException Thrown if an error occurs while writing the data to file.
     */
    public void dump(File file) throws IOException {
        // If the file does not exist yet, create it:
        boolean fileExists = file.exists();
        if(!fileExists) {
            fileExists = file.createNewFile();
        }

        // If creation was successful or file already existed:
        if(fileExists) {
            dump(new FileOutputStream(file));
        }
    }

    /**
     * Deserializes and eventually decompresses (if {@link #useDumpRealtimeCompression} is set) the index
     * read from <code>in</code>.
     * Note that all data currently held by the instance will be overwritten.
     * You must ensure that the data read next from <code>in</code> was originally written by {@link #dump(java.io.OutputStream)}
     * or its overriding methods and that the {@link #useDumpRealtimeCompression} is the same as the data was written with.
     * @param in The source to read from.
     * @throws java.io.IOException Thrown if an error occurs while reading the data,
     * @throws IllegalArgumentException Thrown if the read data is not compliant to the data written by {@link #dump(java.io.OutputStream)}
     * or its overriding methods. It may also be the case that compression is disabled now and was enabled when dumping the index
     * or the other way around.
     */
    public void readFromDump(InputStream in) throws IOException, IllegalArgumentException {
        // Convert the stream to a LZO-compressed one if needed:
        InputStream picked = convertInputStream(in);
        ObjectInputStream deserializedInput = new ObjectInputStream(picked);

        // Lock the index for exclusive use by this thread:
        indexInUseLock.lock();

        try {
            Object readObject = deserializedInput.readObject();
            if(readObject instanceof Map) {
                index = (Map<K, CacheEntry<R>>) readObject;
            } else {
                throw new IllegalArgumentException("Read data is of incorrect type. (Required: Map<K, CacheEntry<R>>, Is: " + readObject.getClass().getSimpleName());
            }

        } catch (ClassNotFoundException e)  {
            // Convert the exception to a more meaningful one:
            throw new IllegalArgumentException(e.getMessage());
        }

        // Free the lock again;
        indexInUseLock.unlock();

        // Maybe the resources contain members not that could not be serialized
        // and thus additional work must be done by the preprocessors:
        preprocessorsInUseLock.lock();

        // The first iteration of the resources is also used to determine the caches last generated timestamp:
        boolean firstIteration = true;
        currentTimeInUseLock.lock();

        for (ResourceCachingHandler preprocessor : preprocessors) {
            for(K resourceKey : index.keySet()) {
                CacheEntry<R> entry = index.get(resourceKey);

                if(firstIteration) {
                    // If the entry has an higher timestamp than found yet, assume it as the caches current one.
                    // Doing this the caches clock will be (after first iteration) at a valid state again:
                    currentTime = Math.max(currentTime, entry.getInsertionTime());
                    currentTime = Math.max(currentTime, entry.getLastUsageTime());
                }


                preprocessor.handleResourceRead(entry.getResource());
            }
            // We iterated over all resources once, so the clock is up to date:
            firstIteration = false;
            currentTimeInUseLock.unlock();
        }
        preprocessorsInUseLock.unlock();
    }

    /**
     * Reads a dumped cache-index from a file.
     * For detailed information c.f. {@link #readFromDump(java.io.InputStream)}.
     * @param file The file to read from.
     * @throws java.io.IOException Thrown if an error occurs while reading from file.
     * @throws IllegalArgumentException Thrown if the read data is not compliant to the data written by {@link #dump(java.io.OutputStream)}
     * or its overriding methods. It may also be the case that compression is disabled now and was enabled when dumping the index
     * or the other way around.
     */
    public void readFromDump(File file) throws IOException, IllegalArgumentException {
        readFromDump(new FileInputStream(file));
    }

    /**
     * Returns the resource associated with <code>key</code>.
     * @param key The key of the resource to find.
     * @return The resource found or <code>null</code> if there is no resource associated with <code>key</code>.
     */
    public R getResourceByKey(K key) {
        indexInUseLock.lock();
        CacheEntry<R> entry = index.get(key);
        indexInUseLock.unlock();

        if(entry != null) {
            hitsInUseLock.lock();
            hits++; // Cache-hit!
            hitsInUseLock.unlock();

            // Assume the resource will be used, so update its last usage and use count:
            entry.setLastUsageTime(getNextTimestamp());
            entry.setUsageCount(entry.getUsageCount() + 1);
            return entry.getResource();
        } else {
            missesInUseLock.lock();
            misses++; // Resource not found, increment cache-misses
            missesInUseLock.unlock();
            return null;
        }
    }

    /**
     * Adds <code>resource</code> identifiable by <code>key</code> to the cache and invokes the removal strategy.
     * @param key A unique identifier for the resource to be stored.
     * @param resource The resource to be stored.
     * @return <code>true</code> if the resources was successfully stored in cache.
     * <code>false</code> if storing has failed (because there is already a resource identified by the given key).
     */
    public boolean addResource(K key, R resource) {
        // The index will be in use for a moment:
        indexInUseLock.lock();

        // Only add if the key is not already in use:
        boolean isAlreadyContained = index.containsKey(key);
        if(!isAlreadyContained) {
            index.put(key, new CacheEntry<>(resource, getNextTimestamp()));
        }

        // Now gain all other usage rights and invoke the removal-strategy:
        if(removalStrategy != null) {
            currentTimeInUseLock.lock();
            removalStrategy.manageCache(this);
            currentTimeInUseLock.unlock();
        }


        indexInUseLock.unlock();

        return !isAlreadyContained;
    }

    public boolean removeResource(K key) {
        // The index will be in use for a moment:
        indexInUseLock.lock();

        // Only add if the key is not already in use:
        boolean found = index.containsKey(key);
        if(found) {
            CacheEntry<R> entry = index.get(key);

            // Maybe the preprocessors must clean things up:
            preprocessorsInUseLock.lock();
            for(ResourceCachingHandler preprocessor : preprocessors) {
                preprocessor.handleResourcePurged(entry.getResource());
            }
            preprocessorsInUseLock.unlock();

            // Finally remove the resource from index:
            index.remove(key);
        }

        indexInUseLock.unlock();

        return found;
    }

    /**
     * Converts the given output stream to a realtime-compressed (LZO1X) output-stream if
     * {@link #useDumpRealtimeCompression} is set. Otherwise this method will return the original stream.
     * @param original The stream to eventually convert.
     * @return The appropriately converted stream.
     */
    private OutputStream convertOutputStream(OutputStream original) {
        // TODO: Link shevek/lzo-java and uncomment code below:
        if(useDumpRealtimeCompression) {
            /*
            LzoAlgorithm algorithm = LzoAlgorithm.LZO1X;
            LzoCompressor compressor = LzoLibrary.getInstance().newCompressor(algorithm, null);
            return new LzoOutputStream(out, compressor, 256);
             */
            return original;
        } else {
            return original;
        }
    }

    /**
     * Converts the given input stream to a realtime-compressed (LZO1X) input-stream if
     * {@link #useDumpRealtimeCompression} is set. Otherwise this method will return the original stream.
     * @param original The stream to eventually convert.
     * @return The appropriately converted stream.
     */
    private InputStream convertInputStream(InputStream original) {
        InputStream converted = null;
        // TODO: Link shevek/lzo-java and uncomment code below:
        if(useDumpRealtimeCompression) {
            /*
             LzoAlgorithm algorithm = LzoAlgorithm.LZO1X;
             LzoDecompressor decompressor = LzoLibrary.getInstance().newDecompressor(algorithm, null);
             return new LzoInputStream(in, decompressor);
             */
            return original;
        } else {
            return original;
        }
    }

    /**
     * Returns the caches current Lamport-time and increments its value.
     * @return The timestamp that can be used for the current moment.
     */
    private long getNextTimestamp() {
        // Lock the caches timestamp for exclusive usage:
        currentTimeInUseLock.lock();

        // Get the current timestamp and increment the caches counter:
        long time = currentTime;
        currentTime++;

        currentTimeInUseLock.unlock();

        return time;
    }

    /**
     * The caches association between the keys and the entries.
     * When calling this method usage rights must have been gained first (e.g. through the removal-strategy event).
     * This method does not lock the index, so from great power comes great responsibility!
     * Note that the returned map should only be read, but for removal of entries
     * {@link #removeResource(java.io.Serializable)} must be called in order to avoid unexpected behaviour or
     * memory leaks.
     * @return The caches index.
     */
    public Map<K, CacheEntry<R>> getIndex() {
        return index;
    }

    /**
     * Adds a preprocessor that can prepare/store/load resources when they are written or read from dump.
     * @param preprocessor The preprocessor to add.
     */
    public void addCachingPreprocessor(ResourceCachingHandler<R> preprocessor) {
        preprocessorsInUseLock.lock();
        preprocessors.add(preprocessor);
        preprocessorsInUseLock.unlock();
    }

    /**
     * @return True, if LZO compression is used when writing the caches data via {@link #dump(java.io.OutputStream)} or
     * its overriding methods. Otherwise false.
     */
    public boolean isDumpRealtimeCompressionUsed() {
        return useDumpRealtimeCompression;
    }

    /**
     * Returns the number of times a request to this cache was successful.
     * This counter is not stored when the caches index is dumped, so its only relative to the lifetime of the object.
     * @return Number of times a request to this cache was successful.
     */
    public long getCacheHitCount() {
        hitsInUseLock.lock();
        long result = hits;
        hitsInUseLock.unlock();
        return result;
    }

    /**
     * Returns the number of times a request to this cache did not result in a match.
     * This counter is not stored when the caches index is dumped, so its only relative to the lifetime of the object.
     * @return Number of times a request to this cache failed.
     */
    public long getCacheMissCount() {
        missesInUseLock.lock();
        long result = misses;
        missesInUseLock.unlock();
        return result;
    }
}
