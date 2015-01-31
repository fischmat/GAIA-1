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

import sep.gaia.resources.poi.POICategory;
import sep.gaia.resources.poi.POIManager;
import sep.gaia.resources.poi.SubCategory;

/**
 * This is to implement the <code>ActionListener</code> Interface to check if
 * the user wants to change the categories of the <code>PointOfInterest</code>
 * objects which should be displayed in the view by setting a
 * <code>ComboBox</code> in the poibar.
 * 
 * @author Michael Mitterer
 */
public class POIBarListener implements ActionListener {
	/** 
	 * The major poi category
	 */
	private SubCategory cat;
	
	/**
	 * The POI-manager managing the linked subcategory.
	 */
	private POIManager manager;
	
	/**
	 * POIBarListener constructor
	 * 
	 * @param cat the major poi category
	 */
	public POIBarListener(SubCategory cat, POIManager manager) {
		this.cat = cat;
		this.manager = manager;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(manager != null && cat != null) {
			// Toggle the activation of the category:
			manager.setSubCategoryActive(cat, !cat.isActivated());
		}
	}
}
