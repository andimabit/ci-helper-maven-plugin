package com.sample.ci.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.sample.ci.helper.model.SkipWhen;

@RunWith(MockitoJUnitRunner.class)
public class SkipWhenHelperPackagingTest extends AbstractSkipWhenHelperSupport {

	// --- packaging tests
	
	@Test
	public void testWhenPackagingMatch() {
		Mockito.when(this.project.getPackaging()).thenReturn("pom");
		Mockito.when(this.project.getGroupId()).thenReturn("com.sample");
		
		SkipWhen skipWhen = new SkipWhen().withPackagingEquals("pom").withGroupIdEquals("com.sample");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
		
		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenPackagingNotMatching() {
		Mockito.when(this.project.getPackaging()).thenReturn("pom");
		SkipWhen skipWhen = new SkipWhen().withPackagingEquals("jar").useBooleanOperator(SkipWhen.BooleanOperator.OR);

		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenPackagingNegateMatching() {
		Mockito.when(this.project.getPackaging()).thenReturn("pom");
		SkipWhen skipWhen = new SkipWhen().withPackagingEquals("!pom").useBooleanOperator(SkipWhen.BooleanOperator.OR);

		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		Mockito.when(this.project.getPackaging()).thenReturn("jar");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}
	
	@Test
	public void testWhenPackagingNegateNotMatching() {
		Mockito.when(this.project.getPackaging()).thenReturn("pom");
		SkipWhen skipWhen = new SkipWhen().withPackagingEquals("!poms");

		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		Mockito.when(this.project.getPackaging()).thenReturn("jar");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

}
