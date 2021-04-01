package org.geogebra.desktop.euclidean.event;

/**
 * interface to merge MouseEventD and Mouse3DEvent
 * 
 * @author mathieu
 *
 */
public interface MouseEventND {

	/**
	 * 
	 * @return component where the event occurred
	 */
	public java.awt.Component getComponent();

	public boolean isMiddleClick();

}
