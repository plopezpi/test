package com.pldp.platernia;

import com.pldp.util.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    private String name;
    private Options options = new Options();
    private List<Effect> effects = new ArrayList<Effect>();
    private Rectangle rectangle = new Rectangle();
    private Physics physics = new Physics();
    private Universe u;
    private boolean collidable;

    public Entity(Universe u, String name) {
        this.u = u;
        this.name = name;
    }

    public boolean tick() {
        for (Effect e : effects) {
            e.tick(this, options);
        }
        updatePos();
        return true;
    }

    public void updatePos() {
        physics.tick();
        if (physics.vx != 0) {
            long dx = physics.vx;
            Rectangle nr = new Rectangle(rectangle);
            nr.x += dx;
            Entity cx = null;
            for(Entity e: u.getEntities()) {
                if(!e.collidable) continue;
                if(nr.collides(e.getRectangle())) {
                    //say("collides X with " + e.getName());
                    dx = rectangle.getLeewayX(e.getRectangle());
                    nr.x = rectangle.x + dx;
                    physics.vx = 0;
                    cx = e;
                }
            }
            if(cx != null) {
                onCollide(cx, dx, 0);
            }
            rectangle.x += dx;
        }
        if (physics.vy != 0) {
            long dy = physics.vy;
            Rectangle nr = new Rectangle(rectangle);
            nr.y += dy;
            Entity cx = null;
            for(Entity e: u.getEntities()) {
                if(!e.collidable) continue;
                if(nr.collides(e.getRectangle())) {
                    //say("collides Y with " + e.getName());
                    dy = rectangle.getLeewayY(e.getRectangle());
                    nr.y = rectangle.y + dy;
                    physics.vy = 0;
                    cx = e;
                }
            }
            if(cx != null) {
                onCollide(cx, 0, dy);
            }
            rectangle.y += dy;
        }
    }
    
    public void onCollide(Entity e, long dx, long dy) {
        
    }
    
    public void say(String str) {
        System.out.println(String.format("[%.2f][%s] %s", Math.random(), name, str));
    }

    public boolean canPlace(Rectangle r) {
        for (Entity e : u.getEntities()) {
            if (e == this) {
                continue;
            }
            if (r.collides(e.getRectangle())) {
                return false;
            }
        }
        return true;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Physics getPhysics() {
        return physics;
    }

    public void setPhysics(Physics physics) {
        this.physics = physics;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
