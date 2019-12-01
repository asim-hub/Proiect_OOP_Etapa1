package main;

import common.Constants;
import matrix.Map;

public class Knight extends Hero {

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
    public final void fightwith(final Hero hero) {
        hero.salute(this);
    }

    @Override
    public final void salute(final Knight knight) {
        Map instance = Map.getInstance();
        //aplic lui knight overtimedamage daca are
        if (knight.getNrRoundOvertime() > 0) {
            knight.sethp(knight.gethp() - knight.getDemageOvertime());
            knight.setNrRoundOvertime(knight.getNrRoundOvertime() - 1);
            knight.life();
        }
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if (this.getDie() == 0 && knight.getDie() == 0) {
            //knight ataca
            float hplimit1;
            float damageExecuteBase1;
            // verific daca clasa moare din prima
            if (Constants.ZEROZEROONE * knight.getLevel() < Constants.ZEROTWO) {
                hplimit1 = (float) ((Constants.ZEROTWO
                        + Constants.ZEROZEROONE * knight.getLevel())
                        * (Constants.KNIGHT + this.getLevel() * Constants.EIGHTY));
            } else {
                hplimit1 = (float) ((2 * Constants.ZEROTWO)
                        * (Constants.KNIGHT + this.getLevel() * Constants.EIGHTY));
            }
            if (hplimit1 >= this.gethp()) {
                damageExecuteBase1 = this.gethp();

            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase1 = (Constants.TWOH + Constants.THIRTIETH
                        * knight.getLevel()) * Constants.KK;
                if (instance.getArray(knight.getCoordX(), knight.getCoordY()) == Constants.LAND) {
                    damageExecuteBase1 = Constants.MODIFICATORL * damageExecuteBase1;
                }
                damageExecuteBase1 = Math.round(damageExecuteBase1);
            }
            //knight-slam
            float damageSlamBase1 = (Constants.ONEH + Constants.FORTY
                    * knight.getLevel()) * Constants.MODIFICATORK;
            if (instance.getArray(knight.getCoordX(), knight.getCoordY()) == Constants.LAND) {
                damageSlamBase1 = Constants.MODIFICATORL * damageSlamBase1;
            }
            damageSlamBase1 = Math.round(damageSlamBase1);

            float damage1 = damageSlamBase1 + damageExecuteBase1;
            this.sethp(this.gethp() - (int) damage1);
            //incapacitatea de miscare-> sterg celelate overtime
            this.setStay(Constants.ONE);
            this.setNrRoundOvertime(Constants.ZERO);
            this.setDemageOvertime(Constants.ZERO);

            //this , clasa ataca
            float hplimit2;
            float damageExecuteBase2;
            // verific daca knight moare din prima
            if (Constants.ZEROZEROONE * this.getLevel() < Constants.ZEROTWO) {
                hplimit2 = (float) ((Constants.ZEROTWO
                        + Constants.ZEROZEROONE * this.getLevel())
                        * (Constants.KNIGHT + knight.getLevel() * Constants.EIGHTY));
            } else {
                hplimit2 = (float) ((2 * Constants.ZEROTWO)
                        * (Constants.KNIGHT + knight.getLevel() * Constants.EIGHTY));
            }
            if (hplimit2 >= knight.gethp()) {
                damageExecuteBase2 = knight.gethp();
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH
                        * this.getLevel()) * Constants.KK;
                if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.LAND) {
                    damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
                }
                damageExecuteBase2 = Math.round(damageExecuteBase2);
            }
            //knight-slam
            float damageSlamBase2 = (Constants.ONEH + Constants.FORTY
                    * this.getLevel()) * Constants.MODIFICATORK;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.LAND) {
                damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
            }
            damageSlamBase2 = Math.round(damageSlamBase2);
            float damage2 = damageSlamBase2 + damageExecuteBase2;
            knight.sethp(knight.gethp() - (int) damage2);
            //incapacitatea de miscare-> sterg celelate overtime
            knight.setStay(Constants.ONE);
            knight.setNrRoundOvertime(Constants.ZERO);
            knight.setDemageOvertime(Constants.ZERO);

            //verific daca dupa lupta mai traiesc
            knight.life();
            this.life();
            //verific daca se omoara si daca da adaug experienta
            // verific daca avanseaza in level si actualizez hp
            if (this.getDie() == 1) {
                //insemna ca la omorat knight
                knight.setxp(knight.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (knight.getLevel() - this.getLevel()) * Constants.FORTY));
                int oldLevel = knight.getLevel();
                int newLevel = (int) (knight.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    knight.setLevel(newLevel);
                    knight.sethp(Constants.KNIGHT + newLevel * Constants.EIGHTY);
                }

            }
            if (knight.getDie() == 1) {
                //insemna ca la omorat clasa
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - knight.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.KNIGHT + newLevel * Constants.EIGHTY);
                }
            }
        }
    }

    @Override
    public final void salute(final Pyromancer pyromancer) {
        Map instance = Map.getInstance();
        //aplic lui pyromancer overtimedamage
        if (pyromancer.getNrRoundOvertime() > 0) {
            pyromancer.sethp(pyromancer.gethp() - pyromancer.getDemageOvertime());
            pyromancer.setNrRoundOvertime(pyromancer.getNrRoundOvertime() - 1);
            pyromancer.life();
        }
        //aplic lui this/knight overtimedamage
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(pyromancer.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if (this.getDie() == 0 && pyromancer.getDie() == 0) {
            //Pyromancer-Fireblast
            float damageFireblastBase = Constants.MODIFICATORK
                    * (Constants.DAMAGEFB + pyromancer.getLevel() * Constants.LEVELFB);
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float damageIgniteBase = Constants.MODIFICATORK
                    * (Constants.DAMAGEI + pyromancer.getLevel() * Constants.LEVELI);
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageIgniteBase = Constants.MODIFICATORV * damageIgniteBase;
            }
            damageIgniteBase = Math.round(damageIgniteBase);
            float damage;
            damage = damageFireblastBase + damageIgniteBase;
            this.sethp((int) (this.gethp() - damage));
            float damageOvertime;
            damageOvertime = Constants.MODIFICATORK * (Constants.LEVELFB
                    + Constants.THIRTIETH * pyromancer.getLevel());
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageOvertime = Constants.MODIFICATORV * damageOvertime;
            }
            damageOvertime = Math.round(damageOvertime);
            //noile damage si anulez paralizie
            this.setDemageOvertime((int) damageOvertime);
            this.setNrRoundOvertime(Constants.TWO);
            this.setStay(Constants.ZERO);
            //clasa this ataca pyromancer
            float hplimit2;
            float damageExecuteBase2;
            // verific daca pyromancer moare din prima
            if (Constants.ZEROZEROONE * this.getLevel() < Constants.ZEROTWO) {
                hplimit2 = (float) ((Constants.ZEROTWO
                        + Constants.ZEROZEROONE * this.getLevel()) * (Constants.PYROMANCER
                        + pyromancer.getLevel() * Constants.FIFTY));
            } else {
                hplimit2 = (float) ((2 * Constants.ZEROTWO)
                        * (Constants.PYROMANCER + pyromancer.getLevel() * Constants.FIFTY));
            }
            if (hplimit2 >= pyromancer.gethp()) {
                damageExecuteBase2 = pyromancer.gethp();
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH * this.getLevel())
                        * Constants.KP;
                if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.LAND) {
                    damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
                }
                damageExecuteBase2 = Math.round(damageExecuteBase2);
            }
            //knight-slam
            float damageSlamBase2 = (Constants.ONEH + Constants.FORTY
                    * this.getLevel()) * Constants.MODIFICATORP;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.LAND) {
                damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
            }
            damageSlamBase2 = Math.round(damageSlamBase2);
            float damage2 = damageSlamBase2 + damageExecuteBase2;
            pyromancer.sethp(pyromancer.gethp() - (int) damage2);
            //incapacitatea de miscare-> sterg celelate overtime
            pyromancer.setStay(Constants.ONE);
            pyromancer.setNrRoundOvertime(Constants.ZERO);
            pyromancer.setDemageOvertime(Constants.ZERO);
            //verific daca mai au viata
            this.life();
            pyromancer.life();
            //verific daca se omoara si daca da adaug experienta
            // verific daca avanseaza in level si actualizez hp
            if (this.getDie() == 1) {
                //insemna ca la omorat pyromancer
                pyromancer.setxp(pyromancer.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (pyromancer.getLevel() - this.getLevel()) * Constants.FORTY));
                int oldLevel = pyromancer.getLevel();
                int newLevel = (int) (pyromancer.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    pyromancer.setLevel(newLevel);
                    pyromancer.sethp(Constants.PYROMANCER + newLevel * Constants.FIFTY);
                }
            }
            if (pyromancer.getDie() == 1) {
                //insemna ca la omorat clasa/knight
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - pyromancer.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.KNIGHT + newLevel * Constants.EIGHTY);
                }
            }
        }
    }

    @Override
    public final void salute(final Rogue rogue) {
        Map instance = Map.getInstance();
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
            damageBackstab1 = (Constants.TWOH + rogue.getLevel()
                    * Constants.LEVELI) * Constants.MODIFICATORP;
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY()) == Constants.WOODS) {
                damageBackstab1 = damageBackstab1 * Constants.KR;
            }
            //hit
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY())
                    == Constants.WOODS && rogue.getHit() % Constants.THREE == 1)/*??*/ {
                damageBackstab1 = damageBackstab1 * Constants.ONEFIVE;
            }
            damageBackstab1 = Math.round(damageBackstab1);
            //rogue-Paralysis
            float damageParalysis1 = Constants.ZERO;
            damageParalysis1 = (Constants.FORTY + Constants.TEN * rogue.getLevel()) * Constants.KW;
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY()) == Constants.WOODS) {
                damageParalysis1 = damageParalysis1 * Constants.KR;
            }
            damageParalysis1 = Math.round(damageParalysis1);
            float damage1;
            damage1 = damageBackstab1 + damageParalysis1;
            this.sethp((int) (this.gethp() - damage1));
            //damage extins si paralizie 3/6
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY()) == Constants.WOODS) {
                this.setDemageOvertime((int) damageParalysis1);
                this.setNrRoundOvertime(Constants.SIX);
                this.setStay(Constants.SIX);
            } else {
                this.setDemageOvertime((int) damageParalysis1);
                this.setNrRoundOvertime(Constants.THREE);
                this.setStay(Constants.THREE);
            }
            //clasa this/knight ataca rogue
            float hplimit2;
            float damageExecuteBase2;
            // verific daca rogur moare din prima
            if (Constants.ZEROZEROONE * this.getLevel() < Constants.ZEROTWO) {
                hplimit2 = (float) ((Constants.ZEROTWO
                        + Constants.ZEROZEROONE * this.getLevel())
                        * (Constants.ROGUE + rogue.getLevel() * Constants.FORTY));
            } else {
                hplimit2 = (float) ((2 * Constants.ZEROTWO)
                        * (Constants.ROGUE + rogue.getLevel() * Constants.FORTY));
            }
            if (hplimit2 >= rogue.gethp()) {
                damageExecuteBase2 = rogue.gethp();
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH
                        * this.getLevel()) * Constants.KR;
                if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.LAND) {
                    damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
                }
                damageExecuteBase2 = Math.round(damageExecuteBase2);
            }
            //knight-slam
            float damageSlamBase2 = (Constants.ONEH + Constants.FORTY
                    * this.getLevel()) * Constants.MODIFICATORR;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.LAND) {
                damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
            }
            damageSlamBase2 = Math.round(damageSlamBase2);
            float damage2 = damageSlamBase2 + damageExecuteBase2;
            rogue.sethp(rogue.gethp() - (int) damage2);
            //incapacitatea de miscare-> sterg celelate overtime
            rogue.setStay(Constants.ONE);
            rogue.setNrRoundOvertime(Constants.ZERO);
            rogue.setDemageOvertime(Constants.ZERO);
            //verific daca mai sunt in viata
            this.life();
            rogue.life();
            //verific daca se omoara si daca da adaug experienta
            // verific daca avanseaza in level si actualizez hp
            if (rogue.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - rogue.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.KNIGHT + newLevel * Constants.EIGHTY);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat rogue
                rogue.setxp(rogue.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (rogue.getLevel() - this.getLevel()) * Constants.FORTY));
                int oldLevel = rogue.getLevel();
                int newLevel = (int) (rogue.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    rogue.setLevel(newLevel);
                    rogue.sethp(Constants.ROGUE + newLevel * Constants.FORTY);
                }
            }
        }
    }

    @Override
    public final void salute(final Wizard wizard) {
        Map instance = Map.getInstance();
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
            float hplimit2;
            float damageExecuteBase2;
            // verific daca wizard moare din prima
            if (Constants.ZEROZEROONE * this.getLevel() < Constants.ZEROTWO) {
                hplimit2 = (float) ((Constants.ZEROTWO
                        + Constants.ZEROZEROONE * this.getLevel())
                        * (Constants.WIZARD + wizard.getLevel() * Constants.THIRTIETH));
            } else {
                hplimit2 = (float) ((2 * Constants.ZEROTWO)
                        * (Constants.WIZARD + wizard.getLevel() * Constants.THIRTIETH));
            }
            float preexecute;
            if (hplimit2 >= wizard.gethp()) {
                damageExecuteBase2 = wizard.gethp();
                preexecute = damageExecuteBase2;

            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH
                        * this.getLevel()) * Constants.KW;
                if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.LAND) {
                    damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
                }
                preexecute = Math.round(damageExecuteBase2 / Constants.KW);
                damageExecuteBase2 = Math.round(damageExecuteBase2);
            }
            //knight-slam
            float preslam;
            float damageSlamBase2 = (Constants.ONEH + Constants.FORTY
                    * this.getLevel()) * Constants.MODIFICATORW;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.LAND) {
                damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
            }
            preslam = Math.round(damageSlamBase2 / Constants.MODIFICATORW);
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
            damagedrain1 = (Constants.MODIFICATORK * procent1)
                    * Math.min(Constants.ZEROTHREE * (Constants.KNIGHT
                    + this.getLevel() * Constants.EIGHTY), this.gethp());
            if (instance.getArray(wizard.getCoordX(), wizard.getCoordY()) == Constants.DESERT) {
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
            if (instance.getArray(wizard.getCoordX(), wizard.getCoordY()) == Constants.DESERT) {
                damagedeflect *= Constants.KP;
            }
            damagedeflect = Math.round(damagedeflect);
            float damage = damagedrain1 + damagedeflect;
            this.sethp((int) (this.gethp() - damage));
            //verific daca mai traiesc
            this.life();
            wizard.life();
            if (wizard.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - wizard.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.KNIGHT + newLevel * Constants.EIGHTY);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat wizard
                wizard.setxp(wizard.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (wizard.getLevel() - this.getLevel()) * Constants.FORTY));
                int oldLevel = wizard.getLevel();
                int newLevel = (int) (wizard.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    wizard.setLevel(newLevel);
                    wizard.sethp(Constants.WIZARD + newLevel * Constants.THIRTIETH);
                }
            }
        }
    }
}
