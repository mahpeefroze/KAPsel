package de.kapsel.global.beans;

public abstract class AbstractModulBean {
	
	protected boolean emptyList;
	protected boolean editMode;
	protected long passedID;
	
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
	
	public abstract void cancelEditMode();
	
	public abstract void onEditComplete();

}
