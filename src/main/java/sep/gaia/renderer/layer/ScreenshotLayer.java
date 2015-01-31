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

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLException;

import sep.gaia.environment.Environment;
import sep.gaia.environment.Environment.EnvVariable;
import sep.gaia.state.AbstractStateManager.StateType;
import sep.gaia.state.GLState;
import sep.gaia.state.StateManager;
import sep.gaia.util.FloatBoundingBox;
import sep.gaia.util.FloatVector3D;
import sep.gaia.util.Logger;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

public class ScreenshotLayer extends AbstractLayer {

	private boolean active;
	
	private GLState state;
	
	private Texture blendTexture;
	
	public ScreenshotLayer() {
		state = (GLState) StateManager.getInstance().getState(StateType.GLState);
	}
	
	@Override
	public void draw(GL2 gl, float height) {
		if(blendTexture == null) {
			Environment environment = Environment.getInstance();
			String texPath = environment.getString(EnvVariable.SCREENSHOT_BLEND_TEXTURE);
			try {
				blendTexture = TextureIO.newTexture(new File(texPath), false);
			} catch (GLException | IOException e) {
				Logger.getInstance().warning("Texture " + texPath + " not found!");
			}
		}
		
		if(active && state != null && blendTexture != null) {
			
			TextureCoords texCoords = blendTexture.getImageTexCoords();
			FloatVector3D[] vertices = state.getBoundingBox().getCornersCounterClockwise();
			
			blendTexture.bind(gl);
			
			gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(texCoords.right(), texCoords.top());
			gl.glVertex3f(vertices[0].getX(), vertices[0].getY(), 0);
			gl.glTexCoord2f(texCoords.left(), texCoords.top());
			gl.glVertex3f(vertices[1].getX(), vertices[1].getY(), 0);
			gl.glTexCoord2f(texCoords.left(), texCoords.bottom());
			gl.glVertex3f(vertices[2].getX(), vertices[2].getY(), 0);
			gl.glTexCoord2f(texCoords.right(), texCoords.bottom());
			gl.glVertex3f(vertices[3].getX(), vertices[3].getY(), 0);
			gl.glEnd();
		}
		
		// Draw next layer.
		if (this.hasNextLayer()) {
			this.getNextLayer().draw(gl, height + HEIGHT_GAP);
		}
	}

	public synchronized void setActive(boolean active) {
		this.active = active;
	}
	
	
}
