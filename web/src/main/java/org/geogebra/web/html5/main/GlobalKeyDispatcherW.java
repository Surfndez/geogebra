package org.geogebra.web.html5.main;

import java.util.ArrayList;

import org.geogebra.common.gui.AccessibilityManagerInterface;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.main.App;
import org.geogebra.common.main.GlobalKeyDispatcher;
import org.geogebra.common.util.CopyPaste;
import org.geogebra.gwtutil.NavigatorUtil;
import org.geogebra.web.html5.Browser;
import org.geogebra.web.html5.gui.AlgebraInput;
import org.geogebra.web.html5.gui.GuiManagerInterfaceW;
import org.geogebra.web.html5.util.CopyPasteW;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.himamis.retex.editor.share.util.GWTKeycodes;
import com.himamis.retex.editor.share.util.KeyCodes;

/**
 * Handles keyboard events.
 */
public class GlobalKeyDispatcherW extends GlobalKeyDispatcher
		implements KeyUpHandler, KeyDownHandler, KeyPressHandler {

	private static boolean controlDown = false;
	private static boolean shiftDown = false;

	private boolean escPressed = false;

	/**
	 * @return whether ctrl is pressed
	 */
	public static boolean getControlDown() {
		return controlDown;
	}

	/**
	 * @return whether shift is pressed
	 */
	public static boolean getShiftDown() {
		return shiftDown;
	}

	/**
	 * Update ctrl, shift flags
	 *
	 * @param ev
	 *            key event
	 */
	public static void setDownKeys(KeyEvent<? extends EventHandler> ev) {
		setDownKeys(ev.isControlKeyDown(), ev.isShiftKeyDown());
	}

	/**
	 * Update ctrl, shift flags
	 *
	 * @param control
	 *            if control is down.
	 * @param shift
	 *            if shift is down.
	 */
	private static void setDownKeys(boolean control, boolean shift) {
		controlDown = control;
		shiftDown = shift;
	}

	/**
	 * @param app
	 *            application
	 */
	public GlobalKeyDispatcherW(AppW app) {
		super(app);
	}

	private class GlobalShortcutHandler implements EventListener {

		@Override
		public void onBrowserEvent(Event event) {
			if (DOM.eventGetType(event) == Event.ONKEYDOWN) {
				boolean handled = false;

				if (event.getKeyCode() == GWTKeycodes.KEY_X
						&& event.getCtrlKey()
						&& event.getAltKey()) {
					handleCtrlAltX();
					handled = true;
				}

				if (event.getKeyCode() == GWTKeycodes.KEY_ONE && event.getCtrlKey()) {
					handleCtrlOne(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_TWO && event.getCtrlKey()) {
					handleCtrlTwo(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_THREE && event.getCtrlKey()) {
					handleCtrlThree(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_A && event.getCtrlKey()) {
					handleCtrlA(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_K && event.getCtrlKey()) {
					handleCtrlK(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_L && event.getCtrlKey()) {
					handleCtrlL(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_O && event.getCtrlKey()) {
					handleCtrlO(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_P && event.getCtrlKey()) {
					handleCtrlP(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_T && event.getCtrlKey()) {
					handleCtrlT(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_W && event.getCtrlKey()) {
					handleCtrlW(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_F4 && event.getCtrlKey()) {
					handleCtrlF4(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_F10 && event.getCtrlKey()) {
					handleCtrlF10(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_I && event.getCtrlKey()) {
					handleCtrlI(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_X && event.getCtrlKey()) {
					handleCtrlX(false);
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_C && event.getCtrlKey()) {
					handleCtrlC(event.getShiftKey(), false);
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_M && event.getCtrlKey()) {
					handleCtrlM(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_B && event.getCtrlKey()) {
					handleCtrlB(event.getShiftKey());
				}
				if ((event.getKeyCode() == GWTKeycodes.KEY_G
						|| event.getKeyCode() == GWTKeycodes.KEY_H) && event.getCtrlKey()) {
					handleCtrlGH(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_E && event.getCtrlKey()) {
					handleCtrlE();
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_F && event.getCtrlKey()) {
					handleCtrlF();
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_N && event.getCtrlKey()) {
					handleCtrlN(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_Z && event.getCtrlKey()) {
					handleCtrlZ(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_U && event.getCtrlKey()) {
					handleCtrlU(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_V && event.getCtrlKey()) {
					handleCtrlV(false);
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_R && event.getCtrlKey()) {
					handleCtrlR();
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_S && event.getCtrlKey()) {
					handleCtrlS(event.getShiftKey());
				}
				if (event.getKeyCode() == GWTKeycodes.KEY_Y && event.getCtrlKey()) {
					handleCtrlY(event.getShiftKey());
				}
				if ((event.getKeyCode() == GWTKeycodes.KEY_J
						|| event.getKeyCode() == GWTKeycodes.KEY_Q) && event.getCtrlKey()) {
					handleCtrlJQ(event.getShiftKey());
				}
				if ((event.getKeyCode() == GWTKeycodes.KEY_NUM_PLUS
						|| event.getKeyCode() == GWTKeycodes.KEY_MINUS
						|| event.getKeyCode() == GWTKeycodes.KEY_NUM_MINUS
						|| event.getKeyCode() == GWTKeycodes.KEY_EQUALS) && event.getCtrlKey()) {
					handleCtrlPlusAddSubtractMinusEquals(true,
							KeyCodes.translateGWTcode(event.getKeyCode()));
				}
				if ((event.getKeyCode() == GWTKeycodes.KEY_D
						|| event.getKeyCode() == GWTKeycodes.KEY_BACK_QUOTE) && event
						.getCtrlKey()) {
					handleCtrlJQ(event.getShiftKey());
				}
				if (NavigatorUtil.isiOS() && isControlKeyDown(event)) {
					handleIosKeyboard((char) event.getCharCode());
					handled = true;
				}

				KeyCodes kc = KeyCodes.translateGWTcode(event.getKeyCode());
				if (kc == KeyCodes.TAB) {
					if (!escPressed) {
						handled = handleTab(event.getShiftKey());
					}
				} else if (kc == KeyCodes.ESCAPE) {
					escPressed = true;
					handled = true;
				}

				if (handled) {
					event.preventDefault();
					event.stopPropagation();
				}
			}
		}
	}

	private void handleCtrlAltX() {
		app.hideMenu();
		app.closePopups();
		if (app.getActiveEuclidianView() != null) {
			app.getActiveEuclidianView()
					.getEuclidianController()
					.hideDynamicStylebar();
		}
		app.getSelectionManager().clearSelectedGeos();
		app.getAccessibilityManager().focusInput(true);
	}

	public EventListener getGlobalShortcutHandler() {
		return new GlobalShortcutHandler();
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		setDownKeys(event);
		KeyCodes kc = KeyCodes.translateGWTcode(event.getNativeEvent()
				.getKeyCode());
		// Do not prevent default for the v key, otherwise paste events are not fired
		if (kc != KeyCodes.TAB && event.getCharCode() != 'v'
				&& event.getCharCode() != 'c' && event.getCharCode() != 'x') {
			event.preventDefault();
			event.stopPropagation();
		}
		// this needs to be done in onKeyPress -- keyUp is not case sensitive
		if (!event.isAltKeyDown() && !event.isControlKeyDown() && !app.isWhiteboardActive()) {
			this.renameStarted(event.getCharCode());
		}
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		setDownKeys(event);
		handleGeneralKeys(event);
		storeUndoInfoIfChanged();
	}

	/**
	 * Handles key event by disassembling it into primitive types and handling
	 * it using the method from common
	 *
	 * @param event
	 *            event
	 */
	public void handleGeneralKeys(KeyUpEvent event) {
		KeyCodes kc = KeyCodes.translateGWTcode(event.getNativeKeyCode());

		boolean handled = handleGeneralKeys(kc,
				event.isShiftKeyDown(),
				isControlKeyDown(event.getNativeEvent()),
		        event.isAltKeyDown(), false, true);
		if (handled) {
			event.preventDefault();
		}
	}

	private static boolean isControlKeyDown(NativeEvent event) {
		return event.getCtrlKey()
				|| (Browser.isMacOS() || NavigatorUtil.isiOS()) && event.getMetaKey();
	}

	/**
	 * handle function keys, arrow keys, +/- keys for selected geos, etc.
	 * @param event
	 *            native event
	 * @return if key was consumed
	 */
	public boolean handleSelectedGeosKeys(NativeEvent event) {
		return handleSelectedGeosKeys(
				KeyCodes.translateGWTcode(event
						.getKeyCode()), selection.getSelectedGeos(),
				event.getShiftKey(), event.getCtrlKey(), event.getAltKey(),
				false);
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		KeyCodes kc = KeyCodes.translateGWTcode(event.getNativeKeyCode());
		setDownKeys(event);

		boolean handled = handleSelectedGeosKeys(event.getNativeEvent());

		if (handled || preventBrowserCtrl(kc, event.isShiftKeyDown())
				&& event.isControlKeyDown()) {
			event.preventDefault();
			event.stopPropagation();
		}
	}

	private static boolean preventBrowserCtrl(KeyCodes kc, boolean shift) {
		return kc == KeyCodes.S || kc == KeyCodes.O
				|| (kc == KeyCodes.D && shift) || (kc == KeyCodes.C && shift);
	}

	/**
	 * @param isShiftDown whether Shift+Tab was pressed
	 * @return whether the tab was handled internally
	 */
	public boolean handleTab(boolean isShiftDown) {
		AccessibilityManagerInterface am = app.getAccessibilityManager();

		app.getActiveEuclidianView().closeDropdowns();

		if (isShiftDown) {
			return am.focusPrevious();
		} else {
			return am.focusNext();
		}
	}

	@Override
	protected boolean handleCtrlShiftN(boolean isAltDown) {
		// unimplemented
		return false;
	}

	@Override
	protected boolean handleEnter() {
		if (super.handleEnter()) {
			return true;
		}

		if (app.getGuiManager() != null
		        && app.getGuiManager().noMenusOpen()) {
			if (app.showAlgebraInput()) {
				AlgebraInput algebraInput = ((GuiManagerInterfaceW) app.getGuiManager())
						.getAlgebraInput();
				if (algebraInput != null) {
					algebraInput.requestFocus();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void copyDefinitionsToInputBarAsList(ArrayList<GeoElement> geos) {
		// unimplemented
	}

	@Override
	protected void createNewWindow() {
		// unimplemented
	}

	@Override
	protected void showPrintPreview(App app2) {
		// unimplemented
	}

	/**
	 *
	 * @param e
	 *            The KeyEvent
	 * @return true if unwanted key combination has pressed.
	 */
	public static boolean isBadKeyEvent(KeyEvent<? extends EventHandler> e) {
		return e.isAltKeyDown() && !e.isControlKeyDown()
				&& e.getNativeEvent().getCharCode() > 128;
	}

	@Override
	protected KeyCodes translateKey(int i) {
		return KeyCodes.translateGWTcode(i);
	}

	private void handleIosKeyboard(char code) {
		switch (code) {
			case 'v':
				CopyPasteW.pasteInternal((AppW) app);
				break;
			case 'c':
				CopyPaste.handleCutCopy(app, false);
				break;
			case 'x':
				CopyPaste.handleCutCopy(app, true);
				break;
			default:
				break;
		}
	}

	public void setEscPressed(boolean escPressed) {
		this.escPressed = escPressed;
	}

	public boolean isEscPressed() {
		return escPressed;
	}
}
