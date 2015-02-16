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
package sep.gaia.controller.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import sep.gaia.resources.ResourceMaster;
import sep.gaia.resources.caching.AdvancedCache;
import sep.gaia.resources.tiles2d.TileCache;
import sep.gaia.resources.tiles2d.TileManager;
import sep.gaia.resources.tiles2d.TileResource;

/**
 * This is to implement the <code>ActionListener</code> Interface to check if
 * the user wants to clear the cache.
 * The configuration file will be updated immediately.
 * 
 * @author Michael Mitterer, Max Witzelsperger
 */
public class CacheSettingsListener implements ActionListener {

	private JLabel cacheSize;
	
	/**
	 * Constructs a <code>CacheSettingsListener</code> object which deletes
	 * all the data in the cache of tiles whenever the button it is attached to
	 * is pressed. Simultaneously, the view gets updated about the cache being
	 * empty
	 * 
	 * @param cacheSize The label that shows the current cache size to the
	 * viewer, which is set to zero by <code>this</code> when
	 * <code>actionPerformed</code> is invoked
	 */
	public CacheSettingsListener(JLabel cacheSize) {
		assert cacheSize != null : "JLabel with cache size must not be null!";
		
		this.cacheSize = cacheSize;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cacheSize.setText("0"); // update UI
		
		TileManager manager = (TileManager) ResourceMaster.getInstance().getResourceManager(TileManager.MANAGER_LABEL);
		
		AdvancedCache<String, TileResource> cache = manager.getCache();
		// TODO: cache.clear();
	}

	

}
