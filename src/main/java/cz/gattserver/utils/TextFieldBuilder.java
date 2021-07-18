package cz.gattserver.utils;

import com.vaadin.flow.component.textfield.TextField;
import static cz.gattserver.utils.UIUtils.localize;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.Binder.BindingBuilder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.ValueProvider;

public class TextFieldBuilder<T> {

	private String labelToLocalize;
	private boolean required;
	private Integer minLength;
	private Integer maxLength;
	private Binder<T> binder;
	private ValueProvider<T, String> getter;
	private Setter<T, String> setter;
	private Validator<String> additionalValidator;

	public String getLabelToLocalize() {
		return labelToLocalize;
	}

	public void setLabelToLocalize(String labelToLocalize) {
		this.labelToLocalize = labelToLocalize;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Binder<T> getBinder() {
		return binder;
	}

	public void setBinder(Binder<T> binder) {
		this.binder = binder;
	}

	public ValueProvider<T, String> getGetter() {
		return getter;
	}

	public void setGetter(ValueProvider<T, String> getter) {
		this.getter = getter;
	}

	public Setter<T, String> getSetter() {
		return setter;
	}

	public void setSetter(Setter<T, String> setter) {
		this.setter = setter;
	}

	public Validator<String> getAdditionalValidator() {
		return additionalValidator;
	}

	public void setAdditionalValidator(Validator<String> additionalValidator) {
		this.additionalValidator = additionalValidator;
	}

	public TextField build() {
		TextField textField = new TextField(localize(labelToLocalize));
		if (maxLength != null)
			textField.setMaxLength(maxLength);
		BindingBuilder<T, String> builder = binder.forField(textField);
		if (required) {
			if (minLength == null)
				minLength = 1;
			builder.asRequired(localize(UIUtils.POVINNE_POLE_MSG_KEY));
			builder.withValidator(new StringLengthValidator(
					localize("text.not.blank.length.msg", "" + minLength, "" + maxLength), minLength, maxLength));
		} else {
			if (minLength == null)
				minLength = 0;
			builder.withValidator(new StringLengthValidator(localize("text.length.msg", "" + minLength, "" + maxLength),
					minLength, maxLength));
		}
		if (additionalValidator != null)
			builder.withValidator(additionalValidator);
		builder.bind(getter::apply, (to, val) -> setter.accept(to, val.trim()));
		textField.setClearButtonVisible(!required);
		textField.setWidthFull();
		return textField;
	}

}
