/**
 * 
 */
package com.revencoft.example.generic.factory;

/**
 * @author mengqingyan
 * @version 
 */
public class IntegerFactory implements GenericFactory<Integer> {

	@Override
	public Integer create() {
		return new Integer(0);
	}

}
