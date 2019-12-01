package main;
import common.Constants;
import matrix.map;

public class Rogue extends Hero {
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
              damageExecuteBase1 = this.gethp();
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase1 = (Constants.TWOH + Constants.THIRTIETH * knight.getLevel()) * Constants.KR;
                if (instance.getArray(knight.getCoord_x(), knight.getCoord_y()) == Constants.LAND) {
                    damageExecuteBase1 = Constants.MODIFICATORL * damageExecuteBase1;
                }
                damageExecuteBase1 = Math.round(damageExecuteBase1);
            }
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

            //this.rogue ataca pe knight
            this.setHit(this.getHit() + 1);
            float damageBackstab2 = Constants.ZERO;
            damageBackstab2 = (Constants.TWOH + this.getLevel() * Constants.LEVELI) * Constants.MODIFICATORP;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
                damageBackstab2 = damageBackstab2 * Constants.KR;
            }
            //hit
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS && this.getHit() % 3 == 1)/*??*/ {
                damageBackstab2 = damageBackstab2 * Constants.ONEFIVE;
            }
            damageBackstab2 = Math.round(damageBackstab2);
            //rogue/this-Paralysis
            float damageParalysis2 = Constants.ZERO;
            damageParalysis2 = (Constants.FORTY + Constants.TEN * this.getLevel()) * Constants.KW;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
                damageParalysis2 = damageParalysis2 * Constants.KR;
            }
            damageParalysis2 = Math.round(damageParalysis2);
            float damage2;
            damage2 = damageBackstab2 + damageParalysis2;
            knight.sethp((int) (knight.gethp() - damage2));
            //damage extins si paralizie 3/6 si se suprascriu si celelate overtime
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
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
            //verific daca se omoara si daca da adaug experienta//verific daca avanseaza in level si actualizez hp
            if (knight.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (this.getLevel() - knight.getLevel()) * Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int) (this.getxp() / 50) - 4;
                if (new_level > old_level) {
                    this.setLevel(new_level);
                    this.sethp(Constants.ROGUE + new_level * Constants.FORTY);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat knight
                knight.setxp(knight.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (knight.getLevel() - this.getLevel()) * Constants.FORTY));
                int old_level = knight.getLevel();
                int new_level = (int) (knight.getxp() / 50) - 4;
                if (new_level > old_level) {
                    knight.setLevel(new_level);
                    knight.sethp(Constants.KNIGHT + new_level * Constants.EIGHTY);
                }
            }

        }
    }

    @Override
    public void salute(Pyromancer pyromancer) {
        System.out.println("14");
        /*P.R*/
        map instance = map.getInstance();
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
            float damageFireblastBase = Constants.MODIFICATORR * (Constants.DAMAGEFB + pyromancer.getLevel() * Constants.LEVELFB);
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float damageIgniteBase = Constants.MODIFICATORR * (Constants.DAMAGEI + pyromancer.getLevel() * Constants.LEVELI);
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageIgniteBase = Constants.MODIFICATORV * damageIgniteBase;
            }
            damageIgniteBase = Math.round(damageIgniteBase);
            float damage;
            damage = damageFireblastBase + damageIgniteBase;
            this.sethp((int) (this.gethp() - damage));
            float damageOvertime;
            damageOvertime = Constants.MODIFICATORR * (Constants.LEVELFB + Constants.THIRTIETH * pyromancer.getLevel());
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
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
            damageBackstab2 = (Constants.TWOH + this.getLevel() * Constants.LEVELI) * Constants.MODIFICATORV;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
                damageBackstab2 = damageBackstab2 * Constants.KR;
            }
            //hit
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS && this.getHit() % 3 == 1)/*??*/ {
                damageBackstab2 = damageBackstab2 * Constants.ONEFIVE;
            }
            damageBackstab2 = Math.round(damageBackstab2);
            //rogue-Paralysis
            float damageParalysis2 = Constants.ZERO;
            damageParalysis2 = (Constants.FORTY + Constants.TEN * this.getLevel()) * Constants.MODIFICATORK;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
                damageParalysis2 = damageParalysis2 * Constants.KR;
            }
            damageParalysis2 = Math.round(damageParalysis2);
            float damage2;
            damage2 = damageBackstab2 + damageParalysis2;
            pyromancer.sethp((int) (pyromancer.gethp() - damage2));
            //damage extins si paralizie 3/6 si se suprascriu si celelate overtime
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
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
            //verific daca se omoara si daca da adaug experienta//verific daca avanseaza in level si actualizez hp
            if (pyromancer.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (this.getLevel() - pyromancer.getLevel()) * Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int) (this.getxp() / 50) - 4;
                if (new_level > old_level) {
                    this.setLevel(new_level);
                    this.sethp(Constants.ROGUE + new_level * Constants.FORTY);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat pyromancer
                pyromancer.setxp(pyromancer.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (pyromancer.getLevel() - this.getLevel()) * Constants.FORTY));
                int old_level = pyromancer.getLevel();
                int new_level = (int) (pyromancer.getxp() / 50) - 4;
                if (new_level > old_level) {
                    pyromancer.setLevel(new_level);
                    pyromancer.sethp(Constants.PYROMANCER + new_level * Constants.LEVELFB);
                }
            }
        }
    }

    @Override
    public void salute(Rogue rogue) {
        System.out.println("15");
        /*R.R*/
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
            //rogue ataca clasa/this/rogue
            //rogue-Backstab
            rogue.setHit(rogue.getHit() + 1);
            float damageBackstab1 = Constants.ZERO;
            damageBackstab1 = (Constants.TWOH + rogue.getLevel() * Constants.LEVELI) * Constants.MODIFICATORK;
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS) {
                damageBackstab1 = damageBackstab1 * Constants.KR;
            }
            //hit
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS && rogue.getHit() % 3 == 1)/*??*/ {
                damageBackstab1 = damageBackstab1 * Constants.ONEFIVE;
            }
            damageBackstab1 = Math.round(damageBackstab1);
            //rogue-Paralysis
            float damageParalysis1 = Constants.ZERO;
            damageParalysis1 = (Constants.FORTY + Constants.TEN * rogue.getLevel()) * Constants.MODIFICATORP;
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS) {
                damageParalysis1 = damageParalysis1 * Constants.KR;
            }
            damageParalysis1 = Math.round(damageParalysis1);
            float damage1;
            damage1 = damageBackstab1 + damageParalysis1;
            this.sethp((int) (this.gethp() - damage1));
            //damage extins si paralizie 3/6
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS) {
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
            damageBackstab2 = (Constants.TWOH + this.getLevel() * Constants.LEVELI) * Constants.MODIFICATORK;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
                damageBackstab2 = damageBackstab2 * Constants.KR;
            }
            //hit
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS && this.getHit() % 3 == 1)/*??*/ {
                damageBackstab2 = damageBackstab2 * Constants.ONEFIVE;
            }
            damageBackstab2 = Math.round(damageBackstab2);
            //rogue-Paralysis
            float damageParalysis2 = Constants.ZERO;
            damageParalysis2 = (Constants.FORTY + Constants.TEN * this.getLevel()) * Constants.MODIFICATORP;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
                damageParalysis2 = damageParalysis2 * Constants.KR;
            }
            damageParalysis2 = Math.round(damageParalysis2);
            float damage2;
            damage2 = damageBackstab2 + damageParalysis2;
            rogue.sethp((int) (rogue.gethp() - damage2));
            //damage extins si paralizie 3/6 si se suprascriu si celelate overtime
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
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
            //verific daca se omoara si daca da adaug experienta//verific daca avanseaza in level si actualizez hp
            if (rogue.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (this.getLevel() - rogue.getLevel()) * Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int) (this.getxp() / 50) - 4;
                if (new_level > old_level) {
                    this.setLevel(new_level);
                    this.sethp(Constants.ROGUE + new_level * Constants.FORTY);
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
        System.out.println("16");
        /*W.R*/
        map instance = map.getInstance();
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
            damageBackstab2 = (Constants.TWOH + this.getLevel() * Constants.LEVELI) * Constants.MODIFICATORV;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
                damageBackstab2 = damageBackstab2 * Constants.KR;
            }
            //hit
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS && this.getHit() % 3 == 1)/*??*/ {
                damageBackstab2 = damageBackstab2 * Constants.ONEFIVE;
            }
            float prebackstab = Math.round(damageBackstab2/Constants.MODIFICATORV);
            damageBackstab2 = Math.round(damageBackstab2);
            //rogue-Paralysis
            float damageParalysis2 = Constants.ZERO;
            damageParalysis2 = (Constants.FORTY + Constants.TEN * this.getLevel()) * Constants.MODIFICATORV;
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
                damageParalysis2 = damageParalysis2 * Constants.KR;
            }
            float preparalysis = Math.round(damageParalysis2/Constants.MODIFICATORV);
            damageParalysis2 = Math.round(damageParalysis2);
            float damage2;
            damage2 = damageBackstab2 + damageParalysis2;
            wizard.sethp((int) (wizard.gethp() - damage2));
            //damage extins si paralizie 3/6 si se suprascriu si celelate overtime
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.WOODS) {
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
            damagedrain1 = (Constants.MODIFICATORR * procent1) * Math.min(Constants.ZEROTHREE * (Constants.ROGUE + this.getLevel() * Constants.FORTY), this.gethp());
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
            damagedeflect = (procent2 * Constants.MODIFICATORK) * (prebackstab + preparalysis);
            if(instance.getArray(wizard.getCoord_x(), wizard.getCoord_y()) == Constants.DESERT){
                damagedeflect*=Constants.KP;
            }
            damagedeflect = Math.round(damagedeflect);
            float damage = damagedrain1 + damagedeflect;
            this.sethp((int) (this.gethp() - damage));

            //verific daca mai traiesc
            this.life();
            wizard.life();
            //verific daca se omoara si daca da adaug experienta//verific daca avanseaza in level si actualizez hp
            if (wizard.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (this.getLevel() - wizard.getLevel()) * Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int) (this.getxp() / 50) - 4;
                if (new_level > old_level) {
                    this.setLevel(new_level);
                    this.sethp(Constants.ROGUE + new_level * Constants.FORTY);
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
