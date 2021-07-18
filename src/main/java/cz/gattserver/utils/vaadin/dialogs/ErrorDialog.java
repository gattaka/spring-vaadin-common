package cz.gattserver.utils.vaadin.dialogs;

import com.vaadin.flow.component.icon.VaadinIcon;

public class ErrorDialog extends MessageDialog {

	private static final long serialVersionUID = -4793025663820815400L;

	public ErrorDialog(String labelCaption) {
		super("Problém", labelCaption, VaadinIcon.EXCLAMATION_CIRCLE);
		icon.setColor("red");
	}

}
