package org.geogebra.web.full.gui.dialog;

import org.geogebra.common.awt.GColor;
import org.geogebra.common.gui.dialog.handler.ColorChangeHandler;
import org.geogebra.ggbjdk.java.awt.geom.Dimension;
import org.geogebra.web.full.gui.util.ColorChooserW;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.shared.components.ComponentDialog;
import org.geogebra.web.shared.components.DialogData;

public class ColorChooserDialog extends ComponentDialog {
	private ColorChooserW colorChooserW;
	private GColor selectedColor;
	private ColorChangeHandler handler;
	private GColor originalColor;
	
	/**
	 * @param app application
	 * @param data dialog data
	 * @param originalColor initial color
	 * @param handler color handler
	 */
	public ColorChooserDialog(AppW app, DialogData data,
			final GColor originalColor, final ColorChangeHandler handler) {
		super(app, data, false, true);
		addStyleName("colorChooser");
		this.handler = handler;
		this.originalColor = originalColor;
		buildGUI();
		setHandlers();
	}

	/**
	 * @param app application
	 * @param data dialog data
	 * @param originalColor initial color
	 * @param handler color handler
	 * @param colorChooser color chooser panel
	 */
	public ColorChooserDialog(AppW app, DialogData data, final GColor originalColor,
			final ColorChangeHandler handler, ColorChooserW colorChooser) {
		super(app, data, false, true);
		addStyleName("colorChooser");
		this.handler = handler;
		this.originalColor = originalColor;
		colorChooserW = colorChooser;
		colorChooserW.setSelectedColor(originalColor);
		setSelectedColor(originalColor);
		setDialogContent(colorChooserW);
		setHandlers();
	}

	private void buildGUI() {
		final Dimension colorIconSizeW = new Dimension(20, 20);
		colorChooserW = new ColorChooserW(app, 400, 210, colorIconSizeW, 4);
		colorChooserW.enableOpacity(false);
		colorChooserW.enableBackgroundColorPanel(false);
		colorChooserW.setSelectedColor(originalColor);
		setSelectedColor(originalColor);
		setDialogContent(colorChooserW);
	}

	private void setHandlers() {
		setOnPositiveAction(() -> handler.onColorChange(getSelectedColor()));
		colorChooserW.addChangeHandler(new ColorChangeHandler() {
			@Override
			public void onForegroundSelected() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onColorChange(GColor color) {
				setSelectedColor(color);
			}

			@Override
			public void onClearBackground() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onBackgroundSelected() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAlphaChange() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onBarSelected() {
				// TODO Auto-generated method stub
			}
		});

	}

	public GColor getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(GColor selectedColor) {
		this.selectedColor = selectedColor;
	}

	public void setHandler(ColorChangeHandler handler) {
		this.handler = handler;
	}

	public ColorChooserW getColorChooserPanel() {
		return colorChooserW;
	}

	/**
	 * @param color
	 *            initial color
	 */
	public void setOriginalColor(GColor color) {
		originalColor = color;
		reset();
	}

	private void reset() {
		setSelectedColor(originalColor);
		colorChooserW.setSelectedColor(originalColor);
		colorChooserW.update();
	}
}