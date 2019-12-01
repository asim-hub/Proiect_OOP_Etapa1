package main;

import common.Constants;
import matrix.Map;

public class Pyromancer extends Hero {
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
    public final void fightwith(final Hero hero) {
        hero.salute(this);
    }

    @Override
    public final void salute(final Knight knight) {
        Map instance = Map.getInstance();
        //aplic lui pyromancer overtimedamage
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        //aplic lui knight overtimedamage
        if (knight.getNrRoundOvertime() > 0) {
            knight.sethp(knight.gethp() - knight.getDemageOvertime());
            knight.setNrRoundOvertime(knight.getNrRoundOvertime() - 1);
            knight.life();
        }
        if (this.getDie() == Constants.ZERO && knight.getDie() == Constants.ZERO) {
            //knight ataca clasa
            float hplimit2;
            float damageExecuteBase2;
            // verific daca pyromancer/this moare din prima
            if (Constants.ZEROZEROONE * knight.getLevel() < Constants.ZEROTWO) {
                hplimit2 = (float) ((Constants.ZEROTWO
                        + Constants.ZEROZEROONE * knight.getLevel())
                        * (Constants.PYROMANCER + this.getLevel() * Constants.FIFTY));
            } else {
                hplimit2 = (float) ((2 * Constants.ZEROTWO)
                        * (Constants.PYROMANCER + this.getLevel() * Constants.FIFTY));
            }
            if (hplimit2 >= this.gethp()) {
                damageExecuteBase2 = this.gethp();
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase2 = (Constants.TWOH + Constants.THIRTIETH
                        * knight.getLevel()) * Constants.KP;
                if (instance.getArray(knight.getCoordX(), knight.getCoordY()) == Constants.LAND) {
                    damageExecuteBase2 = Constants.MODIFICATORL * damageExecuteBase2;
                }
                damageExecuteBase2 = Math.round(damageExecuteBase2);
            }
            //knight-slam
            float damageSlamBase2 = (Constants.ONEH + Constants.FORTY
                    * knight.getLevel()) * Constants.MODIFICATORP;
            if (instance.getArray(knight.getCoordX(), knight.getCoordY()) == Constants.LAND) {
                damageSlamBase2 = Constants.MODIFICATORL * damageSlamBase2;
            }
            damageSlamBase2 = Math.round(damageSlamBase2);
            float damage2 = damageSlamBase2 + damageExecuteBase2;
            this.sethp(this.gethp() - (int) damage2);
            //incapacitatea de miscare-> sterg celelate damageovertime si imobilizari
            this.setStay(Constants.ONE);
            this.setNrRoundOvertime(Constants.ZERO);
            this.setDemageOvertime(Constants.ZERO);
            //pyromancerclasa ataca pe knight
            //Pyromancer-Fireblast
            float damageFireblastBase = Constants.MODIFICATORK
                    * (Constants.DAMAGEFB + this.getLevel() * Constants.LEVELFB);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float damageIgniteBase = Constants.MODIFICATORK
                    * (Constants.DAMAGEI + this.getLevel() * Constants.LEVELI);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageIgniteBase = Constants.MODIFICATORV * damageIgniteBase;
            }
            damageIgniteBase = Math.round(damageIgniteBase);
            float damage;
            damage = damageFireblastBase + damageIgniteBase;
            knight.sethp((int) (knight.gethp() - damage));
            float damageOvertime;
            damageOvertime = Constants.MODIFICATORK
                    * (Constants.LEVELFB + Constants.THIRTIETH * this.getLevel());
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
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
            //verific daca se omoara si daca da adaug experienta
            // verific daca avanseaza in level si actualizez hp
            if (knight.getDie() == 1) {
                //insemna ca la omorat clasa/pyromancer
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - knight.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.PYROMANCER + newLevel * Constants.FIFTY);
                }
            }
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
        }
    }

    @Override
    public final void salute(final Pyromancer pyromancer) {
        Map instance = Map.getInstance();
        //aplic lui pyromancer overtimedamage
        if (pyromancer.getNrRoundOvertime() > 0 && pyromancer.getDie() == 0) {
            pyromancer.sethp(pyromancer.gethp() - pyromancer.getDemageOvertime());
            pyromancer.setNrRoundOvertime(pyromancer.getNrRoundOvertime() - 1);
            pyromancer.life();
        }
        //aplic lui pyromancer overtimedamage
        if (this.getNrRoundOvertime() > 0 && this.getDie() == 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if (this.getDie() == 0 && pyromancer.getDie() == 0) {
            //Pyromancer-Fireblast
            float damageFireblastBase1 = Constants.MODIFICATORP
                    * (Constants.DAMAGEFB + pyromancer.getLevel() * Constants.LEVELFB);
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageFireblastBase1 = Constants.MODIFICATORV * damageFireblastBase1;
            }
            damageFireblastBase1 = Math.round(damageFireblastBase1);
            //Pyromancer-Ignite
            float damageIgniteBase1 = Constants.MODIFICATORP
                    * (Constants.DAMAGEI + pyromancer.getLevel() * Constants.LEVELI);
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageIgniteBase1 = Constants.MODIFICATORV * damageIgniteBase1;
            }
            damageIgniteBase1 = Math.round(damageIgniteBase1);
            float damage1;
            damage1 = damageFireblastBase1 + damageIgniteBase1;
            this.sethp((int) (this.gethp() - damage1));
            float damageOvertime1;
            damageOvertime1 = Constants.MODIFICATORP
                    * (Constants.LEVELFB + Constants.THIRTIETH * pyromancer.getLevel());
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageOvertime1 = Constants.MODIFICATORV * damageOvertime1;
            }
            damageOvertime1 = Math.round(damageOvertime1);
            //schimb damage obertime si anulez paralizie
            this.setDemageOvertime((int) damageOvertime1);
            this.setNrRoundOvertime(Constants.TWO);
            this.setStay(Constants.ZERO);
            //clasa pyromancer ataca pyromancer
            //Pyromancer-Fireblast
            float damageFireblastBase2 = Constants.MODIFICATORP
                    * (Constants.DAMAGEFB + this.getLevel() * Constants.LEVELFB);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageFireblastBase2 = Constants.MODIFICATORV * damageFireblastBase2;
            }
            damageFireblastBase2 = Math.round(damageFireblastBase2);
            //Pyromancer-Ignite
            float damageIgniteBase2 = Constants.MODIFICATORP
                    * (Constants.DAMAGEI + this.getLevel() * Constants.LEVELI);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageIgniteBase2 = Constants.MODIFICATORV * damageIgniteBase2;
            }
            damageIgniteBase2 = Math.round(damageIgniteBase2);
            float damage2;
            damage2 = damageFireblastBase2 + damageIgniteBase2;
            pyromancer.sethp((int) (pyromancer.gethp() - damage2));
            float damageOvertime2;
            damageOvertime2 = Constants.MODIFICATORP
                    * (Constants.LEVELFB + Constants.THIRTIETH * this.getLevel());
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
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
            //verific daca se omoara si daca da adaug experienta
            // verific daca avanseaza in level si actualizez hp
            if (pyromancer.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - pyromancer.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.PYROMANCER + newLevel * Constants.FIFTY);
                }
            }
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
        }
    }

    @Override
    public final void salute(final Rogue rogue) {
        Map instance = Map.getInstance();
        //aplic lui pyromancer overtimedamage
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        //aplic lui rogue overtimedamage
        if (rogue.getNrRoundOvertime() > 0) {
            rogue.sethp(rogue.gethp() - rogue.getDemageOvertime());
            rogue.setNrRoundOvertime(rogue.getNrRoundOvertime() - 1);
            rogue.life();
        }
        if (this.getDie() == 0 && rogue.getDie() == 0) {
            //roguie ataca clasa
            rogue.setHit(rogue.getHit() + 1);
            float damageBackstab1 = Constants.ZERO;
            damageBackstab1 = (Constants.TWOH + rogue.getLevel()
                    * Constants.LEVELI) * Constants.MODIFICATORV;
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
            damageParalysis1 = (Constants.FORTY + Constants.TEN
                    * rogue.getLevel()) * Constants.MODIFICATORK;
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
            //Pyromancer-Fireblast
            float damageFireblastBase = Constants.MODIFICATORR
                    * (Constants.DAMAGEFB + this.getLevel() * Constants.LEVELFB);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float damageIgniteBase = Constants.MODIFICATORR
                    * (Constants.DAMAGEI + this.getLevel() * Constants.LEVELI);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageIgniteBase = Constants.MODIFICATORV * damageIgniteBase;
            }
            damageIgniteBase = Math.round(damageIgniteBase);
            float damage;
            damage = damageFireblastBase + damageIgniteBase;
            rogue.sethp((int) (rogue.gethp() - damage));
            float damageOvertime;
            damageOvertime = Constants.MODIFICATORR
                    * (Constants.LEVELFB + Constants.THIRTIETH * this.getLevel());
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageOvertime = Constants.MODIFICATORV * damageOvertime;
            }
            damageOvertime = Math.round(damageOvertime);
            //dau damage prelungit si anulez paralizia
            rogue.setDemageOvertime((int) damageOvertime);
            rogue.setNrRoundOvertime(Constants.TWO);
            rogue.setStay(Constants.ZERO);
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
                    this.sethp(Constants.PYROMANCER + newLevel * Constants.LEVELFB);
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
        //aplic lui pyromancer overtimedamage
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        //aplic lui wizard overtimedamage
        if (wizard.getNrRoundOvertime() > 0) {
            wizard.sethp(wizard.gethp() - wizard.getDemageOvertime());
            wizard.setNrRoundOvertime(wizard.getNrRoundOvertime() - 1);
            wizard.life();
        }
        if (this.getDie() == 0 && wizard.getDie() == 0) {
            //Pyromancer-Fireblast
            float prefireblast;
            float damageFireblastBase = Constants.MODIFICATORW
                    * (Constants.DAMAGEFB + this.getLevel() * Constants.LEVELFB);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            prefireblast = Math.round(damageFireblastBase / Constants.MODIFICATORW);
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float preignite;
            float damageIgniteBase = Constants.MODIFICATORW
                    * (Constants.DAMAGEI + this.getLevel() * Constants.LEVELI);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageIgniteBase = Constants.MODIFICATORV * damageIgniteBase;
            }
            preignite = Math.round(damageIgniteBase / Constants.MODIFICATORW);
            damageIgniteBase = Math.round(damageIgniteBase);
            float damage;
            damage = damageFireblastBase + damageIgniteBase;
            wizard.sethp((int) (wizard.gethp() - damage));
            float damageOvertime;
            damageOvertime = Constants.MODIFICATORW
                    * (Constants.LEVELFB + Constants.THIRTIETH * this.getLevel());
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.VOLCANIC) {
                damageOvertime = Constants.MODIFICATORV * damageOvertime;
            }
            damageOvertime = Math.round(damageOvertime);
            //damage pelungit si anulez paralizia
            wizard.setDemageOvertime((int) damageOvertime);
            wizard.setNrRoundOvertime(Constants.TWO);
            wizard.setStay(Constants.ZERO);
            //wizard ataca clasa pyromancer
            //drain
            float procent1;
            procent1 = Constants.ZEROTWO + Constants.ZZFIVE * wizard.getLevel();
            float damagedrain1 = Constants.ZERO;
            damagedrain1 = (Constants.MODIFICATORP * procent1)
                    * Math.min(Constants.ZEROTHREE * (Constants.PYROMANCER
                    + this.getLevel() * Constants.FIFTY), this.gethp());
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
            damagedeflect = (procent2 * Constants.ONETHREE) * (prefireblast + preignite);
            if (instance.getArray(wizard.getCoordX(), wizard.getCoordY()) == Constants.DESERT) {
                damagedeflect *= Constants.KP;
            }
            damagedeflect = Math.round(damagedeflect);
            float damage1 = damagedrain1 + damagedeflect;
            this.sethp((int) (this.gethp() - damage1));
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
                    this.sethp(Constants.PYROMANCER + newLevel * Constants.FIFTY);
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
