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
package sep.gaia.resources.poi;

import sep.gaia.resources.AbstractLoaderWorker;
import sep.gaia.resources.Cache;
import sep.gaia.resources.WorkerFactory;

/**
 * Class for generating new workers. This functionality is used by the Loader-instance
 * responsible for loading POIs.
 * @author Matthias Fisch
 *
 */
public class POIWorkerFactory implements WorkerFactory<POIQuery, PointOfInterest> {

	/**
	 * Returns a worker for processing <code>query</code>.
	 * @return A worker for processing <code>query</code>.
	 */
	@Override
	public AbstractLoaderWorker<POIQuery, PointOfInterest> createWorker(
			POIQuery query, Cache<PointOfInterest> cache) {
		return new POILoaderWorker(query, cache);
	}

}
