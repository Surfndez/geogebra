package org.geogebra.web.full.gui.exam.classic;

import org.geogebra.common.util.AsyncOperation;
import org.geogebra.web.full.css.MaterialDesignResources;
import org.geogebra.web.html5.gui.util.NoDragImage;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.shared.components.ComponentDialog;
import org.geogebra.web.shared.components.DialogData;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class ExamClassicLogAndExitDialog extends ComponentDialog {

	/**
	 * @param app see {@link AppW}
	 * @param data dialog data
	 * @param content log information of exam mode
	 * @param handler needed for the exam exit dialog
	 */
	public ExamClassicLogAndExitDialog(AppW app, DialogData data,
			HTML content, AsyncOperation<String> handler) {
		super(app, data, false, true);
		addStyleName("examClassicLogDialog");
		buildGUI(content);
		setOnPositiveAction(() -> {
				if (handler != null) {
					handler.callback("exit");
				}
		});
	}

	private void buildGUI(HTML content) {
		FlowPanel mainPanel = new FlowPanel();
		mainPanel.addStyleName("mainPanel");
		NoDragImage infoImg = new NoDragImage(MaterialDesignResources.INSTANCE.info_black(), 32);
		mainPanel.add(infoImg);

		FlowPanel contentPanel = new FlowPanel();
		contentPanel.add(content);
		mainPanel.add(contentPanel);

		setDialogContent(mainPanel);
	}
}
