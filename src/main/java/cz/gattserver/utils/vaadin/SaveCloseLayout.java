package cz.gattserver.utils.vaadin;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;

import cz.gattserver.utils.vaadin.buttons.CloseButton;
import cz.gattserver.utils.vaadin.buttons.SaveButton;

public class SaveCloseLayout extends OperationsLayout {

	private static final long serialVersionUID = 3909022460514320026L;

	protected SaveButton saveButton;
	protected CloseButton closeButton;

	public SaveCloseLayout(ComponentEventListener<ClickEvent<Button>> saveClickListener,
			ComponentEventListener<ClickEvent<Button>> closeClickListener) {
		setJustifyContentMode(JustifyContentMode.BETWEEN);
		setSpacing(false);
		setWidthFull();

		saveButton = new SaveButton(saveClickListener);
		addButton(saveButton);

		closeButton = new CloseButton(closeClickListener);
		add(closeButton);
	}

	public SaveButton getSaveButton() {
		return saveButton;
	}

}
