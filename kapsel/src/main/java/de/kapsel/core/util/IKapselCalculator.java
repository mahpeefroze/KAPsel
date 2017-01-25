package de.kapsel.core.util;

import java.io.Serializable;

public interface IKapselCalculator<T extends Serializable> {
	
	int calculateTime(T t);
	
	double calculateNettoPrice(T t);
	
	double calculateBruttoPrice(T t);
	
	double calculateAfterDiscount(T t);
	
}
