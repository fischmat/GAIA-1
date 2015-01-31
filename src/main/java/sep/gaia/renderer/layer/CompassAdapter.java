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

import java.io.IOException;

import javax.media.opengl.GL2;

import sep.gaia.environment.Environment;
import sep.gaia.environment.Environment.EnvVariable;
import sep.gaia.resources.DataResource;
import sep.gaia.state.GLState;
import sep.gaia.state.State;
import sep.gaia.state.StateObserver;
import sep.gaia.util.AlgoUtil;
import sep.gaia.util.FloatBoundingBox;
import sep.gaia.util.FloatVector3D;
import sep.gaia.util.Logger;

/**
 * Gets updates from <code>GLState</code> and calculates the compasses drawing-box.
 * Also this adapter manages the compasses texture.
 * @author Matthias Fisch
 *
 */
public class CompassAdapter extends TextureAdapter<DataResource> implements StateObserver {

	/**
	 * The key associated with the compasses texture.
	 */
	protected static final String COMPASS_TEXTURE_KEY = "compass";
	
	
	/**
	 * The box in which the compass should be drawn.
	 */
	private FloatBoundingBox drawBox;
	
	@Override
	protected void performGLInit(GL2 gl) {
		// Get the texture-files path from the environment:
		Environment environment = Environment.getInstance();
		String compassTexturePath = environment.getString(EnvVariable.COMPASS_TEXTURE_FILE);
		
		// Schedule the textures creation and insertion into primary cache:
		try {
			scheduleTextureCreation(gl.getGLProfile(), COMPASS_TEXTURE_KEY, compassTexturePath);
		
		} catch (IOException e) {
			Logger.getInstance().error("Cannot create texture for compass. "
										+ "Detailed message: " + e.getMessage());
		}
	}



	@Override
	public void onUpdate(State state) {
		GLState glState = (GLState) state;
		
		FloatBoundingBox glBox = glState.getBoundingBox();
		
		// Create the non-rotated bounding-box:
		drawBox = new FloatBoundingBox(new FloatVector3D(0.9f, -0.9f, 0), new FloatVector3D(1, -1, 0));
		
		// Calculate the new center of the box by using the half of its diagonal:
		FloatVector3D center = new FloatVector3D(drawBox.getLowerRight());
		center.sub(drawBox.getUpperLeft());
		center.mul(0.5f);
		center.add(drawBox.getUpperLeft());
		
		// Now rotate around the compasses center against the states rotation:
		drawBox.rotate(center, -glState.getRotation().getZ());
	}
	
	/**
	 * Returns the box in which the compass should be drawn.
	 * @return The box in which the compass should be drawn.
	 */
	public FloatBoundingBox getDrawBox() {
		return drawBox;
	}

}
