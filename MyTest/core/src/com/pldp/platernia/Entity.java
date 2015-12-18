package com.pldp.platernia;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private Options options = new Options();
    private List<Effect> effects = new ArrayList<Effect>();
    private Rectangle rectangle = new Rectangle();
    private Physics physics = new Physics();
    private Universe u;

    public Entity(Universe u) {
        this.u = u;
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
        if (physics.vx != 0 || physics.vy != 0) {
            rectangle.x += physics.vx;
            rectangle.y += physics.vy;
        }
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
}
