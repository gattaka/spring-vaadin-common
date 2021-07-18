package cz.gattserver.utils.vaadin;

import com.vaadin.flow.component.html.Div;

import cz.gattserver.utils.UIUtils;

public class LabelDiv extends Div {

	private static final long serialVersionUID = 5316725335817036412L;

	public LabelDiv(String text) {
		this(text, true);
	}

	public LabelDiv(String text, boolean topMargin) {
		setText(text);
		addClassName("label-div");
		if (topMargin)
			addClassName(UIUtils.TOP_MARGIN_CSS_CLASS);
	}

}
