package sep.gaia.resources.tiles2d;

import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import sep.gaia.environment.Environment;
import sep.gaia.resources.caching.AdvancedCache;
import sep.gaia.resources.caching.CacheRemovalStrategy;
import sep.gaia.resources.caching.ResourceCachingHandler;
import sep.gaia.resources.caching.strategies.LRURemovalStrategy;
import sep.gaia.util.IntegerVector3D;
import sep.gaia.util.Logger;

import javax.media.opengl.GLProfile;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fischmat on 10.02.15.
 */
public class AdvancedTileCache extends AdvancedCache<String, TileResource> implements ResourceCachingHandler<TileResource> {




    /**
     * The OpenGL-Profile that should be used when creating texture-data from previously cached files.
     */
    private GLProfile glProfile;

    /**
     * Initializes the cache with a LRU removal-strategy removing a certain
     * percentage of the caches data, if the number of cached tiles exceeds <code>threshold</code>.
     * @param glProfile The OpenGL-Profile to use when handling textures.
     * @param threshold When the number of cached tiles gets higher than this value a certain amount will
     *                  be deleted.
     */
    public AdvancedTileCache(GLProfile glProfile, int threshold) {
        this.glProfile = glProfile;
        addCachingPreprocessor(this);
        setRemovalStrategy(new DynamicTilePersistenceStrategy(100, -1, new LRURemovalStrategy(threshold, 0.2f, false)));
    }

    @Override
    public boolean handleResourceDump(TileResource tile) {
        // Get a unique location for this tile:
        String cachedFilePath = generateCachingPath(tile, "png", true);

        try {
            File cachedFile = new File(cachedFilePath);

            // If the file does not exist yet, create it:
            if(!cachedFile.exists()) {
                cachedFile.createNewFile();
            }

            // Write the textures data into the file:
            TextureIO.write(tile.getTextureData(), cachedFile);

            return true;

        } catch (IOException e) { // On error advise the cache to remove the resource from index
            return false;
        }
    }

    @Override
    public boolean handleResourceRead(TileResource tile) {
        // Get the location where this tiles texture was stored:
        String cachedFilePath = generateCachingPath(tile, "png", true);

        try {
            File cachedFile = new File(cachedFilePath);

            if(cachedFile.exists()) {
                // Read the texture from file and add it to the tile:
                TextureData textureData = TextureIO.newTextureData(glProfile, cachedFile, false, "PNG");
                tile.setTextureData(textureData);

                // In order to limit memory consumption let the strategy unload some textures:
                invokeRemoval();

                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            return false;
        }

    }

    @Override
    public void handleResourcePurged(TileResource tile) {
        if(tile.getTextureData() != null) {
            tile.getTextureData().destroy();
        }
    }

    @Override
    public TileResource getResourceByKey(String key) {
        TileResource result = super.getResourceByKey(key);

        if(result != null) {
            // The tiles texture might have been persisted and unloaded, so try to reload it:
            if(result.getTextureData() == null) {

                String slippyMapsPath = AdvancedTileCache.generateCachingPath(result, "png", true);

                File textureFile = new File(slippyMapsPath);

                if(textureFile.exists()) {
                    try {
                        TextureData textureData = TextureIO.newTextureData(glProfile, textureFile, false, TextureIO.PNG);
                        if(textureData != null) {
                            result.setTextureData(textureData);
                        } else {
                            return null;
                        }

                    } catch (IOException e) {
                        return null;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Serializes and stores the caches data in a file specified by the {@link sep.gaia.environment.Environment.EnvVariable}
     * <code>TILE_CACHE_INDEX_DUMP</code>.
     * If the file does not exist, it will be created.
     * For detailed information c.f. {@link #dump(java.io.OutputStream)}
     * @throws java.io.IOException Thrown if an error occurs while writing the data to file.
     */
    public void dump() throws IOException {
        Environment environment = Environment.getInstance();
        String defaultDumpLocation = environment.getString(Environment.EnvVariable.TILE_CACHE_INDEX_DUMP);
        super.dump(new File(defaultDumpLocation));
    }


    /**
     * Generates a path for storing purposes of a cached tile following the slippy-maps convention.
     * Note that only the path is generated and optionally the directory hierarchy is created,
     * but the actually described file is not.
     * The root location (where the slippy-maps formatted part starts from) is defined by the
     * {@link sep.gaia.environment.Environment.EnvVariable} TILE_CACHE_ROOT_DIR environment variable.
     * The directory of the slippy-maps-path identifying the style will be the styles URL-encoded name
     * in order to avoid problems with the file-systems naming limitations.
     * The directory will be named by the styles <code>hashCode()</code> if UTF-8 encoding
     * is not supported by the system.
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @param zoom The zoom-level of the tile.
     * @param style The name of the style of the tile.
     * @param extension The extension of the file.
     * @param makeDirs If true, this method will try to create all necessary directories.
     * @return Returns an unique location for caching the described file following slippy-maps conventions.
     */
    protected static String generateCachingPath(int x, int y, int zoom, String style, String extension,  boolean makeDirs) {
        Environment environment = Environment.getInstance();
        StringBuilder builder = new StringBuilder();
        builder.append(environment.getString(Environment.EnvVariable.TILE_CACHE_ROOT_DIR));

        // First part is a directory, having the name of url-encoded name of the style:
        // Don't use it, if UTF-8 is not supported.
        try {
            builder.append(URLEncoder.encode(style, "UTF-8"));
            builder.append(environment.getString(Environment.EnvVariable.SYSTEM_PATH_SEPARATOR));
        } catch (UnsupportedEncodingException e) {
            builder.append(style.hashCode());
            builder.append(environment.getString(Environment.EnvVariable.SYSTEM_PATH_SEPARATOR));
        }

        // Second is a directory identifying the zoom-level:
        builder.append(zoom);
        builder.append(environment.getString(Environment.EnvVariable.SYSTEM_PATH_SEPARATOR));

        // Third is a directory identifying the x coordinate:
        builder.append(x);
        builder.append(environment.getString(Environment.EnvVariable.SYSTEM_PATH_SEPARATOR));

        // If set, create any not yet existing directories:
        if(makeDirs) {
            File directoryHierarchy = new File(builder.toString());
            directoryHierarchy.mkdirs();
        }

        // Finally the files name is the y-coordinate and the extension is PNG:
        builder.append(y);
        builder.append(".");
        builder.append(extension);

        return builder.toString();
    }

    /**
     * Generates a path for storing purposes of a cached tile following the slippy-maps convention.
     * Note that only the path is generated and optionally the directory hierarchy is created,
     * but the actually described file is not.
     * The root location (where the slippy-maps formatted part starts from) is defined by the
     * {@link sep.gaia.environment.Environment.EnvVariable} TILE_CACHE_ROOT_DIR environment variable.
     * The directory of the slippy-maps-path identifying the style will be the styles URL-encoded name
     * in order to avoid problems with the file-systems naming limitations.
     * The directory will be named by the styles <code>hashCode()</code> if UTF-8 encoding
     * is not supported by the system.
     * @param tile The tile which slippy-maps path should be generated,
     * @param extension The extension of the file.
     * @param makeDirs If true, this method will try to create all necessary directories.
     * @return Returns an unique location for caching the described file following slippy-maps conventions.
     */
    public static String generateCachingPath(TileResource tile, String extension, boolean makeDirs) {
        IntegerVector3D coords = tile.getCoord();

        return generateCachingPath(coords.getX(), coords.getY(), coords.getZ(),
                                   tile.getStyle().getLabel(), extension, makeDirs);
    }
}
