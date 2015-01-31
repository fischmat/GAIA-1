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

import sep.gaia.util.FloatBoundingBox;

/**
 * Each drawable <code>DataResource</code> is prepared for OpenGL by the
 * <code>ResourceAdapter</code> class. The <code>ResourceAdapter</code> holds
 * then the "transformed" <code>TextureResource</code> objects which are ready
 * to be drawn by opengl.
 * 
 * @author Johannes Bauer
 */
public class GLResource {
	
	/**
	 * The key of the texture to use for the draw-area.
	 */
	private String key;
	
	/**
	 * The area to draw on.
	 */
	private FloatBoundingBox box;
	
	/**
	 * An optional name for the resource.
	 */
	private String name;

	/**
	 * Initializes the resource with the textures key and its bounding box in
	 * GL-coordinates.
	 * @param key The textures key.
	 * @param box The textures bounding box in GL-coordinates.
	 */
	public GLResource(String key, FloatBoundingBox box) {
		super();
		this.key = key;
		this.box = box;
	}

	/**
	 * Returns the textures key.
	 * @return The textures key.
	 */
	public String getKey() {
		return key;
	}



	/**
	 * Sets the textures key.
	 * @param key The textures key.
	 */
	public void setKey(String key) {
		this.key = key;
	}



	/**
	 * Returns the rectangle - in GL-coordinates - of the entity to draw.
	 * @return The rectangle of the entity to draw.
	 */
	public FloatBoundingBox getBox() {
		return box;
	}

	/**
	 * Sets the rectangle of the entity to draw.
	 * @param box The rectangle - in GL-coordinates - of the entity to draw.
	 */
	public void setBox(FloatBoundingBox box) {
		this.box = box;
	}

	
	/**
	 * Sets the name of this resource.
	 * @param name The name of this resource.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of this resource.
	 * @return The name of this resource or <code>null</code> if not set.
	 */
	public String getName() {
		return name;
	}
	
	
}
