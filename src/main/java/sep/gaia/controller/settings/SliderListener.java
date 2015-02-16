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

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sep.gaia.resources.ResourceMaster;
import sep.gaia.resources.caching.CacheRemovalStrategy;
import sep.gaia.resources.caching.strategies.OrderedRemovalStrategy;
import sep.gaia.resources.tiles2d.AdvancedTileCache;
import sep.gaia.resources.tiles2d.TileCache;
import sep.gaia.resources.tiles2d.TileManager;
import sep.gaia.resources.tiles2d.TileResource;


/**
 * This class is to implement the <code>ActionListener</code> interface
 * to check if the user wants to change the maximal cache size.
 * 
 * @author Max Witzelsperger
 *
 */
public class SliderListener implements ChangeListener {

	private static final long MEGABYTE_TO_BYTE = 1024 * 1024;

	/**
	 * the current size of the cache, which is to be set to 0 when the maximum
	 * cache size changes
	 */
	private JLabel cacheSize;
	
	/**
	 * Constructs a new <code>SliderListener</code>, which changes the size
	 * of the tile cache dependent on the status of the slider that
	 * <code>this</code> is attached to. When the cache size is changed,
	 * the cached data gets automatically deleted.
	 * 
	 * @param cacheSize shows the current cache size to the user, which
	 * is set to 0 when <code>stateChanged</code> is invoked
	 */
	public SliderListener(JLabel cacheSize) {
		assert cacheSize != null : "JLabel with cache size must not be null!";
		
		this.cacheSize = cacheSize;
	}
	
	@Override
	public void stateChanged(ChangeEvent c) {

		JSlider source = (JSlider)c.getSource();
		TileManager manager = (TileManager) ResourceMaster.getInstance().getResourceManager(TileManager.MANAGER_LABEL);

		AdvancedTileCache cache = manager.getCache();
		CacheRemovalStrategy<String, TileResource> strategy = cache.getRemovalStrategy();

		if(strategy instanceof OrderedRemovalStrategy) {
			((OrderedRemovalStrategy<String, TileResource>) strategy).setThreshold(source.getValue());
		}

		cacheSize.setText(Integer.toString(cache.getCachedResourcesCount()));
	}

}
