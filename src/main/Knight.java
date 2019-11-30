package main;
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
        //aplic lui rogue overtimedamage
        if (rogue.getNrRoundOvertime() > 0) {
            rogue.sethp(rogue.gethp() - rogue.getDemageOvertime());
            rogue.setNrRoundOvertime(rogue.getNrRoundOvertime() - 1);
            rogue.life();
        }

        //aplic lui this/clasa overtimedamage
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if (this.getDie() == 0 && rogue.getDie() == 0) {


            //roguie ataca clasa
            rogue.setHit(rogue.getHit() + 1);
            float damageBackstab1 = Constants.ZERO;
            damageBackstab1 = (Constants.TWOH + rogue.getLevel() * Constants.LEVELI) * Constants.MODIFICATORV;
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS) {
                damageBackstab1 = damageBackstab1 * Constants.KR;
            }
            //hit
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS && rogue.getHit() % 3 == 1)/*??*/ {
                damageBackstab1 = damageBackstab1 * Constants.ONEFIVE;
                rogue.setHit(Constants.ZERO);
            }
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) != Constants.WOODS && rogue.getHit() % 3 == 1) {
                rogue.setHit(Constants.ZERO);
            }
            damageBackstab1 = Math.round(damageBackstab1);
            //rogue-Paralysis
            float damageParalysis1 = Constants.ZERO;
            damageParalysis1 = (Constants.FORTY + Constants.TEN * rogue.getLevel()) * Constants.KW;
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS) {
                damageParalysis1 = damageParalysis1 * Constants.KR;
            }
            damageParalysis1 = Math.round(damageParalysis1);
            float damage1;
            damage1 = damageBackstab1 + damageParalysis1;
            this.sethp((int) (this.gethp() - damage1));
            //damage extins si paralizie 3/6
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS) {
                this.setDemageOvertime((int) damage1);
                this.setNrRoundOvertime(Constants.SIX);
                this.setStay(Constants.SIX);
            } else {
                this.setDemageOvertime((int) damage1);
                this.setNrRoundOvertime(Constants.THREE);
                this.setStay(Constants.THREE);
            }

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
            //verific daca mai sutnt in viata
            this.life();
            rogue.life();
            //verific daca se omoara si daca da adaug experienta//verific daca avanseaza in level si actualizez hp
            if (rogue.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (this.getLevel() - rogue.getLevel()) * Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int) (this.getxp() / 50) - 4;
                if (new_level > old_level) {
                    this.setLevel(new_level);
                    this.sethp(Constants.KNIGHT + new_level * Constants.EIGHTY);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat rogue
                rogue.setxp(rogue.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (rogue.getLevel() - this.getLevel()) * Constants.FORTY));
                int old_level = rogue.getLevel();
                int new_level = (int) (rogue.getxp() / 50) - 4;
                if (new_level > old_level) {
                    rogue.setLevel(new_level);
                    rogue.sethp(Constants.ROGUE + new_level * Constants.FORTY);
                }
            }
        }
    }
    
    @Override
    public void salute(Wizard wizard) {
        System.out.println("4");
        /*W.K*/
        map instance = map.getInstance();
        //aplic lui knight overtimedamage daca are
        if (wizard.getNrRoundOvertime() > 0) {
            wizard.sethp(wizard.gethp() - wizard.getDemageOvertime());
            wizard.setNrRoundOvertime(wizard.getNrRoundOvertime() - 1);
            wizard.life();
        }
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if (this.getDie() == 0 && wizard.getDie() == 0) {


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
            float preexecute;
            if (HPLIMIT2 >= wizard.gethp()) {
               damageExecuteBase2 = wizard.gethp();
               preexecute=damageExecuteBase2;

            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH * this.getLevel()) * Constants.KW;
                if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                    damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
                }
                preexecute=Math.round(damageExecuteBase2/Constants.KW);
                damageExecuteBase2 = Math.round(damageExecuteBase2);
            }
                //knight-slam
            float preslam;
            float damageSlamBase2 = (Constants.ONEH + Constants.FORTY * this.getLevel()) * Constants.MODIFICATORW;
                if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.LAND) {
                    damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
                }
            preslam = Math.round(damageSlamBase2/Constants.MODIFICATORW);
            damageSlamBase2 = Math.round(damageSlamBase2);
                float damage2 = damageSlamBase2 + damageExecuteBase2;
                wizard.sethp(wizard.gethp() - (int) damage2);
                //incapacitatea de miscare-> sterg celelate overtime
                wizard.setStay(Constants.ONE);
                wizard.setNrRoundOvertime(Constants.ZERO);
                wizard.setDemageOvertime(Constants.ZERO);

                //wizard ataca clasa knight
            //drain
            float procent1;
            procent1 = Constants.ZEROTWO + Constants.ZZFIVE * wizard.getLevel();
            float damagedrain1 = Constants.ZERO;
            damagedrain1 = (Constants.MODIFICATORV * procent1) * Math.min(Constants.ZEROTHREE * (Constants.KNIGHT + this.getLevel() * Constants.EIGHTY), this.gethp());
            if (instance.getArray(wizard.getCoord_x(), wizard.getCoord_y()) == Constants.DESERT) {
                damagedrain1 *= Constants.KP;
            }
            damagedrain1 = Math.round(damagedrain1);
            //Deflect
            float procent2;
            procent2 = Constants.ZTHREEFIVE + Constants.ZZTWO * wizard.getLevel();
            if (procent2 > Constants.MAXPRO) {
                procent2 = Constants.MAXPRO;
            }

            float damagedeflect;
            damagedeflect = (procent2 * Constants.ONEFOUR) * (preexecute + preslam);
            damagedeflect = Math.round(damagedeflect);
            float damage = damagedrain1 + damagedeflect;
            this.sethp((int) (this.gethp() - damage));
            //verific daca mai traiesc
            this.life();
            wizard.life();

            if (wizard.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (this.getLevel() - wizard.getLevel()) * Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int) (this.getxp() / 50) - 4;
                if (new_level > old_level) {
                    this.setLevel(new_level);
                    this.sethp(Constants.KNIGHT + new_level * Constants.EIGHTY);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat wizard
                wizard.setxp(wizard.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (wizard.getLevel() - this.getLevel()) * Constants.FORTY));
                int old_level = wizard.getLevel();
                int new_level = (int) (wizard.getxp() / 50) - 4;
                if (new_level > old_level) {
                    wizard.setLevel(new_level);
                    wizard.sethp(Constants.WIZARD + new_level * Constants.THIRTIETH);
                }
            }

        }
    }
}
