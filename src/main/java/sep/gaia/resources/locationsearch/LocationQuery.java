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

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import sep.gaia.resources.DataResource;
import sep.gaia.resources.NotADummyException;
import sep.gaia.resources.Query;

/**
 * This class inherits from <code<Query</code> class. So it is responsible for
 * transmitting the <code>userSearchString</code> from the <code>queryForLocations</code> 
 * function of the <code>LocationSearch</code> class to the requesting
 * <code>loader</code> and thus to the corresponding <code>LocationWorker</code>.
 * 
 * 
 * @author Fabian Buske
 *
 */
public class LocationQuery extends Query {

	/**
	 * This is the name of the <code>Location</code> to be searched for.
	 */
	private String search;
	
	
	/**
	 * Constructor to transmit <code>search</code> from the 
	 * <code>queryForLocations</code> of the <code>LocationSearch</code> class
	 * to the corresponding <code>loader</code>.
	 * 
	 * @param resource The <code>Location</code> to be searched for.
	 * 
	 * @param search The name of the <code>Location</code> to be searched for.
	 * 
	 * @throws NotADummyException Thrown if <code>resource</code> has not been
	 * filled with appropriate values after the loading process.
	 */
	public LocationQuery(DataResource resource, String searchString)
			throws NotADummyException {
		super(new LinkedList<DataResource>());
		Collection<DataResource> resources = new LinkedList<DataResource>();
		resources.add(resource);
		search = searchString;
		setResources(resources);		
	}
	
	/**
	 * This method returns the name of the search.
	 * 
	 * @return The name of the search.
	 */
	public String getSearch() {
		return this.search;
	}
	
	/**
	 * This method sets the name of the search.
	 * 
	 * @param search The name of the search to be set.
	 */
	public void setSearch(String search) {
		this.search = search;
	}

}
