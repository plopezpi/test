
package com.pldp.util;

public class Rectangle {
    public enum Pos {
        TOPLEFT,
        TOP,
        TOPRIGHT,
        LEFT,
        CENTER,
        RIGHT,
        BOTTOMLEFT,
        BOTTOM,
        BOTTOMRIGHT
    }
    public long x, y, width, height;
    public Rectangle(){}
    public Rectangle(Rectangle o){
        x = o.x;
        y = o.y;
        width = o.width;
        height = o.height;
    }
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
        return p1 + sz1 > p2;
    }
    
    public Point getPos(Pos p) {
        long rx = 0, ry = 0;
        switch(p) {
            case TOPLEFT:
            case TOP:
            case TOPRIGHT:
                ry = y + height;
                break;
            case LEFT:
            case CENTER:
            case RIGHT:
                ry = y + height / 2;
                break;
            case BOTTOMLEFT:
            case BOTTOM:
            case BOTTOMRIGHT:
                ry = y;
                break;
        }
        
        switch(p) {
            case TOPLEFT:
            case LEFT:
            case BOTTOMLEFT:
                rx = x;
                break;
            case TOP:
            case CENTER:
            case BOTTOM:
                rx = x + width / 2;
                break;
            case TOPRIGHT:
            case RIGHT:
            case BOTTOMRIGHT:
                rx = x + width;
                break;
        }
        return new Point(rx, ry);
    } 
    
    public long getLeewayX(Rectangle d) {
        if(d.x > x + width) {
            return d.x - (x + width);
        }
        else if(x > d.x + d.width) {
            return (d.x + d.width) - x;
        } else {
            return 0;
        }
    }
    
    public long getLeewayY(Rectangle d) {
        if(d.y > y + height) {
            return d.y - (y + height);
        }
        else if(y > d.y + d.height) {
            return (d.y + d.height) - y;
        } else {
            return 0;
        }
    }
}
