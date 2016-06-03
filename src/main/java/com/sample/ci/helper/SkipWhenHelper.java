package com.sample.ci.helper;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import com.sample.ci.helper.model.SkipWhen;

public class SkipWhenHelper {

	private Log log;

	private SkipWhen skipWhen;

	private MavenProject project;

	public SkipWhenHelper() {
	}
	
	public SkipWhenHelper(SkipWhen skipWhen, MavenProject project, Log log) {
		this.skipWhen = skipWhen;
		this.project = project;
		this.log = log;
	}
	
	public SkipWhenHelper forSkipWhen(SkipWhen skipWhen) {
		this.skipWhen = skipWhen;
		return this;
	}
	
	public SkipWhenHelper forProject(MavenProject project) {
		this.project = project;
		return this;
	}

	public SkipWhenHelper usingLog(Log log) {
		this.log = log;
		return this;
	}
	
	public boolean skipWhenMet() {
		if (this.skipWhen == null) {
			return false;
		}

		boolean skip = checkSingleValue(this.skipWhen.getPackagingEquals(), this.project.getPackaging(),
				"packagingEquals", "packaging");
		if (skip) {
			return true;
		}
		skip = checkSingleValue(this.skipWhen.getGroupIdEquals(), this.project.getGroupId(), "groupIdEquals",
				"groupId");
		if (skip) {
			return true;
		}
		skip = checkSingleValue(this.skipWhen.getArtifactIdEquals(), this.project.getArtifactId(), "artifactIdEquals",
				"artifactId");
		if (skip) {
			return true;
		}
		skip = checkSingleValue(this.skipWhen.getVersionEquals(), this.project.getVersion(), "versionEquals",
				"version");
		if (skip) {
			return true;
		}
		/*
		 * TODO <classifierEquals>something</classifierEquals>
		 * <noTestsFound>false</noTestsFound>
		 * <activeProfileIdEquals>profile-id</activeProfileIdEquals>
		 */

		return false;

	}

	public boolean checkSingleValue(String configurationEntry, String projectProperty, String configurationEntryName,
			String projectEntryName) {
		if (configurationEntry == null || configurationEntry.isEmpty()) {
			return false;
		}
		boolean negateFlag = false;
		String checkAgainst = configurationEntry;
		if (configurationEntry.startsWith("!")) {
			negateFlag = true;
			checkAgainst = configurationEntry.substring(1);
		}
		boolean result = projectProperty.equalsIgnoreCase(checkAgainst) ^ negateFlag;
		
		if (result && this.log != null) {
			this.log.info("skipWhen " + configurationEntryName + " condition met, " + projectEntryName + " equals to "
					+ projectProperty + ", expected " + configurationEntry);
		}
		return result;
	}
}
