package sep.gaia.resources.tiles2d;

import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import sep.gaia.resources.caching.AdvancedCache;
import sep.gaia.resources.caching.CacheEntry;
import sep.gaia.resources.caching.CacheRemovalStrategy;
import sep.gaia.resources.caching.strategies.OrderedRemovalStrategy;
import sep.gaia.state.AbstractStateManager;
import sep.gaia.state.GLState;
import sep.gaia.state.StateManager;
import sep.gaia.util.AlgoUtil;
import sep.gaia.util.FloatVector3D;
import sep.gaia.util.IntegerVector3D;
import sep.gaia.util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This strategy dynamically persists tiles in cache in order to ensure a maximum level of
 * memory consumption for tiles keeping their texture data in memory.
 * Furthermore it holds another {@link sep.gaia.resources.caching.CacheRemovalStrategy} for purging
 * the cache and thus managing disk space.
 */
public class DynamicTilePersistenceStrategy extends OrderedRemovalStrategy<String, TileResource> {

    /**
     * The strategy actually intended to purge the cache and thus free space on disk.
     * AdvancedTileCache will remove the files created by this class when a tile is removed from cache.
     */
    private CacheRemovalStrategy<String, TileResource> purgingStrategy;


    /**
     * @param threshold The amount of tiles to be held in memory at maximum.
     *                  Lower values slightly decrease performance due to tiles having to be reloaded from disk.
     * @param maxSortingAmount Maximum number of entries to inspect when deciding what elements to persist.
     *                         Lower values imply better performance.
     * @param purgingStrategy The strategy to be used when deciding what tiles should be finally removed from cache.
     */
    public DynamicTilePersistenceStrategy(int threshold, int maxSortingAmount, CacheRemovalStrategy<String, TileResource> purgingStrategy) {
        super(threshold, 0, true, maxSortingAmount, new Comparator<Map.Entry<String, CacheEntry<TileResource>>>() {
            @Override
            public int compare(Map.Entry<String, CacheEntry<TileResource>> first, Map.Entry<String, CacheEntry<TileResource>> second) {
                // We don't want to target tiles having their texture already persisted or tiles currently visible:
                if(first.getValue().getResource().getTextureData() == null) {
                    // If the tiles texture is not present simply say its always greater than the other:
                    return 0;
                } else {
                    return (int) (first.getValue().getLastUsageTime() - second.getValue().getLastUsageTime());
                }
            }
        });
        this.purgingStrategy = purgingStrategy;
    }

    @Override
    public void manageCache(AdvancedCache cache) {
        Map<String, CacheEntry<TileResource>> index = cache.getIndex();

        if(index.size() > getThreshold()) {
            LinkedHashMap<String, CacheEntry<TileResource>> sorted = selectWeakEntries(index);

            for(Map.Entry<String, CacheEntry<TileResource>> entry : sorted.entrySet()) {
                TileResource tile = entry.getValue().getResource();

                GLState glState = (GLState) StateManager.getInstance().getState(AbstractStateManager.StateType.GLState);
                boolean tileVisible = glState.getBoundingBox().contains(AlgoUtil.tileToGLBox(tile.getCoord()));

                // Check if the tiles texture was not already persisted:
                if(tile.getTextureData() != null && !tileVisible) {
                    IntegerVector3D coordinates = tile.getCoord();

                    // For storing we use slippy-maps convention (same as AdvancedTileCache):
                    String slippyMapsPath = AdvancedTileCache.generateCachingPath(coordinates.getX(),
                                                                                    coordinates.getY(),
                                                                                    coordinates.getZ(),
                                                                                    tile.getStyle().getLabel(),
                                                                                    "png", true);

                    try {
                        File textureFile = new File(slippyMapsPath);

                        // Store the texture...
                        TextureData textureData = tile.getTextureData();
                        TextureIO.write(textureData, textureFile);

                        // ... and unload it:
                        textureData.destroy();
                        tile.setTextureData(null);

                    } catch (IOException e) {
                        Logger.getInstance().warning("While dynamically persisting tile: " + e.getMessage());
                    }
                }
            }
        }

        // Let the other strategy decide whether to purge the cache:
        purgingStrategy.manageCache(cache);
    }
}
