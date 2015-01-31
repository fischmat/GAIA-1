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
package sep.gaia.ui;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLCapabilitiesImmutable;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;


public class GaiaCanvas extends GLCanvas {
	private static final long serialVersionUID = 4264757805580255971L;
	
	private static GaiaCanvas instance;
	
	private GaiaCanvas(GLCapabilitiesImmutable capsReqUser) throws GLException {
		super(capsReqUser);
	}

	public static GaiaCanvas getInstance() throws IllegalStateException {
		if(instance == null) {
			throw new IllegalStateException("The canvas must be initialized with GL-capabilities.");
		}
		return instance;
	}
	
	public static GaiaCanvas getInstance(GLCapabilities capabilities) {
		if(instance == null) {
			instance = new GaiaCanvas(capabilities);
		}
		return instance;
	}
}