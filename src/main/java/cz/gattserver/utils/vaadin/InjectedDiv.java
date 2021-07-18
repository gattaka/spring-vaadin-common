package cz.gattserver.utils.vaadin;

import com.vaadin.flow.component.html.Div;

import cz.gattserver.utils.config.SpringContextHelper;

public class InjectedDiv extends Div {

	private static final long serialVersionUID = -8000259724564867413L;

	public InjectedDiv() {
		SpringContextHelper.inject(this);
	}

}
