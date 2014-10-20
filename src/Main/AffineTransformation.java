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
import java.util.ArrayList;

/**
 * @author Aleksandr Šmailov
 */
public class AffineTransformation {
    private static float a, b, c, d, e, f;
    
    public AffineTransformation(int a, int b, int c,
                                int d, int e, int f){
        init();
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }
    public AffineTransformation(float a, float b, float c,
                                float d, float e, float f){
        init();
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }
    
    private void init(){
        a = 0f;
        b = 0f;
        c = 0f;
        d = 0f;
        e = 0f;
        f = 0f;
    }
    
    /**
     * Transforms triangle using AffineTransformation.
     * @param t Triangle
     */
    public static void transform(Triangle t){
        int x, y;
        Point p;
        for(int i = 0; i < 3; i++){
            p = t.getPoint(i);
            x = (int)(p.x * a + p.y * b + e);
            y = (int)(p.x * c + p.y * d + f);
            t.setPoint(i, x, y);
        }
    }
    
    /**
     * Returns ArrayList of triangles which represent every step of
     * transformation.
     * @param t Triangle.
     * @param frames how many steps is required.
     * @return ArrayList of Triangle.
     */
    public ArrayList<Triangle> getTransformationSteps(Triangle t, int frames){
        ArrayList<Triangle> triangles = new ArrayList();
        if(t != null){
            Triangle oldt = t.getCopy();
            transform(t);
            Point[] p1;
            Point[] p2;
            Point[] p = new Point[t.getPoints().length];
            int x,y;
            for (int i = 1; i <= frames; i++){
                p1 = oldt.getPoints();
                p2 = t.getPoints();
                for (int j = 0; j < p1.length; j++){
                    x = p1[j].x + 
                        Math.round((p2[j].x - p1[j].x) / (float)frames * i);
                    y = p1[j].y + 
                        Math.round((p2[j].y - p1[j].y) / (float)frames * i);
                    p[j] = new Point(x,y);
                }
                triangles.add(new Triangle(p[0],p[1],p[2]));
            }
        }
        return triangles;
    }
}
