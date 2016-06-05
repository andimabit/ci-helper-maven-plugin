package com.sample.ci.helper;

import java.util.ArrayList;
import java.util.List;

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

		List<Boolean> skipList = new ArrayList<>(8);
		
		skipList.add(checkSingleValue(this.skipWhen.getPackagingEquals(), this.project.getPackaging(),
				"packagingEquals", "packaging"));
		skipList.add(checkSingleValue(this.skipWhen.getGroupIdEquals(), this.project.getGroupId(), "groupIdEquals",
				"groupId"));
		skipList.add(checkSingleValue(this.skipWhen.getArtifactIdEquals(), this.project.getArtifactId(), "artifactIdEquals",
				"artifactId"));
		skipList.add(checkSingleValue(this.skipWhen.getVersionEquals(), this.project.getVersion(), "versionEquals",
				"version"));
		
		boolean result = false;
		if (SkipWhen.BOOLEAN_OPERATOR_AND.equals(this.skipWhen.getBooleanOperator())) {
			for (Boolean b : skipList) {
				result = result && b;
			}
		} else {
			for (Boolean b : skipList) {
				result = result || b;
			}
		}
		
		/*
		 * TODO <classifierEquals>something</classifierEquals>
		 * <noTestsFound>false</noTestsFound>
		 * <activeProfileIdEquals>profile-id</activeProfileIdEquals>
		 */

		return result;
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
