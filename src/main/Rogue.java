package main;
import common.Constants;
import matrix.map;

public class Rogue extends Hero{
    private Integer hit;

    public void setHit(Integer hit) {
        this.hit = hit;
    }

    public Integer getHit() {
        return hit;
    }

    public Rogue() {
		this.sethp(Constants.ROGUE);
		this.setxp(Constants.ZERO);
        this.setLevel(Constants.ZERO);
        this.setDemageOvertime(Constants.ZERO);
        this.setNrRoundOvertime(Constants.ZERO);
        this.setDie(Constants.ZERO);
        this.setStay(Constants.ZERO);
        this.setHit(Constants.ZERO);
	}
	
	@Override
    public void fightwith(Hero hero) {
        hero.salute(this);
    }

    @Override
    public void salute(Knight knight) {
        System.out.println("13");
        map instance = map.getInstance();
        /*K.R*/

        //aplic lui knight overtimedamage daca are
        if(knight.getNrRoundOvertime()>0) {
            knight.sethp(knight.gethp() - knight.getDemageOvertime());
            knight.setNrRoundOvertime(knight.getNrRoundOvertime() - 1);
            knight.life();
        }

        //knight ataca pe rogue
        //knight ataca
        float HPLIMIT1;
        float damageExecuteBase1;
        // verific daca clasa/rogue moare din prima
        if (Constants.ZEROZEROONE * knight.getLevel() < Constants.ZEROTWO) {
            HPLIMIT1 = (float) ((Constants.ZEROTWO +
                    Constants.ZEROZEROONE * knight.getLevel()) * (Constants.ROGUE + this.getLevel() * Constants.FORTY));
        } else {
            HPLIMIT1 = (float) ((2 * Constants.ZEROTWO) * (Constants.ROGUE + this.getLevel() * Constants.FORTY));
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
            damageExecuteBase1 = (Constants.TWOH + Constants.THIRTIETH * knight.getLevel()) * Constants.KR;
            if (instance.getArray(knight.getCoord_x(), knight.getCoord_y()) == Constants.LAND) {
                damageExecuteBase1 = Constants.MODIFICATORL * damageExecuteBase1;
            }
            damageExecuteBase1 = Math.round(damageExecuteBase1);

            //knight-slam
            float damageSlamBase1 = (Constants.ONEH + Constants.FORTY * knight.getLevel()) * Constants.KW;
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
        System.out.println("14");
        /*P.R*/


        //aplic lui pyromancer overtimedamage
        if(pyromancer.getNrRoundOvertime()>0) {
            pyromancer.sethp(pyromancer.gethp() - pyromancer.getDemageOvertime());
            pyromancer.setNrRoundOvertime(pyromancer.getNrRoundOvertime() - 1);
        }
        //Pyromancer-Fireblast
        map instance = map.getInstance();
        float damageFireblastBase = Constants.MODIFICATORR*(Constants.DAMAGEFB + pyromancer.getLevel()*Constants.LEVELFB);
        if(instance.getArray(pyromancer.getCoord_x(),pyromancer.getCoord_y()) == Constants.VOLCANIC){
            damageFireblastBase = Constants.MODIFICATORV*damageFireblastBase;
        }
        damageFireblastBase = Math.round(damageFireblastBase);
        //Pyromancer-Ignite
        float damageIgniteBase = Constants.MODIFICATORR*(Constants.DAMAGEI + pyromancer.getLevel()*Constants.LEVELI);
        if(instance.getArray(pyromancer.getCoord_x(),pyromancer.getCoord_y()) == Constants.VOLCANIC){
            damageIgniteBase = Constants.MODIFICATORV*damageIgniteBase;
        }
        damageIgniteBase = Math.round(damageIgniteBase);
        float damage;
        damage = damageFireblastBase + damageIgniteBase;
        this.sethp((int) (this.gethp()-damage));
        float damageOvertime;
        damageOvertime = Constants.MODIFICATORR*(Constants.LEVELFB + Constants.THIRTIETH*pyromancer.getLevel());
        if(instance.getArray(pyromancer.getCoord_x(),pyromancer.getCoord_y()) == Constants.VOLCANIC){
            damageOvertime = Constants.MODIFICATORV*damageOvertime;
        }
        damageOvertime=Math.round(damageOvertime);
        //incapacitatea de miscare-> sterg celelate overtime
        this.setStay(Constants.ZERO);
        this.setDemageOvertime((int) damageOvertime);
        this.setNrRoundOvertime(Constants.TWO);
    }

    @Override
    public void salute(Rogue rogue) {
        System.out.println("15");
        /*R.R*/
        map instance = map.getInstance();

        //aplic lui rogue overtimedamage
        if(rogue.getNrRoundOvertime()>0) {
            rogue.sethp(rogue.gethp() - rogue.getDemageOvertime());
            rogue.setNrRoundOvertime(rogue.getNrRoundOvertime() - 1);
        }

        //aplic lui this/clasa overtimedamage
        if(this.getNrRoundOvertime()>0) {
            this.sethp(rogue.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
        }
        if(this.getDie() == 0 && rogue.getDie() ==0) {
            //rogue ataca clasa/this/rogue
            //rogue-backstab
            rogue.setHit(rogue.getHit()+1);
            float damageBackstab1 = Constants.ZERO;
            damageBackstab1 = (Constants.TWOH + rogue.getLevel()*Constants.LEVELI);
            if(instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS){
                damageBackstab1 = damageBackstab1*Constants.KR;
            }
            //hit
            if(instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS && rogue.getHit()%3 == 0)/*??*/{
                damageBackstab1 = damageBackstab1*Constants.ONEFIVE;
                rogue.setHit(Constants.ZERO);
            }
            if(instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) != Constants.WOODS && rogue.getHit()%3 == 0){
                rogue.setHit(Constants.ZERO);
            }
            //rogue-Paralysis
            float damageParalysis1 = Constants.ZERO;
            damageParalysis1 = (Constants.FORTY + Constants.TEN*rogue.getLevel());
            if(instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS){
                damageParalysis1 = damageParalysis1*Constants.KR;
            }


        }
    }
    
    @Override
    public void salute(Wizard wizard) {
        System.out.println("16");
        /*W.R*/
    }
}
