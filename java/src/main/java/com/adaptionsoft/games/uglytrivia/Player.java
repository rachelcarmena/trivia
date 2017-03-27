package com.adaptionsoft.games.uglytrivia;

public class Player {

    private int place;
    private int purse;
    private boolean inPenaltyBox;
    private final String name;

    public Player(String name) {
        this.name = name;
        this.place = 0;
        this.purse = 0;
        this.inPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPurse() {
        return purse;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }

    public boolean inPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox() {
        this.inPenaltyBox = true;
    }
}
