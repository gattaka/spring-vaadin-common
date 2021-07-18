package cz.gattserver.utils.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import cz.gattserver.utils.UIUtils;

public class OperationsLayout extends HorizontalLayout {

	private static final long serialVersionUID = -7665980667994173144L;

	private HorizontalLayout buttonLayout;

	public OperationsLayout() {
		setSpacing(false);
		setJustifyContentMode(JustifyContentMode.BETWEEN);
		addClassName(UIUtils.TOP_MARGIN_CSS_CLASS);

		buttonLayout = new HorizontalLayout();
		buttonLayout.setPadding(false);
		add(buttonLayout);
	}

	public void addButton(Component... components) {
		buttonLayout.add(components);
	}
}
