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
import java.awt.Dimension;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sep.gaia.renderer.layer.ScreenshotLayer;

/** 
 * The bean class <code>MainWindow</code> is a JFrame
 * 
 * All graphical Swing components are built here.
 * @author Johannes Bauer, Michael Mitterer
 */
public class MainWindow {
	
	/**
	 * The windows info-bar at its bottom.
	 */
	private GAIAInfoBar infoBar;
	
	public MainWindow(GLCanvas canvas, MarkerPanel markerPanel, PoiBar poiBar, ScreenshotLayer screenshotLayer) {
		JFrame frame = new JFrame("GAIA");
		ImageIcon frameIcon = createIcon("/sep/gaia/renderer/icons/logo.png");
		//frame.setIconImage(frameIcon.getImage());
		//setNativeLookAndFeel();
		
		// Divide main frame: Menu bar in the north, "rest" in center.
		frame.setLayout(new BorderLayout());
		
		// Add menu bar.
		frame.getContentPane().add(new GAIAMenu(frame, markerPanel, poiBar), BorderLayout.NORTH);
		
		// Add main panel.
		frame.getContentPane().add(new GAIAPanel(canvas, markerPanel, poiBar, screenshotLayer), BorderLayout.CENTER);
		
		// Create and add the info-bar at the windows bottom:
		infoBar = new GAIAInfoBar();
		frame.getContentPane().add(infoBar, BorderLayout.SOUTH);

		// Prepare main frame for getting visible.
		frame.setMinimumSize(new Dimension(800, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void setNativeLookAndFeel() {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {

		}
	}
	
	/**
	 * Creates a new ImageIcon for the buttons
	 * 
	 * @param pathToImage The path to the Icons (which are included in it)
	 * @return The new ImageIcon
	 */
	protected ImageIcon createIcon(String pathToImage) {
	    java.net.URL imageURL = getClass().getResource(pathToImage);
	    if (imageURL != null) {
	        return new ImageIcon(imageURL);
	    } else {
	        System.err.println("Couldn't find the image.");
	        return null;
	    }
	}

	/**
	 * @return The windows info-bar.
	 */
	public GAIAInfoBar getInfoBar() {
		return infoBar;
	}
}