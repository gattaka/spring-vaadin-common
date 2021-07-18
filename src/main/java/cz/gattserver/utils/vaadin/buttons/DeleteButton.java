package cz.gattserver.utils.vaadin.buttons;

import static cz.gattserver.utils.UIUtils.localize;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

public class DeleteButton extends ImageButton {

	private static final long serialVersionUID = -9054113192020716390L;

	public DeleteButton(ComponentEventListener<ClickEvent<Button>> clickListener) {
		this(localize("smazat.label"), clickListener);
	}

	public DeleteButton(String caption, ComponentEventListener<ClickEvent<Button>> clickListener) {
		super(caption, VaadinIcon.TRASH, clickListener);
	}

}
