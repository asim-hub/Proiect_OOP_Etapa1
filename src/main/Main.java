package main;

import java.util.LinkedList;
import fileio.FileSystem;
import matrix.map;
import common.Constants;
public class Main {
	
	 public static void main(String[] args) {
	 	 LinkedList<Hero> hero = new LinkedList<>();
		 HeroFactory heroFactory = HeroFactory.getInstance();
		 /*CITIRE*/
		 int n;
		 int m;
		 int nr_players;
		 int nr_round;
		 try {
			 FileSystem fs = new FileSystem(args[0], args[1]);

			 n = fs.nextInt();
			 m = fs.nextInt();
			 map instance = map.getInstance(n,m);
			 for (int i = 0; i < n; i++){
			 	String a;
			 	a = fs.nextWord();
			 	for (int j = 0; j < a.length(); j++) {
					instance.setArray(a.charAt(j), i, j);
				}
			 }
			 nr_players = fs.nextInt();
			 for (int i = 0; i < nr_players; i++){
			 	 String a;
			 	 a = fs.nextWord();
				 hero.add(heroFactory.createHero(a));
				 hero.get(i).setType(a);
				 hero.get(i).setCoord_x(fs.nextInt());
				 hero.get(i).setCoord_y(fs.nextInt());
			 }
			 nr_round = fs.nextInt();
			 //de aici incep sa fac rundele
			 String move =new String();
			 for ( int k = 0; k < nr_round; k++){
			 	 move = fs.nextWord();
			 	 //deplasez jucatorii
				 for(int d = 0; d < move.length(); d++){
				     if(hero.get(d).getStay()<=0) {
                         if (move.charAt(d) == Constants.RIGHT) {
                             hero.get(d).setCoord_y((hero.get(d).getCoord_y() + Constants.ONE));
                         }
                         if (move.charAt(d) == Constants.LEFT) {
                             hero.get(d).setCoord_y((hero.get(d).getCoord_y() - Constants.ONE));
                         }
                         if (move.charAt(d) == Constants.UP) {
                             hero.get(d).setCoord_x((hero.get(d).getCoord_x() - Constants.ONE));
                         }
                         if (move.charAt(d) == Constants.DOWN) {
                             hero.get(d).setCoord_x((hero.get(d).getCoord_x() + Constants.ONE));
                         }
                     }else{
				         hero.get(d).setStay(hero.get(d).getStay()-1);
                     }
				 }
				 //aici ii lupt
                 /*System.out.println(hero.get(0).getCoord_x() + " " + hero.get(0).getCoord_y());
                 System.out.println(hero.get(1).getCoord_x() + " " + hero.get(1).getCoord_y());
                 System.out.println(hero.get(2).getCoord_x() + " " + hero.get(2).getCoord_y());
                 System.out.println(hero.get(3).getCoord_x() + " " + hero.get(3).getCoord_y());*/

				 for (int i = 0; i < hero.size()-1; i++) {
				    for (int j = i+1; j < hero.size(); j++) {
				        if(hero.get(i).getCoord_x() == hero.get(j).getCoord_x() &&
                                hero.get(i).getCoord_y() == hero.get(j).getCoord_y() &&
                                hero.get(i).getDie() == 0 && hero.get(j).getDie() == 0) {
                            hero.get(i).fightwith(hero.get(j));
                            //System.out.println(hero.get(i).gethp());
                            //System.out.println(hero.get(j).gethp());

                        }
				    }
				 }
			 }
			 fs.close();

		 } catch (Exception e1) {
			 e1.printStackTrace();
		 }
	     //output:
         for (int i = 0; i < hero.size(); i++){
             if(hero.get(i).getDie() == 1){
                 System.out.println(hero.get(i).getType() + " " + "dead");
             }else{
                 System.out.println(hero.get(i).getType() + " " + hero.get(i).getLevel() +
                         " " + hero.get(i).getxp()+" "+ hero.get(i).gethp() + " " + hero.get(i).getCoord_x() + " " +
                         hero.get(i).getCoord_y());
             }
         }

	 }
}
