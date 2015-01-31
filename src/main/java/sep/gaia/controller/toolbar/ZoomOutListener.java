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

import sep.gaia.controller.KeyboardZoomAdapter;
import sep.gaia.state.StateManager;
import sep.gaia.state.GLState;

/**
 * This is to implement the <code>ActionListener</code> Interface to check if
 * the user wants to change the zoom level by pressing the zoom in or zoom out
 * button in the toolbar.
 * 
 * @author Johannes Bauer (Spezifikation: Michael Mitterer)
 */
public class ZoomOutListener implements ActionListener {
	/** 
	 * The <code>GLState</code> reference.
	 */
	private GLState state;
	
	/**
	 * ZoomToolbarListener constructor
	 * 
	 * @param state The current <code>GLState</code>
	 */
	public ZoomOutListener(GLState state) {
		this.state = state;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		state.zoom(KeyboardZoomAdapter.ZOOM_OUT);
	}
}
