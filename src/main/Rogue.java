package main;

import common.Constants;
import matrix.Map;

public class Rogue extends Hero {
    private Integer hit;

    public final void setHit(final Integer hit) {
        this.hit = hit;
    }

    public final Integer getHit() {
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
        //aplic lui this/rogue overtime daca are
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if (this.getDie() == 0 && knight.getDie() == 0) {
            //knight ataca pe rogue/this
            float hplimit1;
            float damageExecuteBase1;
            // verific daca clasa/rogue moare din prima
            if (Constants.ZEROZEROONE * knight.getLevel() < Constants.ZEROTWO) {
                hplimit1 = (float) ((Constants.ZEROTWO
                        + Constants.ZEROZEROONE * knight.getLevel())
                        * (Constants.ROGUE + this.getLevel() * Constants.FORTY));
            } else {
                hplimit1 = (float) ((2 * Constants.ZEROTWO)
                        * (Constants.ROGUE + this.getLevel() * Constants.FORTY));
            }
            if (hplimit1 >= this.gethp()) {
                damageExecuteBase1 = this.gethp();
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase1 = (Constants.TWOH + Constants.THIRTIETH
                        * knight.getLevel()) * Constants.KR;
                if (instance.getArray(knight.getCoordX(), knight.getCoordY()) == Constants.LAND) {
                    damageExecuteBase1 = Constants.MODIFICATORL * damageExecuteBase1;
                }
                damageExecuteBase1 = Math.round(damageExecuteBase1);
            }
            //knight-slam
            float damageSlamBase1 = (Constants.ONEH + Constants.FORTY
                    * knight.getLevel()) * Constants.KW;
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
            //this.rogue ataca pe knight
            this.setHit(this.getHit() + 1);
            float damageBackstab2 = Constants.ZERO;
            damageBackstab2 = (Constants.TWOH + this.getLevel()
                    * Constants.LEVELI) * Constants.MODIFICATORP;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                damageBackstab2 = damageBackstab2 * Constants.KR;
            }
            //hit
            if (instance.getArray(this.getCoordX(), this.getCoordY())
                    == Constants.WOODS && this.getHit() % Constants.THREE == Constants.ONE) {
                damageBackstab2 = damageBackstab2 * Constants.ONEFIVE;
            }
            damageBackstab2 = Math.round(damageBackstab2);
            //rogue/this-Paralysis
            float damageParalysis2 = Constants.ZERO;
            damageParalysis2 = (Constants.FORTY + Constants.TEN * this.getLevel()) * Constants.KW;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                damageParalysis2 = damageParalysis2 * Constants.KR;
            }
            damageParalysis2 = Math.round(damageParalysis2);
            float damage2;
            damage2 = damageBackstab2 + damageParalysis2;
            knight.sethp((int) (knight.gethp() - damage2));
            //damage extins si paralizie 3/6 si se suprascriu si celelate overtime
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                knight.setDemageOvertime((int) damageParalysis2);
                knight.setNrRoundOvertime(Constants.SIX);
                knight.setStay(Constants.SIX);
            } else {
                knight.setDemageOvertime((int) damageParalysis2);
                knight.setNrRoundOvertime(Constants.THREE);
                knight.setStay(Constants.THREE);
            }
            //verific daca mai traiesc
            this.life();
            knight.life();
            //verific daca se omoara si daca da adaug experienta
            // verific daca avanseaza in level si actualizez hp
            if (knight.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH
                        - (this.getLevel() - knight.getLevel()) * Constants.FORTY));
                int oldLevel = this.getLevel();
                int newLevel = (int) (this.getxp() / Constants.FIFTY) - Constants.FOUR;
                if (newLevel > oldLevel) {
                    this.setLevel(newLevel);
                    this.sethp(Constants.ROGUE + newLevel * Constants.FORTY);
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
        if (pyromancer.getNrRoundOvertime() > 0) {
            pyromancer.sethp(pyromancer.gethp() - pyromancer.getDemageOvertime());
            pyromancer.setNrRoundOvertime(pyromancer.getNrRoundOvertime() - 1);
            pyromancer.life();
        }
        //aplic lui this/clasa overtimedamage
        if (this.getNrRoundOvertime() > 0) {
            this.sethp(this.gethp() - this.getDemageOvertime());
            this.setNrRoundOvertime(this.getNrRoundOvertime() - 1);
            this.life();
        }
        if (this.getDie() == 0 && pyromancer.getDie() == 0) {
            //Pyromancer-Fireblast
            float damageFireblastBase = Constants.MODIFICATORR
                    * (Constants.DAMAGEFB + pyromancer.getLevel() * Constants.LEVELFB);
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float damageIgniteBase = Constants.MODIFICATORR
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
            damageOvertime = Constants.MODIFICATORR
                    * (Constants.LEVELFB + Constants.THIRTIETH * pyromancer.getLevel());
            if (instance.getArray(pyromancer.getCoordX(), pyromancer.getCoordY())
                    == Constants.VOLCANIC) {
                damageOvertime = Constants.MODIFICATORV * damageOvertime;
            }
            damageOvertime = Math.round(damageOvertime);
            //incapacitatea de miscare-> sterg celelate overtime
            this.setStay(Constants.ZERO);
            this.setDemageOvertime((int) damageOvertime);
            this.setNrRoundOvertime(Constants.TWO);
            //this/clasa ataca
            this.setHit(this.getHit() + 1);
            float damageBackstab2 = Constants.ZERO;
            damageBackstab2 = (Constants.TWOH + this.getLevel()
                    * Constants.LEVELI) * Constants.MODIFICATORV;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                damageBackstab2 = damageBackstab2 * Constants.KR;
            }
            //hit
            if (instance.getArray(this.getCoordX(), this.getCoordY())
                    == Constants.WOODS && this.getHit() % Constants.THREE == Constants.ONE) {
                damageBackstab2 = damageBackstab2 * Constants.ONEFIVE;
            }
            damageBackstab2 = Math.round(damageBackstab2);
            //rogue-Paralysis
            float damageParalysis2 = Constants.ZERO;
            damageParalysis2 = (Constants.FORTY + Constants.TEN
                    * this.getLevel()) * Constants.MODIFICATORK;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                damageParalysis2 = damageParalysis2 * Constants.KR;
            }
            damageParalysis2 = Math.round(damageParalysis2);
            float damage2;
            damage2 = damageBackstab2 + damageParalysis2;
            pyromancer.sethp((int) (pyromancer.gethp() - damage2));
            //damage extins si paralizie 3/6 si se suprascriu si celelate overtime
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                pyromancer.setDemageOvertime((int) damageParalysis2);
                pyromancer.setNrRoundOvertime(Constants.SIX);
                pyromancer.setStay(Constants.SIX);
            } else {
                pyromancer.setDemageOvertime((int) damageParalysis2);
                pyromancer.setNrRoundOvertime(Constants.THREE);
                pyromancer.setStay(Constants.THREE);
            }
            //verific viata
            this.life();
            pyromancer.life();
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
                    this.sethp(Constants.ROGUE + newLevel * Constants.FORTY);
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
                    pyromancer.sethp(Constants.PYROMANCER + newLevel * Constants.LEVELFB);
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
            //rogue ataca clasa/this/rogue
            //rogue-Backstab
            rogue.setHit(rogue.getHit() + 1);
            float damageBackstab1 = Constants.ZERO;
            damageBackstab1 = (Constants.TWOH + rogue.getLevel() * Constants.LEVELI)
                    * Constants.MODIFICATORK;
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY()) == Constants.WOODS) {
                damageBackstab1 = damageBackstab1 * Constants.KR;
            }
            //hit
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY())
                    == Constants.WOODS && rogue.getHit() % Constants.THREE == Constants.ONE)/*??*/ {
                damageBackstab1 = damageBackstab1 * Constants.ONEFIVE;
            }
            damageBackstab1 = Math.round(damageBackstab1);
            //rogue-Paralysis
            float damageParalysis1 = Constants.ZERO;
            damageParalysis1 = (Constants.FORTY + Constants.TEN
                    * rogue.getLevel()) * Constants.MODIFICATORP;
            if (instance.getArray(rogue.getCoordX(), rogue.getCoordY())
                    == Constants.WOODS) {
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
            //clasa/this ataca rogue
            this.setHit(this.getHit() + 1);
            float damageBackstab2 = Constants.ZERO;
            damageBackstab2 = (Constants.TWOH + this.getLevel()
                    * Constants.LEVELI) * Constants.MODIFICATORK;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                damageBackstab2 = damageBackstab2 * Constants.KR;
            }
            //hit
            if (instance.getArray(this.getCoordX(), this.getCoordY())
                    == Constants.WOODS && this.getHit() % Constants.THREE == Constants.ONE) {
                damageBackstab2 = damageBackstab2 * Constants.ONEFIVE;
            }
            damageBackstab2 = Math.round(damageBackstab2);
            //rogue-Paralysis
            float damageParalysis2 = Constants.ZERO;
            damageParalysis2 = (Constants.FORTY + Constants.TEN
                    * this.getLevel()) * Constants.MODIFICATORP;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                damageParalysis2 = damageParalysis2 * Constants.KR;
            }
            damageParalysis2 = Math.round(damageParalysis2);
            float damage2;
            damage2 = damageBackstab2 + damageParalysis2;
            rogue.sethp((int) (rogue.gethp() - damage2));
            //damage extins si paralizie 3/6 si se suprascriu si celelate overtime
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                rogue.setDemageOvertime((int) damageParalysis2);
                rogue.setNrRoundOvertime(Constants.SIX);
                rogue.setStay(Constants.SIX);
            } else {
                rogue.setDemageOvertime((int) damageParalysis2);
                rogue.setNrRoundOvertime(Constants.THREE);
                rogue.setStay(Constants.THREE);
            }
            //verific daca mai traiesc
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
                    this.sethp(Constants.ROGUE + newLevel * Constants.FORTY);
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
            //clasa/this.rogue ataca wizard
            this.setHit(this.getHit() + 1);
            float damageBackstab2 = Constants.ZERO;
            damageBackstab2 = (Constants.TWOH + this.getLevel() * Constants.LEVELI)
                    * Constants.MODIFICATORV;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                damageBackstab2 = damageBackstab2 * Constants.KR;
            }
            //hit
            if (instance.getArray(this.getCoordX(), this.getCoordY())
                    == Constants.WOODS && this.getHit() % Constants.THREE == Constants.ONE) {
                damageBackstab2 = damageBackstab2 * Constants.ONEFIVE;
            }
            float prebackstab = Math.round(damageBackstab2 / Constants.MODIFICATORV);
            damageBackstab2 = Math.round(damageBackstab2);
            //rogue-Paralysis
            float damageParalysis2 = Constants.ZERO;
            damageParalysis2 = (Constants.FORTY + Constants.TEN
                    * this.getLevel()) * Constants.MODIFICATORV;
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                damageParalysis2 = damageParalysis2 * Constants.KR;
            }
            float preparalysis = Math.round(damageParalysis2 / Constants.MODIFICATORV);
            damageParalysis2 = Math.round(damageParalysis2);
            float damage2;
            damage2 = damageBackstab2 + damageParalysis2;
            wizard.sethp((int) (wizard.gethp() - damage2));
            //damage extins si paralizie 3/6 si se suprascriu si celelate overtime
            if (instance.getArray(this.getCoordX(), this.getCoordY()) == Constants.WOODS) {
                wizard.setDemageOvertime((int) damageParalysis2);
                wizard.setNrRoundOvertime(Constants.SIX);
                wizard.setStay(Constants.SIX);
            } else {
                wizard.setDemageOvertime((int) damageParalysis2);
                wizard.setNrRoundOvertime(Constants.THREE);
                wizard.setStay(Constants.THREE);
            }
            //wizard ataca clasa rogue
            //drain
            float procent1;
            procent1 = Constants.ZEROTWO + Constants.ZZFIVE * wizard.getLevel();
            float damagedrain1 = Constants.ZERO;
            damagedrain1 = (Constants.MODIFICATORR * procent1)
                    * Math.min(Constants.ZEROTHREE * (Constants.ROGUE
                    + this.getLevel() * Constants.FORTY), this.gethp());
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
            damagedeflect = (procent2 * Constants.MODIFICATORK) * (prebackstab + preparalysis);
            if (instance.getArray(wizard.getCoordX(), wizard.getCoordY()) == Constants.DESERT) {
                damagedeflect *= Constants.KP;
            }
            damagedeflect = Math.round(damagedeflect);
            float damage = damagedrain1 + damagedeflect;
            this.sethp((int) (this.gethp() - damage));

            //verific daca mai traiesc
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
                    this.sethp(Constants.ROGUE + newLevel * Constants.FORTY);
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
