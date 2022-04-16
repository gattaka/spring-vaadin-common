package cz.gattserver.utils.vaadin.dialogs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import cz.gattserver.utils.config.SpringContextHelper;

public class WebDialog extends Dialog {

	private static final long serialVersionUID = -9184044674542039306L;

	protected VerticalLayout layout = new VerticalLayout();

	private boolean initialized = false;

	public WebDialog(String caption) {
		layout.add(new Span(caption));
	}

	public WebDialog() {
	}

	public void init() {
		SpringContextHelper.inject(this);

		add(layout);
		setCloseOnOutsideClick(false);
		setCloseOnEsc(false);

		layout.setSpacing(true);
		layout.setPadding(false);

		initialized = true;
	}

	public void addComponent(Component component) {
		layout.add(component);
	}

	public void addComponent(Component component, Alignment alignment) {
		layout.add(component);
		setComponentAlignment(component, alignment);
	}

	public void setComponentAlignment(Component component, Alignment alignment) {
		layout.setHorizontalComponentAlignment(alignment, component);
	}

	@Override
	public void open() {
		if (!initialized)
			init();
		super.open();
	}

}
