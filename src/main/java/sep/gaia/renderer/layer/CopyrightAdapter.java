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
import java.lang.management.OperatingSystemMXBean;

import javax.media.opengl.GL2;

import sep.gaia.environment.Environment;
import sep.gaia.environment.Environment.EnvVariable;
import sep.gaia.resources.DataResource;
import sep.gaia.state.GLState;
import sep.gaia.state.State;
import sep.gaia.state.StateObserver;
import sep.gaia.util.FloatBoundingBox;
import sep.gaia.util.FloatVector3D;
import sep.gaia.util.Logger;

public class CopyrightAdapter extends TextureAdapter<DataResource> implements StateObserver {

	/**
	 * The key associated with the copyright-notes texture.
	 */
	protected static final String COPYRIGHT_TEXTURE_KEY = "osmcopy";
	

	/**
	 * The box in which the note should be drawn.
	 */
	private FloatBoundingBox drawBox;
	
	@Override
	protected void performGLInit(GL2 gl) {
		// Get the texture-files path from the environment:
		Environment environment = Environment.getInstance();
		String copyrightTexturePath = environment.getString(EnvVariable.OSM_COPYRIGHT_TEXTURE_FILE);
		
		// Schedule the textures creation and insertion into primary cache:
		try {
			scheduleTextureCreation(gl.getGLProfile(), COPYRIGHT_TEXTURE_KEY, copyrightTexturePath);
		
		} catch (IOException e) {
			Logger.getInstance().error("Cannot create texture for copyright-note. "
										+ "Detailed message: " + e.getMessage());
		}
	}



	@Override
	public void onUpdate(State state) {
		// Create the non-rotated bounding-box:
		drawBox = new FloatBoundingBox(new FloatVector3D(0.6f, 0.95f, 0), new FloatVector3D(1, 1, 0));
	}
	
	/**
	 * Returns the box in which the copyright should be drawn.
	 * @return The box in which the copyright should be drawn.
	 */
	public FloatBoundingBox getDrawBox() {
		return drawBox;
	}
}
