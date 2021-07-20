package org.geogebra.web.full.helper;

import org.geogebra.common.util.StringUtil;
import org.geogebra.web.full.css.GuiResources;
import org.geogebra.web.html5.js.ResourcesInjector;
import org.geogebra.web.html5.util.AppletParameters;
import org.geogebra.web.resources.JavaScriptInjector;
import org.geogebra.web.resources.StyleInjector;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Resource injector that includes UI styles.
 */
public class ResourcesInjectorFull extends ResourcesInjector {

	@Override
	protected void injectResourcesGUI(AppletParameters parameters) {
		JavaScriptInjector.inject(GuiResources.INSTANCE.propertiesKeysJS());

		String path = "web3d/sass";
		String varyingPath = path + (isMebis(parameters) ? "/mebis" : "");

		StyleInjector.inject(varyingPath, "mow");
		StyleInjector.inject(varyingPath, "mow-toolbar");
		StyleInjector.inject(varyingPath, "open-screen");
		StyleInjector.inject(varyingPath, "component-styles");
		StyleInjector.inject(varyingPath, "dialog-styles");
		StyleInjector.inject(varyingPath, "settings-styles");

		// StyleInjector.inject(path, KeyboardResources.INSTANCE.keyboardStyle());
		// StyleInjector.inject(GuiResources.INSTANCE.fonts());
		StyleInjector.inject(path, "spreadsheet");
		StyleInjector.inject(path, "web-styles");
		StyleInjector.inject(path, "av-styles");
		StyleInjector.inject(path, "toolbar-styles");
		StyleInjector.inject(path, "context-menu");
		StyleInjector.inject(path, "tableview");
		StyleInjector.inject(path, "menu-styles");
		StyleInjector.inject(path, "popup-styles");
		StyleInjector.inject(path, "perspectives-popup");
		StyleInjector.inject(path, "snackbar");
		StyleInjector.inject(path, "scientific-layout");
		StyleInjector.inject(path, "evaluator-styles");
		StyleInjector.inject(path, "text-styles");
		StyleInjector.inject(path, "general");
		StyleInjector.inject(path, "headerbar");

		injectGreekFonts();
	}

	private boolean isMebis(AppletParameters parameters) {
		return "mebis".equalsIgnoreCase(parameters.getParamVendor());
	}

	public void injectGreekFonts() {
		// StyleInjector.inject(KeyboardResources.INSTANCE.greekFonts());
	}

	@Override
	public void loadWebFont(String fontUrl) {
		if (!StringUtil.empty(fontUrl)) {
			LinkElement link = Document.get().createLinkElement();
			link.setHref(fontUrl);
			link.setRel("stylesheet");
			RootPanel.getBodyElement().appendChild(link);
		}
	}
}
