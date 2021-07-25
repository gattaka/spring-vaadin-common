package cz.gattserver.utils;

public enum NumberConstraints {

	ALL, NEGATIVE, NEGATIVE_OR_ZERO, POSITIVE, POSITIVE_OR_ZERO;

	public boolean validate(Number number) {
		switch (this) {
		case NEGATIVE:
			return number.doubleValue() < 0;
		case NEGATIVE_OR_ZERO:
			return number.doubleValue() <= 0;
		case POSITIVE:
			return number.doubleValue() > 0;
		case POSITIVE_OR_ZERO:
			return number.doubleValue() >= 0;
		default:
		case ALL:
			return true;
		}
	}

}
