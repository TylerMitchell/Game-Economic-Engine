import java.util.Random;


public class Person {
	protected int gender;
	protected int happiness;
	protected int health;//when did we eat 1=yesterday,2=within 3 days, 3=within week, 4 =within 10 days, 5=14 days eat or die
	protected boolean employmentstatus;
	protected int profession;		//kids under 9 dont work
	protected Item[] possessions= new Item[150];
	protected int[] schedule;
	protected int[] relationship;  //might need to be multidimensional
	protected int[] needheirarchy; //1=eat, 2=find shelter, 3=get work tools, 4=buyorsave, 5=what to produce, 6=find a wife, 7=try for child, 8 = start a buisness, 9=hire/fire laborers
	protected int skilllevel;
	protected int lreputation; //local reputation
	protected int wreputation; //world reputation
	protected int anger;
	protected int fear;
	protected int age;
	protected int[] family=new int[6];
	protected int[] traits;
	protected int residence;//0=homeless 1=has shelter (more to come)
	protected int money;
	protected int savings;
	protected Item[] iteminventory= new Item[5];
	protected int laborershired;
	protected int[][] lastsales={{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};//intemnum order (last sale price),(average sale price)
	public Person(){
		money=0;savings=0;residence=0;traits=new int[5];age=0;fear=0;anger=0;wreputation=0;lreputation=0;skilllevel=0;gender=0;laborershired=0;
	}
	public Person(int type, int a, int sav, int employed, int[] fa, int l, int w, int r, int s,Item[] item,Item[] item2,Kingdom you){
		gender=type;age=a;profession=employed;savings=sav;money=0;residence=r;traits=new int[5];family=fa;fear=0;anger=0;wreputation=w;lreputation=l;skilllevel=s;health=1;
		possessions=item;iteminventory=item2;laborershired=0;
	}
	public Person(int type, int a, int sav, int employed){
		gender=type;age=a;profession=employed;savings=sav;money=0;residence=1;traits=new int[5];fear=0;anger=0;wreputation=0;lreputation=0;skilllevel=0;health=1;
	}
	public void setAge(int a){
		age=a;
	}
	public void makestuff(){
		findprofession();//produce your items}
	}
	public void needshierarchy(){
		int eat=0;
		int shelter=0;
		
		if(health>=10){
			eat=eat();
		}//eat
		if(residence == 1){shelter=1;}
		else if(residence==0){}//find house
		if(health<10 && health>7){//eat
			eat=eat();
			if(money>0 || savings>0){
				for(int i2=2;i2<6;i2++){
					Person t = (Person)GlobalDataStore.yours.citizens.get((family[i2]-1));
					int[] decide = t.feedme(money,savings);
					if(decide[0]==1){money=decide[1];}
					if(decide[0]==2){savings=decide[1];}
				}
			}
		}
		if(health<=7 && health>3){
			eat=eat();
			if(money>0 || savings>0){
				for(int i2=2;i2<6;i2++){
					Person t = (Person)GlobalDataStore.yours.citizens.get((family[i2]-1));
					int[] decide = t.feedme(money,savings);
					if(decide[0]==1){money=decide[1];}
					if(decide[0]==2){savings=decide[1];}
				}
			}
		}//eat
		if(health<=3 && health>1){
			eat=eat();
			if(money>0 || savings>0){
				for(int i2=2;i2<6;i2++){
					Person t = (Person)GlobalDataStore.yours.citizens.get((family[i2]-1));
					int[] decide = t.feedme(money,savings);
					if(decide[0]==1){money=decide[1];}
					if(decide[0]==2){savings=decide[1];}
				}
			}
		}//eat
		if(health<=1){
			eat=eat();
			if(money>0 || savings>0){
				for(int i2=2;i2<6;i2++){
					Person t = (Person)GlobalDataStore.yours.citizens.get((family[i2]));
					int[] decide = t.feedme(money,savings);
					if(decide[0]==1){money=decide[1];}
					if(decide[0]==2){savings=decide[1];}
				}
			}
		}//eat
		if(eat==1){health=1;}
		if(eat==0 && health==14){die();}
	}
	public void die(){//zero everything out
		
	}
	public int eat(){
		int eat=0;
		int[] itmplace={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		Item[] temp = new Item[15];int t=0;
		for(int i=0;i<GlobalDataStore.yours.farmersmarket.size();i++){
			Item itm = (Item)GlobalDataStore.yours.farmersmarket.get(i);
			if((t<15 && itm.itemnum<34 && itm.itemnum>30)||(t<15 && itm.itemnum>37 && itm.itemnum<40)){
				temp[t]=itm;itmplace[t]=i;t++;
			}
		}
		int price=0;int d=0;
		for(int p=0;p<15;p++){
			if(temp[p]!=null){
			if(money>temp[p].rvalue && d==0){
				
					price=temp[p].rvalue;money-=temp[p].rvalue;d+=1;eat+=1;temp[p].setzero();
					GlobalDataStore.yours.farmersmarket.set(itmplace[p],temp[p]);
				}
			}
		}
		for(int p=0;p<15;p++){
			if(temp[p]!=null){
			if(savings>temp[p].rvalue && d==0){
				
					price=temp[p].rvalue;savings-=temp[p].rvalue;d+=1;eat+=1;temp[p].setzero();
					GlobalDataStore.yours.farmersmarket.set(itmplace[p],temp[p]);
				}
			}
		}
		GlobalDataStore.yours.farmersmoney += price;
		return eat;
	}
	
	public int[] feedme(int parentmoney, int parentsavings){
		int[] itmplace={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[] moneysub={0,0};
		Item[] temp = new Item[15];int t=0;
		for(int i=0;i<GlobalDataStore.yours.farmersmarket.size();i++){
			Item itm = (Item)GlobalDataStore.yours.farmersmarket.get(i);
			if((t<15 && itm.itemnum<34 && itm.itemnum>30)||(t<15 && itm.itemnum>37 && itm.itemnum<40)){
				temp[t]=itm;itmplace[t]=i;t++;
			}
		}
		int price=0;int d=0;
		for(int p=0;p<15;p++){
			if(temp[p]!=null){
			if(parentmoney>temp[p].rvalue && d==0){
				
					price=temp[p].rvalue;parentmoney-=temp[p].rvalue;d+=1;moneysub[0]=1;temp[p].setzero();
					GlobalDataStore.yours.farmersmarket.set(itmplace[p],temp[p]);
				}
			}
		}
		for(int p=0;p<15;p++){
			if(temp[p]!=null){
			if(parentsavings>temp[p].rvalue && d==0){
				
					price=temp[p].rvalue;parentsavings-=temp[p].rvalue;d+=1;moneysub[0]=2;temp[p].setzero();
					GlobalDataStore.yours.farmersmarket.set(itmplace[p],temp[p]);
				}
			}
		}
		if(moneysub[0]==1){moneysub[1]=parentmoney;}
		if(moneysub[0]==2){moneysub[1]=parentsavings;}
		GlobalDataStore.yours.farmersmoney += price;
		return moneysub;
	}
	public String getAge(){String temp =Integer.toString(age);return temp;}
	public void findprofession(){
		switch(profession){
		case 0:
			break;
		case 1:
			ifLaborer();
			break;
		case 2:
			ifGuard();
			break;
		case 3:
			ifMetalsmith();
			break;
		case 4:
			ifCarpenter();
			break;
		case 5:
			ifFarmer();
			break;
		case 6:
			ifTaxcollector();
			break;
		case 7:
			ifBandit();
			break;
		case 8:
			ifMiner();
			break;
		case 9:
			ifLogger();
			break;
		case 10:
			ifClothier();
			break;
		case 11:
			ifLandlord();
			break;
		case 12:
			ifTrader();
			break;
		case 13:
			ifBuilder();
			break;
		case 14:
			ifPriest();
			break;
		case 15:
			ifDoctor();
			break;
		case 16:
			ifPolice();
			break;
		default:
			break;
		}
	}
	public static int per(int[] x,int y){
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
	public int determinequality(){
		int[][] skill={{70,20,8,1,1},{50,30,10,9,1},{30,30,20,15,5},{20,20,30,20,10},{10,20,30,25,15},{10,10,30,30,20}};//chance of each quality level
		int count=0;
		//determine item quality
		int sk=skilllevel;
		Random generator = new Random();
		int a = generator.nextInt(100);
		a= (a+1)+(lreputation*3)+(wreputation*5);
		if(a<=100){}
		else{a=100;}
		boolean loopif=true;
		while(loopif){
			if(a<=skill[sk][count]){loopif=false;}
			else{a-=skill[sk][count];count++;}
			}return count;
	}
	public int selectmaterial(int[] choices){int t=0;return t;}//for choosing which material to build stuff out of
	public int[] productionbounds(int[] current, int arrlen,int ind){
		int[] temp = current;
		boolean loopif=true;
		int c=0;
		while(loopif){
			if(lastsales[c][0]>0 || lastsales[c][1]>0){loopif=false;}
			else{c++;}
			if(c>=15){
				for(int b=0;b<arrlen;b++){
					temp[b]=current[b];
				}//end for
				loopif=false;
				}
		}//end while
		if(c<15){//if youv sold an item
			for(c=0;c<15;c++){//run through item sale prices
				if(lastsales[c][0]>0){//if that item has been sold
					if(ind+c<58){//if itemnum is in bounds
						if(lastsales[c][0]>GlobalDataStore.rv[(ind+c)]){//if item was sold for higher then real value
							int mod = lastsales[c][0]-GlobalDataStore.rv[(ind+c)];//how much more
							int omg = (int)(100*((double)mod)/((double)GlobalDataStore.rv[(ind+c)]));//find the percent over the rvalue sold for
							temp[c]+=omg/15;//
							boolean yay=true;
							while(yay){
								Random wtf = new Random();
								int poop = wtf.nextInt(arrlen);
								if(c!=poop){
									if(temp[c]>current[poop]){
										int another = temp[poop];
										another-=omg/15;yay=false;
										if(another>0){temp[poop]=another;}
										else{temp[poop]=0;}
										}
									}
								}
							}
						}
					}
				}
			}
		
		return temp;
		}
	public void ifGuard(){}
	public void ifLaborer(){}
	public void ifMetalsmith(){//check materials
		Random gen = new Random();
		int[] production ={7,6,7,7,7,6,6,7,7,7,7,6,7,7,6};
		int mod = (int)((float)laborershired*(1/4))+2;//remember to add method for deciding when to hire laborers--------------------------------
		int lol = (int)(1+((float)laborershired*(1/4)));
		int ct = lol+gen.nextInt(mod);//amnt item made
		int t=0;
		int m=0,q=0;
		int wow=0;
		for(int c=0;c<ct;c++){
			int[] matavail={0,0,0,0,0};
			for(int v=0;v<150;v++){
				if(possessions[v].itemnum==6){matavail[0]+=1;}
				if(possessions[v].itemnum==7){matavail[1]+=1;}
				if(possessions[v].itemnum==8){matavail[2]+=1;}
				if(possessions[v].itemnum==9){matavail[3]+=1;}
				if(possessions[v].itemnum==10){matavail[4]+=1;}
			}
			boolean loop=true;
			int ind = per(production,0);
			if(ind<16){t=15+ind;}
			q=determinequality();
			int count=0;
			for(int b=0;b<5;b++){
				if(iteminventory[b].itemnum==0){
					while(loop){int num=gen.nextInt(5);
					if(matavail[num]>0){m=6+num;loop=false;matavail[num]--;}
					count++;
					if(count>25){loop=false;}
					if(m>0){Item itm = new Item(m,t,GlobalDataStore.rv[t],q);iteminventory[b]=itm;}
					else{matavail[num]++;}}
				}//end if
				else{wow++;}
			}//end for
			if(wow>5){
				//consider forming a buisness------------------------------
			}
			wow=0;}
	}
	public void ifCarpenter(){
		Random gen = new Random();
		int[] production ={30,20,10,10,30};
		int mod = (int)((float)laborershired*(1/4))+2;//remember to add method for deciding when to hire laborers--------------------------------
		int lol = (int)(1+((float)laborershired*(1/4)));
		int ct = lol+gen.nextInt(mod);//amnt item made
		int t=0;
		int m=0,q=0;
		int wow=0;
		for(int c=0;c<ct;c++){
			int[] matavail={0,0,0,0,0};
			for(int v=0;v<150;v++){
				if(possessions[v].itemnum==1){matavail[0]+=1;}
				if(possessions[v].itemnum==2){matavail[1]+=1;}
				if(possessions[v].itemnum==3){matavail[2]+=1;}
				if(possessions[v].itemnum==4){matavail[3]+=1;}
				if(possessions[v].itemnum==5){matavail[4]+=1;}
			}
			boolean loop=true;
			int ind = per(production,0);
			if(ind<6){t=10+ind;}
			q=determinequality();
			int count=0;
			for(int b=0;b<5;b++){
				if(iteminventory[b].itemnum==0){
					while(loop){int num=gen.nextInt(5);
					if(matavail[num]>0){m=1+num;loop=false;matavail[num]--;}
					count++;
					if(count>25){loop=false;}
					if(m>0){Item itm = new Item(m,t,GlobalDataStore.rv[t],q);iteminventory[b]=itm;}
					else{matavail[num]++;}}
				}//end if
				else{wow++;}
			}//end for
			if(wow>5){
				//consider forming a buisness------------------------------
			}
			wow=0;}
	}
	public void ifFarmer(){
		Random food = new Random();
		//farmer itemnums 31-39
		int[] f1 = {31,32,33,38,39};//itemnums for food
		int[] f2 = {34,35,36,37};//itemnums for items
		
		int[] foodproduction ={15,20,20,15,30};
		int[] clothproduction ={10,45,35,10};
		int flmod = (laborershired*(51/4))+10;//remember to add method for deciding when to hire laborers--------------------------------
		int lol = 10+(laborershired*(51/4));
		int ctmod = (laborershired*(12/4))+12;
		int lol2 = 9+(laborershired*(12/4));
		int ft = lol+food.nextInt(flmod);//amnt food made
		int ct = lol2+food.nextInt(ctmod);//amnt cloth made
		int t=0;
		int m=0,q=0;
		int test=0;
		for(int c=0;c<ft;c++){
			int ind = per(foodproduction,0);
			if(ind<4){t=30+ind;}
			else if(ind<6){t=34+ind;}
			m=t;
			q=determinequality();
			Item itm = new Item(m,t,GlobalDataStore.rv[t],q);
			GlobalDataStore.yours.farmersmarket.addElement(itm);
			//add ability of rvalue to change
		}
		for(int c=0;c<ct;c++){
			int ind = per(clothproduction,0);
			if(ind<5){t=33+ind;}
			m=t;
			q=determinequality();
			Item itm = new Item(m,t,GlobalDataStore.rv[t],q);
			GlobalDataStore.yours.farmersmarket.addElement(itm);
			
		}
		
	}
	public void ifTaxcollector(){}
	public void ifBandit(){}
	public void ifMiner(){
		Random gen = new Random();
		int[] production ={35,15,1,19,30};
		int mod = (int)((float)laborershired*(1/4))+2;//remember to add method for deciding when to hire laborers--------------------------------
		int lol = (int)(1+((float)laborershired*(1/4)));
		int ct = lol+gen.nextInt(mod);//amnt item made
		int t=0;
		int m=0,q=0;
		int wow=0;
		for(int c=0;c<ct;c++){
			int ind = per(production,0);
			if(ind<6){t=5+ind;}
			q=determinequality();
			for(int b=0;b<5;b++){
				if(iteminventory[b].itemnum==0){Item itm = new Item(m,t,GlobalDataStore.rv[t],q);iteminventory[b]=itm;}//end if
				else{wow++;}
			}//end for
			if(wow>5){
				//consider forming a buisness------------------------------
			}
			wow=0;
		}//end for
	}
	public void ifLogger(){
		Random gen = new Random();
		int[] production ={25,15,13,12,35};
		int mod = (int)((float)laborershired*(1/4))+2;//remember to add method for deciding when to hire laborers--------------------------------
		int lol = (int)(1+((float)laborershired*(1/4)));
		int ct = lol+gen.nextInt(mod);//amnt item made
		int t=0;
		int m=0,q=0;
		int wow=0;
		for(int c=0;c<ct;c++){
			int ind = per(production,0);
			if(ind<6){t=0+ind;}
			m=t;
			q=determinequality();
			Item itm = new Item(m,t,GlobalDataStore.rv[t],q);
			for(int b=0;b<5;b++){
				if(iteminventory[b].itemnum==0){iteminventory[b]=itm;}//end if
				else{wow++;}
			}//end for
			if(wow>5){
				//consider forming a buisness------------------------------
			}
			wow=0;
		}//end for
	}
	public void ifClothier(){
		Random gen = new Random();
		int[] production ={25,25,10,10,10,10,10,10};
		int mod = (int)((float)laborershired*(1/4))+2;//remember to add method for deciding when to hire laborers--------------------------------
		int lol = (int)(1+((float)laborershired*(1/4)));
		int ct = lol+gen.nextInt(mod);//amnt item made
		int t=0;
		int m=0,q=0;
		int wow=0;
		for(int c=0;c<ct;c++){
			int[] matavail={0,0,0,0};
			for(int v=0;v<150;v++){
				if(possessions[v].itemnum==34){matavail[0]+=1;}
				if(possessions[v].itemnum==35){matavail[1]+=1;}
				if(possessions[v].itemnum==36){matavail[2]+=1;}
				if(possessions[v].itemnum==37){matavail[3]+=1;}
			}
			boolean loop=true;
			int ind = per(production,0);
			if(ind<5){t=39+ind;}
			q=determinequality();
			int count=0;
			for(int b=0;b<5;b++){
				if(iteminventory[b].itemnum==0){
					while(loop){int num=gen.nextInt(4);
					if(matavail[num]>0){m=34+num;loop=false;matavail[num]--;}
					count++;
					if(count>25){loop=false;}
					if(m>0){Item itm = new Item(m,t,GlobalDataStore.rv[t],q);iteminventory[b]=itm;}
					else{matavail[num]++;}}
				}//end if
				else{wow++;}
			}//end for
			if(wow>5){
				//consider forming a buisness------------------------------
			}
			wow=0;}
	}
	public void ifLandlord(){
		//not implemented
	}
	public void ifTrader(){}
	public void ifBuilder(){
		//not implemented
	}
	public void ifPriest(){}
	public void ifDoctor(){}
	public void ifPolice(){}
}
