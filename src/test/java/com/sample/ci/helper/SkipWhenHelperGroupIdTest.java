package com.sample.ci.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.sample.ci.helper.model.SkipWhen;

@RunWith(MockitoJUnitRunner.class)
public class SkipWhenHelperGroupIdTest extends AbstractSkipWhenHelperSupport {

	@Test
	public void testWhenGroupIdMatch() {
		Mockito.when(this.project.getGroupId()).thenReturn("com.sample");

		SkipWhen skipWhen = new SkipWhen().withGroupIdEquals("com.sample");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenGroupIdNotMatching() {
		Mockito.when(this.project.getGroupId()).thenReturn("com.sample");

		SkipWhen skipWhen = new SkipWhen().withGroupIdEquals("com.samples");
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenGroupIdNegateMatching() {
		Mockito.when(this.project.getGroupId()).thenReturn("com.sample");

		SkipWhen skipWhen = new SkipWhen().withGroupIdEquals("!com.sample");
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenGroupIdNegateNotMatching() {
		Mockito.when(this.project.getGroupId()).thenReturn("com.sample");

		SkipWhen skipWhen = new SkipWhen().withGroupIdEquals("!com.samples");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}
}
