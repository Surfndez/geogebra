package org.geogebra.web.full.gui.toolbarpanel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.geogebra.common.gui.view.table.TableValuesPoints;
import org.geogebra.common.gui.view.table.TableValuesView;
import org.geogebra.common.gui.view.table.dialog.StatisticRow;
import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoList;
import org.geogebra.common.kernel.geos.GeoNumeric;
import org.geogebra.common.kernel.kernelND.GeoEvaluatable;
import org.geogebra.common.main.DialogManager;
import org.geogebra.common.plugin.Event;
import org.geogebra.common.plugin.EventType;
import org.geogebra.web.full.gui.menubar.MainMenu;
import org.geogebra.web.full.javax.swing.GPopupMenuW;
import org.geogebra.web.html5.gui.GuiManagerInterfaceW;
import org.geogebra.web.html5.gui.util.AriaMenuItem;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.html5.util.TestHarness;
import org.geogebra.web.resources.SVGResource;

import com.google.gwt.user.client.Command;

/**
 * Context menu which is opened with the table of values header 3dot button
 * 
 * @author csilla
 *
 */
public class ContextMenuTV {
	private final TableValuesView view;
	/**
	 * popup for the context menu
	 */
	protected GPopupMenuW wrappedPopup;
	/**
	 * application
	 */
	protected AppW app;
	private final int columnIdx;
	private final GeoElement geo;

	/**
	 * @param app
	 *            see {@link AppW}
	 * @param geo
	 *            label of geo
	 * @param column
	 *            index of column
	 */
	public ContextMenuTV(AppW app, TableValuesView view, GeoElement geo, int column) {
		this.app = app;
		this.view = view;
		this.columnIdx = column;
		this.geo = geo;
		buildGui();
	}

	/**
	 * @return application
	 */
	public AppW getApp() {
		return app;
	}

	/**
	 * @return index of column
	 */
	public int getColumnIdx() {
		return columnIdx;
	}

	private void buildGui() {
		wrappedPopup = new GPopupMenuW(app);
		if (getColumnIdx() > 0) {
			// column index > 0 -> edit function
			if (view.getTableValuesModel().isColumnEditable(getColumnIdx())) {
				addStats("Statistics", view::getStatistics);
				addStats("Statistics2", view::getStatistics2);
				addCommand(this::showRegression, "Regression",
						"regression");
			} else {
				addShowHide();
				addEdit(() -> {
					GuiManagerInterfaceW guiManager = getApp().getGuiManager();
					if (guiManager != null) {
						guiManager.startEditing(geo);
					}
				});
			}
			addDelete();
		} else {
			// column index = 0 -> edit x-column
			addEdit(() -> {
				DialogManager dialogManager = getApp().getDialogManager();
				if (dialogManager != null) {
					dialogManager.openTableViewDialog(null);
				}
			});
			addStats("Statistics", view::getStatistics);
			addAdd();
		}
	}

	private void addStats(String transKey, Function<Integer, List<StatisticRow>> statFunction) {
		addCommand(() -> showStats(statFunction), transKey,	transKey.toLowerCase(Locale.US));
	}

	private void showRegression() {
		StatsDialogTV dialog = new StatsDialogTV(app, view, getColumnIdx());
		dialog.addRegressionChooserAndShow();
		dialog.show();
	}

	private void showStats(Function<Integer, List<StatisticRow>> statFunction) {
		StatsDialogTV dialog = new StatsDialogTV(app, view, getColumnIdx());
		dialog.updateContent(statFunction);
	}

	private void addShowHide() {
		final TableValuesPoints tvPoints = getApp().getGuiManager()
				.getTableValuesPoints();
		final int column = getColumnIdx();
		String transKey = tvPoints.arePointsVisible(column) ? "HidePoints"
				: "ShowPoints";
		Command pointCommand = () -> {
			dispatchShowPointsTV(column, !tvPoints.arePointsVisible(column));
			tvPoints.setPointsVisible(column,
				!tvPoints.arePointsVisible(column));
		};
		addCommand(pointCommand, transKey, "showhide");
	}

	private void dispatchShowPointsTV(int column, boolean show) {
		Map<String, Object> showPointsJson = new HashMap<>();
		showPointsJson.put("column", column);
		showPointsJson.put("show",  show);
		app.dispatchEvent(new Event(EventType.SHOW_POINTS_TV).setJsonArgument(showPointsJson));
	}

	private void addCommand(Command pointCommand, String transKey, String title) {
		AriaMenuItem mi = new AriaMenuItem(
				MainMenu.getMenuBarHtml((SVGResource) null,
						app.getLocalization().getMenu(transKey)),
				true, pointCommand);
		mi.addStyleName("no-image");
		TestHarness.setAttr(mi, "menu_" + title);
		wrappedPopup.addItem(mi);
	}

	private void addDelete() {
		Command deleteCommand = () -> {
					GuiManagerInterfaceW guiManager = getApp().getGuiManager();
					if (guiManager != null && guiManager.getTableValuesView() != null) {
						TableValuesView tableValuesView = (TableValuesView) guiManager
								.getTableValuesView();
						GeoEvaluatable column = tableValuesView
								.getEvaluatable(getColumnIdx());
						tableValuesView.hideColumn(column);
						app.dispatchEvent(new Event(EventType.REMOVE_TV, (GeoElement) column));
					}
				};
		addCommand(deleteCommand, "RemoveColumn", "delete");
	}

	private void addEdit(Command cmd) {
		addCommand(cmd, "Edit", "edit");
	}

	private void addAdd() {
		Command add = () -> {
			Construction construction = app.getKernel().getConstruction();
			GeoList gl = new GeoList(construction);
			for (int i = 0; i < 5; i++) {
				gl.add(new GeoNumeric(construction, 3));
			}
			gl.setLabel(construction.getLabelManager()
					.getNextNumberedLabel("y"));
			view.showColumn(gl);
		};
		addCommand(add, "Add", "add");
	}

	/**
	 * Show the context menu at the (x, y) screen coordinates.
	 * 
	 * @param x
	 *            y coordinate.
	 * @param y
	 *            y coordinate.
	 */
	public void show(int x, int y) {
		wrappedPopup.show(x, y);
		wrappedPopup.getPopupMenu().focusDeferred();
	}
}
