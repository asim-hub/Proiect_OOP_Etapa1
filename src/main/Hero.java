package main;

public abstract class Hero {
    private Integer hp;
    private Integer xp;
    private Integer coordX;
    private Integer coordY;
    private Integer level;
    private Integer nrRoundOvertime;
    private Integer demageOvertime;
    private Integer die;
    private Integer stay;
    private String type;

    public Hero() {
    }

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final Integer getStay() {
        return stay;
    }

    public final void setStay(final Integer stay) {
        this.stay = stay;
    }

    public final Integer getDie() {
        return die;
    }

    public final void setDie(final Integer die) {
        this.die = die;
    }

    public final Integer getNrRoundOvertime() {
        return nrRoundOvertime;
    }

    public final void setNrRoundOvertime(final Integer nrRoundOvertime) {
        this.nrRoundOvertime = nrRoundOvertime;
    }

    public final Integer getDemageOvertime() {
        return demageOvertime;
    }

    public final void setDemageOvertime(final Integer demageOvertime) {
        this.demageOvertime = demageOvertime;
    }

    public final Integer getLevel() {
        return level;
    }

    public final void setLevel(final Integer level) {
        this.level = level;
    }

    public final Integer getCoordX() {
        return coordX;
    }

    public final void setCoordX(final Integer coordX) {
        this.coordX = coordX;
    }

    public final Integer getCoordY() {
        return coordY;
    }

    public final void setCoordY(final Integer coordY) {
        this.coordY = coordY;
    }

    public final Integer gethp() {
        return hp;
    }

    public final void sethp(final Integer hP) {
        hp = hP;
    }

    public final Integer getxp() {
        return xp;
    }

    public final void setxp(final Integer xP) {
        xp = xP;
    }

    public final void life() {
        if (this.gethp() <= 0) {
            this.setDie(1);
        }
    }

    public abstract void fightwith(Hero hero);

    public abstract void salute(Knight knight);

    public abstract void salute(Pyromancer pyromancer);

    public abstract void salute(Rogue rogue);

    public abstract void salute(Wizard wizard);


}
