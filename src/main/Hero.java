package main;

public abstract class Hero {
	private Integer hp;
	private Integer xp;
	private Integer coord_x;
	private Integer coord_y;
	private Integer level;
	private Integer nrRoundOvertime;
	private Integer demageOvertime;
    private Integer die;
    private Integer stay;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getStay() {
        return stay;
    }

    public void setStay(Integer stay) {
        this.stay = stay;
    }

    public Integer getDie() {
        return die;
    }

    public void setDie(Integer die) {
        this.die = die;
    }

    public void setDemageOvertime(Integer demageOvertime) {
        this.demageOvertime = demageOvertime;
    }

    public void setNrRoundOvertime(Integer nrRoundOvertime) {
        this.nrRoundOvertime = nrRoundOvertime;
    }

    public Integer getNrRoundOvertime() {
        return nrRoundOvertime;
    }

    public Integer getDemageOvertime() {
        return demageOvertime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Hero(){}

	public void setCoord_y(Integer coord_y) {
		this.coord_y = coord_y;
	}

	public void setCoord_x(Integer coord_x) {
		this.coord_x = coord_x;
	}

	public Integer getCoord_x() {
		return coord_x;
	}

	public Integer getCoord_y() {
		return coord_y;
	}

	public Integer gethp() {
		return hp;
	}
	public void sethp(Integer hP) {
		hp = hP;
	}
	public Integer getxp() {
		return xp;
	}
	public void setxp(Integer xP) {
		xp = xP;
	}
	public void life(){
        if(this.gethp()<=0){
            this.setDie(1);
        }
    }
    public abstract void fightwith(Hero hero);
    public abstract void salute(Knight knight);
    public abstract void salute(Pyromancer pyromancer);
    public abstract void salute(Rogue rogue);
    public abstract void salute(Wizard wizard);

	
}
