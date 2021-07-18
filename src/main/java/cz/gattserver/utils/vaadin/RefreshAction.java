package cz.gattserver.utils.vaadin;

public interface RefreshAction<T> {
	void onRefresh(T to);
}