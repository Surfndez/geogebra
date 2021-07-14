package org.geogebra.gwtutil;

import java.util.Locale;

import elemental2.dom.DomGlobal;
import jsinterop.base.Js;

public class NavigatorUtil {

	/**
	 * @return whether app is running in a mobile browser
	 */
	public static boolean isMobile() {
		String browsers = "android|webos|blackberry|iemobile|opera mini";
		return doesUserAgentContainRegex(browsers) || isiOS();
	}


	/**
	 * UA string check, may not be reliable
	 * @return whether the app is running in Firefox
	 */
	public static boolean isFirefox() {
		return doesUserAgentContainRegex("firefox");
	}

	/**
	 * Check if browser is Internet Explorer
	 *
	 * (Note: only IE11 is supported now)
	 *
	 * @return true if IE
	 */
	public static boolean isIE() {
		// check if app is running in IE5 or greater
		return doesUserAgentContainRegex("msie |trident/");
	}


	/**
	 * Check if browser is Safari on iOS
	 *
	 * check isiOS() && isSafari() if you want just iOS browser & not webview
	 *
	 * (Note: returns true for Chrome on iOS as that's really an iOS Webview)
	 *
	 * @return true if iOS (WebView or Safari browser)
	 */
	public static boolean isiOS() {
		return doesUserAgentContainRegex("iphone|ipad|ipod")
				// only iPhones iPads and iPods support multitouch
				|| ("MacIntel".equals(DomGlobal.navigator.platform) && getMaxPointTouch() > 1);
	}

	private static boolean doesUserAgentContainRegex(String regex) {
		String userAgent = DomGlobal.navigator.userAgent.toLowerCase(Locale.US);
		return userAgent.matches(".*(" + regex + ").*");
	}

	private static int getMaxPointTouch() {
		Object touchPoints =  Js.asPropertyMap(DomGlobal.navigator).get("maxTouchPoints");
		return touchPoints == null ? 0 : Js.asInt(touchPoints);
	}
}
