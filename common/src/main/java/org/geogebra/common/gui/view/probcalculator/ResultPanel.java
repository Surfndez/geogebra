package org.geogebra.common.gui.view.probcalculator;

import org.geogebra.common.gui.SetLabels;

public interface ResultPanel extends SetLabels {
	void showInterval();

	void showTwoTailed();

	void showTwoTailedOnePoint();

	void showLeft();

	void showRight();

	void setResultEditable(boolean value);

	void updateResult(String text);

	void updateResultSum(String text);

	void updateLowHigh(String low, String high);

	void updateTwoTailedResult(String low, String high);

	boolean isFieldLow(Object source);

	boolean isFieldHigh(Object source);

	boolean isFieldResult(Object source);

	void setGreaterThan();

	void setGreaterOrEqualThan();
}
