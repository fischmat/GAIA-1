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
package sep.gaia.util.exception;

/**
 * To be thrown if a bounding-box is created with corners not forming
 * a rectangle.
 * @author Matthias Fisch
 *
 */
public class NotABoxException extends IllegalArgumentException {
	private static final long serialVersionUID = 6789662737738573048L;

	/**
	 * Initializes the exception without specifying an error-message.
	 */
	public NotABoxException() {
		super();
	}

	/**
	 * Initializes the exception with an error-message.
	 * @param s The error-message to be stored in the exception.
	 */
	public NotABoxException(String s) {
		super(s);
	}
}
