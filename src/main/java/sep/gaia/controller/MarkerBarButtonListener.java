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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import sep.gaia.resources.ResourceMaster;
import sep.gaia.resources.markeroption.MarkerResource;
import sep.gaia.resources.markeroption.MarkerResourceManager;
import sep.gaia.state.AbstractStateManager.StateType;
import sep.gaia.state.StateManager;
import sep.gaia.util.AlgoUtil;
import sep.gaia.util.FloatVector3D;

public class MarkerBarButtonListener implements ActionListener {

	private DefaultListModel<MarkerResource> markerList;
	
	public MarkerBarButtonListener(DefaultListModel<MarkerResource> markerList) {//GeoState state) {
		this.markerList = markerList;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (MarkerBarListener.objectNumber < markerList.getSize() && MarkerBarListener.objectNumber >= 0) {
			MarkerResourceManager markerResource = (MarkerResourceManager) ResourceMaster.getInstance().getResourceManager("Marker");
			MarkerResource marker = markerList.get(MarkerBarListener.objectNumber);
			FloatVector3D vector = new FloatVector3D(marker.getLat(), marker.getLon(), marker.getZoom());
			
			if (arg0.getActionCommand() == "Umbenennen") {
				String input = JOptionPane.showInputDialog("Bitte einen neuen Namen für den Marker eingeben:");
				if (!input.isEmpty() && !input.startsWith(" ")) {
					markerResource.renameMarker(vector, AlgoUtil.glToTileZoom(marker.getZoom()), input, MarkerBarListener.objectNumber);
				}
			} else if (arg0.getActionCommand() == "Löschen") {
				markerResource.removeMarker(vector, AlgoUtil.glToTileZoom(marker.getZoom()), MarkerBarListener.objectNumber);
				// Invoke updates by states:
				StateManager.getInstance().getState(StateType.GLState).notifyManager();
			}
		}
	}
}
