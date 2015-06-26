/**
 * 
 */
package com.revencoft.example.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.revencoft.example.service.BankService;
import com.revencoft.example.service.UserService;

/**
 * @author mengqingyan
 * @version 
 */
public class UserServiceImpl implements UserService {

	private final Logger log = Logger.getLogger(getClass());
	private int id;
	
	@Autowired
	private BankService bankService;
	private int bankId;
	@Override
	public void print(String msg) {
		log.info("print msg: " + msg);
		if(bankService != null)
			bankService.printBankName("test BankName.");
	}
	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	@Override
	public int getUserId() {
		
		return this.id;
	}
	
	public void setUserId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return String.format("UserServiceImpl [id=%s]", id);
	}
	
	

}
