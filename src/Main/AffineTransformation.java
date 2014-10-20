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
public class AffineTransformation {
    private float a, b, c, d, e, f;
    
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
    public void transform(Triangle t){
        int x, y;
        Point p;
        for(int i = 0; i < 3; i++){
            p = t.getPoint(i);
            x = (int)(p.x * a + p.y * b + e);
            y = (int)(p.x * c + p.y * d + f);
            t.setPoint(i, x, y);
        }
    }
}