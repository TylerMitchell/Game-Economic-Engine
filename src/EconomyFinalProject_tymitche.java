import java.util.*;

public class EconomyFinalProject_tymitche {
	// how do i get this to work? public static enum Profession {None, Laborer, Guard, Metalsmith, Carpenter, farmer, taxcollector, bandit, miner, logger, clothier, landlord, trader, builder, priest, doctor, police };
	public enum needs {};
	public static boolean minmet=false;
	public static boolean maxmet=false;
	public static Kingdom[] kingdoms = new Kingdom[4];
	public static int men=100;
	public static int wemen =100;
	public static int children=300;
	public static int malechildpercent = 50;
	public static float marrige = .8f;
	public static int[][] professionbounds = {{0,0},{22,56},{8,12},{3,5},{2,4},{4,7},{1,2},{2,5},{4,8},{2,3},{4,8},{1,3},{2,3},{2,5},{1,2},{1,2},{6,10}};// lower and upper % for each profession
	public static int[] professionassign = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	public static int[][] savingsavg = {{8000,9000},{600,833},{100,250}}; //average starting savings for rich middle then poor classes
	public static int[] wealthdistrobution = {5,17};//5%rich 12%middle class, everyone else poor
	public static int hitch;
	public static int peeps,countpop;

	public static int[] agearr = {40,20,20,10,5,3,1,1};//represents ages 0-9,9-13,13-18,18-22,22-25,25-30,30-35,35-65 the percentages of each age range
	public static final int[][] agebounds = {
		{0,9},{9,13},{13,18},{18,22},{22,25},{25,30},{30,35},{35,65}
	};
	public static int[][] professionitems= {{0,0},{0,0},{0,0},{16,30},{11,15},{31,39},{0,0},{0,0},{6,10},{1,5},{40,47},{0,0},{0,0},{53,57},{0,0},{0,0},{0,0}};
	public static Kingdom place1,place2,place3,place4;
	public static Item[][] i,i2;
	
	public static void main(String[] args) {
		place1= GenerateKingdom(500, "Sandcastle",place1);
		menu();
		new GlobalDataStore(place1);
		
	}
	public static void menu(){
		place1.updatekingdomstats();
		System.out.println(place1.men);
		System.out.println(place1.wemen);
		System.out.println(place1.avgsavings);
		System.out.println(countpop);
		EconGUI screen = new EconGUI(place1);
	}
	public static Kingdom GenerateKingdom(int pop, String name, Kingdom p){
		/*in status slot 0 1=male 2=female 3=malechild 4=femalechild
		 *in status slot 1 if setages is sent true it picks an adult age else it picks a child age
		 *
		 */
		i=new Item[pop][150];
		i2=new Item[pop][5];
		peeps=pop;
		children=300;
		countpop = pop;
		Kingdom pl = p;
		pl = new Kingdom(name,pop);
		if((men)*marrige <= (wemen)*marrige){
			hitch = (int)(((float)men)*marrige);
			countpop -= 2*((men)*marrige);
		}
		else{
			hitch = (int)(((float)wemen)*marrige);
			countpop -= 2*((wemen)*marrige);
		}
		int mc=men,fc=wemen;
		int[][][] family = new int[hitch][6][10]; //temporary structure to hold values [pop][family members][sex age employment savings]
		for(int counter=0;counter<hitch;counter++){
			int limit = numberchildren();
			countpop -= limit;
			limit += 1;
			for(int pers=0;pers<6;pers++){
				if(pers<=limit){
				for(int status=0;status<10;status++){//where all the action happens
					if(status==0){//if we are setting sex of person
						if(pers == 0){family[counter][pers][status] = 1;mc--;}//set father
						else if(pers==1){family[counter][pers][status] = 2;fc--;}//set mother
						else{//decide sex of the children
							Random temp = new Random();
							int sex = temp.nextInt(100);
							if(sex<malechildpercent){family[counter][pers][status] = 3;}
							else{family[counter][pers][status] = 4;}
						}//end else
					}//end if
					else if(status==1){//set ages
						if(pers<2){family[counter][pers][status] = setages(true);}
						else{family[counter][pers][status] = setages(false);}
					}//end if
					else if(status==2){//set savings totals
						if(pers<2){
							Random t = new Random();
							int a = t.nextInt(100);
							int caste =0;
							if(a<wealthdistrobution[0]){caste=1;}
							else if(a<wealthdistrobution[1]){caste=2;}
							else{caste=3;}
							family[counter][pers][status] = generatesavings(caste,pop);
						}
						else{family[counter][pers][status] = 0;}
					}
					else if(status==3){//set profession
						if(pers<2){
							family[counter][pers][status] = genprofession();
						}
						else{family[counter][pers][status] = 0;}
					}
					else if(status==4){//setup local reputation
						if(family[counter][pers][0]<3){
						family[counter][pers][4]=localrep();}
					}
					else if(status==5){//setup world reputation
						if(family[counter][pers][0]<3){
						if(family[counter][pers][4]>=3){
						family[counter][pers][5]=worldrep();}
						else{family[counter][pers][5]=0;}}
					}
					else if(status==6){//setup residence type
						if(family[counter][pers][0]<3){
						if(family[counter][pers][2]>250){family[counter][pers][6]=residencetype();}
						else{
							Random t = new Random();
							int a= t.nextInt(100);
							if(a<5){family[counter][pers][6]=0;}//no shelter
							else{family[counter][pers][6]=1;}
						}}
						
					}
					else if(status==7){//setup skilllvl
						if(family[counter][pers][0]<3){family[counter][pers][7]=skilllvl();}
					}
					else if(status==8){//setup possessions
						posesseditems(family[counter][pers][3],family[counter][pers][6],family[counter][pers][2],i[(counter*6)+pers]);
						System.out.println(i[(counter*6)][17].itemnum);
					}
					else if(status==9){//setup inventory
						Item[] it = inventoryitems(family[counter][pers][3],family[counter][pers][7],family[counter][pers][4],family[counter][pers][5]);
						i2[(counter*6)+pers]=it;
					}
				}//end for status
				}//end if
				else{//if there are no more people in the family
					for(int status=0;status<4;status++){
						family[counter][pers][status] = 0;
					}//end for
				}//end else
			}//end for pers
		}//end for counter
		if(children>0){
			boolean loopif=true;
			Random generator = new Random();
			while(loopif){//if any children need families assign them one
				if(children<=0){loopif=false;}
				int fam = generator.nextInt(hitch);
				for(int count=0;count<6;count++){
					if(family[fam][count][0] == 0){//if the family does not have maximum children fill it up
						Random temp = new Random();
						int sex = temp.nextInt(100);
						if(sex<malechildpercent){family[fam][count][0] = 3;}
						else{family[fam][count][0] = 4;}
						family[fam][count][1] = setages(false);
						family[fam][count][2] = 0;
						family[fam][count][3] = 0;
						countpop -=1;
						children -=1;
					}//end for
				}//end if
			}//end while
		}//end if
		for(int count=0;count<hitch;count++){//add people to the kingdom vector
			int tem[] = new int[6];
			for(int a=0;a<6;a++){tem[a]= (count*6)+a;} //members of family and their places in the vector
			for(int counttwo=0;counttwo<6;counttwo++){
				if(family[count][counttwo][0]>0){
					Item[] it = new Item[150];
					Item[] it2 = new Item[5];
					for(int c=0;c<150;c++){//copy items
						it[c] =i[((count*6)+counttwo)][c];
						}//end for
					for(int c=0;c<5;c++){//copy items
						it2[c] =i2[((count*6)+counttwo)][c];
						}//end for
					if(family[count][counttwo][0]>2){
						for(int a=0;a<150;a++){
							i[((count*6)+counttwo)][a]=new Item();
							it[a]=i[((count*6)+counttwo)][a];
						}
						for(int a=0;a<5;a++){
							i2[((count*6)+counttwo)][a]=new Item();
							it2[a]=i2[((count*6)+counttwo)][a];
						}
					}
				Person temp = new Person(family[count][counttwo][0], family[count][counttwo][1], family[count][counttwo][2], family[count][counttwo][3], tem, family[count][counttwo][4], family[count][counttwo][5], family[count][counttwo][6], family[count][counttwo][7],it,it2,pl);
				pl.addperson(temp);}
			}
		}//end for
		for(int c=0;c<mc;c++){//create remaining males
			Random t = new Random();
			int a = t.nextInt(100);
			int caste =0;
			if(a<wealthdistrobution[0]){caste=1;}
			else if(a<wealthdistrobution[1]){caste=2;}
			else{caste=3;}
			int s=generatesavings(caste,pop);
			int pr=genprofession();
			int l =localrep();
			int w=0;
			if(l>=3){w=worldrep();}
			int r=0;
			if(s>250){r=residencetype();}
			else{
				Random gen = new Random();
				a= gen.nextInt(100);
				if(a<5){r=0;}//no shelter
				else{r=1;}
			}
			int sk = skilllvl();
			int[] fam = {pl.citizens.size(),0,0,0,0,0};
			Item[] itm= new Item[150]; 
			posesseditems(pr,r,s,itm);
			Item[] it = inventoryitems(pr,sk,l,w);
			Person temp = new Person(1,setages(true),s,pr,fam,l,w,r,sk,itm,it,pl);
			pl.addperson(temp);
		}
		for(int c=0;c<mc;c++){//create remaining females
			Random t = new Random();
			int a = t.nextInt(100);
			int caste =0;
			if(a<wealthdistrobution[0]){caste=1;}
			else if(a<wealthdistrobution[1]){caste=2;}
			else{caste=3;}
			int s=generatesavings(caste,pop);
			int pr=genprofession();
			int l =localrep();
			int w=0;
			if(l>=3){w=worldrep();}
			int r=0;
			if(s>250){r=residencetype();}
			else{
				Random gen = new Random();
				a= gen.nextInt(100);
				if(a<5){r=0;}//no shelter
				else{r=1;}
			}
			int sk = skilllvl();
			int[] fam = {pl.citizens.size(),0,0,0,0,0};
			Item[] itm = new Item[150];
			posesseditems(pr,r,s, itm);
			Item[] it = inventoryitems(pr,sk,l,w);
			Person temp = new Person(2,setages(true),s,pr,fam,l,w,r,sk,itm,it,pl);
			pl.addperson(temp);
		}
		return pl;
	}//end generate kingdom
	public static int numberchildren(){//roll to see how many children are in the family between 1 and 4 unless there are no more children to generate
		Random child = new Random();
		int temp = child.nextInt(4);
		temp +=1;
		if(children>=temp){
		children -= temp;}
		else{temp=children;children=0;}
		return temp;
	}//end numberchildren
	public static int setages(boolean parorchil){//decide what age everyone is
		Random tem = new Random();
		int compage=0;
		boolean loopif = true;
		int i=0; //will be the index of the age range generated
		while(loopif){
			compage = tem.nextInt(100);
			compage +=1;
			if(parorchil==true){//if its an adult
				if(compage>60){loopif=false;}
			}//end if
			else{//if its a child
				if(compage<=60){loopif=false;}
			}//end else
		}//end while
		while(compage>0){
			compage -= agearr[i]; //exit loop if previous statement returned true
			if(compage>0){i++;}//increment i if you need to repeat the process
		}//end while
		int lb = agebounds[i][0];
		int ub = agebounds[i][1];
		int a = ub-lb;
		int temp = tem.nextInt(a);
		temp += lb;
		return temp;
	}//end setages
	public static int generatesavings(int status, int population){
		int lol = population/500;
		int lb=0;
		int ub=0;
		if(status==1){
			lb=lol*savingsavg[0][0];
			ub=lol*savingsavg[0][1];
		}
		else if(status==2){
			lb=lol*savingsavg[1][0];
			ub=lol*savingsavg[1][1];
		}
		else{lb=lol*savingsavg[2][0];ub=lol*savingsavg[2][1];}
		int temp = ub-lb;
		Random t = new Random();
		int a=t.nextInt(temp);
		a += lb;
		return a;
		
	}//end generatesavings
	public static int genprofession(){//not operating correctly--------------------------------------------------------------------------------------
		int temp=0;
		Random generator = new Random();
		boolean loopif=true;
		int c=2;
		int labor = professionbounds[1][0]*(peeps/100);
		while(loopif){
			int l = (professionbounds[c][0])*(peeps/100);
			if(professionassign[c] < l){
				minmet=false;loopif=false;
			}//end if
			if(c==16){loopif=false;minmet=true;}
			c++;
		}//end while
		loopif=true;
		c=2;
		while(loopif){
			int u = (professionbounds[c][1])*(peeps/100);
			if(professionassign[c] < u){
				loopif=false;maxmet=false;
			}//end if
			if(c==16){loopif=false;maxmet=true;}
			c++;
		}//end while
		loopif=true;
		while(loopif==true && maxmet ==false){
		int a = generator.nextInt(15);
		a += 2;
		int lb = professionbounds[a][0];
		lb *= (peeps/100);
		int ub = professionbounds[a][1];
		ub *= (peeps/100);
		if(professionassign[a] < lb){
			temp = a;
			professionassign[a] += 1;
			loopif=false;
		}//end if
		else if(minmet==true && professionassign[a] < ub){temp = a;
			professionassign[a] +=1;
			loopif=false;
		}//end else
		else if(professionassign[1] < labor){
			temp=1;
			professionassign[1] += 1;
			loopif=false;
		}//end else
		}//end while
	if(maxmet == true){
		temp =1;
	}
	return temp;
		
	}
	public static int localrep(){
		int lrep[] = {10,10,30,40,8,2};//percentages of evy rep lvl
		return percentalg(lrep, 0);
	}
	public static int worldrep(){
		int wrep[] = {30,20,30,10,8,2};//percentages of every rep level
		return percentalg(wrep, 0);
	}
	public static int residencetype(){
		int restyp[] = {70,15,8,5,2};//percentages of each type of house
		return percentalg(restyp, 0);
	}
	public static int skilllvl(){
		int skill[] = {20,20,40,15,5};
		return percentalg(skill, 0);
	}
	public static void posesseditems(int p,int r,int s,Item[] poop){
		//find profession, find residence type, give any items of their profession, give profession tools, give each person 2 pieces of clothing, give each person a location and residence type 53.house 48-52 quality
		//Profession {None, Laborer, Guard, Metalsmith, Carpenter, farmer, taxcollector, bandit, miner, logger, clothier, landlord, trader, builder, priest, doctor, police };
		int[] temp = {0,0,0,0,0,0,0,0,0,0,0,0,0};
		Random g = new Random();
		int a=0;
		switch(p){
		case 0://none 
			break;
		case 1://laborer gets shirt pants apple survivable house random carpentry item
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 6+(g.nextInt(5));//random carpentry item
			break;
		case 2://guard gets shirt pants apple survivable house random carpentry item guard tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			temp[6]= 17;//guard tools
			break;
		case 3://metalsmith gets shirt pants apple survivable house random carpentry item metalsmith tools materials 2 base materials/1 random
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			temp[6]=6;//copper
			temp[7]=6;//copper
			temp[8]=6+g.nextInt(5);//random material
			break;
		case 4://carpenter gets shirt pants apple survivable house random carpentry item 95% chance of carpentry tools materials 2 base materials/1 random
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			temp[6]=5;//generic wood
			temp[7]=5;//generic wood
			temp[8]=1+g.nextInt(5);//random material
			a=(g.nextInt(100))+1;
			if(a<95){temp[9]=18;}//carpentertools
			break;
		case 5://farmer gets shirt pants apple survivable house random carpentry item 95% chance of farmer tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=19;}//farmer tools
			break;
		case 6://taxcollector gets shirt pants apple survivable house random carpentry item tax collector tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=20;}//carpenter tools
			break;
		case 7://bandit gets shirt pants apple survivable house random carpentry item 95% chance of bandit tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=21;}//bandit tools
			break;
		case 8://miner gets shirt pants apple survivable house random carpentry item 95% chance of miner tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=22;}//miner tools
			break;
		case 9://logger gets shirt pants apple survivable house random carpentry item 95% chance of logger tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=23;}//miner tools
			break;
		case 10://clothier gets shirt pants apple survivable house random carpentry item 95% chance of clothier tools materials 2 base materials/1 random
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			temp[6]=35;//wool
			temp[7]=35;//wool
			temp[8]=34+g.nextInt(4);//random material
			a=(g.nextInt(100))+1;
			if(a<95){temp[9]=24;}//clothier tools
			break;
		case 11://landlord gets shirt pants apple very good house 2 random carpentry items 95% chance of landlord tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=25;}//landlord tools
			break;
		case 12://trader gets shirt pants apple survivable house random carpentry item 95% chance of trader tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=26;}//trader tools
			break;
		case 13://builder gets shirt pants apple survivable house random carpentry item 95% chance of builder tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			temp[6]=15;//wood beams
			temp[7]=15+g.nextInt(2);//random material
			a=(g.nextInt(100))+1;
			if(a<95){temp[8]=27;}//builder tools
			break;
		case 14://priest gets shirt pants apple survivable house random carpentry item 95% chance of priest tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=28;}//priest tools
			break;
		case 15://doctor gets shirt pants apple survivable house random carpentry item 95% chance of doctor tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=29;}//doctor tools
			break;
		case 16://police gets shirt pants apple survivable house random carpentry item 95% chance of police tools
			temp[0]=41;//pants
			temp[1]=40;//shirt
			temp[2]=53;//house
			temp[3]=52-(r-1);//house location
			temp[4]=39;//food
			temp[5]= 11+(g.nextInt(4));//random carpentry item
			a=(g.nextInt(100))+1;
			if(a<95){temp[6]=30;}//police tools
			break;
		default:
			break;
		}
		for(int c=0;c<150;c++){
			if(c<13){if(temp[c]!=0){
				poop[c]= new Item(temp[c]);
				}
				else{ poop[c]=new Item();
				}
			}
			else{poop[c]=new Item();}
		}
	}
	public static Item[] inventoryitems(int p,int sk,int lrep,int wrep){//what items are they selling right now
		//skill determines item quality, profession determines item possibilities, lrep modifies quality, wrep modifies quality
		int[][] skill={{70,20,8,1,1},{50,30,10,9,1},{30,30,20,15,5},{20,20,30,20,10},{10,20,30,25,15},{10,10,30,30,20}};//chance of each quality level
		int quality=0,material=0;
		boolean loop=true;
		Random z = new Random();
		int cond=0;
		int num=0;
		int loopexit=0;
		Item[] ret={new Item(),new Item(), new Item(), new Item(),new Item()};
		if(p!=13){loopexit = 1+z.nextInt(5);}
		else if(p==13){loopexit = 2;}
		while(loop){
			quality=0;
			material=0;
		if((p>=3 && p<=5)|| (p>=8 && p<=11) || p==13){
			Random generator = new Random();
			int a = generator.nextInt(100);
			a= (a+1)+(lrep*3)+(wrep*5);
			if(a<=100){}
			else{a=100;}
			boolean loopif=true;
			int count=0;
			while(loopif){
				if(a<=skill[sk][count]){loopif=false;}
				else{a-=skill[sk][count];}
				count++;
			}
			quality=count;
		}
		if(p==3){//metalsmith
			int[] mat={5,7,7,7,6,7,7,7,7,6,6,7,7,7,7};//metal
			num=percentalg(mat, 15);
			Item lol=new Item(num,quality);
			ret[(loopexit-1)]=lol;
		}
		else if(p==4){//carpenter
			int[] mat={25,15,40,15,5};//wood
			num=percentalg(mat, 10);
			Item lol=new Item(num,quality);
			ret[(loopexit-1)]=lol;
		}
		else if(p==5){//farmer
			int[] mat={6,7,7,5,20,15,5,15,20};//goods/food
			num=percentalg(mat, 30);
			Item lol=new Item(num,quality);
			ret[(loopexit-1)]=lol;
		}
		else if(p==8){//miner
			int[] mat={70,8,2,10,10};//metal
			num=percentalg(mat, 5);
			Item lol=new Item(num,quality);
			ret[(loopexit-1)]=lol;
		}
		else if(p==9){//logger
			int[] mat={10,20,15,15,40};//wood
			num=percentalg(mat, 0);
			Item lol=new Item(num,quality);
			ret[(loopexit-1)]=lol;
		}
		else if(p==10){//clothier
			int[] mat={5,80,10,5};//cloth
			num=percentalg(mat, 39);
			Item lol=new Item(num,quality);
			ret[(loopexit-1)]=lol;
		}
		else if(p==11){//landlord
			int[] mat={5,5,10,10,70};//location
			num=percentalg(mat, 47);
			Item lol=new Item(num,quality);
			ret[(loopexit-1)]=lol;
		}
		else if(p==13){//builder
			int[] mat={90,10};//beams
			num=percentalg(mat, 52);
			Item lol=new Item(num,quality);
			ret[(loopexit-1)]=lol;
		}
		cond++;
		loopexit--;
		if(loopexit==0){loop=false;}
		}//end while
		return ret;
	}
	public static int percentalg(int[] x,int y){
		Random generator = new Random();
		int a = generator.nextInt(100);
		a+=1;
		boolean loopif=true;
		int count=0;
		while(loopif){
			if(a<=x[count]){loopif=false;}
			else{a-=x[count];}
			count++;
		}
		int num=y+count;
		return num;
	}
}
