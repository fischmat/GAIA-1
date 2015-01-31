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

import sep.gaia.state.GLState;
import sep.gaia.util.FloatVector3D;


/**
 * This is to extent a <code>KeyAdapter</code> to check if the user wants to
 * rotate the virtual earth by pressing one of the arrow keys.
 * 
 * @author Johannes Bauer (Spezifikation: Michael Mitterer)
 */
public class KeyboardRotationAdapter extends KeyAdapter {

	/** 
	 * The <code>GLState</code> reference.
	 */
	private GLState state;
	
	/**
	 * KeyboardRotationAdapter constructor
	 * 
	 * @param state The current <code>GLState</code>
	 */
    public KeyboardRotationAdapter(GLState state) {
    	this.state = state;
    }
	
	/**
	 * Check if one of the rotation keys is pressed.
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch (key) { 
        case KeyEvent.VK_PAGE_DOWN:
        	if (state.is2DMode() && state.getRotation().getX() > 0) {
        		state.rotate(new FloatVector3D(-10, 0, 0));
        	}
        	break;
        case KeyEvent.VK_PAGE_UP:
        	if (state.is2DMode() && state.getRotation().getX() < 45) {
        		state.rotate(new FloatVector3D(10, 0, 0));
        	}
        	break;
        default: break;
		}
	}
}
