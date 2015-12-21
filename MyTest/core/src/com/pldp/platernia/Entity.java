package com.pldp.platernia;

import com.pldp.util.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    private String name;
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
            e.tick(this);
        }
        updatePos();
        return true;
    }

    private void updatePos() {
        physics.tick();
        if (physics.vx != 0) {
            long dx = physics.vx;
            Rectangle nr = new Rectangle(rectangle);
            nr.x += dx;
            for(Entity e: u.getEntities()) {
                if(!e.collidable) continue;
                if(nr.collides(e.getRectangle())) {
                    //say("collides X with " + e.getName());
                    dx = rectangle.getLeewayX(e.getRectangle());
                    nr.x = rectangle.x + dx;
                    physics.vx = 0;
                }
            }
            rectangle.x += dx;
        }
        if (physics.vy != 0) {
            long dy = physics.vy;
            Rectangle nr = new Rectangle(rectangle);
            nr.y += dy;
            for(Entity e: u.getEntities()) {
                if(!e.collidable) continue;
                if(nr.collides(e.getRectangle())) {
                    //say("collides Y with " + e.getName());
                    dy = rectangle.getLeewayY(e.getRectangle());
                    nr.y = rectangle.y + dy;
                    physics.vy = 0;
                }
            }
            rectangle.y += dy;
        }
    }
    
    private void say(String str) {
        System.out.println(String.format("[%.2f][%s] %s", Math.random(), name, str));
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
