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
 * Interface which is implemented by resource-observing classes like the
 * <code>ResourceAdapter</code> class.
 * 
 * @param <R> The type of resources this observer receives on update.
 * Necessary for the connection of layer classes (View) and resources (Model).
 * 
 * @author Johannes Bauer (Spezifikation: Matthias Fisch)
 * 
 */
public interface ResourceObserver<R extends DataResource> {

	/**
	 * Called by the <code>ResourceObservable</code> object to notify
	 * <code>ResourceObserver</code> implementations. It is not guaranteed that
	 * this method is called in the same thread as the registering method.
	 * 
	 * @param resources
	 *            A <code>Collection</code> of current used
	 *            <code>DataResource</code> objects. "Used" means that a
	 *            <code>State</code> indicates, these <code>DataResource</code>
	 *            objects are necessary for the application at this specific
	 *            situation.
	 */
	public void onUpdate(Collection<R> resources);
	
	/**
	 * Called by the <code>ResourceObservable</code> to notify about a subsequent
	 * update with new data.
	 */
	public void onClear();
}
