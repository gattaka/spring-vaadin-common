package cz.gattserver.utils;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow.HeaderCell;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.ExtendedClientDetails;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Binder.BindingBuilder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;

import cz.gattserver.utils.vaadin.dialogs.ErrorDialog;
import cz.gattserver.utils.vaadin.dialogs.InfoDialog;
import cz.gattserver.utils.vaadin.dialogs.WarnDialog;

public class UIUtils {

	public static final String SPACING_CSS_VAR = "var(--lumo-space-m)";
	public static final String BUTTON_SIZE_CSS_VAR = "var(--lumo-button-size)";
	public static final String FIELD_SIZE_CSS_VAR = "var(--lumo-text-field-size)";
	public static final String FIELD_CAPTION_SIZE_CSS_VAR = "1.5em";

	public static final String TOP_MARGIN_CSS_CLASS = "top-margin";
	public static final String TOP_CLEAN_CSS_CLASS = "top-clean";
	public static final String TOP_PULL_CSS_CLASS = "top-pull";
	public static final String THUMBNAIL_200_CSS_CLASS = "thumbnail-200";
	public static final String BUTTON_LINK_CSS_CLASS = "button-link";

	public static final String GRID_ICON_CSS_CLASS = "grid-icon-img";

	public static final String POVINNE_POLE_MSG_KEY = "pole.povinne.msg";

	private UIUtils() {
	}

	/**
	 * Zjistí, zda je používáno mobilní zařízení
	 */
	public static boolean isMobileDevice() {
		WebBrowser wb = VaadinSession.getCurrent().getBrowser();
		ExtendedClientDetails ecd = UI.getCurrent().getInternals().getExtendedClientDetails();
		if (ecd != null && !ecd.isTouchDevice())
			return false;
		return wb.isIPhone() || wb.isAndroid() || wb.isWindowsPhone() || ecd != null && ecd.isIPad();
	}

	/**
	 * Scroll v gridu na pozici
	 */
	public static void scrollGridToIndex(Grid<?> grid, int index) {
		UI.getCurrent().getPage().executeJs("$0._scrollToIndex(" + index + ")", grid.getElement());
	}

	/**
	 * Ostyluje Grid tak, jak vypadají všechny tabulky v systému
	 */
	public static void applyDefaultStyle(Grid<?> grid) {
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
	}

	/**
	 * Přidá styl, aby pole bylo malé
	 */
	public static TextField asSmall(TextField textField) {
		textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		return textField;
	}

	/**
	 * Přidá styl, aby combo bylo malé
	 */
	public static <T> ComboBox<T> asSmall(ComboBox<T> comboBox) {
		comboBox.getElement().setAttribute("theme", TextFieldVariant.LUMO_SMALL.getVariantName());
		return comboBox;
	}

	/**
	 * Přidá filtrovací pole do záhlaví gridu
	 */
	public static TextField addHeaderTextField(HeaderCell cell,
			HasValue.ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> listener) {
		TextField field = UIUtils.asSmall(new TextField());
		field.setWidthFull();
		field.setClearButtonVisible(true);
		field.addValueChangeListener(listener);
		field.setValueChangeMode(ValueChangeMode.EAGER);
		cell.setComponent(field);
		return field;
	}

	/**
	 * Přidá filtrovací combo do záhlaví gridu
	 */
	public static <T extends Enum<T>> ComboBox<T> addHeaderComboBox(HeaderCell cell, Class<T> enumType,
			ItemLabelGenerator<T> itemLabelGenerator,
			HasValue.ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<T>, T>> listener) {
		return addHeaderComboBox(cell, enumType.getEnumConstants(), itemLabelGenerator, listener);
	}

	/**
	 * Přidá filtrovací combo do záhlaví gridu
	 */
	public static <T> ComboBox<T> addHeaderComboBox(HeaderCell cell, T[] values,
			ItemLabelGenerator<T> itemLabelGenerator,
			HasValue.ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<T>, T>> listener) {
		return addHeaderComboBox(cell, Arrays.asList(values), itemLabelGenerator, listener);
	}

	/**
	 * Přidá filtrovací combo do záhlaví gridu
	 */
	public static <T> ComboBox<T> addHeaderComboBox(HeaderCell cell, Collection<T> values,
			ItemLabelGenerator<T> itemLabelGenerator,
			HasValue.ValueChangeListener<? super ComponentValueChangeEvent<ComboBox<T>, T>> listener) {
		ComboBox<T> combo = UIUtils.asSmall(new ComboBox<>(null, values));
		combo.setWidthFull();
		combo.setRequired(false);
		combo.setClearButtonVisible(true);
		combo.addValueChangeListener(listener);
		combo.setItemLabelGenerator(itemLabelGenerator);
		cell.setComponent(combo);
		return combo;
	}

	/**
	 * Přejde na stránku
	 */
	public static void redirect(String uri) {
		UI.getCurrent().getPage().setLocation(uri);
	}

	/**
	 * Notifikace pomocí {@link Notification}
	 */
	public static void showSilentInfo(String caption) {
		Notification.show(caption);
	}

	/**
	 * Notifikace pomocí {@link InfoDialog}
	 */
	public static void showInfo(String caption) {
		new InfoDialog(caption).open();
	}

	/**
	 * Notifikace varování pomocí {@link WarnDialog}
	 */
	public static void showWarning(String caption) {
		new WarnDialog(caption).open();
	}

	/**
	 * Notifikace chyby pomocí {@link ErrorDialog}
	 */
	public static void showError(String caption) {
		new ErrorDialog(caption).open();
	}

	public static String getContextPath() {
		return VaadinRequest.getCurrent().getContextPath();
	}

	/**
	 * Získá URL stránky. Kořen webu + suffix
	 */
	public static String getPageURL(String suffix) {
		return getContextPath() + "/" + suffix;
	}

	/**
	 * Lokalizace
	 */
	public static String localize(String stringToLocalize, String... params) {
		if (stringToLocalize == null)
			return null;

		Object[] oParams = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			String s = params[i];
			oParams[i] = s;
		}

		return DefaultI18NResourceProvider.instance().getMessage(VaadinSession.getCurrent().getLocale(),
				stringToLocalize, oParams);
	}

	public static String localize(String stringToLocalize) {
		return stringToLocalize == null ? null
				: DefaultI18NResourceProvider.instance().getMessage(VaadinSession.getCurrent().getLocale(),
						stringToLocalize);
	}

	/**
	 * Otevře stream v novém tabu
	 */
	public static void openStream(InputStream is, String name) {
		final StreamResource resource = new StreamResource(name, () -> is);
		final StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry()
				.registerResource(resource);
		UI.getCurrent().getPage().open(registration.getResourceUri().toString());
	}

	private static DatePickerI18n createDatePickerI18n() {
		DatePickerI18n datePickerI18n = new DatePickerI18n();
		datePickerI18n.setWeek("Týden");
		datePickerI18n.setCalendar("Kalendář");
		datePickerI18n.setClear("Vymazat");
		datePickerI18n.setToday("Dnes");
		datePickerI18n.setCancel("Zrušit");
		datePickerI18n.setWeekdays(Arrays.asList("Neděle", "Pondělí", "Úterý", "Středa", "Čtvrtek", "Pátek", "Sobota"));
		datePickerI18n.setWeekdaysShort(Arrays.asList("Ne", "Po", "Út", "St", "Čt", "Pa", "So"));
		datePickerI18n.setMonthNames(monthNames());
		return datePickerI18n;
	}

	public static List<String> monthNames() {
		return Arrays.asList("Leden", "Únor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen", "Září",
				"Říjen", "Listopad", "Prosinec");
	}

	public static <T> DateTimePicker createDateTimePicker(String labelToLocalize, boolean required, Binder<T> binder,
			ValueProvider<T, LocalDateTime> getter, Setter<T, LocalDateTime> setter) {
		DateTimePicker dateTimePicker = new DateTimePicker(localize(labelToLocalize));
		dateTimePicker.setLocale(Locale.forLanguageTag("CS"));
		BindingBuilder<T, LocalDateTime> builder = binder.forField(dateTimePicker);
		if (required)
			builder.asRequired(localize(POVINNE_POLE_MSG_KEY));
		builder.bind(getter, setter);

		dateTimePicker.setDatePickerI18n(createDatePickerI18n());
		dateTimePicker.setWidthFull();
		return dateTimePicker;
	}

	public static <T> DatePicker createDatePicker(String labelToLocalize, boolean required, Binder<T> binder,
			ValueProvider<T, LocalDate> getter, Setter<T, LocalDate> setter) {
		DatePicker datePicker = new DatePicker(localize(labelToLocalize));
		datePicker.setLocale(Locale.forLanguageTag("CS"));
		BindingBuilder<T, LocalDate> builder = binder.forField(datePicker);
		if (required)
			builder.asRequired(localize(POVINNE_POLE_MSG_KEY));
		builder.bind(getter, setter);

		datePicker.setI18n(createDatePickerI18n());
		datePicker.setClearButtonVisible(!required);
		datePicker.setWidthFull();
		return datePicker;
	}

	/*
	 * Double text field
	 */

	public static <T> TextField createDoubleTextField(String labelToLocalize, boolean required, boolean onlyPositive,
			Binder<T> binder, ValueProvider<T, Double> getter, Setter<T, Double> setter) {
		return createDoubleTextField(labelToLocalize, required, onlyPositive, binder, getter, setter, null);
	}

	public static <T> TextField createDoubleTextField(String labelToLocalize, boolean required, boolean onlyPositive,
			Binder<T> binder, ValueProvider<T, Double> getter, Setter<T, Double> setter,
			Validator<Double> additionalValidator) {
		TextField textField = new TextField(localize(labelToLocalize));
		BindingBuilder<T, String> builder = binder.forField(textField);
		if (required)
			builder.asRequired(localize(POVINNE_POLE_MSG_KEY));
		builder.withValidator(FormatUtils::isNumber, localize("neni.cislo.msg"));
		if (onlyPositive)
			builder.withValidator(val -> {
				Double result = FormatUtils.parseDoubleOrNull(val);
				if (required)
					return result != null && result > 0;
				return result == null || result >= 0;
			}, localize("nesmi.byt.zaporne.cislo.msg"));
		if (additionalValidator != null)
			builder.withValidator((val, ctx) -> additionalValidator.apply(FormatUtils.parseDoubleOrNull(val), ctx));
		builder.bind(to -> FormatUtils.formatDouble(getter.apply(to)),
				(to, num) -> setter.accept(to, FormatUtils.parseDoubleOrNull(num)));
		textField.setClearButtonVisible(!required);
		textField.setWidthFull();
		return textField;
	}

	/*
	 * Integer text field
	 */

	public static <T> TextField createIntegerTextField(String labelToLocalize, boolean required, boolean onlyPositive,
			Binder<T> binder, ValueProvider<T, Integer> getter, Setter<T, Integer> setter) {
		return createIntegerTextField(labelToLocalize, required, onlyPositive, binder, getter, setter, null);
	}

	public static <T> TextField createIntegerTextField(String labelToLocalize, boolean required, boolean onlyPositive,
			Binder<T> binder, ValueProvider<T, Integer> getter, Setter<T, Integer> setter,
			Validator<Integer> additionalValidator) {
		TextField textField = new TextField(localize(labelToLocalize));
		BindingBuilder<T, String> builder = binder.forField(textField);
		if (required)
			builder.asRequired(localize(POVINNE_POLE_MSG_KEY));
		builder.withValidator(FormatUtils::isNumber, localize("neni.cele.cislo.msg"));
		if (onlyPositive)
			builder.withValidator(val -> {
				Integer result = FormatUtils.parseIntegerOrNull(val);
				if (required)
					return result != null && result > 0;
				return result == null || result >= 0;
			}, localize("nesmi.byt.zaporne.cislo.msg"));
		if (additionalValidator != null)
			builder.withValidator((val, ctx) -> additionalValidator.apply(FormatUtils.parseIntegerOrNull(val), ctx));
		builder.bind(to -> FormatUtils.formatInteger(getter.apply(to)),
				(to, num) -> setter.accept(to, FormatUtils.parseIntegerOrNull(num)));
		textField.setClearButtonVisible(!required);
		textField.setWidthFull();
		return textField;
	}

	/*
	 * Text
	 */

	public static <T> TextField createTextField(String labelToLocalize, boolean required, Integer minLength,
			Integer maxLength, Binder<T> binder, ValueProvider<T, String> getter, Setter<T, String> setter) {
		TextFieldBuilder<T> builder = new TextFieldBuilder<>();
		builder.setLabelToLocalize(labelToLocalize);
		builder.setRequired(required);
		builder.setMinLength(minLength);
		builder.setMaxLength(maxLength);
		builder.setBinder(binder);
		builder.setGetter(getter);
		builder.setSetter(setter);
		return builder.build();
	}

	public static <T> TextField createTextField(String labelToLocalize, boolean required, Integer maxLength,
			Binder<T> binder, ValueProvider<T, String> getter, Setter<T, String> setter) {
		TextFieldBuilder<T> builder = new TextFieldBuilder<>();
		builder.setLabelToLocalize(labelToLocalize);
		builder.setRequired(required);
		builder.setMaxLength(maxLength);
		builder.setBinder(binder);
		builder.setGetter(getter);
		builder.setSetter(setter);
		return builder.build();
	}

	public static <T> TextField createTextField(String labelToLocalize, boolean required, Integer maxLength,
			Binder<T> binder, ValueProvider<T, String> getter, Setter<T, String> setter,
			Validator<String> additionalValidator) {
		TextFieldBuilder<T> builder = new TextFieldBuilder<>();
		builder.setLabelToLocalize(labelToLocalize);
		builder.setRequired(required);
		builder.setMaxLength(maxLength);
		builder.setBinder(binder);
		builder.setGetter(getter);
		builder.setSetter(setter);
		builder.setAdditionalValidator(additionalValidator);
		return builder.build();
	}

	public static <T> TextField createTextField(String labelToLocalize, boolean required, Integer minLength,
			Integer maxLength, Binder<T> binder, ValueProvider<T, String> getter, Setter<T, String> setter,
			Validator<String> additionalValidator) {
		TextFieldBuilder<T> builder = new TextFieldBuilder<>();
		builder.setLabelToLocalize(labelToLocalize);
		builder.setRequired(required);
		builder.setMinLength(minLength);
		builder.setMaxLength(maxLength);
		builder.setBinder(binder);
		builder.setGetter(getter);
		builder.setSetter(setter);
		builder.setAdditionalValidator(additionalValidator);
		return builder.build();
	}

	/*
	 * TextArea
	 */
	public static <T> TextArea createTextArea(String labelToLocalize, boolean required, Integer maxLength,
			Binder<T> binder, ValueProvider<T, String> getter, Setter<T, String> setter) {
		TextArea textArea = new TextArea(localize(labelToLocalize));
		if (maxLength != null)
			textArea.setMaxLength(maxLength);
		BindingBuilder<T, String> builder = binder.forField(textArea);
		if (required) {
			builder.asRequired(localize(POVINNE_POLE_MSG_KEY));
			builder.withValidator(new StringLengthValidator(
					localize("text.not.blank.length.msg", "" + 0, "" + maxLength), 0, maxLength));
		}
		builder.bind(getter::apply, (to, val) -> setter.accept(to, val.trim()));
		textArea.setWidthFull();
		return textArea;
	}

	/*
	 * Combo
	 */
	public static <T, I, C extends ComboValue<I>> ComboBox<C> createComboBox(String labelToLocalize, boolean required,
			Collection<C> data, Binder<T> binder, ValueProvider<T, I> getter, Setter<T, I> setter) {
		return createComboBox(labelToLocalize, required, data, binder, getter, setter, null);
	}

	public static <T, I, C extends ComboValue<I>> ComboBox<C> createComboBox(String labelToLocalize, boolean required,
			Collection<C> data, Binder<T> binder, ValueProvider<T, I> getter, Setter<T, I> setter,
			Validator<C> additionalValidator) {
		Map<I, C> comboValuesMap = new HashMap<>();
		for (C to : data)
			comboValuesMap.put(to.getId(), to);
		ComboBox<C> comboBox = new ComboBox<>(localize(labelToLocalize), comboValuesMap.values());
		BindingBuilder<T, C> builder = binder.forField(comboBox);
		if (required)
			builder.asRequired(localize(POVINNE_POLE_MSG_KEY));
		comboBox.setClearButtonVisible(!required);
		if (additionalValidator != null)
			builder.withValidator(additionalValidator);
		builder.bind(to -> comboValuesMap.get(getter.apply(to)),
				(to, value) -> setter.accept(to, value == null ? null : value.getId()));
		comboBox.setRequired(required);
		comboBox.setItemLabelGenerator(C::getNazev);
		comboBox.setWidthFull();
		return comboBox;
	}

}
