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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import sep.gaia.controller.settings.CacheSettingsListener;
import sep.gaia.controller.settings.SliderListener;
import sep.gaia.resources.ResourceMaster;
import sep.gaia.resources.tiles2d.TileCache;
import sep.gaia.resources.tiles2d.TileManager;

/** 
 * The <code>SettingsWindow</code> is to let the user decide which settings are
 * used for the program, for example the maximum cache size.
 * The settings are stored in a configuration file.
 * 
 * @author Michael Mitterer, Max Witzelsperger
 */
public class SettingsWindow {
	
	/** shows the current size of the cache */
	private JLabel cacheSize;
	
	
	private static final long MEGABYTE_TO_BYTE = 1024 * 1024;
	
	/**
	 * The SettingsWindow constructor
	 * 
	 * @param mainWindow The calling <code>MainWindow</code> to which the SettingsWindow
	 * belongs to.
	 */
	public SettingsWindow(JFrame mainWindow) {
		JDialog settings = new JDialog(mainWindow);
        settings.setTitle("Einstellungen");
               
        // determine the current size of the cache
		TileManager manager = (TileManager) ResourceMaster
				.getInstance().getResourceManager(TileManager.MANAGER_LABEL);
		long sizeOfCache = manager.getCache().getCurrentSizeOnDisk();
        cacheSize = new JLabel("" + sizeOfCache / MEGABYTE_TO_BYTE);
 
        JPanel cachePanel = new JPanel();
        //cachePanel.setLayout(new BoxLayout(cachePanel, BoxLayout.Y_AXIS));
        
        JLabel cacheLabel = new JLabel("Maximal gecachte Kartenkacheln: ");
        cachePanel.add(cacheLabel);
        
        JSlider maximumCache = new JSlider();
        maximumCache.setMaximum(50000);
        maximumCache.setMinimum(0);
        TileManager manager1 = (TileManager) ResourceMaster.getInstance().getResourceManager(TileManager.MANAGER_LABEL);
		
		TileCache cache = manager1.getCache();
        maximumCache.setValue((int) (cache.getMaximumSizeOnDisk() / MEGABYTE_TO_BYTE));
        maximumCache.setMajorTickSpacing(1000);
        maximumCache.setMinorTickSpacing(1000);
        maximumCache.setPaintTicks(true);
        maximumCache.addChangeListener(new SliderListener(cacheSize));
   
        cachePanel.add(maximumCache);
               
        JButton cacheCleaner = new JButton("Cache leeren");
        cacheCleaner.addActionListener(new CacheSettingsListener(cacheSize));
        cachePanel.add(cacheCleaner);  
        settings.getContentPane().add(cachePanel, BorderLayout.PAGE_START);

        /*
        JPanel serverPanel = new JPanel();
        JLabel serverLabel = new JLabel("Tile-Server: ");
        serverPanel.add(serverLabel);
        
        JComboBox<String> tileServer = new JComboBox<String>();
        serverPanel.add(tileServer);
        
        JButton addTileServer = new JButton("+");
        serverPanel.add(addTileServer);
        
        JButton removeTileServer = new JButton("-");
        serverPanel.add(removeTileServer);
        settings.getContentPane().add(serverPanel, BorderLayout.WEST);
        
        JButton poiEditor = new JButton("POI-Editor");
        // TODO Klasse in Controller erstellen
        poiEditor.addActionListener(new ActionListener());{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog poiEditor = new JDialog(settings);
				poiEditor.setTitle("POI-Editor");
				poiEditor.setLocationRelativeTo(frame);
	            poiEditor.pack();
	            
	            poiEditor.setModal(true);
	            poiEditor.setVisible(true);
				
			}
        	
        });
        settings.getContentPane().add(poiEditor, BorderLayout.AFTER_LINE_ENDS);*/
        settings.setLocationRelativeTo(mainWindow);
        settings.pack();
        settings.setMaximumSize(mainWindow.getSize());
        
        settings.setModal(true);
        settings.setVisible(true);
	}
	
	/*/** 
	 * The <code>POIEditorWindow</code> which is build if the user presses the
	 * JButton "POI-Editor" in the <code>SettingsWindow</code> settingsWindow
	 *
	private POIEditorWindow pOIEditorWindow;// = new POIEditorWindow(settings);
	
	/*public POIEditorWindow showPOIEditorWindow() {
		return pOIEditorWindow;
	}*/
}
