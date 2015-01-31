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

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import sep.gaia.controller.MouseClickedListener;
import sep.gaia.state.GLState;
import sep.gaia.state.GeoState;
import sep.gaia.state.State;
import sep.gaia.state.StateObserver;
import sep.gaia.ui.MarkerPanel;

/**
 * This is to implement the <code>ActionListener</code> Interface to check if
 * the user wants to set a new marker by pressing the marker button in the
 * toolbar.
 * 
 * @author Michael Mitterer
 */
public class MarkerToolbarListener implements ActionListener, StateObserver {
	/** 
	 * The <code>GLState</code> reference.
	 */
	private GLState state;
	
	private MarkerPanel markerPanel;
	
	/**
	 * MarkerToolbarListener constructor
	 * 
	 * @param state The current <code>GLState</code>
	 */
	//public MarkerToolbarListener(GeoState state) {
	public MarkerToolbarListener(GLState glState, MarkerPanel markerPanel) {
		this.state = glState;
		this.markerPanel = markerPanel;
		this.state.register(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (state.is2DMode()) {
			MouseClickedListener.markerPressed = !MouseClickedListener.markerPressed;
			MouseClickedListener.setCursor();
			markerPanel.setVisible(true);
		}
	}

	@Override
	public void onUpdate(State state) {
		GLState glState = (GLState) state;
		if (!glState.is2DMode()) {
			markerPanel.setVisible(false);
		}
		
	}

}