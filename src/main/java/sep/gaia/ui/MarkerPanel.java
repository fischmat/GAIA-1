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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import sep.gaia.controller.MarkerBarButtonListener;
import sep.gaia.controller.MarkerBarListener;
import sep.gaia.resources.ResourceMaster;
import sep.gaia.resources.markeroption.MarkerResource;
import sep.gaia.resources.markeroption.MarkerResourceManager;
import sep.gaia.resources.poi.POISet;
import sep.gaia.resources.poi.PointOfInterest;
import sep.gaia.state.AbstractStateManager;
import sep.gaia.state.GLState;
import sep.gaia.state.GeoState;
import sep.gaia.state.StateManager;
import sep.gaia.state.AbstractStateManager.StateType;

/**
 * The marker panel.
 * 
 * Markers set by the user are shown here.
 * 
 * @author Michael Mitterer
 */
public class MarkerPanel extends JPanel {

	private JList<String> list;
	
	/**
	 * The MarkerPanel constructor
	 */
	public MarkerPanel() {
		super();
		setLayout(new BorderLayout());
		init();
		
		validate();
		setPreferredSize(getPreferredSize());
		setVisible(false);
	}
	
	/**
	 * The initialization of the marker panel with the list of the markers and two buttons to manipulate the marker list.
	 */
	private void init() {
		StateManager manager = (StateManager) StateManager.getInstance();
		GLState glState = (GLState) manager.getState(StateType.GLState);
		
		DefaultListModel<MarkerResource> markerList = new DefaultListModel<MarkerResource>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		MarkerResourceManager markerResource = (MarkerResourceManager) ResourceMaster.getInstance().getResourceManager("Marker");
		markerList = markerResource.getMarkerList();
		listModel= markerResource.getListModel();
		
		
		
		list = new JList<String>(listModel);
		list.addMouseListener(new MarkerBarListener(list, markerList, glState));
		list.setAutoscrolls(true);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		
		
		// Scrollable list of the markers
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(150, 390));
		scrollPane.setAlignmentX(JList.LEFT_ALIGNMENT);
		scrollPane.setVisible(true);
		add(scrollPane, BorderLayout.CENTER);
		
		// Marker buttons
		JButton rename = new JButton("Umbenennen");
		rename.addActionListener(new MarkerBarButtonListener(markerList));
		JButton remove = new JButton("Löschen");
		remove.addActionListener(new MarkerBarButtonListener(markerList));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(rename, BorderLayout.WEST);
		buttonPanel.add(remove, BorderLayout.EAST);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		add(buttonPanel, BorderLayout.PAGE_END);
	}
}
