package com.sample.ci.helper;

import java.util.List;
import java.util.Objects;

import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

import com.sample.ci.helper.model.SkipWhen;

/**
 */
@Mojo(name = "echo", defaultPhase = LifecyclePhase.VALIDATE)
public class CIHelperEchoMojo extends AbstractMojo {

	/**
	 * The maven project.
	 */
	@Parameter(defaultValue = "${project}", required = true, readonly = false)
	protected MavenProject project;

	/**
	 * Maven ProjectHelper
	 */
	@Component
	protected MavenProjectHelper projectHelper;

	@Parameter(property = "skipWhen", required = false)
	protected SkipWhen skipWhen;

	@Parameter(property = "message", required = true)
	protected String message;

	@Parameter(property = "useLog", required = false, defaultValue = "false")
	protected String useLog;

	protected SkipWhenHelper skipHelper = new SkipWhenHelper();

	public void execute() throws MojoExecutionException {
		if (this.project.getBuild() != null) {
			List<Plugin> plugins = this.project.getBuild().getPlugins();
			for (Plugin p : plugins) {
				getLog().info("on plugin "+p.getArtifactId());
				List<PluginExecution> executions = p.getExecutions();
				if (executions != null) {
					for (PluginExecution e : executions) {
						getLog().info("\ton execution "+e.getId());
						if ("maven-jar-plugin".equals(p.getArtifactId())) {
							if ("test-id".equals(e.getId())) {
								e.setPhase("skipped");
							}
						}
					}
				}
			}
		}
		
		this.skipHelper.usingLog(getLog()).forProject(this.project).forSkipWhen(this.skipWhen);

		getLog().debug("skipWen configuration: " + Objects.toString(this.skipWhen));

		if (this.skipHelper.skipWhenMet()) {
			getLog().info("skipping execution according to skipWhen condition(s)");
			return;
		}

		if ("true".equalsIgnoreCase(this.useLog)) {
			getLog().info(this.message);
		} else {
			System.out.println(this.message);
		}
	}

}
