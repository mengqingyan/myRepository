/**
 * 
 */
package com.revencoft.example.service.impl;

import org.apache.log4j.Logger;

import com.revencoft.example.service.BankService;

/**
 * @author mengqingyan
 * @version 
 */
public class BankServiceImpl implements BankService {

	private final Logger log = Logger.getLogger(getClass());
	
	private int bankId;
	
	@Override
	public void printBankName(String bankName) {
		log.info("print bankName: " + bankName);
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	
}
