package com.sample.ci.helper;

import java.util.Arrays;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.sample.ci.helper.model.SkipWhen;

@RunWith(MockitoJUnitRunner.class)
public class SkipWhenHelperTest {

	private SkipWhenHelper helper;

	@Mock
	private MavenProject project;

	@Mock
	private Log log;

	@Before
	public void setUp() {
		Mockito.doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) {
		      Object[] args = invocation.getArguments();
		      System.out.println("[INFO] "+Arrays.toString(args));
		      return null;
		    }
		}).when(this.log).info(Mockito.any(String.class));
		
		this.helper = new SkipWhenHelper().forProject(this.project).usingLog(this.log);
	}

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

}
