package com.sample.ci.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import com.sample.ci.helper.model.SkipWhen;

public class SkipWhenHelper {

	protected Log log;

	protected SkipWhen skipWhen;

	protected MavenProject project;

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

		List<Boolean> skipList = this.retrieveSkipExpression();
		if (skipList.isEmpty()) {
			return false;
		}

		if (this.log != null && this.log.isDebugEnabled()) {
			this.log.debug(
					"Evaluating: " + skipList.toString().replaceAll(",", " " + this.skipWhen.getBooleanOperator()));
		}

		boolean result = skipList.get(0);
		if (SkipWhen.BOOLEAN_OPERATOR_AND.equals(this.skipWhen.getBooleanOperator())) {
			for (int i = 1; i < skipList.size(); i++) {
				result = result && skipList.get(i);
			}
		} else {
			for (int i = 1; i < skipList.size(); i++) {
				result = result || skipList.get(i);
			}
		}

		/*
		 * TODO <classifierEquals>something</classifierEquals>
		 * <noTestsFound>false</noTestsFound>
		 * <activeProfileIdEquals>profile-id</activeProfileIdEquals>
		 */

		return result;
	}

	protected List<Boolean> retrieveSkipExpression() {
		List<Boolean> skipList = new ArrayList<>(8);

		populateList(skipList, checkSingleValue(this.skipWhen.getPackagingEquals(), this.project.getPackaging(),
				"packagingEquals", "packaging"));
		populateList(skipList, checkSingleValue(this.skipWhen.getGroupIdEquals(), this.project.getGroupId(),
				"groupIdEquals", "groupId"));
		populateList(skipList, checkSingleValue(this.skipWhen.getArtifactIdEquals(), this.project.getArtifactId(),
				"artifactIdEquals", "artifactId"));
		populateList(skipList, checkSingleValue(this.skipWhen.getVersionEquals(), this.project.getVersion(),
				"versionEquals", "version"));
		
		return skipList;
	}

	private static void populateList(List<Boolean> list, int value) {
		if (value != -1) {
			list.add(value > 0 ? Boolean.TRUE : Boolean.FALSE);
		}
	}

	public int checkSingleValue(String configurationEntry, String projectProperty, String configurationEntryName,
			String projectEntryName) {
		if (configurationEntry == null || configurationEntry.isEmpty()) {
			return -1;
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
		return result ? 1 : 0;
	}
}
