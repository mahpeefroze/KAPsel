package de.kapsel.core.global;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BusinessNumberStorage {
	
	private ConcurrentHashMap<String, AtomicLong> storage;
	
	public BusinessNumberStorage(long anr, long knr, long pnr){
		
		long temp = prefillStorage();
		
		if(anr>temp){
			storage.replace("ANR", new AtomicLong(anr));
		}
		
		if(knr>temp){
			storage.replace("KNR", new AtomicLong(knr));
		}
		
		if(pnr>temp){
			storage.replace("PNR", new AtomicLong(pnr));
		}
		
	}
	
	public BusinessNumberStorage(){
		prefillStorage();
	}
	
	private long prefillStorage(){
		long temp = (long) (( Calendar.getInstance().get(Calendar.YEAR) * Math.pow(10, 6)) +
				( (Calendar.getInstance().get(Calendar.MONTH)+1) * Math.pow(10, 4)) + 1);
		storage = new ConcurrentHashMap<String, AtomicLong>();
		storage.put("ANR", new AtomicLong(temp));
		storage.put("KNR", new AtomicLong(temp));
		storage.put("PNR", new AtomicLong(temp));
		return temp;
	}
	
	public long getNextMax(String name){
		return storage.get(name).getAndIncrement();
	}
	
	public long rollbackLast(String name){
		return storage.get(name).decrementAndGet();
	}
	
	public HashMap<String, AtomicLong> getValues(){
		return new HashMap<String, AtomicLong>(this.storage);
	}
}
