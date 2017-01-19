package de.kapsel.global.beans;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

public abstract class AbstractModulBean {
	
	protected boolean emptyList;
	protected boolean editMode;
	protected long passedID;
	protected String activeTabs;
	
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
	
	public long getPassedID() {
		return passedID;
	}
	public void setPassedID(long passedID) {
		this.passedID = passedID;
	}

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
	
	public abstract void cancelEditMode();
	
	public abstract void onEditComplete();

}
