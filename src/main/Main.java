package main;

import java.io.IOException;
import java.util.LinkedList;

import fileio.FileSystem;
import matrix.Map;
import common.Constants;

public final class Main {
    private Main() {
    }
    public static void main(final String[] args) {
        LinkedList<Hero> hero = new LinkedList<>();
        HeroFactory heroFactory = HeroFactory.getInstance();
        //CITIRE
        int n;
        int m;
        int nrPlayers;
        int nrRound;
        try {
            FileSystem fs = new FileSystem(args[0], args[1]);

            n = fs.nextInt();
            m = fs.nextInt();
            Map instance = Map.getInstance(n, m);
            for (int i = 0; i < n; i++) {
                String a;
                a = fs.nextWord();
                for (int j = 0; j < a.length(); j++) {
                    instance.setArray(a.charAt(j), i, j);
                }
            }
            nrPlayers = fs.nextInt();
            for (int i = 0; i < nrPlayers; i++) {
                String a;
                a = fs.nextWord();
                hero.add(heroFactory.createHero(a));
                hero.get(i).setType(a);
                hero.get(i).setCoordX(fs.nextInt());
                hero.get(i).setCoordY(fs.nextInt());
            }
            nrRound = fs.nextInt();
            //de aici incep sa fac rundele
            String move = new String();
            for (int k = 0; k < nrRound; k++) {
                move = fs.nextWord();
                //deplasez jucatorii
                for (int d = 0; d < move.length(); d++) {
                    if (hero.get(d).getStay() <= 0) {
                        if (move.charAt(d) == Constants.RIGHT) {
                            hero.get(d).setCoordY((hero.get(d).getCoordY() + Constants.ONE));
                        }
                        if (move.charAt(d) == Constants.LEFT) {
                            hero.get(d).setCoordY((hero.get(d).getCoordY() - Constants.ONE));
                        }
                        if (move.charAt(d) == Constants.UP) {
                            hero.get(d).setCoordX((hero.get(d).getCoordX() - Constants.ONE));
                        }
                        if (move.charAt(d) == Constants.DOWN) {
                            hero.get(d).setCoordX((hero.get(d).getCoordX() + Constants.ONE));
                        }
                    } else {
                        hero.get(d).setStay(hero.get(d).getStay() - 1);
                    }
                }

                for (int i = 0; i < hero.size() - 1; i++) {
                    for (int j = i + 1; j < hero.size(); j++) {
                        if (hero.get(i).getCoordX() == hero.get(j).getCoordX()
                                && hero.get(i).getCoordY() == hero.get(j).getCoordY()
								&& (hero.get(i).getDie() == 0
								&& hero.get(j).getDie() == 0)) {
                            hero.get(i).fightwith(hero.get(j));
                        }

                    }
                }
            }

            fs.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //output
        try {
            FileSystem f = new FileSystem(args[0], args[1]);
            for (int i = 0; i < hero.size(); i++) {
                if (hero.get(i).getDie() == 1) {
                    f.writeWord(hero.get(i).getType() + " " + "dead");
                    f.writeNewLine();
                } else {
                    f.writeWord(hero.get(i).getType() + " ");
                    f.writeInt(hero.get(i).getLevel());
                    f.writeCharacter((char) Constants.SPACE);
                    f.writeInt(hero.get(i).getxp());
                    f.writeCharacter((char) Constants.SPACE);
                    f.writeInt(hero.get(i).gethp());
                    f.writeCharacter((char) Constants.SPACE);
                    f.writeInt(hero.get(i).getCoordX());
                    f.writeCharacter((char) Constants.SPACE);
                    f.writeInt(hero.get(i).getCoordY());
                    f.writeNewLine();

                }
            }
            f.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }


    }
}
