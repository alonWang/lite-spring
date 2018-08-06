package com.github.alonwang.service.v5;

import com.github.alonwang.beans.core.annotation.Autowired;
import com.github.alonwang.dao.v5.AccountDao;
import com.github.alonwang.dao.v5.ItemDao;
import com.github.alonwang.stereotype.Component;
import com.github.alonwang.util.MessageTracker;

@Component(value="petStore")
public class PetStoreService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    ItemDao itemDao;

    public PetStoreService() {

    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void placeOrder(){
        System.out.println("place order");
        MessageTracker.addMsg("place order");

    }
}