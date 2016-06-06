package com.sample.ci.helper;

import org.junit.Assert;
import org.junit.Test;



public class BooleanEvaluationTest {

	@Test
	public void testNotProcessed() {
		SkipWhenHelper.BOOLEAN_EVAL eval = SkipWhenHelper.BOOLEAN_EVAL.NOT_PROCESSED;
		Assert.assertFalse(eval.isProcessed());
		
		try {
			Assert.assertFalse(eval.toBoolean());
			Assert.fail("not processed operation should be converted");
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testTrue() {
		SkipWhenHelper.BOOLEAN_EVAL eval = SkipWhenHelper.BOOLEAN_EVAL.TRUE;
		Assert.assertTrue(eval.isProcessed());
		Assert.assertTrue(eval.toBoolean());
	}
	
	@Test
	public void testFalse() {
		SkipWhenHelper.BOOLEAN_EVAL eval = SkipWhenHelper.BOOLEAN_EVAL.FALSE;
		Assert.assertTrue(eval.isProcessed());
		Assert.assertFalse(eval.toBoolean());
	}

}
