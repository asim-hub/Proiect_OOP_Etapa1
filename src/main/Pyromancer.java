package main;
import common.Constants;
import matrix.map;

public class Pyromancer extends Hero{
	
	public Pyromancer() {
		this.sethp(Constants.PYROMANCER);
		this.setxp(Constants.ZERO);
        this.setLevel(Constants.ZERO);
        this.setDemageOvertime(Constants.ZERO);
        this.setNrRoundOvertime(Constants.ZERO);
        this.setStay(Constants.ZERO);
        this.setDie(Constants.ZERO);
	}
	@Override
    public void fightwith(Hero hero) {
        hero.salute(this);
    }

    @Override
    public void salute(Knight knight) {
        System.out.println("5");
        map instance = map.getInstance();
        /*K.P*/
        //aplic lui pyromancer overtimedamage
        if(this.getNrRoundOvertime()>0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        //aplic lui knight overtimedamage
        if(knight.getNrRoundOvertime()>0) {
            knight.sethp(knight.gethp() - knight.getDemageOvertime());
            knight.setNrRoundOvertime(knight.getNrRoundOvertime() - 1);
            knight.life();
        }

        if(this.getDie() == Constants.ZERO && knight.getDie() == Constants.ZERO) {
            //knight ataca clasa
            float HPLIMIT2;
            float damageExecuteBase2;
            // verific daca pyromancer/this moare din prima
            if (Constants.ZEROZEROONE * knight.getLevel() < Constants.ZEROTWO) {
                HPLIMIT2 = (float) ((Constants.ZEROTWO +
                        Constants.ZEROZEROONE * knight.getLevel()) * (Constants.PYROMANCER + this.getLevel() * Constants.FIFTY));
            } else {
                HPLIMIT2 = (float) ((2 * Constants.ZEROTWO) * (Constants.PYROMANCER + this.getLevel() * Constants.FIFTY));
            }
            if (HPLIMIT2 >= this.gethp()) {
                this.setDie(Constants.ONE);
                this.sethp(Constants.ZERO);
            /*
            this.life();
            */
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH * knight.getLevel()) * Constants.KP;
                if (instance.getArray(knight.getCoord_x(), knight.getCoord_y()) == Constants.LAND) {
                    damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
                }
                damageExecuteBase2 = Math.round(damageExecuteBase2);

                //knight-slam
                float damageSlamBase2 = (Constants.ONEH + Constants.FORTY * knight.getLevel()) * Constants.MODIFICATORP;
                if (instance.getArray(knight.getCoord_x(), knight.getCoord_y()) == Constants.LAND) {
                    damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
                }
                damageSlamBase2 = Math.round(damageSlamBase2);
                float damage2 = damageSlamBase2 + damageExecuteBase2;
                this.sethp(this.gethp() - (int) damage2);
                //incapacitatea de miscare-> sterg celelate damageovertime si imobilizari
                this.setStay(Constants.ONE);
                this.setNrRoundOvertime(Constants.ZERO);
                this.setDemageOvertime(Constants.ZERO);
            }

            //Pyromancer-Fireblast
            float damageFireblastBase = Constants.MODIFICATORK * (Constants.DAMAGEFB + this.getLevel() * Constants.LEVELFB);
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float damageIgniteBase = Constants.MODIFICATORK * (Constants.DAMAGEI + this.getLevel() * Constants.LEVELI);
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.VOLCANIC) {
                damageIgniteBase = Constants.MODIFICATORV * damageIgniteBase;
            }
            damageIgniteBase = Math.round(damageIgniteBase);
            float damage;
            damage = damageFireblastBase + damageIgniteBase;
            knight.sethp((int) (knight.gethp() - damage));
            float damageOvertime;
            damageOvertime = Constants.MODIFICATORK * (Constants.LEVELFB + Constants.THIRTIETH * this.getLevel());
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.VOLCANIC) {
                damageOvertime = Constants.MODIFICATORV * damageOvertime;
            }
            //aplic damage overtime si steg  imobilizarea
            damageOvertime = Math.round(damageOvertime);
            knight.setDemageOvertime((int) damageOvertime);
            knight.setNrRoundOvertime(Constants.TWO);
            knight.setStay(Constants.ZERO);
            //verific daca dupa lupta mai traiesc
            knight.life();
            this.life();
            //verific daca se omoara si daca da adaug experienta//verific daca avanseaza in level si actualizez hp
            if(knight.getDie() == 1){
                //insemna ca la omorat clasa/pyromancer
                this.setxp(this.getxp() + Math.max(Constants.ZERO,Constants.TWOH -
                        (this.getLevel()-knight.getLevel())*Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int)(this.getxp()/50) - 4;
                if(new_level>old_level){
                    this.setLevel(new_level);
                    this.sethp(Constants.PYROMANCER + new_level*Constants.FIFTY);
                }
            }
            if(this.getDie() == 1){
                //insemna ca la omorat knight
                knight.setxp(knight.getxp() + Math.max(Constants.ZERO,Constants.TWOH -
                        (knight.getLevel()-this.getLevel())*Constants.FORTY));
                int old_level = knight.getLevel();
                int new_level = (int)(knight.getxp()/50) - 4;
                if(new_level>old_level){
                    knight.setLevel(new_level);
                    knight.sethp(Constants.KNIGHT + new_level*Constants.EIGHTY);
                }
            }
        }
    }

    @Override
    public void salute(Pyromancer pyromancer) {
        System.out.println("6");
        /*P.P*/

        //aplic lui pyromancer overtimedamage
        if (pyromancer.getNrRoundOvertime() > 0 && pyromancer.getDie() == 0) {
            pyromancer.sethp(pyromancer.gethp() - pyromancer.getDemageOvertime());
            pyromancer.setNrRoundOvertime(pyromancer.getNrRoundOvertime() - 1);
            pyromancer.life();
        }

        //aplic lui pyromancer. overtimedamage
        if (this.getNrRoundOvertime() > 0 && this.getDie() == 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }

        if(this.getDie() == 0 && pyromancer.getDie() ==0) {
            map instance = map.getInstance();
            //Pyromancer-Fireblast
            float damageFireblastBase1 = Constants.MODIFICATORP * (Constants.DAMAGEFB + pyromancer.getLevel() * Constants.LEVELFB);
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageFireblastBase1 = Constants.MODIFICATORV * damageFireblastBase1;
            }
            damageFireblastBase1 = Math.round(damageFireblastBase1);
            //Pyromancer-Ignite
            float damageIgniteBase1 = Constants.MODIFICATORP * (Constants.DAMAGEI + pyromancer.getLevel() * Constants.LEVELI);
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageIgniteBase1 = Constants.MODIFICATORV * damageIgniteBase1;
            }
            damageIgniteBase1 = Math.round(damageIgniteBase1);
            float damage1;
            damage1 = damageFireblastBase1 + damageIgniteBase1;
            this.sethp((int) (this.gethp() - damage1));
            float damageOvertime1;
            damageOvertime1 = Constants.MODIFICATORP * (Constants.LEVELFB + Constants.THIRTIETH * pyromancer.getLevel());
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageOvertime1 = Constants.MODIFICATORV * damageOvertime1;
            }
            damageOvertime1 = Math.round(damageOvertime1);
            //schimb damage obertime si anulez paralizie
            this.setDemageOvertime((int) damageOvertime1);
            this.setNrRoundOvertime(Constants.TWO);
            this.setStay(0);


            //Pyromancer-Fireblast
            float damageFireblastBase2 = Constants.MODIFICATORP * (Constants.DAMAGEFB + this.getLevel() * Constants.LEVELFB);
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.VOLCANIC) {
                damageFireblastBase2 = Constants.MODIFICATORV * damageFireblastBase2;
            }
            damageFireblastBase2 = Math.round(damageFireblastBase2);
            //Pyromancer-Ignite
            float damageIgniteBase2 = Constants.MODIFICATORP * (Constants.DAMAGEI + this.getLevel() * Constants.LEVELI);
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.VOLCANIC) {
                damageIgniteBase2 = Constants.MODIFICATORV * damageIgniteBase2;
            }
            damageIgniteBase2 = Math.round(damageIgniteBase2);
            float damage2;
            damage2 = damageFireblastBase2 + damageIgniteBase2;
            pyromancer.sethp((int) (pyromancer.gethp() - damage2));
            float damageOvertime2;
            damageOvertime2 = Constants.MODIFICATORP * (Constants.LEVELFB + Constants.THIRTIETH * this.getLevel());
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.VOLCANIC) {
                damageOvertime2 = Constants.MODIFICATORV * damageOvertime2;
            }
            damageOvertime2 = Math.round(damageOvertime2);
            //demigi prelungit
            pyromancer.setDemageOvertime((int) damageOvertime2);
            pyromancer.setNrRoundOvertime(Constants.TWO);
            //anulez celelate overtime
            pyromancer.setStay(Constants.ZERO);
            //verific daca dupa lupta mai traiesc
            pyromancer.life();
            this.life();
            //verific daca se omoara si daca da adaug experienta//verific daca avanseaza in level si actualizez hp
            if(pyromancer.getDie() == 1){
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO,Constants.TWOH -
                        (this.getLevel()-pyromancer.getLevel())*Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int)(this.getxp()/50) - 4;
                if(new_level>old_level){
                    this.setLevel(new_level);
                    this.sethp(Constants.PYROMANCER + new_level*Constants.FIFTY);
                }
            }
            if(this.getDie() == 1){
                //insemna ca la omorat pyromancer
                pyromancer.setxp(pyromancer.getxp() + Math.max(Constants.ZERO,Constants.TWOH -
                        (pyromancer.getLevel()-this.getLevel())*Constants.FORTY));
                int old_level = pyromancer.getLevel();
                int new_level = (int)(pyromancer.getxp()/50) - 4;
                if(new_level>old_level){
                    pyromancer.setLevel(new_level);
                    pyromancer.sethp(Constants.PYROMANCER + new_level*Constants.FIFTY);
                }
            }
        }
    }

    @Override
    public void salute(Rogue rogue) {
        System.out.println("7");
        /*R.P*/


        //aplic lui pyromancer overtimedamage
        if(this.getNrRoundOvertime()>0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
        }
        //Pyromancer-Fireblast
        map instance = map.getInstance();
        float damageFireblastBase = Constants.MODIFICATORR*(Constants.DAMAGEFB + this.getLevel()*Constants.LEVELFB);
        if(instance.getArray(this.getCoord_x(),this.getCoord_y()) == Constants.VOLCANIC){
            damageFireblastBase = Constants.MODIFICATORV*damageFireblastBase;
        }
        damageFireblastBase = Math.round(damageFireblastBase);
        //Pyromancer-Ignite
        float damageIgniteBase = Constants.MODIFICATORR*(Constants.DAMAGEI + this.getLevel()*Constants.LEVELI);
        if(instance.getArray(this.getCoord_x(),this.getCoord_y()) == Constants.VOLCANIC){
            damageIgniteBase = Constants.MODIFICATORV*damageIgniteBase;
        }
        damageIgniteBase = Math.round(damageIgniteBase);
        float damage;
        damage = damageFireblastBase + damageIgniteBase;
        rogue.sethp((int) (rogue.gethp()-damage));
        float damageOvertime;
        damageOvertime = Constants.MODIFICATORR*(Constants.LEVELFB + Constants.THIRTIETH*this.getLevel());
        if(instance.getArray(this.getCoord_x(),this.getCoord_y()) == Constants.VOLCANIC){
            damageOvertime = Constants.MODIFICATORV*damageOvertime;
        }
        damageOvertime=Math.round(damageOvertime);
        //dau damage prelungit si anulez paralizia
        rogue.setDemageOvertime((int) damageOvertime);
        rogue.setNrRoundOvertime(Constants.TWO);
        rogue.setStay(Constants.ZERO);


    }
    
    @Override
    public void salute(Wizard wizard) {
        System.out.println("8");
        /*W.P*/

        //aplic lui pyromancer overtimedamage
        if(this.getNrRoundOvertime()>0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
        }
        //Pyromancer-Fireblast
        map instance = map.getInstance();
        float damageFireblastBase = Constants.MODIFICATORW*(Constants.DAMAGEFB + this.getLevel()*Constants.LEVELFB);
        if(instance.getArray(this.getCoord_x(),this.getCoord_y()) == Constants.VOLCANIC){
            damageFireblastBase = Constants.MODIFICATORV*damageFireblastBase;
        }
        damageFireblastBase = Math.round(damageFireblastBase);
        //Pyromancer-Ignite
        float damageIgniteBase = Constants.MODIFICATORW*(Constants.DAMAGEI + this.getLevel()*Constants.LEVELI);
        if(instance.getArray(this.getCoord_x(),this.getCoord_y()) == Constants.VOLCANIC){
            damageIgniteBase = Constants.MODIFICATORV*damageIgniteBase;
        }
        damageIgniteBase = Math.round(damageIgniteBase);
        float damage;
        damage = damageFireblastBase + damageIgniteBase;
        wizard.sethp((int) (wizard.gethp()-damage));
        float damageOvertime;
        damageOvertime = Constants.MODIFICATORW*(Constants.LEVELFB + Constants.THIRTIETH*this.getLevel());
        if(instance.getArray(this.getCoord_x(),this.getCoord_y()) == Constants.VOLCANIC){
            damageOvertime = Constants.MODIFICATORV*damageOvertime;
        }
        damageOvertime=Math.round(damageOvertime);
        //damage pelungit si anulez paralizia
        wizard.setDemageOvertime((int) damageOvertime);
        wizard.setNrRoundOvertime(Constants.TWO);
        wizard.setStay(Constants.ZERO);



    }
}
