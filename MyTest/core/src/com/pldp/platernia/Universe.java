package com.pldp.platernia;

import java.util.ArrayList;
import java.util.List;

public class Universe {
    private final List<Entity> entities = new ArrayList<Entity>();
    
    public void tick() {
        for(Entity e: entities) {
            e.tick();
        }
    }
    
    public void addEntity(Entity e) {
        entities.add(e);
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
