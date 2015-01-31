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

import java.util.Collection;

/**
 * An interface for reacting if the corresponding loader has partially processed
 * a query and therefore new data is avaliable.
 * @author Matthias Fisch
 *
 * @param <R> Type of the resources the information which availability implementing instances
 * want to be informed about.
 */
public interface LoaderEventListener<R extends DataResource> {

	/**
	 * Will be called if the loader the implementation of this listener is assigned to,
	 * has partially processed a query.
	 * @param resources The loaded resources mixed with dummy-objects representing those not yet loaded.
	 */
	public void onResourcesAvailable(Collection<R> resources);
}
