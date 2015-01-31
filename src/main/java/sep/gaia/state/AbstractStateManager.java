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
package sep.gaia.state;

import java.util.Map;

/**
 * Interface to manage the different categories of states by updating all
 * states whenever one of them is changed.
 * An implementing class has to be a Singleton.
 * 
 * @author Max Witzelsperger
 *
 */
public interface AbstractStateManager {

	/**
	 * Enumerates all possible types of concrete states to be managed.
	 * @author Matthias Fisch
	 *
	 */
	public enum StateType {GLState, GeoState, TileState}	
	
	/**
	 * Returns the state of type <code>type</code> managed.
	 * @param type The type of the state to return.
	 * @return The state of type <code>type</code> or <code>null</code>
	 * if there is none.
	 */
	public State getState(StateType type);
	
}