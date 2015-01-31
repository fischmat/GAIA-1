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

import javax.swing.JTextField;

import sep.gaia.resources.Loader;
import sep.gaia.resources.locationsearch.Location;
import sep.gaia.resources.locationsearch.LocationQuery;
import sep.gaia.resources.locationsearch.LocationSearch;
import sep.gaia.ui.LocationWindow;

/**
 * This is to implement the <code>ActionListener</code> Interface to check if
 * the user wants to search a location by entering a String in the search field
 * in the toolbar.
 * 
 * @author Michael Mitterer
 */
public class SearchToolbarListener implements ActionListener {
	/** 
	 * The JTextField reference.
	 */
	private JTextField text;
	
	private LocationSearch search;
	private Loader<LocationQuery, Location> loader;
	private LocationWindow window;
	
	/**
	 * SearchToolbarListener constructor
	 * 
	 * @param text The search text
	 */
	public SearchToolbarListener(JTextField text) {
		this.text = text;
		search = new LocationSearch();
		loader = search.getLoader();
		window = new LocationWindow();
		window.setSearch(search);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (text.getText() != null) {
			loader.removeListener(window);
			search.queryforLocations(text.getText().replace(' ', '+'));
			loader.addListener(window);
		}
	}

}
