
package com.pldp.platernia;

public class Rectangle {
    public long x, y, width, height;
    public Rectangle(){}
    public Rectangle(long x, long y, long width, long height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public boolean inside(long px, long py) {
        return x <= px && x + width > px
                && y <= py && y + height > py;
    }
    
    public boolean collides(Rectangle r) {
        return collides(x, width, r.x, r.width) && collides(y, height, r.y, r.height);
    }
    
    private static boolean collides(long p1, long sz1, long p2, long sz2) {
        if(p1 > p2) {
            return collides(p2, sz2, p1, sz1);
        }
        if(p1 + sz1 < p2) {
            return false;
        }
        return true;
    }
}
