package com.pldp.platernia;

public class Physics {
    public long vx;
    public long vy;
    public long ax;
    public long ay;
    
    public void tick() {
        vx += ax;
        vy += ay;
    }
}
