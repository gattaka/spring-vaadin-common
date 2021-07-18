package cz.gattserver.utils.vaadin.buttons;

import static cz.gattserver.utils.UIUtils.localize;

import java.util.Set;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import cz.gattserver.utils.vaadin.dialogs.ConfirmDialog;


public class DeleteGridButton<T> extends GridButton<T> {

	private static final long serialVersionUID = -5924239277930098183L;

	private static final String CONFIRM_MSG = localize("opravdu.smazat.msg");

	public interface ConfirmAction<T> {
		public void onConfirm(Set<T> items);
	}

	public interface ConfirmMsgFactory<T> {
		public String create(Set<T> items);
	}

	public DeleteGridButton(ConfirmAction<T> confirmAction, Grid<T> grid) {
		this(localize("smazat.label"), confirmAction, items -> CONFIRM_MSG, grid);
	}

	public DeleteGridButton(String caption, ConfirmAction<T> confirmAction, Grid<T> grid) {
		this(caption, confirmAction, items -> CONFIRM_MSG, grid);
	}

	public DeleteGridButton(String caption, ConfirmAction<T> confirmAction, ConfirmMsgFactory<T> confirmMsgFactory,
			Grid<T> grid) {
		super(caption, grid);
		setClickListener(
				items -> new ConfirmDialog(confirmMsgFactory.create(items), ev -> confirmAction.onConfirm(items))
						.open());
		setIcon(new Icon(VaadinIcon.TRASH));
	}

}
