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
package sep.gaia.resources.locationsearch;

import sep.gaia.resources.AbstractLoaderWorker;
import sep.gaia.resources.Cache;
import sep.gaia.resources.WorkerFactory;
import sep.gaia.resources.caching.AdvancedCache;

/**
 * This class is to generate new workers necessary to initialize the 
 * request of the <code>http://nominatim.openstreetmap.org</code> api.
 * This functionality is used by the Loader instances responsible 
 * for loading locations.
 * 
 * @author Fabian Buske
 * 
 */
public class LocationWorkerFactory implements WorkerFactory<LocationQuery, Location> {

	@Override
	public AbstractLoaderWorker<LocationQuery, Location> createWorker(
			LocationQuery query, AdvancedCache<String, Location> cache) {
		return new LocationWorker(query);
	}

}
