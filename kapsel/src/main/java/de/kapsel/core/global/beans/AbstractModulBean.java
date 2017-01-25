package de.kapsel.core.global.beans;

import java.util.ArrayList;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import de.kapsel.core.global.DTItem;

public abstract class AbstractModulBean {
	
	protected long passedID;
	protected boolean emptyList;
	protected boolean editMode;
	protected String activeTabs;
	
	public long getPassedID() {
		return passedID;
	}
	public void setPassedID(long passedID) {
		this.passedID = passedID;
	}
	
	public boolean isEmptyList() {
		return emptyList;
	}
	public void setEmptyList(boolean emptyList) {
		this.emptyList = emptyList;
	}
	public boolean isEditMode() {
		return editMode;
	}
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	
	public void enableEditMode(){
		setEditMode(true);
	}
	
	public void disableEditMode(){
		setEditMode(false);
	}
	
	public abstract void cancelEditMode();
	
	public abstract void onEditComplete();

	public String getActiveTabs() {
		return activeTabs;
	}

	public void setActiveTabs(String activeTabs) {
		this.activeTabs = activeTabs;
	}
	
	public void onTabChange(TabChangeEvent event){
		AccordionPanel ap = (AccordionPanel)event.getSource();
		setActiveTabs(ap.getActiveIndex());
	}
	
	public void onTabClose(TabCloseEvent event){
		AccordionPanel ap = (AccordionPanel)event.getSource();
		setActiveTabs(ap.getActiveIndex());
	}
	
	//Only 1 Position can be deleted at the same time, so just -1 everything that was higher than deleted item
	public void updateItemPosition(int delPos, ArrayList<DTItem> items){
		for(DTItem t:items){
			if(t.getPosition()>delPos){
				t.setPosition(t.getPosition()-1);
			}
		}
	}
	

}
