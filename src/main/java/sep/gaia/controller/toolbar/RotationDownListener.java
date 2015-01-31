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
package sep.gaia.controller.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sep.gaia.state.GLState;
import sep.gaia.util.FloatVector3D;

/**
 * This class is to implement the <code>ActionListener</code> interface to 
 * check if the user wants to change the rotation (direction down)
 *   by pressing the rotation down button in the tool bar. 
 *
 */
public class RotationDownListener implements ActionListener {
	
	/**
	 * The <code>GLState</code> reference.
	 */
	private GLState state;
	
	/**
	 * RotationDownListener constructor.
	 * 
	 * @param state The current <code>GLState</code>.
	 */
	public RotationDownListener(GLState state) {
		this.state = state;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (state.is2DMode() && state.getRotation().getX() > 0) {
    		state.rotate(new FloatVector3D(-10, 0, 0));
    	}
	}

}
