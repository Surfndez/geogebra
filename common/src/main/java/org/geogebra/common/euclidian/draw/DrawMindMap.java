package org.geogebra.common.euclidian.draw;

import org.geogebra.common.awt.GColor;
import org.geogebra.common.awt.GGeneralPath;
import org.geogebra.common.awt.GGraphics2D;
import org.geogebra.common.awt.GPoint2D;
import org.geogebra.common.euclidian.EuclidianBoundingBoxHandler;
import org.geogebra.common.euclidian.EuclidianView;
import org.geogebra.common.factories.AwtFactory;
import org.geogebra.common.kernel.geos.GeoInline;
import org.geogebra.common.kernel.geos.GeoMindMapNode;
import org.geogebra.common.kernel.geos.GeoMindMapNode.NodeAlignment;

public class DrawMindMap extends DrawInlineText {

	private static final int BORDER_RADIUS = 8;

	private final GeoMindMapNode node;
	private MindMapEdge mindMapEdge;

	private static class MindMapEdge {
		private double x0 = 0;
		private double x1 = 0;
		private double y0 = 0;
		private double y1 = 0;

		public MindMapEdge(DrawMindMap parent, DrawMindMap child, NodeAlignment alignment) {
			x0 = parent.rectangle.getLeft() + alignment.dx0 * parent.rectangle.getWidth();
			y0 = parent.rectangle.getTop() + alignment.dy0 * parent.rectangle.getHeight();
			x1 = child.rectangle.getLeft() + alignment.dx1 * child.rectangle.getWidth();
			y1 = child.rectangle.getTop() + alignment.dy1 * child.rectangle.getHeight();
		}

		private boolean isIntersecting(NodeAlignment alignment) {
			return ((alignment.dx0 - 0.5) * (x0 - x1) > 0)
					|| ((alignment.dy0 - 0.5) * (y0 - y1) > 0);
		}

		private GGeneralPath getConnectionPath(GeoMindMapNode node) {
			GGeneralPath path = AwtFactory.getPrototype().newGeneralPath();
			path.moveTo(x0, y0);
			double w0 = 1.0 / 4;
			if (isIntersecting(node.getAlignment())) {
				w0 = 2;
			}
			double w1 = 1 - w0;
			if (node.getAlignment() == NodeAlignment.TOP
					|| node.getAlignment() == NodeAlignment.BOTTOM) {
				path.curveTo(x0, w0 * y0 + w1 * y1, x1,
						w1 * y0 + w0 * y1, x1, y1);
			} else {
				path.curveTo(w0 * x0 + w1 * x1, y0, w1 * x0 + w0 * x1, y1, x1, y1);
			}
			return path;
		}

		public double getAngle(NodeAlignment alignment) {
			return ((x0 - x1) * (alignment.dx0 - 0.5)
					+ (y0 - y1) * (alignment.dy0 - 0.5)) / Math.hypot(x0 - x1, y0 - y1);
		}
	}

	public DrawMindMap(EuclidianView view, GeoInline text) {
		super(view, text);
		this.node = (GeoMindMapNode) text;
	}

	@Override
	public void update() {
		super.update();
		GeoMindMapNode parentGeo = node.getParent();
		DrawMindMap parent = (DrawMindMap) view.getDrawableFor(parentGeo);
		if (parent == null) {
			return;
		}
		NodeAlignment alignment = node.getAlignment();
		if (mindMapEdge == null) {
			mindMapEdge = new MindMapEdge(parent, this, alignment);
		}
		if (mindMapEdge.isIntersecting(alignment)
				|| alignment.isOpposite(parent.node.getAlignment())) {
			parent.updateAlignment(this);
		} else {
			mindMapEdge = new MindMapEdge(parent, this, alignment);
		}
	}

	@Override
	public void draw(GGraphics2D g2) {
		for (GeoMindMapNode childGeo : node.getChildren()) {
			DrawMindMap child = (DrawMindMap) view.getDrawableFor(childGeo);
			if (child == null) {
				continue;
			}
			GGeneralPath path = child.mindMapEdge.getConnectionPath(childGeo);
			g2.setStroke(border1);
			g2.setColor(GColor.BLACK);
			g2.draw(path);
		}

		draw(g2, BORDER_RADIUS);
	}

	private void updateAlignment(DrawMindMap child) {
		double angle = Double.POSITIVE_INFINITY;
		boolean intersect = true;
		for (NodeAlignment alignment: NodeAlignment.values()) {
			if (node.getParent() != null && alignment.isOpposite(node.getAlignment())) {
				continue;
			}
			MindMapEdge connection = new MindMapEdge(this, child, alignment);
			double newAngle = connection.getAngle(alignment);
			boolean newIntersect = connection.isIntersecting(alignment);
			if ((!newIntersect && intersect) || ((newIntersect == intersect) && newAngle < angle)) {
				child.mindMapEdge = connection;
				child.node.setAlignment(alignment);
				intersect = newIntersect;
				angle = newAngle;
			}
		}
	}

	private NodeAlignment toAlignment(EuclidianBoundingBoxHandler addHandler) {
		switch (addHandler) {
		case ADD_TOP:
			return NodeAlignment.TOP;
		case ADD_RIGHT:
			return NodeAlignment.RIGHT;
		case ADD_BOTTOM:
			return NodeAlignment.BOTTOM;
		case ADD_LEFT:
			return NodeAlignment.LEFT;
		default:
			return null;
		}
	}

	public GeoMindMapNode addChildNode(EuclidianBoundingBoxHandler addHandler) {
		GPoint2D newLocation = new GPoint2D(node.getLocation().x, node.getLocation().y);
		GeoMindMapNode child = new GeoMindMapNode(node.getConstruction(), newLocation);
		child.setSize(GeoMindMapNode.MIN_WIDTH, GeoMindMapNode.CHILD_HEIGHT);
		child.setParent(node, toAlignment(addHandler));
		child.setBackgroundColor(child.getKernel().getApplication().isMebis()
				? GColor.MOW_MIND_MAP_CHILD_BG_COLOR : GColor.MIND_MAP_CHILD_BG_COLOR);
		child.setBorderColor(child.getKernel().getApplication().isMebis()
				? GColor.MOW_MIND_MAP_CHILD_BORDER_COLOR : GColor.MIND_MAP_CHILD_BORDER_COLOR);
		return child;
	}
}
