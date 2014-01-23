package com.pma.vaadin.component;

import java.text.NumberFormat;

import com.pma.vaadin.converter.PercentToBigDecimalConverter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PercentCompleteSlider extends CustomComponent {

	private static final NumberFormat FORMATTER_PERCENT = NumberFormat.getPercentInstance();
	Label label = new Label();
	public Slider slider = new Slider();

	public PercentCompleteSlider() {

		VerticalLayout content = new VerticalLayout();

		// Compose from multiple components
		content.addComponent(label);
		content.addComponent(slider);

		slider.setMin(0);
		slider.setMax(100);
		slider.setResolution(0);
		slider.setConverter(new PercentToBigDecimalConverter());

		slider.setWidth("100px");
		slider.setHeight("-1px");

		label.setWidth("30px");
		label.setHeight("-1px");
		label.setValue("0%");

		// // Set the size as undefined at all levels
		// setSizeUndefined();

		slider.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				double value = (Double) event.getProperty().getValue();
				label.setValue(FORMATTER_PERCENT.format(value / 100));
			}
		});

		// The composition root MUST be set
		setCompositionRoot(content);
	}
}
