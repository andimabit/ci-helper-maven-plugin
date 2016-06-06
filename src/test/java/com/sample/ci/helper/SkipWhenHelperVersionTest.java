package com.sample.ci.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.sample.ci.helper.model.SkipWhen;

@RunWith(MockitoJUnitRunner.class)
public class SkipWhenHelperVersionTest extends AbstractSkipWhenHelperSupport {

	@Test
	public void testWhenVersionMatch() {
		Mockito.when(this.project.getVersion()).thenReturn("1.0.0");

		SkipWhen skipWhen = new SkipWhen().withVersionEquals("1.0.0");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenVersionNotMatching() {
		Mockito.when(this.project.getVersion()).thenReturn("1.0.0");

		SkipWhen skipWhen = new SkipWhen().withVersionEquals("1.0.1");
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenVersionNegateMatching() {
		Mockito.when(this.project.getVersion()).thenReturn("1.0.0");

		SkipWhen skipWhen = new SkipWhen().withVersionEquals("!1.0.0");
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenVersionNegateNotMatching() {
		Mockito.when(this.project.getVersion()).thenReturn("1.0.0");

		SkipWhen skipWhen = new SkipWhen().withVersionEquals("!1.0.1");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}
}
