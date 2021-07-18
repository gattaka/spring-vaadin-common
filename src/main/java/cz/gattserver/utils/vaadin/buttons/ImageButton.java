package cz.gattserver.utils.vaadin.buttons;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ImageButton extends Button {

	private static final long serialVersionUID = 4204958919924333786L;

	public ImageButton(String caption, VaadinIcon vaadinIcon) {
		this(caption, vaadinIcon, null);
	}

	public ImageButton(String caption, VaadinIcon vaadinIcon,
			ComponentEventListener<ClickEvent<Button>> clickListener) {
		if (caption != null) {
			setText(caption);
			setTooltip(caption);
		}
		if (vaadinIcon != null)
			setIcon(new Icon(vaadinIcon));
		if (clickListener != null)
			addClickListener(clickListener);
	}

	public ImageButton setTooltip(String value) {
		getElement().setProperty("title", value);
		return this;
	}

}
