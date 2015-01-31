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
package sep.gaia.resources.weather;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.jogamp.opengl.util.texture.TextureIO;

import sep.gaia.resources.Cache;
import sep.gaia.resources.DataResource;
import sep.gaia.resources.LoaderEventListener;
import sep.gaia.resources.Query;

/**
 * <code>WeatherResource</code> objects are only stored in memory. The removal
 * strategy is last recently used.
 * 
 * @author Johannes Bauer
 * 
 */
public class WeatherCache extends Cache<WeatherResource> implements
		LoaderEventListener<WeatherResource> {

	private static final int MAX_ENTRIES = 20;
	
	private WeatherManager manager;

	/**
	 * Constructs a <code>WeatherCache</code> object with a specific manager.
	 * 
	 * @param manager the weather manager object to which <code>this</code>
	 * sends information about changes
	 */
	public WeatherCache(WeatherManager manager) {
		super(MAX_ENTRIES);
		this.manager = manager;
	}

	public void get(WeatherResource dummy) {
		String key = dummy.getKey();
		WeatherResource result = super.get(key);
		if (result == null) {
			Collection<DataResource> querySet = new HashSet<>();
			querySet.add(dummy);
			WeatherLoaderWorker worker = new WeatherLoaderWorker(new Query(querySet), this);
			worker.start();
		} else {
			Collection<WeatherResource> results = new HashSet<>();
			results.add(result);
			onResourcesAvailable(results);
		}
	}

	@Override
	public void onResourcesAvailable(Collection<WeatherResource> resources) {
		for (WeatherResource res : resources) {
			this.add(res);
		}
		Collection<CacheEntry> toRemove = this.manage();
		for (CacheEntry entry : toRemove) {
			Iterator<WeatherResource> iter = entry.getAll().iterator();
			while (iter.hasNext()) {
				WeatherResource res = iter.next();
				res.getTexture().destroy();
			}
		}
		manager.onResourcesAvailable(resources);
	}
}
