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

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import sep.gaia.util.Logger;

/**
 * This is to implement the <code>ActionListener</code> Interface to check if
 * the user presses the "Help" item in the menubar to open the help documentation.
 * 
 * @author Michael Mitterer
 */
public class HelpMenubarListener implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
			try {
				Desktop.getDesktop().open(new File("Manual.pdf"));
			} catch (IOException e1) {
				Logger.getInstance().message("Das Handbuch konnte nicht geöffnet werden.");
			}
	}

}
