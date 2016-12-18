package de.kapsel.global.beans;

public abstract class AbstractModulBean {
	
	protected boolean emptyList;
	protected boolean editMode;
	
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
	
	public abstract void cancelEditMode();
	
	public abstract void onEditComplete();

}
