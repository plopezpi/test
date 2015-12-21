package com.pldp.platernia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.pldp.util.Point;
import java.util.ArrayList;
import java.util.List;
public class Stage {
    private final List<Point> blocks = new ArrayList<Point>();
    private final List<Point> enemies = new ArrayList<Point>();
    private final List<Point> players = new ArrayList<Point>();
    private final Point size;
    
    // RGBA8888
    private static final int WHITE = 0xffffffff;
    private static final int BLACK = 0x000000ff;
    private static final int RED = 0xff0000ff;
    private static final int GREEN = 0x00ff00ff;
    //private static final int BLUE = 0x0000ffff;
    
    public Stage(String name) {
        FileHandle fh = Gdx.files.internal(name);
        Pixmap pm = new Pixmap(fh);
        size = new Point(pm.getWidth(), pm.getHeight());
        for (int x = 0; x < pm.getWidth(); ++x) {
            for (int y = 0; y < pm.getHeight(); ++y) {
                int c = pm.getPixel(x, pm.getHeight() - y - 1);
                switch (c) {
                    case BLACK:
                        blocks.add(new Point(x, y));
                        break;
                    case RED:
                        enemies.add(new Point(x, y));
                        break;
                    case GREEN:
                        players.add(new Point(x, y));
                        break;
                    case WHITE:
                        break;
                }
            }
        }
    }
        
    public Universe createUniverse(Options o) {
        Universe u = new Universe(o);
        for(Point p: blocks) u.addBlock(p.x, p.y);
        for(Point p: enemies) u.addEnemy(p.x, p.y);
        for(Point p: players) u.addPlayer(p.x, p.y);
        return u;
    }
    
    public List<Point> getBlocks() {
        return blocks;
    }

    public List<Point> getEnemies() {
        return enemies;
    }

    public List<Point> getPlayers() {
        return players;
    }

    public Point getSize() {
        return size;
    }
}
