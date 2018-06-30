package com.github.alonwang.service.v2;

import com.github.alonwang.test.dao.v2.AccountDao;
import com.github.alonwang.test.dao.v2.ItemDao;

/**
 * @author weilong.wang Created on 2018/6/30
 */
public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
