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

import sep.gaia.resources.caching.AdvancedCache;
import sep.gaia.resources.tiles2d.AdvancedTileCache;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A thread processing a part of a query in order to take e.g. advantage of
 * higher bandwidth-usage. After a worker was created, a task has been assigned
 * and it was started, it will perform its query and finish afterwards.
 * @author Matthias Fisch
 *
 */
public abstract class AbstractLoaderWorker<Q extends Query, R extends DataResource> extends Thread {

	/**
	 * The part of the query this worker is supposed to process.
	 */
	private Q subQuery;

	/**
	 * The resources generated from the queries result.
	 */
	private Collection<R> results = new LinkedList<>();
	
	/**
	 * The cache this worker should use
	 */
	private AdvancedCache<String, R> cache;
	
	/**
	 * Flag if this worker was already evaluated by its loader.
	 */
	private boolean evaluated;

	/**
	 * Initializes the worker.
	 * @param subQuery The part of the query this worker is supposed to process.
	 */
	public AbstractLoaderWorker(Q subQuery) {
		super();
		this.subQuery = subQuery;
	}
	
	/**
	 * Initializes the worker.
	 * @param subQuery The part of the query this worker is supposed to process.
	 * @param cache The cache this worker should use or <code>null</code> if no cache
	 * should be used.
	 */
	public AbstractLoaderWorker(Q subQuery, AdvancedCache<String, R> cache) {
		super();
		this.subQuery = subQuery;
		this.cache = cache;
	}

	/**
	 * Performs the partial query and stores the result in <code>result</code>.
	 * After the result is stored this thread terminates.
	 */
	public abstract void run();
	
	
	/**
	 * Returns the part of the query this worker is supposed to process.
	 * @return The part of the query this worker is supposed to process.
	 */
	public Q getSubQuery() {
		return subQuery;
	}

	/**
	 * Returns if this worker was already evaluated by a loader.
	 * @return <code>true</code> if the worker was evaluated. Otherwise <code>false</code>
	 * is returned.
	 */
	public boolean isEvaluated() {
		return evaluated;
	}

	/**
	 * Sets the result of the query.
	 * @param results Collection of all resources resulted from the query.
	 */
	protected void setResults(Collection<R> results) {
		this.results = results;
	}

	/**
	 * Returns the cache this worker should use.
	 * @return The cache this worker should use or <code>null</code> if no cache
	 * should be used.
	 */
	protected AdvancedCache<String, R> getCache() {
		return cache;
	}

	/**
	 * Returns the resources generated from the queries result and marks the worker as evaluated.
	 * @return The resources generated from the queries result or <code>null</code>
	 * if no result could be generated.
	 */
	public Collection<R> getResults() {
		if(!isAlive()) {
			evaluated = true;
		}
		return results;
	}
}
