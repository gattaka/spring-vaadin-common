package cz.gattserver.utils.vaadin.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.vaadin.flow.component.icon.VaadinIcon;

import cz.gattserver.utils.vaadin.dialogs.MessageDialog;

public class ExceptionDialog extends MessageDialog {

	private static final long serialVersionUID = -2077736292967107272L;

	public ExceptionDialog(Throwable throwable) {
		super("Neočekávaná systémová chyba",
				"Důvod:\n" + throwable.getMessage() + "\n\nDetaily:\n" + ExceptionUtils.getStackTrace(throwable),
				VaadinIcon.EXCLAMATION_CIRCLE);
		layout.addClassName("error-layout");
		detailsArea.addClassName("error-text-field");
		detailsArea.setHeight("500px");
		detailsArea.setWidth("1200px");
	}

}
