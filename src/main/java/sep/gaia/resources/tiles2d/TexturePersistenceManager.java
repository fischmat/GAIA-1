package sep.gaia.resources.tiles2d;

import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GLProfile;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * This class is the central repository for managing textures dynamically persisted
 * by {@link sep.gaia.resources.tiles2d.AdvancedTileCache} during runtime in order to reduce memory consumption.
 * {@link sep.gaia.resources.tiles2d.TileResource}s use it for reloading their texture if it was persisted earlier.
 * The textures persisted via {@link #persist(TileResource)} will be stored in
 * the same place like they were stored by the cache.
 */
public class TexturePersistenceManager {

    private GLProfile glProfile;

    private Map<String, String> offlineTextures;

    public TexturePersistenceManager(GLProfile glProfile) {
        this.glProfile = glProfile;
    }

    public boolean persist(TileResource tile) {
        String slippyMapsPath = AdvancedTileCache.generateCachingPath(tile, "png", true);

        try {
            File file = new File(slippyMapsPath);

            TextureData textureData = tile.getTextureData();
            TextureIO.write(textureData, file);

            tile.setTextureData(null);
            textureData.destroy();

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public boolean restore(TileResource tile) {
        if(tile.getTextureData() == null) {
            String slippyMapsPath = AdvancedTileCache.generateCachingPath(tile, "png", true);

            try {
                File file = new File(slippyMapsPath);

                TextureData textureData = TextureIO.newTextureData(glProfile, file, false, TextureIO.PNG);

                tile.setTextureData(textureData);

                return true;

            } catch (IOException e) {
                return false;
            }


        } else {
            return true;
        }
    }
}
