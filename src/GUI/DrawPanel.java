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
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * @author Aleksandr Šmailov
 */
public class DrawPanel extends JPanel implements Runnable{
    
    private int width, height;
    private int x0,y0;
    private Triangle triangle;
    private Triangle transfTriangle;
    private AffineTransformation transf;
    private ArrayList<Triangle> steps;
    private int xDrag, yDrag;
    
    private static Thread animator;
    private int stepCount;
    private int stepAmount;
    private int speed;
    private boolean animation;
    private boolean transformation;
    private boolean drawTriangle;
    private int start;
    
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
        triangle = new Triangle();
        transfTriangle = new Triangle();
        transf = new AffineTransformation();
        animation = false;
        stepCount = 0;
        start = 0;
        // Creating and starting new Thread so we can do animation.
        if (animator == null) {
            animator = new Thread(this, "AffineTransformation animation");
            animator.start();
        }
    }
    
    /**
     * Resets step count.
     */
    public void resetStepCount(){
        this.stepCount = 0;
    }
    
    /**
     * Make panel draw transformed triangle.
     */
    public void enableTransformationDrawing(){
        this.transformation = true;
        this.animation = false;
        this.drawTriangle = false;
    }
    
    /**
     * Make panel draw animation.
     */
    public void enableAnimationDrawing(){
        this.animation = true;
        this.transformation = false;
        this.drawTriangle = false;
    }
    
    /**
     * Make panel draw starting triangle.
     */
    public void enableTriangleDrawing(){
        this.drawTriangle = true;
        this.animation = false;
        this.transformation = false;
    }
    
    /**
     * Disable drawing.
     */
    public void disableAnimation(){
        this.animation = false;
    }
    
    /**
     * Method draws X and Y axes on the JPanel.
     * @param g Graphics2D
     */
    private void drawAxes(Graphics2D g){
        this.width = this.getWidth();
        this.height = this.getHeight();
        // Draw axes.
        // X axis.
        g.drawLine(0, y0, width, y0);
        // Y axis.
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
        // Draw axes.
        drawAxes(g2d);
        // Draw triangle.
        if(drawTriangle || animation){
            drawTriangle(g2d,triangle);
        } else if(transformation){
            drawTriangle(g2d,transfTriangle);
        }
    }
    
    /**
     * Calculates starting point of X and Y axes.
     */
    public void calcStartingPoint(){
        if (start == 0){
            this.width = this.getWidth();
            this.height = this.getHeight();
            this.x0 = this.getWidth()/8;
            this.y0 = this.getHeight() - this.getHeight()/8;
        }
        if (start <= 1){
            start++;
        }
    }
    
    /**
     * Overriding this so we can draw our own Graphic.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Calculating correct starting point.
        calcStartingPoint();
        // Do drawing.
        doDrawing(g);
    }
    
    /**
     * Run method.
     */
    @Override
    public void run() {
        while(true){
            repaint();
            try {
                if(animation){
                    if(stepCount < stepAmount){
                        triangle = steps.get(stepCount);
                        stepCount++;
                    } else {
                        stepCount = 0;
                    }
                    Thread.sleep(10 + 500 - speed*50);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
    
    // Setters and getters.
    
    /**
     * @return initial triangle which is added by the user.
     */
    public Triangle getTriangle() {
        return triangle;
    }

    /**
     * @param triangle initial triangle.
     */
    public void setTriangle(Triangle triangle) {
        this.triangle = triangle;
    }

    /**
     * @return transformed triangle.
     */
    public Triangle getTransfTriangle() {
        return transfTriangle;
    }

    /**
     * @param transfTriangle transformed triangle.
     */
    public void setTransfTriangle(Triangle transfTriangle) {
        this.transfTriangle = transfTriangle;
    }

    /**
     * @return affine transformation.
     */
    public AffineTransformation getTransf() {
        return transf;
    }

    /**
     * @param transf affine transformation.
     */
    public void setTransf(AffineTransformation transf) {
        this.transf = transf;
    }

    /**
     * @return x0 coordinate on JPanel representing origin point of X and Y
     * intersection.
     */
    public int getX0() {
        return x0;
    }

    /**
     * @param x0 x0 coordinate on JPanel representing origin point of X and Y
     * intersection.
     */
    public void setX0(int x0) {
        this.x0 = x0;
    }

    /**
     * @return y0 coordinate on JPanel representing origin point of X and Y
     * intersection.
     */
    public int getY0() {
        return y0;
    }

    /**
     * @param y0 y0 coordinate on JPanel representing origin point of X and Y
     * intersection.
     */
    public void setY0(int y0) {
        this.y0 = y0;
    }

    /**
     * @return array of triangles which represent each step of transformation.
     */
    public ArrayList<Triangle> getSteps() {
        return steps;
    }

    /**
     * @param steps set the array of triangles which represent each step of
     * transformation.
     */
    public void setSteps(ArrayList<Triangle> steps) {
        this.steps = steps;
    }

    /**
     * @return amount of steps which are required to animate a transformation.
     */
    public int getStepAmount() {
        return stepAmount;
    }

    /**
     * @param stepAmount amount of steps which are required to animate a 
     * transformation.
     */
    public void setStepAmount(int stepAmount) {
        this.stepAmount = stepAmount;
    }

    /**
     * @return speed of animation.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed speed of animation.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return x coordinate of previous mouse location(one tick from current).
     */
    public int getxDrag() {
        return xDrag;
    }

    /**
     * @param xDiff x coordinate of previous mouse location(one tick 
     * from current).
     */
    public void setxDiff(int xDiff) {
        this.xDrag = xDiff;
    }

    /**
     * @return y coordinate of previous mouse location(one tick from current).
     */
    public int getyDrag() {
        return yDrag;
    }

    /**
     * @param yDiff y coordinate of previous mouse location(one tick 
     * from current).
     */
    public void setyDiff(int yDiff) {
        this.yDrag = yDiff;
    }
}
