package com.github.alonwang.service.v3;

import com.github.alonwang.dao.v3.AccountDao;
import com.github.alonwang.dao.v3.ItemDao;

/**
 * @author weilong.wang Created on 2018/7/3
 */
public class PetStoreService {
	private AccountDao accountDao;
	private ItemDao itemDao;
	private int version;

	public PetStoreService(AccountDao accountDao, ItemDao itemDao) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = -1;
	}

	public PetStoreService(AccountDao accountDao, ItemDao itemDao,
			int version) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = version;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public int getVersion() {
		return version;
	}
}
