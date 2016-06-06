package com.sample.ci.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.sample.ci.helper.model.SkipWhen;

@RunWith(MockitoJUnitRunner.class)
public class SkipWhenHelperArtifactIdTest extends AbstractSkipWhenHelperSupport {

	@Test
	public void testWhenArtifactIdMatch() {
		Mockito.when(this.project.getArtifactId()).thenReturn("sample");

		SkipWhen skipWhen = new SkipWhen().withArtifactIdEquals("sample");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenArtifactIdNotMatching() {
		Mockito.when(this.project.getArtifactId()).thenReturn("sample");

		SkipWhen skipWhen = new SkipWhen().withArtifactIdEquals("samples");
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenArtifactIdNegateMatching() {
		Mockito.when(this.project.getArtifactId()).thenReturn("sample");

		SkipWhen skipWhen = new SkipWhen().withArtifactIdEquals("!sample");
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}

	@Test
	public void testWhenArtifactIdNegateNotMatching() {
		Mockito.when(this.project.getArtifactId()).thenReturn("sample");

		SkipWhen skipWhen = new SkipWhen().withArtifactIdEquals("!samples");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());

		skipWhen.useBooleanOperator(SkipWhen.BooleanOperator.AND);
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}
}
