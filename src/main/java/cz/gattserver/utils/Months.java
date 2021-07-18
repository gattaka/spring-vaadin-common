package cz.gattserver.utils;

public enum Months implements ComboValue<Integer> {

	JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;

	@Override
	public Integer getId() {
		return ordinal() + 1;
	}

	@Override
	public String getNazev() {
		return UIUtils.monthNames().get(ordinal());
	}

}
