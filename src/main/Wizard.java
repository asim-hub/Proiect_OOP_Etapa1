package main;
import common.Constants;
import matrix.map;
public class Wizard extends Hero{

	public Wizard() {
		this.sethp(Constants.WIZARD);
		this.setxp(Constants.ZERO);
        this.setLevel(Constants.ZERO);
        this.setDemageOvertime(Constants.ZERO);
        this.setNrRoundOvertime(Constants.ZERO);
        this.setDie(Constants.ZERO);
        this.setStay(Constants.ZERO);
	}
	@Override
    public void fightwith(Hero hero) {
        hero.salute(this);
    }

    @Override
    public void salute(Knight knight) {
        System.out.println("9");
        map instance = map.getInstance();
        /*K.W*/
        //aplic lui knight overtimedamage daca are
        if(knight.getNrRoundOvertime()>0) {
            knight.sethp(knight.gethp() - knight.getDemageOvertime());
            knight.setNrRoundOvertime(knight.getNrRoundOvertime() - 1);
            knight.life();
        }

        //knight ataca pe wizard
        //knight ataca
        float HPLIMIT1;
        float damageExecuteBase1;
        // verific daca clasa/wizard moare din prima
        if (Constants.ZEROZEROONE * knight.getLevel() < Constants.ZEROTWO) {
            HPLIMIT1 = (float) ((Constants.ZEROTWO +
                    Constants.ZEROZEROONE * knight.getLevel()) * (Constants.WIZARD + this.getLevel() * Constants.THIRTIETH));
        } else {
            HPLIMIT1 = (float) ((2 * Constants.ZEROTWO) * (Constants.WIZARD + this.getLevel() * Constants.THIRTIETH));
        }
        if (HPLIMIT1 >= this.gethp()) {
            this.setDie(Constants.ONE);
            this.sethp(Constants.ZERO);
            /*
            this.life();
            */
        } else {
            //daca nu moare din prima fac abilitatile
            //knight-execute
            damageExecuteBase1 = (Constants.TWOH + Constants.THIRTIETH * knight.getLevel()) * Constants.KW;
            if (instance.getArray(knight.getCoord_x(), knight.getCoord_y()) == Constants.LAND) {
                damageExecuteBase1 = Constants.MODIFICATORL * damageExecuteBase1;
            }
            damageExecuteBase1 = Math.round(damageExecuteBase1);

            //knight-slam
            float damageSlamBase1 = (Constants.ONEH + Constants.FORTY * knight.getLevel()) * Constants.MODIFICATORW;
            if (instance.getArray(knight.getCoord_x(), knight.getCoord_y()) == Constants.LAND) {
                damageSlamBase1 = Constants.MODIFICATORL * damageSlamBase1;
            }
            damageSlamBase1 = Math.round(damageSlamBase1);

            float damage1 = damageSlamBase1 + damageExecuteBase1;
            this.sethp(this.gethp() - (int) damage1);
            //incapacitatea de miscare-> sterg celelate overtime
            this.setStay(Constants.ONE);
            this.setNrRoundOvertime(Constants.ZERO);
            this.setDemageOvertime(Constants.ZERO);
        }


    }

    @Override
    public void salute(Pyromancer pyromancer) {
        System.out.println("10");
        /*P.W*/
        //aplic lui pyromancer overtimedamage
        if(pyromancer.getNrRoundOvertime()>0) {
            pyromancer.sethp(pyromancer.gethp() - pyromancer.getDemageOvertime());
            pyromancer.setNrRoundOvertime(pyromancer.getNrRoundOvertime() - 1);
        }
        //Pyromancer-Fireblast
        map instance = map.getInstance();
        float damageFireblastBase = Constants.MODIFICATORW*(Constants.DAMAGEFB + pyromancer.getLevel()*Constants.LEVELFB);
        if(instance.getArray(pyromancer.getCoord_x(),pyromancer.getCoord_y()) == Constants.VOLCANIC){
            damageFireblastBase = Constants.MODIFICATORV*damageFireblastBase;
        }
        damageFireblastBase = Math.round(damageFireblastBase);
        //Pyromancer-Ignite
        float damageIgniteBase = Constants.MODIFICATORW*(Constants.DAMAGEI + pyromancer.getLevel()*Constants.LEVELI);
        if(instance.getArray(pyromancer.getCoord_x(),pyromancer.getCoord_y()) == Constants.VOLCANIC){
            damageIgniteBase = Constants.MODIFICATORV*damageIgniteBase;
        }
        damageIgniteBase = Math.round(damageIgniteBase);
        float damage;
        damage = damageFireblastBase + damageIgniteBase;
        this.sethp((int) (this.gethp()-damage));
        float damageOvertime=Constants.ZERO;
        damageOvertime = Constants.MODIFICATORW*(Constants.LEVELFB + Constants.THIRTIETH*pyromancer.getLevel());
        if(instance.getArray(pyromancer.getCoord_x(),pyromancer.getCoord_y()) == Constants.VOLCANIC){
            damageOvertime = Constants.MODIFICATORV*damageOvertime;
        }
        damageOvertime=Math.round(damageOvertime);
        //damageovertime + anulez paralizia
        this.setDemageOvertime((int) damageOvertime);
        this.setNrRoundOvertime(Constants.TWO);
        this.setStay(Constants.ZERO);
    }

    @Override
    public void salute(Rogue rogue) {
        System.out.println("11");
        /*R.W*/
    }
    
    @Override
    public void salute(Wizard wizard) {
        System.out.println("12");
        /*W.W*/
    }
}
