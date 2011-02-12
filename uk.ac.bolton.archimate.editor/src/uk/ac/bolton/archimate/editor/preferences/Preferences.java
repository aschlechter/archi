/*******************************************************************************
 * Copyright (c) 2010-2011 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 *******************************************************************************/
package uk.ac.bolton.archimate.editor.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import uk.ac.bolton.archimate.editor.ArchimateEditorPlugin;


/**
 * Preferences Manager
 * Convenience class
 * 
 * @author Phillip Beauvoir
 */
public class Preferences implements IPreferenceConstants {
    
    public static IPreferenceStore STORE = ArchimateEditorPlugin.INSTANCE.getPreferenceStore();

    public static String getUserDataFolder() {
        return STORE.getString(USER_DATA_FOLDER);
    }
    
    public static int getGridSize() {
        return STORE.getInt(GRID_SIZE);
    }
    
    public static boolean doOpenDiagramsOnLoad() {
        return STORE.getBoolean(OPEN_DIAGRAMS_ON_LOAD);
    }
    
    public static boolean doAnimate() {
        return STORE.getBoolean(ANIMATE);
    }
    
    public static int getAnimationSpeed() {
        return STORE.getInt(ANIMATION_SPEED);
    }
    
    public static boolean useAntiAliasing() {
        return STORE.getBoolean(ANTI_ALIAS);
    }
    
    public static String getDefaultViewFont() {
        return STORE.getString(DEFAULT_VIEW_FONT);
    }
    
    public static void setDefaultViewFont(String val) {
        STORE.setValue(DEFAULT_VIEW_FONT, val);
    }
    
    public static boolean doLinkView() {
        return STORE.getBoolean(LINK_VIEW);
    }
    
    public static void setLinkView(boolean val) {
        STORE.setValue(LINK_VIEW, val);
    }
    
    public static int getApplicationComponentFigureType() {
        return STORE.getInt(APPLICATION_COMPONENT_FIGURE);
    }
    
    public static int getTechnologyNodeFigureType() {
        return STORE.getInt(TECHNOLOGY_NODE_FIGURE);
    }
    
    public static int getTechnologyDeviceFigureType() {
        return STORE.getInt(TECHNOLOGY_DEVICE_FIGURE);
    }
    
    public static boolean doFilterShowEmptyFolders() {
        return STORE.getBoolean(FILTER_SHOW_EMPTY_FOLDERS);
    }
    
    public static boolean doSketchShowBackground() {
        return STORE.getBoolean(SKETCH_SHOW_BACKGROUND);
    }
    
    public static boolean doShowPalette() {
        return STORE.getBoolean(PALETTE_STATE);
    }
    
    public static boolean doAnimateMagicConnector() {
        return STORE.getBoolean(ANIMATE_MAGIC_CONNECTOR);
    }
    
    public static boolean isMagicConnectorPolarity() {
        return STORE.getBoolean(MAGIC_CONNECTOR_POLARITY);
    }
    
    public static boolean doShowViewTooltips() {
        return STORE.getBoolean(VIEW_TOOLTIPS);
    }
}