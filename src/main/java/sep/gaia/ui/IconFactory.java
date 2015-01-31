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

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sep.gaia.environment.Environment;
import sep.gaia.environment.Environment.EnvVariable;
import sep.gaia.util.Logger;

/**
 * Creates ImageIcon from image resources.
 * 
 * @author Johannes Bauer
 */
public class IconFactory {

	private IconFactory() {
	}

	/**
	 * Creates a new ImageIcon for the passed icon name.
	 * 
	 * @param pathToImage
	 *            The path to the Icons (which are included in it)
	 * @return The new ImageIcon
	 */
	public static ImageIcon createIcon(String imageURL) {
		if (imageURL != null && !imageURL.toString().isEmpty()) {
			return (new ImageIcon(imageURL));
		} else {
			Logger.getInstance().error("Couldn't find icon at " + imageURL);
			return null;
		}
		
		
	}

	public static Image createImage(String imageURL) {
		try {
			if (imageURL == null || imageURL.isEmpty()) {
				Logger.getInstance().error("Couldn't find icon at " + imageURL);
				return null;
			}

			return ImageIO.read(new File(imageURL));
		} catch (IOException e) {
			Logger.getInstance().error("Couldn't read image at " + imageURL);
			e.printStackTrace();
		}

		return null;
	}
	
	public static Image getIcon(String iconName) {
		
		return (createImage(Environment.getInstance().getString(EnvVariable.ICONS) + System.getProperty("file.separator") + iconName));
	}
}
