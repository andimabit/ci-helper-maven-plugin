package com.sample.ci.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import com.sample.ci.helper.model.SkipWhen;

public class SkipWhenHelper {

	protected enum BOOLEAN_EVAL {

		NOT_PROCESSED, TRUE, FALSE;

		public boolean isProcessed() {
			return !NOT_PROCESSED.equals(this);
		}
		
		public Boolean toBoolean() {
			if (!isProcessed()) {
				throw new IllegalStateException("cannot convert a non processed boolean value");
			}
			return TRUE.equals(this) ? Boolean.TRUE : Boolean.FALSE;
		}

	}
	
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
			this.log.debug("No skipWhen conditions found.");
			return false;
		}

		if (this.log != null && this.log.isDebugEnabled()) {
			this.log.debug(
					"Evaluating: " + skipList.toString().replaceAll(",", " " + this.skipWhen.getBooleanOperator()));
		}

		boolean result = skipList.get(0);
		if (SkipWhen.BooleanOperator.isOr(this.skipWhen.getBooleanOperator())) {
			for (int i = 1; i < skipList.size(); i++) {
				result = result && skipList.get(i);
			}
		} else {
			for (int i = 1; i < skipList.size(); i++) {
				result = result || skipList.get(i);
			}
		}

		this.log.debug("Evaluation resulted in: "+result);
		
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
	
	public BOOLEAN_EVAL checkSingleValue(String configurationEntry, String projectProperty,
			String configurationEntryName, String projectEntryName) {
		if (configurationEntry == null || configurationEntry.isEmpty()) {
			return BOOLEAN_EVAL.NOT_PROCESSED;
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
		return result ? BOOLEAN_EVAL.TRUE : BOOLEAN_EVAL.FALSE;
	}
	
	private static void populateList(List<Boolean> list, BOOLEAN_EVAL value) {
		if (value.isProcessed()) {
			list.add(value.toBoolean());
		}
	}
}
