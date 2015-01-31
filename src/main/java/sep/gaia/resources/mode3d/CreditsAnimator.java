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
package sep.gaia.resources.mode3d;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An animator for the credits-animation.
 * @author Matthias Fisch
 *
 */
public class CreditsAnimator extends TimerTask {

	/**
	 * The update-period in milliseconds.
	 */
	private static final int PERIOD = 100;
	
	/**
	 * GL-coordinates to move per <code>PERIOD</code>
	 */
	private static final float TRANSLATION_PER_STEP = -0.1f;
	
	/**
	 * The timer to use for animation.
	 */
	private Timer timer = new Timer();
	
	/**
	 * The credits to animate.
	 */
	private Credits credits;
	
	/**
	 * Flag, whether the animation was already started.
	 */
	private boolean started;
	
	/**
	 * Flag, whether the animation has finished.
	 */
	private boolean finished;
	
	/**
	 * @param credits The credits to animate.
	 */
	public CreditsAnimator(Credits credits) {
		this.credits = credits;
	}
	
	@Override
	public void run() {
		if(credits != null) {
			started = true;
			credits.translate(TRANSLATION_PER_STEP);
			
			if(credits.getPosition() > Credits.CREDITS_PANE_HEIGHT * 2) {
				finished = true;
			}
		}
	}

	/**
	 * Starts the animation. This can only be done once.
	 */
	public synchronized void start() {
		if(!started) {
			timer.schedule(this, 0, PERIOD);
		}
	}
	
	/**
	 * @return Whether the animation has finished.
	 */
	public boolean hasFinished() {
		return finished;
	}
}
