package main;

import common.Constants;
import matrix.Map;

public class Wizard extends Hero {

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
            //knight ataca pe wizard
            float hplimit1;
            float damageExecuteBase1;
            // verific daca clasa/wizard moare din prima
            if (Constants.ZEROZEROONE * knight.getLevel() < Constants.ZEROTWO) {
                hplimit1 = (float) ((Constants.ZEROTWO
                        + Constants.ZEROZEROONE * knight.getLevel())
                        * (Constants.WIZARD + this.getLevel() * Constants.THIRTIETH));
            } else {
                hplimit1 = (float) ((2 * Constants.ZEROTWO)
                        * (Constants.WIZARD + this.getLevel() * Constants.THIRTIETH));
            }
            float preexecute;
            if (hplimit1 >= this.gethp()) {
                damageExecuteBase1 = this.gethp();
                preexecute = damageExecuteBase1;
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase1 = (Constants.TWOH + Constants.THIRTIETH
                        * knight.getLevel()) * Constants.KW;
                if (instance.getArray(knight.getCoordX(), knight.getCoordY())
                        == Constants.LAND) {
                    damageExecuteBase1 = Constants.MODIFICATORL * damageExecuteBase1;
                }
                preexecute = Math.round(damageExecuteBase1 / Constants.KW);
                damageExecuteBase1 = Math.round(damageExecuteBase1);
            }
            //knight-slam
            float preslam;
            float damageSlamBase1 = (Constants.ONEH + Constants.FORTY
                    * knight.getLevel()) * Constants.MODIFICATORW;
            if (instance.getArray(knight.getCoordX(), knight.getCoordY()) == Constants.LAND) {
                damageSlamBase1 = Constants.MODIFICATORL * damageSlamBase1;
            }
            preslam = Math.round(damageSlamBase1 / Constants.MODIFICATORW);
            damageSlamBase1 = Math.round(damageSlamBase1);

            float damage1 = damageSlamBase1 + damageExecuteBase1;
            this.sethp(this.gethp() - (int) damage1);
            //incapacitatea de miscare-> sterg celelate overtime
            this.setStay(Constants.ONE);
            this.setNrRoundOvertime(Constants.ZERO);
            this.setDemageOvertime(Constants.ZERO);
            //wizard/clasa ataca knight
            //drain
            float procent1;
            procent1 = Constants.ZEROTWO + Constants.ZZFIVE * this.getLevel();
            float damagedrain1 = Constants.ZERO;
            damagedrain1 = (Constants.MODIFICATORK * procent1)
                    * Math.min(Constants.ZEROTHREE * (Constants.KNIGHT
                    + knight.getLevel() * Constants.EIGHTY), knight.gethp());
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.DESERT) {
                damagedrain1 *= Constants.KP;
            }
            damagedrain1 = Math.round(damagedrain1);
            //Deflect
            float procent2;
            procent2 = Constants.ZTHREEFIVE + Constants.ZZTWO * this.getLevel();
            if (procent2 > Constants.MAXPRO) {
                procent2 = Constants.MAXPRO;
            }
            float damagedeflect;
            damagedeflect = (procent2 * Constants.ONEFOUR) * (preexecute + preslam);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.DESERT) {
                damagedeflect *= Constants.KP;
            }
            damagedeflect = Math.round(damagedeflect);
            float damage = damagedrain1 + damagedeflect;
            knight.sethp((int) (knight.gethp() - damage));
            //verific daca mai sunt in viata
            this.life();
            knight.life();
            if (knight.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - knight.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.WIZARD + newLevel * Constants.THIRTIETH);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat wizard
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
        //aplic lui pyromancer overtimedamage
        if (pyromancer.getNrRoundOvertime() > 0) {
            pyromancer.sethp(pyromancer.gethp() - pyromancer.getDemageOvertime());
            pyromancer.setNrRoundOvertime(pyromancer.getNrRoundOvertime() - 1);
            pyromancer.life();
        }
        //aplic lui wizard overtime
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        Map instance = Map.getInstance();
        if (this.getDie() == 0 && pyromancer.getDie() == 0) {
            //Pyromancer-Fireblast
            float damageFireblastBase = Constants.MODIFICATORW * (Constants.DAMAGEFB
                    + pyromancer.getLevel() * Constants.LEVELFB);
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            float prefireblast = Math.round(damageFireblastBase / Constants.MODIFICATORW);
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float damageIgniteBase = Constants.MODIFICATORW
                    * (Constants.DAMAGEI + pyromancer.getLevel() * Constants.LEVELI);
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageIgniteBase = Constants.MODIFICATORV * damageIgniteBase;
            }
            float preignite = Math.round(damageIgniteBase / Constants.MODIFICATORW);
            damageIgniteBase = Math.round(damageIgniteBase);
            float damage;
            damage = damageFireblastBase + damageIgniteBase;
            this.sethp((int) (this.gethp() - damage));
            float damageOvertime = Constants.ZERO;
            damageOvertime = Constants.MODIFICATORW * (Constants.LEVELFB
                    + Constants.THIRTIETH * pyromancer.getLevel());
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageOvertime = Constants.MODIFICATORV * damageOvertime;
            }
            damageOvertime = Math.round(damageOvertime);
            //damageovertime + anulez paralizia
            this.setDemageOvertime((int) damageOvertime);
            this.setNrRoundOvertime(Constants.TWO);
            this.setStay(Constants.ZERO);
            //clasa wizard ataca pyromancer
            //drain
            float procent1;
            procent1 = Constants.ZEROTWO + Constants.ZZFIVE * this.getLevel();
            float damagedrain1 = Constants.ZERO;
            damagedrain1 = (Constants.MODIFICATORP * procent1)
                    * Math.min(Constants.ZEROTHREE * (Constants.PYROMANCER
                    + pyromancer.getLevel() * Constants.FIFTY), pyromancer.gethp());
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.DESERT) {
                damagedrain1 *= Constants.KP;
            }
            damagedrain1 = Math.round(damagedrain1);
            //Deflect
            float procent2;
            procent2 = Constants.ZTHREEFIVE + Constants.ZZTWO * this.getLevel();
            if (procent2 > Constants.MAXPRO) {
                procent2 = Constants.MAXPRO;
            }
            float damagedeflect;
            damagedeflect = (procent2 * Constants.ONETHREE) * (prefireblast + preignite);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.DESERT) {
                damagedeflect *= Constants.KP;
            }
            damagedeflect = Math.round(damagedeflect);
            float damage1 = damagedrain1 + damagedeflect;
            pyromancer.sethp((int) (pyromancer.gethp() - damage1));
            //verific daca mai sunt in viata
            this.life();
            pyromancer.life();
            if (pyromancer.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - pyromancer.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.WIZARD + newLevel * Constants.THIRTIETH);
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
            //rogue ataca clasa/this/wizard
            //rogue-Backstab
            rogue.setHit(rogue.getHit() + 1);
            float damageBackstab1 = Constants.ZERO;
            damageBackstab1 = (Constants.TWOH + rogue.getLevel()
                    * Constants.LEVELI) * Constants.MODIFICATORV;
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY())
                    == Constants.WOODS) {
                damageBackstab1 = damageBackstab1 * Constants.KR;
            }
            //hit
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY())
                    == Constants.WOODS && rogue.getHit() % Constants.THREE == Constants.ONE) {
                damageBackstab1 = damageBackstab1 * Constants.ONEFIVE;
            }
            float predamageBack = Math.round(damageBackstab1 / Constants.MODIFICATORV);
            damageBackstab1 = Math.round(damageBackstab1);
            //rogue-Paralysis
            float damageParalysis1 = Constants.ZERO;
            damageParalysis1 = (Constants.FORTY + Constants.TEN * rogue.getLevel())
                    * Constants.MODIFICATORV;
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY()) == Constants.WOODS) {
                damageParalysis1 = damageParalysis1 * Constants.KR;
            }
            float predamageParalysis = Math.round(damageParalysis1 / Constants.MODIFICATORV);
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
            //wizard/clasa ataca rogue
            //drain
            float procent1;
            procent1 = Constants.ZEROTWO + Constants.ZZFIVE * this.getLevel();
            float damagedrain1 = Constants.ZERO;
            damagedrain1 = (Constants.MODIFICATORR * procent1)
                    * Math.min(Constants.ZEROTHREE * (Constants.ROGUE + rogue.getLevel()
                    * Constants.FORTY), rogue.gethp());
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.DESERT) {
                damagedrain1 *= Constants.KP;
            }
            damagedrain1 = Math.round(damagedrain1);
            //Deflect
            float procent2;
            procent2 = Constants.ZTHREEFIVE + Constants.ZZTWO * this.getLevel();
            if (procent2 > Constants.MAXPRO) {
                procent2 = Constants.MAXPRO;
            }
            float damagedeflect;
            damagedeflect = (procent2 * Constants.MODIFICATORK)
                    * (predamageBack + predamageParalysis);
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.DESERT) {
                damagedeflect *= Constants.KP;
            }
            damagedeflect = Math.round(damagedeflect);
            float damage = damagedrain1 + damagedeflect;
            rogue.sethp((int) (rogue.gethp() - damage));
            //verific daca mai sunt in viata
            this.life();
            rogue.life();
            if (rogue.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - rogue.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.WIZARD + newLevel * Constants.THIRTIETH);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat wizard
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
        //aplic lui wizard overtimedamage
        if (wizard.getNrRoundOvertime() > 0) {
            wizard.sethp(wizard.gethp() - wizard.getDemageOvertime());
            wizard.setNrRoundOvertime(wizard.getNrRoundOvertime() - 1);
            wizard.life();
        }
        //aplic lui this/clasa overtimedamage
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if (this.getDie() == 0 && wizard.getDie() == 0) {
            //wizard ataca clasa
            //drian
            float procent1;
            procent1 = Constants.ZEROTWO + Constants.ZZFIVE * wizard.getLevel();
            float damagedrain1 = Constants.ZERO;
            damagedrain1 = (Constants.MODIFICATORW * procent1)
                    * Math.min(Constants.ZEROTHREE * (Constants.WIZARD
                    + this.getLevel() * Constants.THIRTIETH), this.gethp());
            if (instance.getArray(wizard.getCoordX(), wizard.getCoordY()) == Constants.DESERT) {
                damagedrain1 *= Constants.KP;
            }
            //deflect-nu da
            damagedrain1 = Math.round(damagedrain1);
            this.sethp((int) (this.gethp() - damagedrain1));
            //clasa ataca wizard
            float procent2;
            procent2 = Constants.ZEROTWO + Constants.ZZFIVE * this.getLevel();
            float damagedrain2 = Constants.ZERO;
            damagedrain2 = (Constants.MODIFICATORW * procent2)
                    * Math.min(Constants.ZEROTHREE * (Constants.WIZARD + wizard.getLevel()
                    * Constants.THIRTIETH), wizard.gethp());
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.DESERT) {
                damagedrain2 *= Constants.KP;
            }
            //deflect nu da
            damagedrain2 = Math.round(damagedrain2);
            wizard.sethp((int) (wizard.gethp() - damagedrain2));
            //verific daca mai sunt in viata
            this.life();
            wizard.life();
            //verific daca se omoara si daca da adaug experienta
            // verific daca avanseaza in level si actualizez hp
            if (wizard.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - wizard.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.WIZARD + newLevel * Constants.THIRTIETH);
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
