package com.pldp.platernia;

import com.pldp.util.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Universe {
    private final List<Entity> entities = new ArrayList<Entity>();
    private final Map<String, Entity> entitiesByName = new HashMap<String, Entity>();
    private final Options o;
    public Universe(Options o) {
        this.o = o;
    }
    
    public void tick() {
        for(Entity e: entities) {
            e.tick();
        }
    }
    
    private void addEntity(Entity e) {
        entities.add(e);
        entitiesByName.put(e.getName(), e);
    }

    public List<Entity> getEntities() {
        return entities;
    }
    
    public Entity getEntity(String name) {
        return entitiesByName.get(name);
    }
    
    public void addBlock(long px, long py) {
        Entity b = new Entity(this, String.format("Block%d,%d", px, py));
        setRect(b, px, py);
        b.setCollidable(true);
        addEntity(b);
    }

    public void addEnemy(long px, long py) {
        Entity e = new Entity(this, String.format("Enemy%d,%d", px, py));
        setRect(e, px, py);
        addEntity(e);
    }

    private void setRect(Entity e, long px, long py) {
        e.setRectangle(new Rectangle((px * o.scale) * o.blockLen, (py * o.scale) * o.blockLen, o.blockLen * o.scale, o.blockLen * o.scale));
    }

    private final Effect gravity = new Effect() {
        @Override
        public boolean tick(Entity e, Options o) {
            final Physics p = e.getPhysics();
            p.vy -= 100L;
            return true;
        }
    };
    private final Effect friction = new Effect() {
        @Override
        public boolean tick(Entity e, Options o) {
            final Physics p = e.getPhysics();
            if (p.vx != 0) {
                p.vx *= 0.90;
            }
            if (p.vy != 0) {
                p.vy *= 0.90;
            }
            return true;
        }
    };

    public void addPlayer(long px, long py) {
        Entity e = new Entity(this, "Player");
        setRect(e, px, py);
        addEntity(e);
        e.getEffects().add(friction);
        e.getEffects().add(gravity);
    }
}
