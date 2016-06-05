package com.sample.ci.helper.model;

public class SkipWhen {

	public static final String BOOLEAN_OPERATOR_OR = "or";

	public static final String BOOLEAN_OPERATOR_AND = "and";

	public static boolean isBooleanOperatorValid(String operator) {
		switch (operator) {
		case BOOLEAN_OPERATOR_AND:
		case BOOLEAN_OPERATOR_OR:
			return true;
		default:
			return false;
		}
	}

	private String packagingEquals;

	private String groupIdEquals;

	private String artifactIdEquals;

	private String versionEquals;

	private String classifierEquals;

	private String noTestsFound;

	private String activeProfileIdEquals;

	private String artifactEquals;

	private String booleanOperator = BOOLEAN_OPERATOR_OR;

	public SkipWhen withPackagingEquals(String packagingEquals) {
		this.packagingEquals = packagingEquals;
		return this;
	}

	public SkipWhen withGroupIdEquals(String groupIdEquals) {
		this.groupIdEquals = groupIdEquals;
		return this;
	}

	public SkipWhen withArtifactIdEquals(String artifactIdEquals) {
		this.artifactIdEquals = artifactIdEquals;
		return this;
	}

	public SkipWhen withVersionEquals(String versionEquals) {
		this.versionEquals = versionEquals;
		return this;
	}

	public SkipWhen withClassifierEquals(String classifierEquals) {
		this.classifierEquals = classifierEquals;
		return this;
	}

	public SkipWhen withNoTestsFound(String noTestsFound) {
		this.noTestsFound = noTestsFound;
		return this;
	}

	public SkipWhen withActiveProfileIdEquals(String activeProfileIdEquals) {
		this.activeProfileIdEquals = activeProfileIdEquals;
		return this;
	}

	public SkipWhen withArtifactEquals(String artifactEquals) {
		this.artifactEquals = artifactEquals;
		return this;
	}

	public SkipWhen useBooleanOperator(String booleanOperator) {
		if (!isBooleanOperatorValid(booleanOperator)) {
			throw new IllegalArgumentException(
					"booleanOperator '" + booleanOperator + "' not valid, must be one of: 'and', 'or'");
		}
		this.booleanOperator = booleanOperator;
		return this;
	}

	public String getPackagingEquals() {
		return packagingEquals;
	}

	public String getGroupIdEquals() {
		return groupIdEquals;
	}

	public String getArtifactIdEquals() {
		return artifactIdEquals;
	}

	public String getVersionEquals() {
		return versionEquals;
	}

	public String getClassifierEquals() {
		return classifierEquals;
	}

	public String getNoTestsFound() {
		return noTestsFound;
	}

	public String getActiveProfileIdEquals() {
		return activeProfileIdEquals;
	}

	public String getArtifactEquals() {
		return artifactEquals;
	}

	public String getBooleanOperator() {
		return booleanOperator;
	}

	@Override
	public String toString() {
		// avoid noise: let's print only what differs from null
		StringBuilder buf = new StringBuilder();
		buf.append("{ booleanOperator=");
		buf.append(this.booleanOperator);
		buf.append(", ");
		if (this.packagingEquals != null) {
			buf.append("packagingEquals=");
			buf.append(this.packagingEquals);
			buf.append(", ");
		}
		if (this.artifactIdEquals != null) {
			buf.append("artifactIdEquals=");
			buf.append(this.artifactIdEquals);
			buf.append(", ");
		}
		if (this.groupIdEquals != null) {
			buf.append("groupIdEquals=");
			buf.append(this.groupIdEquals);
			buf.append(", ");
		}
		if (this.versionEquals != null) {
			buf.append("versionEquals=");
			buf.append(this.versionEquals);
			buf.append(", ");
		}
		if (this.classifierEquals != null) {
			buf.append("classifierEquals=");
			buf.append(this.classifierEquals);
			buf.append(", ");
		}
		if (this.noTestsFound != null) {
			buf.append("noTestsFound=");
			buf.append(this.noTestsFound);
			buf.append(", ");
		}
		if (this.activeProfileIdEquals != null) {
			buf.append("activeProfileIdEquals=");
			buf.append(this.activeProfileIdEquals);
			buf.append(", ");
		}
		if (this.artifactEquals != null) {
			buf.append("artifactEquals=");
			buf.append(this.artifactEquals);
		}
		if (buf.charAt(buf.length() - 2) == ',') {
			buf.deleteCharAt(buf.length() - 2);
		}
		buf.append("}");
		return buf.toString();
	}

}
