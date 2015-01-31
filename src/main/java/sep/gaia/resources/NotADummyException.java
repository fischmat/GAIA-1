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
 * A exception that is thrown if a passed <code>DataResource</code> object
 * unexpectedly does not have the dummy-flag set.
 * 
 * @author Johannes Bauer (Spezifikation: Matthias Fisch)
 * 
 */
public class NotADummyException extends IllegalArgumentException {
	private static final long serialVersionUID = -5890558029746893515L;

	/**
	 * Initializes the exception without specifying an error-message.
	 */
	public NotADummyException() {
		super();
	}

	/**
	 * Initializes the exception with a error-message.
	 * 
	 * @param s
	 *            The error-message to be stored in the exception.
	 */
	public NotADummyException(String s) {
		super(s);
	}

}
