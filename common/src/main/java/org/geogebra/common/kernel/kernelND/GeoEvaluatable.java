package org.geogebra.common.kernel.kernelND;

import org.geogebra.common.kernel.arithmetic.Functional;

/**
 * Geos that can be used as R -&gt; R functions.
 */
public interface GeoEvaluatable extends Functional, GeoElementND {
	/**
	 * @return table index
	 */
	public int getTableColumn();

	/**
	 * @param column
	 *            TV column
	 */
	public void setTableColumn(int column);

	/**
	 * @param pointsVisible
	 *            whether TV points are visible
	 */
	public void setPointsVisible(boolean pointsVisible);

	/**
	 * @return whether TV points are visible
	 */
	public boolean isPointsVisible();

}
