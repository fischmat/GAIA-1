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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import sep.gaia.state.StateManager;
import sep.gaia.state.GLState;
import sep.gaia.util.AlgoUtil;
import sep.gaia.util.FloatVector3D;

/**
 * This is to extent a <code>KeyAdapter</code> to check if the user wants to
 * rotate the virtual earth by pressing one of the arrow keys.
 * 
 * @author Michael Mitterer
 */
public class KeyboardMoveAdapter extends KeyAdapter {
	/** 
	 * The <code>GLState</code> reference.
	 */
	private GLState state;
	
	/**
	 * KeyboardRotationAdapter constructor
	 * 
	 * @param state The current <code>GLState</code>
	 */
    public KeyboardMoveAdapter(GLState state) {
    	this.state = state;
    }
	
	/**
	 * Check if one of the arrow keys is pressed.
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		int tileZoom = AlgoUtil.glToTileZoom(state.getZoom());
		float moveDist = AlgoUtil.glCoordsPerPixelRange((int)AlgoUtil.TILE_LENGTH_IN_PIXELS, tileZoom);
		
		FloatVector3D translation = null;
		
		switch (key) { 
        case KeyEvent.VK_UP:
        	translation = new FloatVector3D(0, moveDist, 0);
        	break;
        	
        case KeyEvent.VK_DOWN:
        	translation = new FloatVector3D(0, -moveDist, 0);
        	break;
        	
        case KeyEvent.VK_LEFT:
        	translation = new FloatVector3D(-moveDist, 0, 0);
        	break;
        	
        case KeyEvent.VK_RIGHT :
        	translation = new FloatVector3D(moveDist, 0, 0);
        	break;
        	
        default: break;
		}
		
		if(translation != null) {
			float rotationZ = state.getRotation().getZ();
			translation.rotateAroundZ(-rotationZ);
			state.translate(translation);
		}
	}
}

