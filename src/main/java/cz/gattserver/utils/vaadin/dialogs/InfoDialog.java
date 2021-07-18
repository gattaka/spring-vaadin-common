package cz.gattserver.utils.vaadin.dialogs;

import com.vaadin.flow.component.icon.VaadinIcon;

public class InfoDialog extends MessageDialog {

	private static final long serialVersionUID = -4793025663820815400L;

	public InfoDialog(String labelCaption) {
		super("Info", labelCaption, VaadinIcon.INFO_CIRCLE);
		icon.setColor("blue");
	}

}
