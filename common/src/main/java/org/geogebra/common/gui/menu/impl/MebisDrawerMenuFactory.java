package org.geogebra.common.gui.menu.impl;

import org.geogebra.common.GeoGebraConstants;
import org.geogebra.common.gui.menu.Action;
import org.geogebra.common.gui.menu.ActionableItem;
import org.geogebra.common.gui.menu.DrawerMenu;
import org.geogebra.common.gui.menu.Icon;
import org.geogebra.common.gui.menu.MenuItem;
import org.geogebra.common.gui.menu.MenuItemGroup;
import org.geogebra.common.move.ggtapi.operations.LogInOperation;

public class MebisDrawerMenuFactory extends DefaultDrawerMenuFactory {

	/**
	 * Create a new DrawerMenuFactory.
	 *
	 * @param platform platform
	 * @param version  version
	 */
	public MebisDrawerMenuFactory(GeoGebraConstants.Platform platform,
								  GeoGebraConstants.Version version) {
		this(platform, version, null);
	}

	/**
	 * Create a new DrawerMenuFactory.
	 *
	 * @param platform       platform
	 * @param version        version
	 * @param logInOperation if loginOperation is not null, it creates menu options that require
	 *                       login based on the {@link LogInOperation#isLoggedIn()} method.
	 */
	public MebisDrawerMenuFactory(GeoGebraConstants.Platform platform,
								  GeoGebraConstants.Version version,
								  LogInOperation logInOperation) {
		super(platform, version, logInOperation, false);
	}

	@Override
	public DrawerMenu createDrawerMenu() {
		MenuItemGroup main = createMainMenuItemGroup();
		MenuItemGroup secondary = createSecondaryMenuItemGroup();

		return new DrawerMenuImpl(null, removeNulls(main, secondary));
	}

	private MenuItemGroup createMainMenuItemGroup() {
		MenuItem share = (getLogInOperation() != null && getLogInOperation().canUserShare())
				? share() : null;

		return new MenuItemGroupImpl(removeNulls(newFile(), myFiles(), openOfflineFile(),
				saveFile(), share, exportImage(), showDownloadAs(), previewPrint()));
	}

	private MenuItemGroup createSecondaryMenuItemGroup() {
		return new MenuItemGroupImpl(showSettings(), showLicence());
	}

	private MenuItem newFile() {
		return new ActionableItemImpl(Icon.CLEAR, "New.Mebis", Action.CLEAR_CONSTRUCTION);
	}

	private MenuItem myFiles() {
		return new ActionableItemImpl(Icon.SEARCH, "Open.Mebis", Action.SHOW_SEARCH_VIEW);
	}

	private MenuItem openOfflineFile() {
		return new ActionableItemImpl(Icon.FOLDER, "mow.offlineMyFiles", Action.OPEN_OFFLINE_FILE);
	}

	private ActionableItem showLicence() {
		return new ActionableItemImpl(Icon.INFO, "AboutLicense", Action.SHOW_LICENSE);
	}
}
