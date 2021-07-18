package cz.gattserver.utils.vaadin;

public interface SaveAction<T> {
	void onSave(T to);
}