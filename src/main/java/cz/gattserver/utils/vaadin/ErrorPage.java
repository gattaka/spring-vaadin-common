package cz.gattserver.utils.vaadin;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.InternalServerError;

import cz.gattserver.utils.vaadin.exception.ExceptionDialog;

public final class ErrorPage extends InternalServerError {

	private static final long serialVersionUID = 5061653512217691056L;

	@Override
	public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {
		if (UI.getCurrent() != null)
			UI.getCurrent().access(() -> new ExceptionDialog(parameter.getException()).open());
		return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	}

}
