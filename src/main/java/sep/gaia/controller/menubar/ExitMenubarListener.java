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

import javax.swing.JFrame;

/**
 * This is to implement the <code>ActionListener</code> Interface to check if
 * the user presses the "Exit" item in the menubar to exit the program.
 * 
 * @author Michael Mitterer
 */
public class ExitMenubarListener implements ActionListener {
	/**
	 * The <code>MainWindow</code> reference.
	 */
	private JFrame mainWindow;
	
	/**
	 * ExitMenubarListener constructor
	 * 
	 * @param mainWindow The <code>MainWindow</code> reference
	 */
	public ExitMenubarListener(JFrame mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.dispose();
		System.exit(0);
	}

}
