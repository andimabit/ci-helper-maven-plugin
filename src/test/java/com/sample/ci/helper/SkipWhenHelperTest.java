package com.sample.ci.helper;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
		this.helper = new SkipWhenHelper().forProject(this.project);
	}
	
	@Test
	public void testWhenPackagingMatch() {
		Mockito.when(this.project.getPackaging()).thenReturn("pom");
		SkipWhen skipWhen = new SkipWhen().withPackagingEquals("pom");
		
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}
	
	@Test
	public void testWhenPackagingNotMatching() {
		Mockito.when(this.project.getPackaging()).thenReturn("pom");
		SkipWhen skipWhen = new SkipWhen().withPackagingEquals("jar");
		
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}
	
	@Test
	public void testWhenPackagingNegateMatching() {
		Mockito.when(this.project.getPackaging()).thenReturn("pom");
		SkipWhen skipWhen = new SkipWhen().withPackagingEquals("!pom");
		
		Assert.assertFalse(this.helper.forSkipWhen(skipWhen).skipWhenMet());
		
		Mockito.when(this.project.getPackaging()).thenReturn("jar");
		Assert.assertTrue(this.helper.forSkipWhen(skipWhen).skipWhenMet());
	}
	
}
