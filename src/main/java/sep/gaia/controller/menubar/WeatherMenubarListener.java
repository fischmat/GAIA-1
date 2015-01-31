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
package sep.gaia.controller.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sep.gaia.resources.ResourceMaster;
import sep.gaia.resources.weather.WeatherManager;
import sep.gaia.resources.wikipedia.WikipediaManager;

/**
 * This is to implement the <code>ActionListener</code> Interface to check if
 * the user wants to enable/disable the weather overlay by pressing the item in
 * the menubar.
 * 
 * @author Michael Mitterer
 */
public class WeatherMenubarListener implements ActionListener {
	/**
	 * This is used to know if the weather informations are available
	 */
	private boolean showWeather = true;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		WeatherManager weatherManager = (WeatherManager) ResourceMaster.getInstance().getResourceManager("WeatherManager");
		
		if (showWeather) {
			weatherManager.disable();
			showWeather = false;
		} else {
			weatherManager.enable();
			showWeather = true;
		}
	}
}
