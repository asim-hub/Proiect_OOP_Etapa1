package main;
import common.Constants;
import matrix.map;
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
    public void fightwith(Hero hero) {
        hero.salute(this);
    }

    @Override
    public void salute(Knight knight) {
        System.out.println("9");
        map instance = map.getInstance();
        /*K.W*/
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
            float preexecute;
            if (HPLIMIT1 >= this.gethp()) {
                damageExecuteBase1 = this.gethp();
                preexecute=damageExecuteBase1;
            } else {
                //daca nu moare din prima fac abilitatile
                //knight-execute
                damageExecuteBase1 = (Constants.TWOH + Constants.THIRTIETH * knight.getLevel()) * Constants.KW;
                if (instance.getArray(knight.getCoord_x(), knight.getCoord_y()) == Constants.LAND) {
                    damageExecuteBase1 = Constants.MODIFICATORL * damageExecuteBase1;
                }
                preexecute=Math.round(damageExecuteBase1/Constants.KW);
                damageExecuteBase1 = Math.round(damageExecuteBase1);
            }
                //knight-slam
                float preslam;
                float damageSlamBase1 = (Constants.ONEH + Constants.FORTY * knight.getLevel()) * Constants.MODIFICATORW;
                if (instance.getArray(knight.getCoord_x(), knight.getCoord_y()) == Constants.LAND) {
                    damageSlamBase1 = Constants.MODIFICATORL * damageSlamBase1;
                }
                preslam = Math.round(damageSlamBase1/Constants.MODIFICATORW);
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
            damagedrain1 = (Constants.MODIFICATORP * procent1) * Math.min(Constants.ZEROTHREE * (Constants.KNIGHT + knight.getLevel() * Constants.EIGHTY), knight.gethp());
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.DESERT) {
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
            damagedeflect = (procent2 * Constants.ONETHREE) * (preexecute + preslam);
            damagedeflect = Math.round(damagedeflect);
            float damage = damagedrain1 + damagedeflect;
            knight.sethp((int) (knight.gethp() - damage));
            //verific daca mai sunt in viata
            this.life();
            knight.life();
            if (knight.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (this.getLevel() - knight.getLevel()) * Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int) (this.getxp() / 50) - 4;
                if (new_level > old_level) {
                    this.setLevel(new_level);
                    this.sethp(Constants.WIZARD + new_level * Constants.THIRTIETH);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat wizard
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
        System.out.println("10");
        /*P.W*/
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
        map instance = map.getInstance();
        if (this.getDie() == 0 && pyromancer.getDie() == 0) {
            //Pyromancer-Fireblast

            float damageFireblastBase = Constants.MODIFICATORW * (Constants.DAMAGEFB + pyromancer.getLevel() * Constants.LEVELFB);
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageFireblastBase = Constants.MODIFICATORV * damageFireblastBase;
            }
            float prefireblast=Math.round(damageFireblastBase/Constants.MODIFICATORW);
            damageFireblastBase = Math.round(damageFireblastBase);
            //Pyromancer-Ignite
            float damageIgniteBase = Constants.MODIFICATORW * (Constants.DAMAGEI + pyromancer.getLevel() * Constants.LEVELI);
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
                damageIgniteBase = Constants.MODIFICATORV * damageIgniteBase;
            }
            float preignite = Math.round(damageIgniteBase/Constants.MODIFICATORW);
            damageIgniteBase = Math.round(damageIgniteBase);
            float damage;
            damage = damageFireblastBase + damageIgniteBase;
            this.sethp((int) (this.gethp() - damage));
            float damageOvertime = Constants.ZERO;
            damageOvertime = Constants.MODIFICATORW * (Constants.LEVELFB + Constants.THIRTIETH * pyromancer.getLevel());
            if (instance.getArray(pyromancer.getCoord_x(), pyromancer.getCoord_y()) == Constants.VOLCANIC) {
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
            damagedrain1 = (Constants.MODIFICATORP * procent1) * Math.min(Constants.ZEROTHREE * (Constants.ROGUE + pyromancer.getLevel() * Constants.FORTY), pyromancer.gethp());
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.DESERT) {
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
            damagedeflect = (procent2 * Constants.ONETHREE) * (prefireblast+ preignite);
            damagedeflect = Math.round(damagedeflect);
            float damage1 = damagedrain1 + damagedeflect;
            pyromancer.sethp((int) (pyromancer.gethp() - damage1));
            //verific daca mai sunt in viata
            this.life();
            pyromancer.life();
            if (pyromancer.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (this.getLevel() - pyromancer.getLevel()) * Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int) (this.getxp() / 50) - 4;
                if (new_level > old_level) {
                    this.setLevel(new_level);
                    this.sethp(Constants.WIZARD + new_level * Constants.THIRTIETH);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat wizard
                pyromancer.setxp(pyromancer.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (pyromancer.getLevel() - this.getLevel()) * Constants.FORTY));
                int old_level = pyromancer.getLevel();
                int new_level = (int) (pyromancer.getxp() / 50) - 4;
                if (new_level > old_level) {
                    pyromancer.setLevel(new_level);
                    pyromancer.sethp(Constants.PYROMANCER + new_level * Constants.FIFTY);
                }
            }
        }
    }
    @Override
    public void salute(Rogue rogue) {
        System.out.println("11");
        /*R.W*/
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
            //rogue ataca clasa/this/wizard
            //rogue-Backstab
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
            float predamageBack = Math.round(damageBackstab1/ Constants.MODIFICATORV);
            damageBackstab1 = Math.round(damageBackstab1);

            //rogue-Paralysis
            float damageParalysis1 = Constants.ZERO;
            damageParalysis1 = (Constants.FORTY + Constants.TEN * rogue.getLevel()) * Constants.MODIFICATORV;
            if (instance.getArray(rogue.getCoord_x(), rogue.getCoord_y()) == Constants.WOODS) {
                damageParalysis1 = damageParalysis1 * Constants.KR;
            }
            float predamageParalysis =Math.round(damageParalysis1/Constants.MODIFICATORV);
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
            //wizard/clasa ataca rogue
            //drain
            float procent1;
            procent1 =Constants.ZEROTWO + Constants.ZZFIVE*this.getLevel();
            float damagedrain1 = Constants.ZERO;
            damagedrain1 = (Constants.MODIFICATORR*procent1)*Math.min(Constants.ZEROTHREE*(Constants.ROGUE + rogue.getLevel()*Constants.FORTY), rogue.gethp());
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.DESERT){
                damagedrain1*=Constants.KP;
            }
            damagedrain1=Math.round(damagedrain1);
            //Deflect
            float procent2;
            procent2= Constants.ZTHREEFIVE + Constants.ZZTWO*this.getLevel();
            if(procent2 >Constants.MAXPRO){
                procent2 = Constants.MAXPRO;
            }

            float damagedeflect;
            damagedeflect=(procent2*Constants.MODIFICATORK)*(predamageBack+predamageParalysis);
            damagedeflect = Math.round(damagedeflect);
            float damage=damagedrain1 + damagedeflect;
            rogue.sethp((int) (rogue.gethp()-damage));
            //verific daca mai sunt in viata
            this.life();
            rogue.life();
            if (rogue.getDie() == 1) {
                //insemna ca la omorat clasa/this
                this.setxp(this.getxp() + Math.max(Constants.ZERO, Constants.TWOH -
                        (this.getLevel() - rogue.getLevel()) * Constants.FORTY));
                int old_level = this.getLevel();
                int new_level = (int) (this.getxp() / 50) - 4;
                if (new_level > old_level) {
                    this.setLevel(new_level);
                    this.sethp(Constants.WIZARD + new_level * Constants.THIRTIETH);
                }
            }
            if (this.getDie() == 1) {
                //insemna ca la omorat wizard
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
        System.out.println("12");
        /*W.W*/
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

            //wizard ataca clasa
            //drian
            float procent1;
            procent1 =Constants.ZEROTWO + Constants.ZZFIVE*wizard.getLevel();
            float damagedrain1 = Constants.ZERO;
            damagedrain1 = (Constants.MODIFICATORW*procent1)*Math.min(Constants.ZEROTHREE*(Constants.WIZARD + this.getLevel()*Constants.THIRTIETH), this.gethp());
            if (instance.getArray(wizard.getCoord_x(), wizard.getCoord_y()) == Constants.DESERT){
                damagedrain1*=Constants.KP;
            }
            //deflect-nu da
            damagedrain1=Math.round(damagedrain1);
            this.sethp((int) (this.gethp()-damagedrain1));
            //clasa ataca wizard
            float procent2;
            procent2=Constants.ZEROTWO + Constants.ZZFIVE*this.getLevel();
            float damagedrain2 = Constants.ZERO;
            damagedrain2 = (Constants.MODIFICATORW*procent1)*Math.min(Constants.ZEROTHREE*(Constants.WIZARD + wizard.getLevel()*Constants.THIRTIETH), wizard.gethp());
            if (instance.getArray(this.getCoord_x(), this.getCoord_y()) == Constants.DESERT){
                damagedrain2*=Constants.KP;
            }
            //deflect nu da
            damagedrain2=Math.round(damagedrain2);
            wizard.sethp((int) (wizard.gethp()-damagedrain2));
            //verific daca mai sunt in viata
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
                    this.sethp(Constants.WIZARD + new_level * Constants.THIRTIETH);
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
