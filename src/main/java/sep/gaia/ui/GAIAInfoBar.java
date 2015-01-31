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

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GAIAInfoBar extends JPanel {

	/**
	 * A label where the status-information can be printed.
	 */
	private JLabel statusInfoLabel;
	
	private JLabel positionLabel;
	
	public GAIAInfoBar() {
		
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		
		// Add the status-label:
		statusInfoLabel = new JLabel();
		statusInfoLabel.setFont(statusInfoLabel.getFont().deriveFont(Font.BOLD));
		add(statusInfoLabel, BorderLayout.WEST);
		
		positionLabel = new JLabel("0.0 0.0");
		add(positionLabel, BorderLayout.EAST);
	}

	public void setStatusMessage(String message) {
		statusInfoLabel.setText(message);
	}
	
	public void setPosition(float longitude, float latitude, float height) {
		String distanceUnit;
		if(height > 1000) {
			height /= 1000;
			distanceUnit = "km";
		} else {
			distanceUnit = "m";
		}
		String text = longitude + " " + latitude + " | " + Math.round(height) + " " + distanceUnit;
		positionLabel.setText(text);
	}
}
