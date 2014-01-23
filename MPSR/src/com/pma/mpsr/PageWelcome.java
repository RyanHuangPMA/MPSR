package com.pma.mpsr;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PageWelcome extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Label label_3;
	@AutoGenerated
	private Label gap3;
	@AutoGenerated
	private Label label_2;
	@AutoGenerated
	private Label gap2;
	@AutoGenerated
	private Label label_1;
	@AutoGenerated
	private Label gap1;
	@AutoGenerated
	private Image logo;
	ThemeResource logoThemeRsrc = new ThemeResource("img/pma.bmp");

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public PageWelcome() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		initLogo();
	}

	private void initLogo() {

		logo.setSource(logoThemeRsrc);
		mainLayout.setComponentAlignment(logo, Alignment.TOP_RIGHT);

		gap1.setValue("");
		gap2.setValue("");
		gap3.setValue("");

		label_1.setValue("PMA DELIVERY PROJECTS & CONSTRUCTION");
		label_1.addStyleName("welcome_1");
		mainLayout.setComponentAlignment(label_1, Alignment.TOP_CENTER);

		label_2.setValue("MAJOR PROJECT STATUS REPORT APPLICATION");
		label_2.addStyleName("welcome_2");
		mainLayout.setComponentAlignment(label_2, Alignment.TOP_CENTER);

		//TODO:
		label_3.setValue("Prototype");
		label_3.addStyleName("prototype1");
		mainLayout.setComponentAlignment(label_3, Alignment.TOP_CENTER);

	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);

		// top-level component properties
		setSizeFull();

		// imgLogo
		logo = new Image();
		logo.setImmediate(false);
		logo.setWidth("-1px");
		logo.setHeight("60px");
		mainLayout.addComponent(logo);

		// gap1
		gap1 = new Label();
		gap1.setImmediate(false);
		gap1.setWidth("-1px");
		gap1.setHeight("100px");
		gap1.setValue("Label");
		mainLayout.addComponent(gap1);

		// label_1
		label_1 = new Label();
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Label");
		mainLayout.addComponent(label_1);

		// gap2
		gap2 = new Label();
		gap2.setImmediate(false);
		gap2.setWidth("-1px");
		gap2.setHeight("60px");
		gap2.setValue("Label");
		mainLayout.addComponent(gap2);

		// label_2
		label_2 = new Label();
		label_2.setImmediate(false);
		label_2.setWidth("-1px");
		label_2.setHeight("-1px");
		label_2.setValue("Label");
		mainLayout.addComponent(label_2);

		// gap3
		gap3 = new Label();
		gap3.setImmediate(false);
		gap3.setWidth("-1px");
		gap3.setHeight("60px");
		gap3.setValue("Label");
		mainLayout.addComponent(gap3);

		//TODO:
		// label_3
		label_3 = new Label();
		label_3.setImmediate(false);
		label_3.setWidth("-1px");
		label_3.setHeight("-1px");
		label_3.setValue("Label");
		mainLayout.addComponent(label_3);

		return mainLayout;
	}

}
