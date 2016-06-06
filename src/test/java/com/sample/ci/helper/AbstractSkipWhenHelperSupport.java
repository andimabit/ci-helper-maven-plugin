package com.sample.ci.helper;

import java.util.Arrays;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

abstract class AbstractSkipWhenHelperSupport {

	protected SkipWhenHelper helper;

	@Mock
	protected MavenProject project;

	@Mock
	protected Log log;

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

}
