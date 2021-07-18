package cz.gattserver.utils.vaadin.dialogs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public class MessageDialog extends WebDialog {

	private static final long serialVersionUID = 4123506060675738841L;

	protected TextArea detailsArea;
	protected Icon icon;

	/**
	 * @param labelCaption
	 *            obsah zprávy v okně
	 * @param imageResource
	 *            resource ikony okna
	 */
	public MessageDialog(String labelCaption, VaadinIcon vaadinIcon) {
		this(labelCaption, null, vaadinIcon);
	}

	/**
	 * @param labelCaption
	 *            obsah zprávy v okně
	 * @param imageResource
	 *            resource ikony okna
	 */
	public MessageDialog(String labelCaption, String details, VaadinIcon vaadinIcon) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		addComponent(horizontalLayout);

		icon = new Icon(vaadinIcon);
		horizontalLayout.add(icon);
		horizontalLayout.setVerticalComponentAlignment(Alignment.CENTER, icon);

		Label msgLabel = new Label(labelCaption);
		msgLabel.setSizeUndefined();
		horizontalLayout.add(msgLabel);

		createDetails(details);

		Button proceedButton = new Button("OK", event -> close());

		addComponent(proceedButton);
		setComponentAlignment(proceedButton, Alignment.END);
	}

	protected void createDetails(String details) {
		if (details != null) {
			detailsArea = new TextArea();
			detailsArea.setValue(details);
			detailsArea.setEnabled(true);
			detailsArea.setReadOnly(true);
			detailsArea.setWidth("500px");
			detailsArea.setHeight("400px");
			addComponent(detailsArea);
		}
	}

}
