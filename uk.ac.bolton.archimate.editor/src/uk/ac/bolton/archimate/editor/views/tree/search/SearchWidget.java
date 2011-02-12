/*******************************************************************************
 * Copyright (c) 2010 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 *******************************************************************************/
package uk.ac.bolton.archimate.editor.views.tree.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import uk.ac.bolton.archimate.editor.actions.AbstractDropDownAction;
import uk.ac.bolton.archimate.editor.ui.ArchimateNames;
import uk.ac.bolton.archimate.editor.ui.IArchimateImages;
import uk.ac.bolton.archimate.editor.utils.PlatformUtils;
import uk.ac.bolton.archimate.model.util.ArchimateModelUtils;


/**
 * Search Widget
 * 
 * @author Phillip Beauvoir
 */
public class SearchWidget extends Composite {

    private Control fSearchControl;
    
    private SearchFilter fSearchFilter;
    
    private IAction fActionFilterName;
    private IAction fActionFilterDoc;
    
    private List<IAction> fObjectActions = new ArrayList<IAction>();
    
    private int fSearchFlags = SearchFilter.FILTER_NAME;

    public SearchWidget(Composite parent, SearchFilter filter) {
        super(parent, SWT.NULL);
        
        GridLayout layout = new GridLayout(2, false);
        setLayout(layout);
        setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        fSearchFilter = filter;

        setupToolBar();
        setupSearchTextWidget();
        
        doSetFilterFlags();
    }
    
    @Override
    public boolean setFocus() {
        return fSearchControl.setFocus();
    }
    
    protected void setupSearchTextWidget() {
        if(PlatformUtils.isWindows()) {
            fSearchControl = new SearchTextWidget(this);
            ((SearchTextWidget)fSearchControl).getTextControl().addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    fSearchFilter.setSearchText(((SearchTextWidget)fSearchControl).getText());
                }
            });
            fSearchControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        }
        else {
            fSearchControl = new Text(this, SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
            ((Text)fSearchControl).addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    fSearchFilter.setSearchText(((Text)fSearchControl).getText());
                }
            });
            fSearchControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        }
    }

    protected void setupToolBar() {
        fActionFilterName = new Action("Name", IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                fSearchFlags ^= SearchFilter.FILTER_NAME;
                doSetFilterFlags();
            };
        };
        fActionFilterName.setToolTipText("Search in Name");
        fActionFilterName.setChecked(true);
        
        fActionFilterDoc = new Action("Documentation", IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                fSearchFlags ^= SearchFilter.FILTER_DOC;
                doSetFilterFlags();
            }
        };
        fActionFilterDoc.setToolTipText("Search in Documentation");

        ToolBarManager toolBarmanager = new ToolBarManager(SWT.FLAT);
        toolBarmanager.createControl(this);

        AbstractDropDownAction dropDownAction = new AbstractDropDownAction("Filter Options") {
            @Override
            public void run() {
                // Clear objects
                for(IAction action : fObjectActions) {
                    action.setChecked(false);
                }
                fSearchFilter.clearObjectFilter();
                // Clear flags
                fSearchFlags = SearchFilter.FILTER_NAME;
                fActionFilterName.setChecked(true);
                fActionFilterDoc.setChecked(false);
                doSetFilterFlags();
            }
            
            @Override
            public ImageDescriptor getImageDescriptor() {
                return IArchimateImages.ImageFactory.getImageDescriptor(IArchimateImages.ICON_FILTER_16);
            }
        };
        toolBarmanager.add(dropDownAction);

        dropDownAction.add(fActionFilterName);
        dropDownAction.add(fActionFilterDoc);
        dropDownAction.add(new Separator());
        
        MenuManager businessMenu = new MenuManager("Business");
        dropDownAction.add(businessMenu);
        for(EClass eClass : ArchimateModelUtils.getBusinessClasses()) {
            businessMenu.add(createObjectAction(eClass));
        }
        
        MenuManager applicationMenu = new MenuManager("Application");
        dropDownAction.add(applicationMenu);
        for(EClass eClass : ArchimateModelUtils.getApplicationClasses()) {
            applicationMenu.add(createObjectAction(eClass));
        }
        
        MenuManager technologyMenu = new MenuManager("Technology");
        dropDownAction.add(technologyMenu);
        for(EClass eClass : ArchimateModelUtils.getTechnologyClasses()) {
            technologyMenu.add(createObjectAction(eClass));
        }
        
        MenuManager relationsMenu = new MenuManager("Relations");
        dropDownAction.add(relationsMenu);
        for(EClass eClass : ArchimateModelUtils.getRelationsClasses()) {
            relationsMenu.add(createObjectAction(eClass));
        }
        
        toolBarmanager.update(true);
    }
    
    private IAction createObjectAction(final EClass eClass) {
        IAction action = new Action(ArchimateNames.getDefaultName(eClass), IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                if(isChecked()) {
                    fSearchFilter.addObjectFilter(eClass);
                }
                else {
                    fSearchFilter.removeObjectFilter(eClass);
                }
            }
            
            @Override
            public ImageDescriptor getImageDescriptor() {
                return null;
                //return UIResources.getImageDescriptor(eClass);
            }
        };
        
        fObjectActions.add(action);
        
        return action;
    }

    private void doSetFilterFlags() {
        fSearchFilter.setFilterFlags(fSearchFlags);
    }
}