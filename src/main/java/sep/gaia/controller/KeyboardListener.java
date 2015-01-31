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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import sep.gaia.state.AbstractStateManager.StateType;
import sep.gaia.state.GLState;
import sep.gaia.state.StateManager;
import sep.gaia.util.AlgoUtil;
import sep.gaia.util.FloatVector3D;

/**
 * TODO
 * @author Johannes Bauer
 * 
 */
public class KeyboardListener implements KeyListener {

	private GLState glState;

	public KeyboardListener() {
		this.glState = (GLState) StateManager.getInstance().getState(
				StateType.GLState);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		float halfTileSize = glState.getZoom() / 2.0f;
		FloatVector3D glCenter = glState.getPosition();
		
		float dX = 0;
		float dY = 0;
		
		switch (keyCode) {
		case KeyEvent.VK_UP:
			dY = glCenter.getY() + halfTileSize;
			break;
		case KeyEvent.VK_DOWN:
			dY = glCenter.getY() - halfTileSize;
			break;
		case KeyEvent.VK_LEFT:
			dX = glCenter.getX() - halfTileSize;
			break;
		case KeyEvent.VK_RIGHT:
			dX = glCenter.getX() + halfTileSize;
			break;
		}
		
		glState.translate(new FloatVector3D(dX, dY, 0.0f));
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
