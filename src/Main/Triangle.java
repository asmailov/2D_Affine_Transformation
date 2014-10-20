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

package Main;

import java.awt.Point;

/**
 * @author Aleksandr Šmailov
 */
public class Triangle {
    private Point[] points;
    
    public Triangle(){
        init();
    }
    
    private void init(){
        points = new Point[3];
    }
    
    /**
     * Triangle constructor which sets locations to the points.
     * @param x1 1 point x coordinate
     * @param y1 1 point y coordinate
     * @param x2 2 point x coordinate
     * @param y2 2 point y coordinate
     * @param x3 3 point x coordinate
     * @param y3 3 point y coordinate
     */
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3){
        points[0].setLocation(x1, y1);
        points[1].setLocation(x2, y2);
        points[2].setLocation(x3, y3);
    }
    
    /**
     * Returns requested point.
     * @param i number of the point.
     * @return Point
     */
    public Point getPoint(int i){
        Point point = null;
        if(i < points.length && i >= 0){
            point = points[i];
        }
        return point;
    }
}
