package org.geogebra.web.html5.euclidean;

import org.geogebra.common.euclidean.euclideanView;
import org.geogebra.common.main.App;
import org.geogebra.web.html5.main.AppW;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RequiresResize;

/**
 * euclidean panel for WebSimple
 *
 */
public class euclideanSimplePanelW extends AbsolutePanel implements
        euclideanPanelWAbstract, RequiresResize {

	private AppW app;
	private int oldHeight = 0;
	private int oldWidth = 0;

	private Canvas eview1 = null; // static foreground

	/**
	 * This constructor is used by the applet
	 * 
	 * @param application
	 *            application
	 */
	public euclideanSimplePanelW(AppW application) {
		super();
		loadComponent();
		app = application;
		getElement().setAttribute("role", "application");
	}

	private void loadComponent() {
		eview1 = Canvas.createIfSupported();
		eview1.getElement().getStyle().setPosition(Style.Position.RELATIVE);
		eview1.getElement().getStyle().setZIndex(0);
		eview1.setWidth("1px");
		eview1.setHeight("1px");
		eview1.setCoordinateSpaceHeight(1);
		eview1.setCoordinateSpaceWidth(1);
		getElement().getStyle().setOverflow(Overflow.VISIBLE);
		add(eview1);

	}

	@Override
	public Canvas getCanvas() {
		return eview1;
	}

	@Override
	public Panel geteuclideanPanel() {
		return this;
	}

	/**
	 * @param app1
	 *            application
	 */
	public void attachApp(App app1) {
		this.app = (AppW) app1;
	}

	@Override
	public AbsolutePanel getAbsolutePanel() {
		return this;
	}

	@Override
	public euclideanView geteuclideanView() {
		if (app != null) {
			return app.geteuclideanView1();
		}
		return null;
	}

	@Override
	public void onResize() {

		// This is probably not needed, but what if yes?

		if (app != null) {
			int h = getOffsetHeight();
			int w = getOffsetWidth();

			// exit if new size cannot be determined
			if (h <= 0 || w <= 0) {
				return;
			}

			if (h != oldHeight || w != oldWidth) {
				app.ggwGraphicsViewDimChanged(w, h);
				oldHeight = h;
				oldWidth = w;
			}
		}
	}

	@Override
	public void deferredOnResize() {

		// There is probably no need for deferred call here, but what if yes?

		Scheduler.get().scheduleDeferred(onResizeCmd);
		// onResize();
	}

	private ScheduledCommand onResizeCmd = new ScheduledCommand() {
		@Override
		public void execute() {
			onResize();
		}
	};

	@Override
	public void updateNavigationBar() {
		// no navigation
	}

	@Override
	public void reset() {
		oldWidth = 0;
		oldHeight = 0;
	}
}
