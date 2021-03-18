package org.geogebra.common.kernel.interval;

import org.geogebra.common.kernel.arithmetic.ExpressionValue;
import org.geogebra.common.kernel.arithmetic.Inspecting;
import org.geogebra.common.plugin.Operation;

/**
 * Checker to determine ia an operation is supported by the interval arithmetic.
 */
public class UnsupportedOperatorChecker implements Inspecting {

	@Override
	public boolean check(ExpressionValue v) {
		Operation operation = v.wrap().getOperation();
		switch (operation) {
		case PLUS:
		case MINUS:
		case MULTIPLY:
		case DIVIDE:
		case NROOT:
		case DIFF:
		case SIN:
		case COS:
		case SEC:
		case COT:
		case CSC:
		case SQRT:
		case TAN:
		case EXP:
		case LOG:
		case ARCCOS:
		case ARCSIN:
		case ARCTAN:
		case ABS:
		case COSH:
		case SINH:
		case TANH:
		case ACOSH:
		case LOG10:
		case LOG2:
		case NO_OPERATION:
			return false;
		case POWER:
			return checkPower(v);
		default:
			return true;
		}
	}

	private boolean checkPower(ExpressionValue v) {
		return v.wrap().getRightTree().containsFunctionVariable();
	}
}
