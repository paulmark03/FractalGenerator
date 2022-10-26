package Fractals;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * We use this class to draw the panel that contains the 
 * tree fractal
 */
/**
 * NAME: Paul Nicolae Marcu
 * ID: 1844989
 */
public class PythagorasTree extends JPanel {
 
    private int thickness = 150;
    public static int angle = 45;
    public static int iterations = 10;
    public static int zoom = 100;

    public static Color backgroundColor = new Color(0, 0, 0);
    public static Color squareColor = new Color(255, 255, 255);
    public static Color triangleColor = new Color(255, 0, 0);
    public static Color lineColor = new Color(0, 255, 255);

    Point2D.Double offset = new Point2D.Double(500, 650);

    public PythagorasTree() {
        MouseAdapter ma = new MouseAdapter() {

            private Point startPoint;
    
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                startPoint.x -= offset.x;
                startPoint.y -= offset.y;
            }
    
            @Override
            public void mouseReleased(MouseEvent e) {
                startPoint = null;
            }
    
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getPoint();
                int x = p.x - startPoint.x;
                int y = p.y - startPoint.y;
                offset = new Point2D.Double(x, y);
                repaint();
            }
 
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
        


    }


    /**   
     *     E
     *    /\
     *   /  \
     *D /____\ C
     * |      |
     * |      |
     * |______|
     * A      B
     * @param <Graphics2D>
     * 
     * @param g
     * @param x1
     * @param y1
     * @param angle
     * @param depth
     */

    private void drawTree(Graphics2D g, Point2D.Double pointA, Point2D.Double pointB, int angle, int iterations) {
        
        
        setBackground(backgroundColor);

        Point2D.Double pointC = new Point2D.Double();
        Point2D.Double pointD = new Point2D.Double();
        Point2D.Double pointE = new Point2D.Double();

        if (iterations == 0) {
            return;
        }

        double dx = pointB.x - pointA.x;
        double dy = pointA.y - pointB.y;
        pointC.x = pointB.x - dy;
        pointC.y = pointB.y - dx;
        pointD.x = pointA.x - dy;
        pointD.y = pointA.y - dx;

        double d = Math.sqrt(
            Math.pow((pointD.x - pointC.x), 2) + Math.pow((pointD.y - pointC.y), 2)
        );

        double r0 = d * Math.cos(Math.toRadians(angle));
        double r1 = d * Math.sin(Math.toRadians(angle));
        double h = r0 * Math.sin(Math.toRadians(angle));

        double a = (Math.pow(r0, 2) - Math.pow(r1, 2) + Math.pow(d, 2)) / (2*d);


        double middleX = pointD.x + (a/d) * (pointC.x - pointD.x);
        double middleY = pointD.y + (a/d) * (pointC.y - pointD.y);

        pointE.x =  (middleX +  (h/d) * (pointC.y - pointD.y));
        pointE.y =  (middleY -  (h/d) * (pointC.x - pointD.x));
   


        g.setColor(triangleColor);
        Path2D triangle = new Path2D.Float();
        triangle.moveTo(pointC.x, pointC.y);
        triangle.lineTo(pointD.x, pointD.y);
        triangle.lineTo(pointE.x, pointE.y);
        triangle.closePath();
        g.fill(triangle);
        g.draw(triangle);

        g.setColor(squareColor);
        Path2D square = new Path2D.Float();
        square.moveTo(pointA.x, pointA.y);
        square.lineTo(pointB.x, pointB.y);
        square.lineTo(pointC.x, pointC.y);
        square.lineTo(pointD.x, pointD.y);
        square.closePath();
        g.fill(square);
        g.setColor(lineColor);
        g.draw(square);
    
        drawTree(g, pointD, pointE, angle, iterations - 1);
        drawTree(g, pointE, pointC, angle, iterations - 1);


  
  

        repaint();


    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        Point2D.Double point2 = new Point2D.Double(offset.x + thickness, offset.y);


        drawTree((Graphics2D) g, offset, point2 ,angle,  iterations);
    }

}