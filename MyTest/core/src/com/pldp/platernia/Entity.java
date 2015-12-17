package com.pldp.platernia;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private Options options = new Options();
    private List<Effect> effects = new ArrayList<Effect>();
    private Rectangle rectangle = new Rectangle();

    public boolean tick() {
        for (Effect e : effects) {
            e.tick(this, options);
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
}
