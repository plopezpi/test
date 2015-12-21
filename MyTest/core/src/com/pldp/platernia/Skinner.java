package com.pldp.platernia;

import com.pldp.util.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Skinner {

    public static class Sprite {

        public Point pos;
        public float u;
        public float v;
        public float u2;
        public float v2;
    }

    private static void add(List<Sprite> ret, long x, long y, long idx) {
        final float dx = 1.f / 12;
        final float dy = 1.f / 10;
        Sprite s = new Sprite();
        long col = idx % 12;
        long row = idx / 12;
        s.pos = new Point(x, y);
        s.u = dx * col;
        s.v = dy * row + dy;
        s.u2 = s.u + dx;
        s.v2 = s.v - dy;
        ret.add(s);
    }

    public static List<Sprite> skinBg(Stage stg) {
        final List<Point> blocks = stg.getBlocks();
        final long offsetx = 2;
        final long offsety = 2;
        final long ntexx = 10L;
        final long ntexy = 6L;
        List<Sprite> ret = new ArrayList<Sprite>(blocks.size());
        for (Point p : blocks) {
            long col = offsetx + p.x % ntexx;
            long row = offsety + 5L - (p.y % ntexy);
            add(ret, p.x, p.y, col + row * 12);
        }
        return ret;
    }

    public static List<Sprite> skinFg(Stage stg) {
        final Set<Point> blocks = new HashSet<Point>(stg.getBlocks());
        final Point sz = stg.getSize();
        List<Sprite> ret = new ArrayList<Sprite>();
        for (Point p : blocks) {
            final long x = p.x;
            final long y = p.y;
            final boolean left = x > 0 && !blocks.contains(p(x - 1, y));
            final boolean right = x < sz.x - 1 && !blocks.contains(p(x + 1, y));
            final boolean bottom = y > 0 && !blocks.contains(p(x, y - 1));
            final boolean top = y < sz.y - 1 && !blocks.contains(p(x, y + 1));
            if (left) {
                add(ret, x - 1, y, (2 + (y % 6)) * 12 + 0);
                add(ret, x, y, (2 + (y % 6)) * 12 + 1);
            }
            if (right) {
                add(ret, x, y, (2 + (y % 6)) * 12);
                add(ret, x + 1, y, (2 + (y % 6)) * 12 + 1);
            }

            if (top) {
                add(ret, x, y + 1, 0 * 12 + 4 + x % 6);
                add(ret, x, y, 1 * 12 + 4 + x % 6);
                if (left) {
                    add(ret, x - 1, y + 1, 0 * 12 + 3);
                    add(ret, x - 1, y, 1 * 12 + 3);
                }
                if (right) {
                    add(ret, x + 1, y + 1, 0 * 12 + 10);
                    add(ret, x + 1, y, 1 * 12 + 10);
                }
            }
            if (bottom) {
                add(ret, x, y, 8 * 12 + 4 + x % 6);
                add(ret, x, y - 1, 9 * 12 + 4 + x % 6);
                if (left) {
                    add(ret, x - 1, y, 8 * 12 + 3);
                    add(ret, x - 1, y - 1, 9 * 12 + 3);
                }
                if (right) {
                    add(ret, x + 1, y, 8 * 12 + 10);
                    add(ret, x + 1, y - 1, 9 * 12 + 10);
                }
            }
        }
        return ret;
    }

    private static Point p(long x, long y) {
        return new Point(x, y);
    }
}
