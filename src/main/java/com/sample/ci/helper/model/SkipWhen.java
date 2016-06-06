package com.sample.ci.helper.model;

public class SkipWhen {

	public enum BooleanOperator {
		OR, AND;

		public static boolean isBooleanOperatorValid(String operator) {
			if (OR.name().equalsIgnoreCase(operator) || AND.name().equalsIgnoreCase(operator)) {
				return true;
			}
			return false;
		}

		public static boolean isOr(String operator) {
			return OR.name().equalsIgnoreCase(operator);
		}
		
		public static boolean isAnd(String operator) {
			return AND.name().equalsIgnoreCase(operator);
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

	private String booleanOperator = BooleanOperator.OR.name();

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

	public SkipWhen useBooleanOperator(BooleanOperator operator) {
		return this.useBooleanOperator(operator.name());
	}

	public SkipWhen useBooleanOperator(String booleanOperator) {
		if (!BooleanOperator.isBooleanOperatorValid(booleanOperator)) {
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
		StringBuilder buf = new StringBuilder("{");
		
		toStringHelper(buf, "booleanOperator", this.booleanOperator, true);
		toStringHelper(buf, "packagingEquals", this.packagingEquals, true);
		toStringHelper(buf, "artifactIdEquals", this.artifactIdEquals, true);
		toStringHelper(buf, "groupIdEquals", this.groupIdEquals, true);
		toStringHelper(buf, "versionEquals", this.versionEquals, true);
		toStringHelper(buf, "classifierEquals", this.classifierEquals, true);
		toStringHelper(buf, "noTestsFound", this.noTestsFound, true);
		toStringHelper(buf, "activeProfileIdEquals", this.activeProfileIdEquals, true);
		toStringHelper(buf, "artifactEquals", this.artifactEquals, false);

		if (buf.charAt(buf.length() - 2) == ',') {
			buf.deleteCharAt(buf.length() - 2);
		}

		return buf.append("}").toString();
	}

	private static void toStringHelper(StringBuilder buf, String propertyName, Object value, boolean addComma) {
		if (value != null) {
			buf.append(propertyName + "=");
			buf.append(value);
			if (addComma) {
				buf.append(", ");
			}
		}
	}

}
