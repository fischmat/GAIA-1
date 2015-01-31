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
package sep.gaia.resources;

/**
 * Class for generating new workers. This functionality is used by <code>AbstractLoader</code>
 * when a query was split and will be processed. Implement this interface when defining own
 * loaders and corresponding workers.
 * @author Matthias Fisch
 *
 */
public interface WorkerFactory<Q extends Query, R extends DataResource> {

	/**
	 * Creates a new worker-object prepared to process <code>query</code>.
	 * @param query The query the created worker is supposed to process.
	 * @param cache The cache the created worker should use.
	 * @return The created worker.
	 */
	public AbstractLoaderWorker<Q, R> createWorker(Q query, Cache<R> cache);
}
