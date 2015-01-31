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
package sep.gaia.controller;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import sep.gaia.state.StateManager;
import sep.gaia.state.GLState;
import sep.gaia.ui.GaiaCanvas;
import sep.gaia.util.FloatBoundingBox;
import sep.gaia.util.FloatVector3D;
import sep.gaia.util.RayCast;

/**
 * This is to implement the <code>MouseWheelListener</code> Interface to check
 * if the user wants to zoom in or out of the virtual earth by rotating the
 * wheel of the mouse.
 * 
 * @author Johannes Bauer (Spezifikation: Michael Mitterer)
 */
public class MouseZoomListener implements MouseWheelListener {
	/**
	 * The <code>GLState</code> reference.
	 */
	private GLState state;

	/**
	 * MouseZoomListener constructor
	 * 
	 * @param state
	 *            The current <code>GLState</code>
	 */
	public MouseZoomListener(GLState state) {
		this.state = state;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		int canvasWidth = GaiaCanvas.getInstance().getWidth();
		int canvasHeight = GaiaCanvas.getInstance().getHeight();
		
		FloatBoundingBox box = state.getBoundingBox();
		float viewWidth = box.getWidth();
		float viewHeight = box.getHeight();
		
		FloatVector3D position = state.getPosition();
		FloatVector3D rotation = state.getRotation();
		
		FloatVector3D newPosition = RayCast.linearCast(e.getX(), e.getY(), canvasWidth, canvasHeight, viewWidth, viewHeight, position, rotation);
		
		state.setPosition(newPosition, false);
		
		// Negative when zoomed from user away. Else positive.
		int notches = e.getWheelRotation();
		if (notches < 0) {
			state.zoom(KeyboardZoomAdapter.ZOOM_IN);
		} else {
			state.zoom(KeyboardZoomAdapter.ZOOM_OUT);
		}
	}

}
