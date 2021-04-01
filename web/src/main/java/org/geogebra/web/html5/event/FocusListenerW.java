package org.geogebra.web.html5.event;

import javax.annotation.Nonnull;

import org.geogebra.common.euclidean.event.FocusListenerDelegate;
import org.geogebra.common.main.ScreenReader;
import org.geogebra.web.html5.gui.view.autocompletion.ScrollableSuggestBox;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;

/**
 * @author judit
 * 
 */
public class FocusListenerW implements FocusHandler, BlurHandler {

	private FocusListenerDelegate delegate;
	private ScrollableSuggestBox textField;

	/**
	 * @param listener
	 *            delegate
	 */
	public FocusListenerW(@Nonnull FocusListenerDelegate listener,
			ScrollableSuggestBox textField) {
		this.delegate = listener;
		this.textField = textField;
	}

	/** dummy method */
	public void init() {
		// avoid warnings
	}

	@Override
	public void onFocus(FocusEvent event) {
		delegate.focusGained();
		ScreenReader.debug(textField.getElement().getAttribute("aria-label"));
	}

	@Override
	public void onBlur(BlurEvent event) {
		delegate.focusLost();
	}
}
