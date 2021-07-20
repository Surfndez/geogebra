/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.geogebra.web.resources;

import com.google.gwt.user.client.DOM;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLStyleElement;


/**
 * Injects stylesheets to the parent document
 */
public class StyleInjector {

	public static final String CLASSNAME = "ggw_resource";

	/**
	 * @param baseUrl (relative or absolute) base url of css file
	 * @param name name of the css file, without extension
	 */
	public static void inject(String baseUrl, String name) {
		if (DOM.getElementById(name) == null) {
			HTMLStyleElement element = createStyleElement();
			element.setAttribute("src", baseUrl + "/" + name + ".css");
			element.setAttribute("id", name);
			DomGlobal.document.head.appendChild(element);
		}
	}

	public static HTMLStyleElement injectStyleSheet(String style) {
		HTMLStyleElement element = createStyleElement();
		element.innerHTML = style;
		return element;
	}

	private static HTMLStyleElement createStyleElement() {
		HTMLStyleElement style
				= (HTMLStyleElement) DomGlobal.document.createElement("style");
		style.className = CLASSNAME;
		return style;
	}
}
