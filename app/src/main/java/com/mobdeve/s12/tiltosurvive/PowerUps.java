package com.mobdeve.s12.tiltosurvive;

import java.util.ArrayList;

public class PowerUps {
    private ArrayList<String> name;
    private ArrayList<Integer> owned;

    public PowerUps() {
        this.name = new ArrayList<String>();
        this.owned = new ArrayList<Integer>();
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<Integer> getOwned() {
        return owned;
    }

    public void setOwned(ArrayList<Integer> owned) {
        this.owned = owned;
    }
}
