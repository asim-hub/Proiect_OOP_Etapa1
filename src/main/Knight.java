package main;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import common.Constants;
import matrix.map;

public class Knight extends Hero{

	public Knight() {
		this.sethp(Constants.KNIGHT);
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
        System.out.println("1");
        map instance = map.getInstance();
        /*K.K*/
        //aplic lui knight overtimedamage daca are
        if(knight.getNrRoundOvertime()>0) {
            knight.sethp(knight.gethp() - knight.getDemageOvertime());
            knight.setNrRoundOvertime(knight.getNrRoundOvertime() - 1);
            knight.life();
        }
        if(this.getNrRoundOvertime()>0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if(this.getDie() == 0 && knight.getDie() ==0) {
            //knight ataca
            float HPLIMIT1;
            float damageExecuteBase1;
            // verific daca clasa moare din prima
            if (Constants.ZEROZEROONE * knight.getLevel() < Constants.ZEROTWO) {
                HPLIMIT1 = (float) ((Constants.ZEROTWO +
                        Constants.ZEROZEROONE * knight.getLevel()) * (Constants.KNIGHT + this.getLevel() * Constants.EIGHTY));
            } else {
                HPLIMIT1 = (float) ((2 * Constants.ZEROTWO) * (Constants.KNIGHT + this.getLevel() * Constants.EIGHTY));
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
                damageExecuteBase1 = (Constants.TWOH + Constants.THIRTIETH * knight.getLevel()) * Constants.KK;
                if (instance.getArray(knight.getCoord_x(), knight.getCoord_y()) == Constants.LAND) {
                    damageExecuteBase1 = Constants.MODIFICATORL * damageExecuteBase1;
                }
                damageExecuteBase1 = Math.round(damageExecuteBase1);

                //knight-slam
                float damageSlamBase1 = (Constants.ONEH + Constants.FORTY * knight.getLevel()) * Constants.MODIFICATORK;
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

            //this , clasa ataca
            float HPLIMIT2;
            float damageExecuteBase2;
            // verific daca knight moare din prima
            if (Constants.ZEROZEROONE * this.getLevel() < Constants.ZEROTWO) {
                HPLIMIT2 = (float) ((Constants.ZEROTWO +
                        Constants.ZEROZEROONE * this.getLevel()) * (Constants.KNIGHT + knight.getLevel() * Constants.EIGHTY));
            } else {
                HPLIMIT2 = (float) ((2 * Constants.ZEROTWO) * (Constants.KNIGHT + knight.getLevel() * Constants.EIGHTY));
            }
            if (HPLIMIT2 >= knight.gethp()) {
                knight.setDie(Constants.ONE);
                knight.sethp(Constants.ZERO);
            /*
            this.life();
            */
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH * this.getLevel()) * Constants.KK;
                if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                    damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
                }
                damageExecuteBase2 = Math.round(damageExecuteBase2);

                //knight-slam
                float damageSlamBase2 = (Constants.ONEH + Constants.FORTY * this.getLevel()) * Constants.MODIFICATORK;
                if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                    damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
                }
                damageSlamBase2 = Math.round(damageSlamBase2);
                float damage2 = damageSlamBase2 + damageExecuteBase2;
                knight.sethp(knight.gethp() - (int) damage2);
                //incapacitatea de miscare-> sterg celelate overtime
                knight.setStay(Constants.ONE);
                knight.setNrRoundOvertime(Constants.ZERO);
                knight.setDemageOvertime(Constants.ZERO);
            }
            //verific daca dupa lupta mai traiesc
            knight.life();
            this.life();
            //verific daca se omoara si daca da adaug experienta//verific daca avanseaza in level si actualizez hp
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
            if(knight.getDie() == 1){
                //insemna ca la omorat clasa
                this.setxp(this.getxp() + Math.max(Constants.ZERO,Constants.TWOH -
                        (this.getLevel()-knight.getLevel())*Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int)(this.getxp()/50) - 4;
                if(new_level>old_level){
                    this.setLevel(new_level);
                    this.sethp(Constants.KNIGHT + new_level*Constants.EIGHTY);
                }
            }
        }
    }

    @Override
    public void salute(Pyromancer pyromancer) {
        System.out.println("2");
        /*P.K*/
        //aplic lui pyromancer overtimedamage
        if(pyromancer.getNrRoundOvertime()>0) {
            pyromancer.sethp(pyromancer.gethp() - pyromancer.getDemageOvertime());
            pyromancer.setNrRoundOvertime(pyromancer.getNrRoundOvertime() - 1);
            pyromancer.life();
        }
        //aplic lui this/knight overtimedamage
        if(this.getNrRoundOvertime()>0) {
            this.sethp(pyromancer.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if(this.getDie() == 0 && pyromancer.getDie() ==0) {
            //Pyromancer-Fireblast
            map instance = map.getInstance();
            float damageFireblastBase = Constants.MODIFICATORK * (Constants.DAMAGEFB + pyromancer.getLevel() * Constants.LEVELFB);
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float damageIgniteBase = Constants.MODIFICATORK * (Constants.DAMAGEI + pyromancer.getLevel() * Constants.LEVELI);
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageIgniteBase = Constants.MODIFICATORV * damageIgniteBase;
            }
            damageIgniteBase = Math.round(damageIgniteBase);
            float damage;
            damage = damageFireblastBase + damageIgniteBase;
            this.sethp((int) (this.gethp() - damage));
            float damageOvertime;
            damageOvertime = Constants.MODIFICATORK * (Constants.LEVELFB + Constants.THIRTIETH * pyromancer.getLevel());
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageOvertime = Constants.MODIFICATORV * damageOvertime;
            }
            damageOvertime = Math.round(damageOvertime);
            //noile damage si anulez paralizie
            this.setDemageOvertime((int) damageOvertime);
            this.setNrRoundOvertime(Constants.TWO);
            this.setStay(Constants.ZERO);

            //clasa this ataca pyromancer
            float HPLIMIT2;
            float damageExecuteBase2;
            // verific daca pyromancer moare din prima
            if (Constants.ZEROZEROONE * this.getLevel() < Constants.ZEROTWO) {
                HPLIMIT2 = (float) ((Constants.ZEROTWO +
                        Constants.ZEROZEROONE * this.getLevel()) * (Constants.PYROMANCER + pyromancer.getLevel() * Constants.FIFTY));
            } else {
                HPLIMIT2 = (float) ((2 * Constants.ZEROTWO) * (Constants.PYROMANCER + pyromancer.getLevel() * Constants.FIFTY));
            }
            if (HPLIMIT2 >= pyromancer.gethp()) {
                pyromancer.setDie(Constants.ONE);
                pyromancer.sethp(Constants.ZERO);
            /*
            this.life();
            */
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH * this.getLevel()) * Constants.KP;
                if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                    damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
                }
                damageExecuteBase2 = Math.round(damageExecuteBase2);

                //knight-slam
                float damageSlamBase2 = (Constants.ONEH + Constants.FORTY * this.getLevel()) * Constants.MODIFICATORK;
                if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                    damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
                }
                damageSlamBase2 = Math.round(damageSlamBase2);
                float damage2 = damageSlamBase2 + damageExecuteBase2;
                pyromancer.sethp(pyromancer.gethp() - (int) damage2);
                //incapacitatea de miscare-> sterg celelate overtime
                pyromancer.setStay(Constants.ONE);
                pyromancer.setNrRoundOvertime(Constants.ZERO);
                pyromancer.setDemageOvertime(Constants.ZERO);
            }
            //verific daca mai au viata
            this.life();
            pyromancer.life();
            //verific daca se omoara si daca da adaug experienta//verific daca avanseaza in level si actualizez hp
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
            if(pyromancer.getDie() == 1){
                //insemna ca la omorat clasa/knight
                this.setxp(this.getxp() + Math.max(Constants.ZERO,Constants.TWOH -
                        (this.getLevel()-pyromancer.getLevel())*Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int)(this.getxp()/50) - 4;
                if(new_level>old_level){
                    this.setLevel(new_level);
                    this.sethp(Constants.KNIGHT + new_level*Constants.EIGHTY);
                }
            }
        }
    }

    @Override
    public void salute(Rogue rogue) {
        System.out.println("3");
        /*R.K*/
        map instance = map.getInstance();



        //clasa this knight ataca rogue
        float HPLIMIT2;
        float damageExecuteBase2;
        // verific daca pyromancer moare din prima
        if (Constants.ZEROZEROONE * this.getLevel() < Constants.ZEROTWO) {
            HPLIMIT2 = (float) ((Constants.ZEROTWO +
                    Constants.ZEROZEROONE * this.getLevel()) * (Constants.ROGUE + rogue.getLevel() * Constants.FORTY));
        } else {
            HPLIMIT2 = (float) ((2 * Constants.ZEROTWO) * (Constants.ROGUE + rogue.getLevel() * Constants.FORTY));
        }
        if (HPLIMIT2 >= rogue.gethp()) {
            rogue.setDie(Constants.ONE);
            rogue.sethp(Constants.ZERO);
            /*
            this.life();
            */
        } else {
            //daca nu moare din prima fac abilitatile
            //knight-execute
            damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH * this.getLevel()) * Constants.KR;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
            }
            damageExecuteBase2 = Math.round(damageExecuteBase2);

            //knight-slam
            float damageSlamBase2 = (Constants.ONEH + Constants.FORTY * this.getLevel()) * Constants.MODIFICATORR;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
            }
            damageSlamBase2 = Math.round(damageSlamBase2);
            float damage2 = damageSlamBase2 + damageExecuteBase2;
            rogue.sethp(rogue.gethp() - (int) damage2);
            //incapacitatea de miscare-> sterg celelate overtime
            rogue.setStay(Constants.ONE);
            rogue.setNrRoundOvertime(Constants.ZERO);
            rogue.setDemageOvertime(Constants.ZERO);
        }

    }
    
    @Override
    public void salute(Wizard wizard) {
        System.out.println("4");
        /*W.K*/
        map instance = map.getInstance();



        //clasa this knight ataca wizard
        float HPLIMIT2;
        float damageExecuteBase2;
        // verific daca pyromancer moare din prima
        if (Constants.ZEROZEROONE * this.getLevel() < Constants.ZEROTWO) {
            HPLIMIT2 = (float) ((Constants.ZEROTWO +
                    Constants.ZEROZEROONE * this.getLevel()) * (Constants.WIZARD + wizard.getLevel() * Constants.THIRTIETH));
        } else {
            HPLIMIT2 = (float) ((2 * Constants.ZEROTWO) * (Constants.WIZARD + wizard.getLevel() * Constants.THIRTIETH));
        }
        if (HPLIMIT2 >= wizard.gethp()) {
            wizard.setDie(Constants.ONE);
            wizard.sethp(Constants.ZERO);
            /*
            this.life();
            */
        } else {
            //daca nu moare din prima fac abilitatile
            //knight-execute
            damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH * this.getLevel()) * Constants.KW;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
            }
            damageExecuteBase2 = Math.round(damageExecuteBase2);

            //knight-slam
            float damageSlamBase2 = (Constants.ONEH + Constants.FORTY * this.getLevel()) * Constants.MODIFICATORW;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
            }
            damageSlamBase2 = Math.round(damageSlamBase2);
            float damage2 = damageSlamBase2 + damageExecuteBase2;
            wizard.sethp(wizard.gethp() - (int) damage2);
            //incapacitatea de miscare-> sterg celelate overtime
            wizard.setStay(Constants.ONE);
            wizard.setNrRoundOvertime(Constants.ZERO);
            wizard.setDemageOvertime(Constants.ZERO);
        }
    }
    
}
