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
package sep.gaia.resources.tiles2d;

import javax.media.opengl.GLProfile;

import sep.gaia.resources.AbstractLoaderWorker;
import sep.gaia.resources.Cache;
import sep.gaia.resources.Query;
import sep.gaia.resources.WorkerFactory;

/**
 * Creates <code>TileLoaderWorker</code> objects.
 * These <code>TileLoaderWorker</code> are used afterwards for working the queries and responding the results to the <code>TileManager</code>.
 * @author Johannes Bauer
 */
public class TileWorkerFactory implements WorkerFactory<TileQuery, TileResource> {

	private GLProfile profile;
	
	
	/**
	 * Initializes the factory with a OpenGl-profile. 
	 * @param profile The GL-Profile to use for creating texture-data in the created workers.
	 */
	public TileWorkerFactory(GLProfile profile) {
		super();
		this.profile = profile;
	}



	@Override
	public AbstractLoaderWorker<TileQuery, TileResource> createWorker(
			TileQuery query, Cache<TileResource> cache) {
		return new TileLoaderWorker(query, profile, cache);
	}

}
