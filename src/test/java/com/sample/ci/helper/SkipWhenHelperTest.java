package com.sample.ci.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.sample.ci.helper.model.SkipWhen;

@RunWith(MockitoJUnitRunner.class)
public class SkipWhenHelperTest extends AbstractSkipWhenHelperSupport {

	@Test
	public void testWhenEmptyConfiguration() {
		SkipWhen skipWhen = new SkipWhen();
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
		Assert.assertFalse(this.helper.forSkipWhen(null).skipWhenMet());
		
		this.helper = new SkipWhenHelper(new SkipWhen(), this.project, this.log);
		Assert.assertFalse(this.helper.skipWhenMet());
		
		this.helper = new SkipWhenHelper(null, this.project, this.log);
		Assert.assertFalse(this.helper.skipWhenMet());
	}
	
	@Test
	public void testWhenConfigurationHasEmptyValue() {
		Mockito.when(this.project.getVersion()).thenReturn("1.0.0");

		SkipWhen skipWhen = new SkipWhen().withVersionEquals("");
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

}
