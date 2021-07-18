package cz.gattserver.utils.vaadin.buttons;

import static cz.gattserver.utils.UIUtils.localize;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class DetailGridButton<T> extends GridButton<T> {

	private static final long serialVersionUID = -5924239277930098183L;

	public interface ClickListener<T> {
		public void buttonClick(T item);
	}

	public DetailGridButton(ClickListener<T> clickListener, Grid<T> grid) {
		this(localize("detail.label"), clickListener, grid);
	}

	public DetailGridButton(String caption, ClickListener<T> clickListener, Grid<T> grid) {
		super(caption, items -> clickListener.buttonClick(items.iterator().next()), grid);
		setIcon(new Icon(VaadinIcon.INFO_CIRCLE));
		setEnableResolver(items -> items.size() == 1);
	}

}
