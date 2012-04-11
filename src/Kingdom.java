import java.util.*;

public class Kingdom {
	int population,totalitems,avginventory,avgpossessions,avgage,oldest,farmersmoney;
	String name;
	Vector citizens = new Vector();
	public Vector farmersmarket= new Vector();
	protected int men,wemen,children,adults,moneycirc,avgsavings,families,laborers,guards,metalsmiths,carpenters,farmers,taxcollectors,bandits,miners,loggers,clothiers,landlords,traders,builders,preists,doctors,police;
	public Kingdom(String n, int p){population=p;name=n;farmersmoney=0;}
	protected int[] itms = new int[58];
	public void addperson(Person i){
		citizens.addElement(i);
	}
	public String getHeadcount(){
		int lol = citizens.size();
		return Integer.toString(lol);
	}
	public void runsim(){
		for(int cnt=0;cnt<citizens.size();cnt++){
			Person temp = (Person)citizens.get(cnt);
			temp.makestuff();
			citizens.set(cnt, temp);
		}
		for(int cnt=0;cnt<citizens.size();cnt++){
			Person temp = (Person)citizens.get(cnt);
			temp.needshierarchy();
			citizens.set(cnt, temp);
		}
		updatekingdomstats();
	}
	public void updatekingdomstats(){
		for(int holy=0;holy<58;holy++){itms[holy]=0;}
		men=0;wemen=0;avgsavings=0;population=0;totalitems=0;families=0;moneycirc=0;adults=0;avginventory=0;avgpossessions=0;avgage=0;oldest=0;
		int t;int itmin=0,itmp=0;
		for(t=0;t<citizens.size();t++){
			Person temp = (Person)citizens.get(t);
			if(temp.gender ==1){men++;population++;adults++;}
			else if(temp.gender ==3){men++;population++;children++;}
			else if(temp.gender==2){wemen++;population++;adults++;}
			else if(temp.gender==4){wemen++;population++;children++;}
			
			moneycirc += temp.savings;
			for(int c=0;c<150;c++){//fix to account for all items
			if(temp.possessions[c].itemnum>0){
				totalitems++;itmp++;
				int numitm = temp.possessions[c].itemnum;
				itms[numitm]++;
			}}
			for(int lo=0;lo<5;lo++){
				if(temp.iteminventory[lo].itemnum>0){itms[temp.iteminventory[lo].itemnum]++;totalitems++;}
			}
			
			if(temp.gender>0){
				avgage+=temp.age;
				if(temp.age>oldest){oldest=temp.age;}
			}
			int p = temp.profession;
			switch(p){
				case 1: laborers++;
					break;
				case 2: guards++;
					break;
				case 3:metalsmiths++;
					break;
				case 4:carpenters++;
					break;
				case 5:farmers++;
					break;
				case 6:taxcollectors++;
					break;
				case 7:bandits++;
					break;
				case 8:miners++;
					break;
				case 9:loggers++;
					break;
				case 10:clothiers++;
					break;
				case 11:landlords++;
					break;
				case 12:traders++;
					break;
				case 13:builders++;
					break;
				case 14:preists++;
					break;
				case 15:doctors++;
					break;
				case 16:police++;
					break;
				default:
					break;
			}
	}
		for(int b=0;b<farmersmarket.size();b++){
			Item i=(Item)farmersmarket.get(b);
			int num=i.itemnum;
			itmp++;itms[num]++;totalitems++;
		}
		avgage/=(citizens.size()+1);
		avginventory = itmin/(citizens.size()+1);
		avgpossessions= itmp/(citizens.size()+1);
		avgsavings =moneycirc/(citizens.size()+1);
	}
}
