package org.geogebra.common.euclidian.plot.interval;

import org.geogebra.common.awt.GPoint;
import org.geogebra.common.euclidian.EuclidianView;
import org.geogebra.common.euclidian.plot.LabelPositionCalculator;
import org.geogebra.common.kernel.interval.Interval;
import org.geogebra.common.kernel.interval.IntervalTuple;
import org.geogebra.common.util.debug.Log;

public class IntervalPath {
	private final IntervalPathPlotter gp;
	private final EuclidianView view;
	private final IntervalPlotModel model;
	private final LabelPositionCalculator labelPositionCalculator;
	private boolean moveTo;
	private GPoint labelPoint = null;

	/**
	 * Constructor.
	 * @param gp {@link IntervalPathPlotter}
	 * @param view {@link EuclidianView}
	 * @param model {@link IntervalPlotModel}
	 */
	public IntervalPath(IntervalPathPlotter gp, EuclidianView view, IntervalPlotModel model) {
		this.gp = gp;
		this.view = view;
		this.model = model;
		labelPositionCalculator = new LabelPositionCalculator(view);
	}

	/**
	 * Update the path based on the model.
	 */
	public synchronized void update() {
		if (model.isEmpty()) {
			return;
		}

		reset();
		Interval lastY = new Interval();

		int pointCount = model.getPoints().count();
		if (pointCount == 1) {
			return;
		}

		for (int i = 0; i < pointCount; i++) {
			IntervalTuple point = model.pointAt(i);
			boolean moveNeeded = isMoveNeeded(point);
			if (!moveNeeded) {
				if (lastY.isEmpty()) {
					moveToCurveBegin(point);
					lastY.set(point.y());
				} else {
					if (point.y().isInverted()) {
						drawInverted(point, model.isAscendingBefore(i));
						lastY.setEmpty();

					} else {
						plotInterval(lastY, point);
						lastY.set(point.y());
					}
				}
			}
			moveTo = moveNeeded;
		}
	}

	private void drawInverted(IntervalTuple point, boolean ascending) {
		Interval x = view.toScreenIntervalX(point.x());
		Interval y = view.toScreenIntervalY(point.y());
		double xMiddle = x.getLow() + (x.getWidth() / 2);
		double yLow = y.getLow() < view.getHeight()
				? Math.max(0, y.getLow())
				: view.getHeight();
		if (ascending) {
			if (yLow > 0) {
				Log.debug("ascending");
				gp.lineTo(xMiddle, 0);
				gp.moveTo(xMiddle, view.getHeight());
				gp.lineTo(x.getHigh(), yLow);
			}
		} else if (yLow < view.getHeight()) {
			Log.debug("descending");

			gp.lineTo(xMiddle, view.getHeight());
			gp.moveTo(xMiddle, 0);
			gp.lineTo(x.getHigh(), yLow);
		}
	}

	private boolean isMoveNeeded(IntervalTuple tuple) {
		return tuple.isEmpty() || tuple.isUndefined() || tuple.isAsymptote();
	}

	private void moveToCurveBegin(IntervalTuple point) {
		Interval x = view.toScreenIntervalX(point.x());
		Interval y = view.toScreenIntervalY(point.y());
		if (model.isAscendingBefore(point)) {
			gp.moveTo(x.getLow(), y.getHigh());
			gp.lineTo(x.getHigh(), y.getLow());
		} else {
			gp.moveTo(x.getLow(), y.getLow());
			gp.lineTo(x.getHigh(), y.getHigh());
		}
	}

	/**
	 * Resets path
	 */
	void reset() {
		gp.reset();
		labelPoint = null;
	}

	private void plotInterval(Interval lastY, IntervalTuple point) {
		Interval x = view.toScreenIntervalX(point.x());
		Interval y = view.toScreenIntervalY(point.y());
		if (y.isGreaterThan(view.toScreenIntervalY(lastY))) {
			plotHigh(x, y);
		} else {
			plotLow(x, y);
		}

		if (labelPoint == null && view.isOnView(point.x().getLow(), point.y().getLow())) {
			this.labelPoint = labelPositionCalculator.calculate(point.x().getLow(),
					point.y().getLow());
		}
	}

	private void plotHigh(Interval x, Interval y) {
		if (moveTo) {
			gp.moveTo(x.getLow(), y.getLow());
		} else {
			lineTo(x.getLow(), y.getLow());
		}

		lineTo(x.getHigh(), y.getHigh());
	}

	private void plotLow(Interval x, Interval y) {
		if (moveTo) {
			gp.moveTo(x.getLow(), y.getHigh());
		} else {
			lineTo(x.getLow(), y.getHigh());
		}

		lineTo(x.getHigh(), y.getLow());
	}

	private void lineTo(double low, double high) {
		gp.lineTo(low, high);
	}

	public GPoint getLabelPoint() {
		return labelPoint;
	}
}
