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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sep.gaia.environment.Environment;
import sep.gaia.environment.Environment.EnvVariable;

/** 
 * This is to show the names of the developers to whom it may interest
 * 
 * @author Michael Mitterer
 */
public class AboutWindow {
	/**
	 * The <code>AboutWindow</code> constructor
	 * 
	 * @param mainWindow The <code>MainWindow</code> to which the <code>AboutWindow</code> belongs to.
	 */
	public AboutWindow(JFrame mainWindow) {
		JDialog about = new JDialog(mainWindow);
        about.setTitle("Über");
        about.setSize(new Dimension(260, 210));
        about.setLocationRelativeTo(mainWindow);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        ImageIcon imageIcon = createIcon("/sep/gaia/renderer/icons/About.png", "Logo");
        JLabel image = new JLabel(imageIcon);
        
        JLabel version = new JLabel(Environment.getInstance().getString(EnvVariable.VERSION_STRING));
        
        JLabel text = new JLabel("Johannes Bauer");
        JLabel text2 = new JLabel("Fabian Buske");
        JLabel text3 = new JLabel("Matthias Fisch");
        JLabel text4 = new JLabel("Michael Mitterer");
        JLabel text5 = new JLabel("Maximilian Witzelsperger");
        
        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        version.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text2.setAlignmentX(Component.CENTER_ALIGNMENT);
        text3.setAlignmentX(Component.CENTER_ALIGNMENT);
        text4.setAlignmentX(Component.CENTER_ALIGNMENT);
        text5.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(image);
        panel.add(version);
        panel.add(new JLabel(" "));
        panel.add(text);
        panel.add(text2);
        panel.add(text3);
        panel.add(text4);
        panel.add(text5);
        about.getContentPane().add(panel);
        about.setModal(true);
        about.setVisible(true);
	}
	
	/**
	 * Creates a new ImageIcon for the buttons
	 * 
	 * @param pathToImage The path to the Icons (which are included in it)
	 * @param shortDescription The short description of the usage of the
	 * corresponding object
	 * @return The new ImageIcon
	 */
	protected ImageIcon createIcon(String pathToImage, String shortDescription) {
	    java.net.URL imageURL = getClass().getResource(pathToImage);
	    if (imageURL != null) {
	        return new ImageIcon(imageURL, shortDescription);
	    } else {
	        System.err.println("Couldn't find the image.");
	        return null;
	    }
	}
}
