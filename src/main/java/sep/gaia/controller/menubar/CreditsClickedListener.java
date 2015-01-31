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
package sep.gaia.controller.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sep.gaia.renderer.Mode3D;
import sep.gaia.resources.mode3d.Credits;
import sep.gaia.resources.mode3d.CreditsAnimator;

public class CreditsClickedListener implements ActionListener {

	
	private Mode3D mode;

	private CreditsAnimator animator;
	
	public CreditsClickedListener(Mode3D mode) {
		super();
		this.mode = mode;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(mode != null) {
			if(animator == null || (animator != null && animator.hasFinished())) {
				// Create the credits and an animator:
				Credits credits = new Credits();
				animator = new CreditsAnimator(credits);
				
				// Register them by the rendering instance and start the animation.
				mode.setCredits(credits);
				animator.start();
			}
		}
	}
	
}
