package com.github.alonwang.service.v6;

import com.github.alonwang.stereotype.Component;
import com.github.alonwang.util.MessageTracker;

@Component(value = "petStore")
public class PetStoreService implements IPetStoreService {

	public PetStoreService() {

	}

	public void placeOrder() {
		System.out.println("place order");
		MessageTracker.addMsg("place order");
	}

}
