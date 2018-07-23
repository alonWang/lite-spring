package com.github.alonwang.service.v4;

import com.github.alonwang.beans.core.annotation.Autowired;
import com.github.alonwang.dao.v4.AccountDao;
import com.github.alonwang.dao.v4.ItemDao;
import com.github.alonwang.stereotype.Component;

/**
 * @author weilong.wang Created on 2018/7/14
 */
@Component(value = "petStore")
public class PetStoreService {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ItemDao itemDao;

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}
}
