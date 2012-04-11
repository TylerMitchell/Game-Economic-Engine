import java.util.Random;

//items:
public class Item {
	//itemnums: 1.Pine 2.Cedar 3.Oak 4.Mahoganney 5.Generic Wood 6.Copper 7.Silver 8.Gold 9.Steel 10.Iron 11.chair 12.table 13.bed 14.dresser 15.wood beams 16.metal beams 
	//17.Guard Tools 18.Carpenter Tools 19.Farmer tools 20.tax collector tools 21.bandit tools 22.miner tools 23.logger tools 24.clothier tools 25.landlord tools
	//26.trader tools 27.builder tools 28.priest tools 29.doctor tools 30.police tools 31.corn 32.beef 33.poultry 34.silk 35.wool 36.cotton 37.leather 38.bread 39.apples
	//40.shirt 41.pants 42.underwear 43.socks 44.shoes 45.gloves 46.hat 47.coat 48.Excellent Location 49.Very Good Location 50.Good Location 51.poor location 52.survivable location
	//53.house 54.buisness 55.storage 56.Public 57.Expansion
	//materials are items with material set to self quality set to 0 and durability set to 1000000	
	
	protected int material, quality, rvalue, itemnum, durability;
	public Item(int m,int q,int r,int i,int d){//item setup
		material=m;quality=q;rvalue=r;itemnum=i;durability=d;
	}
	public Item(int m, int r, int i){//setup material
		material=m;itemnum=i;rvalue=r;quality=0;durability=10000000;
	}
	public Item(int m,int i,int r, int q){
		material=m;itemnum=i;rvalue=r;quality=q;durability=GlobalDataStore.dv[i];
	}
	public Item(int n, int q){//pick material
		itemnum=n;
		quality=q;
		rvalue=GlobalDataStore.rv[n];
		durability=GlobalDataStore.dv[n];
		if(GlobalDataStore.itemmat[n][0]==GlobalDataStore.itemmat[n][1]){material=GlobalDataStore.itemmat[n][1];}
		else if(GlobalDataStore.itemmat[n][0]==1 && GlobalDataStore.itemmat[n][1]==5){
			int[] t = {20,30,10,10,30};
			material=per(t,0);
		}
		else if(GlobalDataStore.itemmat[n][0]==6 && GlobalDataStore.itemmat[n][1]==10){
			int[] t = {50,10,1,24,15};
			material=per(t,5);
		}
		else if(GlobalDataStore.itemmat[n][0]==34 && GlobalDataStore.itemmat[n][1]==37){
			int[] t = {10,40,40,10};
			material=per(t,33);
		}
		else if(GlobalDataStore.itemmat[n][0]==15 && GlobalDataStore.itemmat[n][1]==16){
			int[] t = {90,10};
			material=per(t,14);
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
	public Item(){
		itemnum=0;rvalue=0;durability=0;material=0;quality=0;
	}
	public void setzero(){
		itemnum=0;rvalue=0;durability=0;material=0;quality=0;
	}
	public Item(int n){//decides the remaining variables of items generated at beginning
		itemnum=n;
		rvalue=GlobalDataStore.rv[n];
		durability=GlobalDataStore.dv[n];
		int[] chance1={20,20,15,15,30};//wood material
		int[] chance2={50,1,3,31,15};//metal material
		int[] chance3={5,60,30,5};//cloth material
		int[] chance4={90,10};//building material
		if((n<=10 && n!=0)||(n==15)||(n==16)||(n>=31 && n<=39)||(n>=48)){
			material=n;
		}
		else if(n<=15 && n>=11){//carpentry item
			Random generator = new Random();
			int a = generator.nextInt(100);
			a+=1;
			boolean loopif=true;
			int count=0;
			while(loopif){
				if(a<=chance1[count]){loopif=false;}
				else{a-=chance1[count];}
				count++;
			}
			material=count;
		}
		else if(n>=16 && n<=30){//metalworker item /i recognize alot of copy/paste code ill figure out how to tide it up later
			Random generator = new Random();
			int a = generator.nextInt(100);
			a+=1;
			boolean loopif=true;
			int count=0;
			while(loopif){
				if(a<=chance2[count]){loopif=false;}
				else{a-=chance2[count];}
				count++;
			}
			material=5+count;
		}
		else if(n<=47 && n>=40){//clothier materials
			Random generator = new Random();
			int a = generator.nextInt(100);
			a+=1;
			boolean loopif=true;
			int count=0;
			while(loopif){
				if(a<=chance3[count]){loopif=false;}
				else{a-=chance3[count];}
				count++;
			}
			material=33+count;
		}
		else if(n<=57 && n>=52){//building materials
			Random generator = new Random();
			int a = generator.nextInt(100);
			a+=1;
			boolean loopif=true;
			int count=0;
			while(loopif){
				if(a<=chance4[count]){loopif=false;}
				else{a-=chance4[count];}
				count++;
			}
			material=14+count;
		}
		int q[] = {20,20,40,15,5};
		Random generator = new Random();
		int a = generator.nextInt(100);
		a+=1;
		boolean loopif=true;
		int count=0;
		while(loopif){
			if(a<=q[count]){loopif=false;}
			else{a-=q[count];}
			count++;
		}
		quality=count;
		
		
	}
}
