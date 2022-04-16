package cz.gattserver.utils.vaadin.dialogs;

import com.vaadin.flow.data.binder.Binder;

import cz.gattserver.utils.vaadin.SaveAction;
import cz.gattserver.utils.vaadin.SaveCloseLayout;

public abstract class AbstractFieldsEditDialog<T> extends EditWebDialog {

	private static final long serialVersionUID = -8494081277784752858L;

	public AbstractFieldsEditDialog(SaveAction<T> saveAction) {
		this(null, saveAction);
	}

	public AbstractFieldsEditDialog(T to, SaveAction<T> saveAction) {
		init(to, saveAction);
	}

	protected void init(T to, SaveAction<T> saveAction) {
		final Binder<T> binder = new Binder<>();
		binder.setBean(populateBinder(to));

		createFields(to, binder);

		if (to != null)
			binder.readBean(to);

		addComponent(new SaveCloseLayout(e -> {
			T targetTO = to == null ? createNewInstance() : to;
			processBeforeSave(targetTO);
			if (binder.writeBeanIfValid(targetTO)) {
				saveAction.onSave(targetTO);
				close();
			}
		}, e -> close()));
	}

	protected T populateBinder(T originalTO) {
		return createNewInstance();
	}

	protected boolean processBeforeSave(T targetTO) {
		return true;
	}

	protected abstract T createNewInstance();

	protected abstract void createFields(T originalTO, Binder<T> binder);

}
