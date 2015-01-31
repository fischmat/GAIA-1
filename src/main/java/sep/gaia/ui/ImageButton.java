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
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;

public class ImageButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image icon;
	
	public ImageButton(final File image) {
		super();
		
		// Load icon.
		try {
			icon = ImageIO.read(image);
		} catch (IOException e) {
			/* TODO Fehlermeldung*/
			e.printStackTrace();
		} 
		
		setIcon(new Icon() {
			
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				g.drawImage(icon, c.getWidth()/4, c.getHeight()/4, c.getWidth()/2, c.getHeight()/2, null);
			}
			
			@Override
			public int getIconWidth() {
				return icon.getWidth(null);
			}
			
			@Override
			public int getIconHeight() {
				return icon.getHeight(null);
			}
		});
		
		repaint();
	}
	
	public ImageButton(String label, final File image) {
		super(label);
		
		// Load icon.
		try {
			icon = ImageIO.read(image);
		} catch (IOException e) {
			/* TODO Fehlermeldung*/
			e.printStackTrace();
		} 
		
		setIcon(new Icon() {
			
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				//g.drawImage(icon, c.getWidth()/4, c.getHeight()/4, c.getWidth()/2, c.getHeight()/2, null);
				g.drawImage(icon, 0, 0, c.getWidth(), c.getHeight(), null);
			}
			
			@Override
			public int getIconWidth() {
				return icon.getWidth(null);
			}
			
			@Override
			public int getIconHeight() {
				return icon.getHeight(null);
			}
		});
		
		repaint();
	}
}
