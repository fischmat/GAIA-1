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
/**
 * 
 */
package sep.gaia.renderer.layer;

import javax.media.opengl.GL2;

/**
 * Implements the <code>DrawableLayer</code> interface and implements the
 * <code>add(DrawableLayer layer)</code> method. The <code>draw(GL2 gl)</code>
 * method is abstract, because it's the task of every concrete layer to
 * implement this method (the render process).
 * 
 * @author Johannes Bauer
 */
public abstract class AbstractLayer implements DrawableLayer {

	/**
	 * The space between two layers in GL-coordinates.
	 */
	protected static final float HEIGHT_GAP = 0.01f;
	
	/**
	 * This layer must be called in the <code>draw</code> method, after this
	 * <code>DrawableLayer</code> has finished its own render process.
	 */
	private DrawableLayer layer;

	/**
	 * This constructor is for all layers that don't need any <code>ResourceAdapter</code>.
	 */
	public AbstractLayer() {
	}

	@Override
	public abstract void draw(GL2 gl, float height);

	@Override
	public void add(DrawableLayer layer) {
		this.layer = layer;
	}

	/**
	 * Getter for the "child" layer.
	 * 
	 * @return The "child" layer if it exists, else null.
	 */
	protected DrawableLayer getNextLayer() {
		return layer;
	}

	/**
	 * 
	 * @return True, if there is another layer, else false.
	 */
	protected boolean hasNextLayer() {
		return (layer == null) ? false : true;
	}
}