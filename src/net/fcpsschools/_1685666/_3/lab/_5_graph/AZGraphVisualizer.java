package net.fcpsschools._1685666._3.lab._5_graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Visualizes graphs -- vertices and edges.
 * <p>
 * Usage:
 * <pre>{@code
 * class YourVertex implements AZGraphVisualizer.AZVertex<YourVertex> { ... }
 * class YourGraph implements AZGraphVisualizer.AZGraph<YourVertex> { ... }
 * // Sometime later
 * AZGraphVisualizer.display(yourGraphObject);
 * }</pre>
 *
 * @author ApolloZhu, Period 1
 * @apiNote This is declared as enum to avoid
 * anyone construct any new instances by accident.
 */
public enum AZGraphVisualizer {
    ;

    public static <VertexType extends AZGraphVisualizer.AZVertex<VertexType>>
    void display(AZGraph<VertexType> graph) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Graph");
                frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                frame.setContentPane(new GraphPanel<>(graph));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    interface AZGraph<VertexType extends AZGraphVisualizer.AZVertex<VertexType>> {
        Collection<VertexType> getVertices();
    }

    interface AZVertex<VertexType extends AZGraphVisualizer.AZVertex<VertexType>> {
        String getName();

        Collection<VertexType> getAdjacencies();
    }

    private static class GraphPanel<
            VertexType extends AZGraphVisualizer.AZVertex<VertexType>
            > extends JPanel {
        // The "null" bound
        private static final Rectangle INVALID_BOX =
                new Rectangle(-1, -1, -1, -1);
        // Graph to display
        private AZGraph<VertexType> graph;
        // Calculated positions for each vertex
        private Map<VertexType, Point> positions;
        // Calculated radius of all the vertices
        private int vertexRadius;
        // Selected vertex to highlight
        private VertexType selected;

        GraphPanel(AZGraph<VertexType> graph) {
            this.graph = graph;
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Point point = e.getPoint();
                    for (VertexType vertex : graph.getVertices()) {
                        Rectangle box = getBoundingBoxForVertex(vertex);
                        if (INVALID_BOX != box && box.contains(point)) {
                            selected = vertex;
                            repaint();
                            return;
                        }
                    }
                }
            });
        }

        private static Point makePoint(double x, double y) {
            return new Point((int) x, (int) y);
        }

        // Undergoes linear transformation:
        // [ cos(theta) -sin(theta) ]
        // [ sin(theta)  cos(theta) ]
        private static Point rotatedBy(double radians, Point p) {
            double cos = Math.cos(-radians);
            double sin = Math.sin(-radians);
            return makePoint(
                    cos * p.x - sin * p.y,
                    sin * p.x + cos * p.y
            );
        }

        private void calculatePositions() {
            positions = new HashMap<>();
            int min = Math.min(getWidth(), getHeight());
            vertexRadius = min / 10;
            // We divide the circle into equal parts
            int count = graph.getVertices().size();
            double interval = Math.PI * 2 / count;
            Point radii = makePoint(min * 4 / 10, 0);
            // Start at upper left corner
            radii = rotatedBy(Math.PI * 3 / 4, radii);
            int i = 0, dx = getWidth() / 2, dy = getHeight() / 2;
            for (VertexType vertex : graph.getVertices()) {
                // Clock wise rotation/distribution
                Point end = rotatedBy(i-- * interval, radii);
                end.translate(dx, dy);
                positions.put(vertex, end);
            }
        }

        private void drawArrowHeadAroundPoint(Point p, int dx, int dy, Graphics g) {
            // Calculate arrow head position (vector)
            Point arrowEnd = makePoint(-dx, -dy);
            // Rotate them to be look like arrow head
            Point end1 = rotatedBy(Math.PI / 5, arrowEnd);
            Point end2 = rotatedBy(Math.PI / -5, arrowEnd);
            // Shift them back to the end
            end1.translate(p.x, p.y);
            end2.translate(p.x, p.y);
            // Now it's directed
            g.drawLine(p.x, p.y, end1.x, end1.y);
            g.drawLine(p.x, p.y, end2.x, end2.y);
        }

        private void drawSelfLoopAroundVertex(VertexType vertex, Graphics g) {
            if (vertex == selected) {
                g.setColor(Color.blue);
            } else g.setColor(Color.black);
            // We move the right end of circle to draw the arrow head
            Point point = positions.get(vertex).getLocation();
            point.translate(vertexRadius, 0);
            drawArrowHeadAroundPoint(point, 0, vertexRadius / -2, g);
        }

        private void drawEdges(Graphics g) {
            for (VertexType vertex : graph.getVertices()) {
                for (VertexType neighbor : vertex.getAdjacencies()) {
                    if (vertex == neighbor) {
                        drawSelfLoopAroundVertex(vertex, g);
                        continue;
                    }
                    // Otherwise for normal edges
                    if (neighbor == selected) {
                        g.setColor(Color.pink); // in-degree
                    } else if (vertex == selected) {
                        g.setColor(Color.green); // out-degree
                    } else g.setColor(Color.black);
                    // After getting a COPY of the center of each vertex
                    // Yes, we don't want to mess up the original points
                    Point from = positions.get(vertex).getLocation();
                    Point to = positions.get(neighbor).getLocation();
                    // Let's first shrink them to the edge of vertices
                    int dx = to.x - from.x, dy = to.y - from.y;
                    Point radii = new Point(0, vertexRadius);
                    // Calculate the transform for each end
                    double fromAngle = Math.atan2(dy, dx);
                    double toAngle = Math.atan2(-dy, -dx);
                    Point fromTranslation = rotatedBy(fromAngle, radii);
                    Point toTranslation = rotatedBy(toAngle, radii);
                    // And apply them. However,
                    // FIXME: Why am I adding dy to x component?
                    from.translate(fromTranslation.y, fromTranslation.x);
                    to.translate(toTranslation.y, toTranslation.x);
                    // Now we can draw the arrow
                    g.drawLine(from.x, from.y, to.x, to.y);
                    drawArrowHeadAroundPoint(to, dx / 10, dy / 10, g);
                }
            }
        }

        private Rectangle getBoundingBoxForVertex(VertexType vertex) {
            Point position = positions.get(vertex);
            if (null == position) return INVALID_BOX;
            return new Rectangle(
                    position.x - vertexRadius,
                    position.y - vertexRadius,
                    vertexRadius * 2,
                    vertexRadius * 2
            );
        }

        private void drawVertices(Graphics g) {
            positions.forEach((vertex, position) -> {
                if (vertex == selected) {
                    g.setColor(Color.blue);
                } else g.setColor(Color.black);
                Rectangle bounds = getBoundingBoxForVertex(vertex);
                g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
                FontMetrics metrics = g.getFontMetrics();
                String name = vertex.getName();
                Rectangle2D rect = metrics.getStringBounds(name, g);
                g.drawString(name,
                        position.x - (int) rect.getWidth() / 2,
                        position.y + (int) rect.getHeight() / 2
                );
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            calculatePositions();
            if (g instanceof Graphics2D)
                ((Graphics2D) g).setStroke(new BasicStroke(
                        vertexRadius / 12,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                ));
            g.setFont(g.getFont().deriveFont(vertexRadius / 3f));
            drawEdges(g);
            drawVertices(g);
        }
    }
}
