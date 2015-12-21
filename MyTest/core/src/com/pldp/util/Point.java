package com.pldp.util;

public class Point {

    public final long x, y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object p2) {
        return p2 != null && p2 instanceof Point && ((Point) p2).x == x && ((Point) p2).y == y;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.x ^ (this.x >>> 32));
        hash = 79 * hash + (int) (this.y ^ (this.y >>> 32));
        return hash;
    }
}
