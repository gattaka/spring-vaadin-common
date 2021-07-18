package cz.gattserver.utils.vaadin.buttons;

import static cz.gattserver.utils.UIUtils.localize;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

public class SaveButton extends ImageButton {

	private static final long serialVersionUID = -9054113192020716390L;

	public SaveButton(ComponentEventListener<ClickEvent<Button>> clickListener) {
		this(localize("ulozit.label"), clickListener);
	}

	public SaveButton(String caption, ComponentEventListener<ClickEvent<Button>> clickListener) {
		super(caption, VaadinIcon.CHECK, clickListener);
	}

}
