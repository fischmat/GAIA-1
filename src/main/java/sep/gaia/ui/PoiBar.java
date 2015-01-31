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
package sep.gaia.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import sep.gaia.controller.POIBarListener;
import sep.gaia.resources.ResourceMaster;
import sep.gaia.resources.poi.POICategory;
import sep.gaia.resources.poi.POIManager;
import sep.gaia.resources.poi.SubCategory;
import sep.gaia.state.GLState;
import sep.gaia.state.GeoState;
import sep.gaia.state.StateManager;
import sep.gaia.state.AbstractStateManager.StateType;

/**
 * The poibar
 * 
 * @author Michael Mitterer
 *
 */
public class PoiBar extends JPanel {
	private static final long serialVersionUID = 1L;

	public PoiBar() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// Get the POI-manager and its categories:
		POIManager poiManager = (POIManager) ResourceMaster.getInstance().getResourceManager("POIManager");
		Collection<POICategory> categories = poiManager.getCategories();
		
		// Iterate all categories and add them to tabbed pane:
		for (POICategory poiCat : categories) {
			JComponent jCat = makeTextPanel(poiCat.getName());
			
			for (SubCategory poiSubCat : poiCat.getSubcategories()) {
				JCheckBox jSubCat = new JCheckBox(poiSubCat.getName());
				jSubCat.addActionListener(new POIBarListener(poiSubCat, poiManager));
				jSubCat.setSelected(false);
				jCat.add(jSubCat);
			}
			
			tabbedPane.addTab(poiCat.getName(), jCat);
		}

		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

		// Add tabbed pane to this JPanel.
		add(tabbedPane);

		setVisible(false);
	}

	protected JComponent makeTextPanel(String text) {
		final JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(filler);
		return panel;
	}
}
