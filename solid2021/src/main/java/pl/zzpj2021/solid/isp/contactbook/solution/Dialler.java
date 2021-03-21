package pl.zzpj2021.solid.isp.contactbook.solution;

import org.jetbrains.annotations.NotNull;

interface Dialler {
	
     default void makeCall(@NotNull Contact dialable) {
    	
    	String telephone = dialable.getTelephone();
    	
    	// call using telephone
    }
}