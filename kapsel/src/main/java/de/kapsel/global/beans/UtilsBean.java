package de.kapsel.global.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedProperty;

import org.springframework.dao.DataAccessException;

import de.kapsel.global.BusinessNumberStorage;
import de.kapsel.global.entities.Utils;
import de.kapsel.global.services.IUtilsService;

@ManagedBean
@ApplicationScoped
public class UtilsBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{utilsService}")
	private IUtilsService utilsService;
	
	private Utils selectedUtils;
	private HashMap<String, Utils> utilsMap;
	private BusinessNumberStorage nrStorage;
	private String[] rabatteCB;
	
	//region Getter und Setter

	public IUtilsService getUtilsService() {
		return utilsService;
	}

	public void setUtilsService(IUtilsService utilsService) {
		this.utilsService = utilsService;
	}
	
	public Utils getSelectedUtils() {
		return selectedUtils;
	}

	public void setSelectedUtils(Utils selectedUtils) {
		this.selectedUtils = selectedUtils;
	}

	public HashMap<String, Utils> getUtilsMap() {
		return utilsMap;
	}

	public void setUtilsMap(HashMap<String, Utils> utilsMap) {
		this.utilsMap = utilsMap;
	}

	public String[] getRabatteCB() {
		ArrayList<String> rabatte = new ArrayList<String>();
		if(getUtilsMap()!=null){
			for(String key: getUtilsMap().keySet()){
				if(key.startsWith("Rab") && key.endsWith("S") && getUtilsMap().get(key).getValue()==1){
					rabatte.add(key);
				}
			}
		}
		this.rabatteCB = new String[rabatte.size()];
		for(int i=0; i<rabatte.size(); i++){
			this.rabatteCB[i]=rabatte.get(i);
		}
		
		return this.rabatteCB;
	}

	public void setRabatteCB(String[] rabatteCB) {
		this.rabatteCB = rabatteCB;
		//Set all statuses 0
		setRabattActivity("RabAlleS", 0);
		setRabattActivity("RabOeffS", 0);
		setRabattActivity("RabFiS", 0);
		setRabattActivity("RabPrivS", 0);
		
		for(int i=0; i<this.rabatteCB.length; i++){
			//Set all activated Statuses to 1 
			setRabattActivity(this.rabatteCB[i],1);
		}
	}
	
	private void setRabattActivity(String rabName, int value){
		getUtilsMap().get(rabName).setValue(value);
	}
	
	//endregion

	@PostConstruct
	public void myInit(){
		try {
			setUtilsMap(new HashMap<String, Utils>());
			for(Utils u:getUtilsService().getUtils()){
				getUtilsMap().put(u.getAbbr(), u);
			}
			this.nrStorage = new BusinessNumberStorage((long)getUtilsMap().get("ANR").getValue() , 
													(long)getUtilsMap().get("KNR").getValue(), 
													(long)getUtilsMap().get("PNR").getValue());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
		}	
	}
	
	public void updateNrStorage(){
		for(Map.Entry<String, AtomicLong> entry:this.nrStorage.getValues().entrySet()){
			getUtilsMap().get(entry.getKey()).setValue(entry.getValue().doubleValue());
			getUtilsService().updateUtils(getUtilsMap().get(entry.getKey()));
		}
	}
	
	public long getNextMax(String name){
		return this.nrStorage.getNextMax(name);
	}
	
	public void rollbackLast(String name){
		System.out.println(this.nrStorage.rollbackLast(name) + "is free again.");
	}

	//region UTILS Settings
	public void updateUtils(){
		getUtilsService().updateUtils(getSelectedUtils());
	}
	
	public void updateUtilsByType(char type){
		for(Utils u:getUtilsMap().values()){
			if(u.getTyp()!=type){
				continue;
			}
			getUtilsService().updateUtils(u);
		}
	}
	
	public boolean grabRabStatus(String rabName){
		if(getUtilsMap().get(rabName).getValue()==0){
			return false;
		}
		return true;
	}
	
	//endregion UTILS

}
