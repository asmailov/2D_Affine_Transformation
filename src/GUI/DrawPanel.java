/*
 * The MIT License
 *
 * Copyright 2014 Aleksandr Šmailov.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package GUI;

import Main.AffineTransformation;
import Main.Triangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.io.PrintStream;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * @author Aleksandr Šmailov
 */
public class DrawPanel extends JPanel implements Runnable{
    
    private final static PrintStream out = System.out;
    private int x0, y0, width, height;
    private static Thread animator;
    
    /**
     * DrawPanel constructor.
     */
    public DrawPanel(){
        init();
    }
    /**
     * Initialize variables.
     */
    private void init(){
        this.width = this.getWidth();
        this.height = this.getHeight();
        this.x0 = this.getWidth()/8;
        this.y0 = this.getHeight() - this.getHeight()/8;
        if (animator == null) {
            animator = new Thread(this, "AffineTransformation animation");
            animator.start();
        }
    }
    
    /**
     * Method draws X and Y axes on the JPanel.
     * @param g Graphics2D
     */
    private void drawAxes(Graphics2D g){
        // Draw axes.
        g.drawLine(0, y0, width, y0);
        g.drawLine(x0, 0, x0, height);
        // Diameter of Axes centre.
        int diameter;
        // Should be set to odd number, otherwise it will position not in the
        // center.
        diameter = 5;
        Ellipse2D.Double circle;
        circle = new Ellipse2D.Double(x0-diameter/2, y0-diameter/2, 
                                      diameter, diameter);
        g.fill(circle);
    }
    /**
     * Draws triangle.
     * @param g Graphics2D
     * @param t Triangle
     */
    private void drawTriangle(Graphics2D g, Triangle t){
        // n is 3 because triangle has 3 vertices.
        int n = 3;
        
        int x[] = t.getXArray();
        int y[] = t.getYArray();
        int i;
        // Coordinates are shifted because we have a different starting point.
        for(i = 0; i < x.length; i++){
            x[i] += x0;
        }
        for(i = 0; i < y.length; i++){
            y[i] = y0 - y[i];
        }
        Polygon p = new Polygon(x, y, n);
        g.fillPolygon(p);
    }
    /**
     * Execute drawing.
     * @param g Graphics
     */
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        // Enable antialias
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        this.width = this.getWidth();
        this.height = this.getHeight();
        this.x0 = this.getWidth()/8;
        this.y0 = this.getHeight() - this.getHeight()/8;
        // Draw axes
        drawAxes(g2d);
        Triangle t = new Triangle(10,0,100,20,50,100);
        Triangle tmp = new Triangle(10,0,100,20,50,100);
        out.println(t.toString());
        AffineTransformation f = new AffineTransformation(3f, 1f, 1f, 3f, 0f, 0f);
        f.transform(t);
        out.println(t.toString());
        out.println("________________");
        drawTriangle(g2d,t);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void run() {
        while(true){
            try {
                animator.sleep(2000);
            } catch (InterruptedException ex) {
                out.println(ex);
            }
        }
    }
}
