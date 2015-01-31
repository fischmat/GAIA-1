/*		
 *		Copyright (c) 2015. 
 *		Johannes Bauer, Fabian Buske, Matthias Fisch,
 *		Michael Mitterer, Maximilian Witzelsperger
 *
 *		Licensed under the Apache License, Version 2.0 (the "License");
 *		you may not use this file except in compliance with the License.
 *		You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *		Unless required by applicable law or agreed to in writing, software
 *		distributed under the License is distributed on an "AS IS" BASIS,
 *		WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *		See the License for the specific language governing permissions and
 *		limitations under the License.
 */
package sep.gaia.renderer.layer;

import java.util.Collection;
import java.util.LinkedList;

import sep.gaia.resources.tiles2d.TileResource;
import sep.gaia.state.AbstractStateManager.StateType;
import sep.gaia.state.GLState;
import sep.gaia.state.StateManager;
import sep.gaia.util.AlgoUtil;
import sep.gaia.util.FloatBoundingBox;
import sep.gaia.util.IntegerVector3D;

import com.jogamp.opengl.util.texture.TextureData;

/**
 * This class receives information about all currently available tiles and
 * processes to fit the requirements of the <code>TileLayer</code>.
 * Apart from converting coordinates the creation of textures from the tile-images
 * is done here.
 * 
 * @author Johannes Bauer, Matthias Fisch
 */
public class TileAdapter extends TextureAdapter<TileResource> {

	private FloatBoundingBox getTileBoundingBox(TileResource tile) {
		IntegerVector3D upperLeft = tile.getCoord();
		return AlgoUtil.tileToGLBox(upperLeft);
	}
	
	@Override
	public void onUpdate(Collection<TileResource> resources) {
		if(resources != null) {
			// Call the method of TextureAdapter as required:
			super.onUpdate(resources);
			
			Collection<GLResource> drawableResources = new LinkedList<>();
			
			GLState glState = (GLState) StateManager.getInstance().getState(StateType.GLState);
			FloatBoundingBox glBBox = glState.getBoundingBox();
			glBBox.getLowerLeft();
			
			for(TileResource tile : resources) {
				if(!tile.isDummy()) {
					String key = tile.getKey();
					FloatBoundingBox bbox = getTileBoundingBox(tile);
					TextureData texData = tile.getTextureData();
					
					scheduleTextureCreation(key, texData);
					
					GLResource glResource = new GLResource(key, bbox);
					
					drawableResources.add(glResource);
				}
			}
			
			setGLResources(drawableResources);
		}
	}

	
	
}