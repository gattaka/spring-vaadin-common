package cz.gattserver.utils.vaadin.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;

public class ApplicationErrorHandler implements ErrorHandler {

	private static final long serialVersionUID = -7739910142600177544L;

	private static final Logger logger = LoggerFactory.getLogger(ApplicationErrorHandler.class);

	public void error(ErrorEvent event) {
		error(event.getThrowable());
	}

	public void error(Throwable throwable) {
		logger.error("V aplikaci došlo k chybě", throwable);
		if (UI.getCurrent() != null) {
			UI.getCurrent().access(() -> new ExceptionDialog(throwable).open());
		}
	}
}
