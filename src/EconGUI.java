import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
public class EconGUI extends JFrame implements ActionListener{
	java.awt.Container surface;//screen
	java.awt.Rectangle Windowsize; //screen size
	Container p = this.getContentPane(); 
	Kingdom current;
	JPanel north,south,center,center2,center3,center4,center5;
	JLabel nam,ga;
	int omg,lastpage;
	JButton runsim,back;
	protected int lastscr;
	JComboBox choose,peeps;
	String[] peo;
	JLabel[] ic = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),
				new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),
				new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),
				new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),
				new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),};
	String[] jobs = {"Guards: ","Laborers: ","Metalsmiths: ","Carpenters: ","Farmers: ","Tax Collectors: ","Bandits: ","Miners: ","Loggers: ","Clothiers: ","Landlords: ",
			"Traders: ","Builders: ","Preists: ","Doctors: ","Police: "};
	
	String[] itmnames = {"","1.Pine Wood","2.Cedar Wood","3.Oak Wood","4.Mahogganny Wood","5.Generic Wood","6.Copper","7.Silver","8.Gold","9.Steel","10.Iron","11.Chair","12.Table",
			 "13.bed","14.Dresser","15.Wood Beams","16.Metal Beams","17.Guards Tools","18.Carpenters Tools","19.Farmers Tools","20.Tax Collectors Tools","21.Bandits Tools",
			 "22.Miners Tools","23.Loggers Tools","24.Clothiers Tools","25.Landlords Tools","26.Traders Tools","27.Builders Tools","28.Preists Tools","29.Doctors Tools","30.Police Tools",
			 "31.corn","32.Beef","33.Poultry","34.Silk","35.Wool","36.Cotten","37.Leather","38.Bread","39.Apples","40.Shirts","41.Pants","42.Underwear","43.Socks","44.Shoes","45.Gloves",
			 "46.Hat","47.Coat","48.Excellent Land","49.Very Good Land","50.Good Land","51.Poor Land","52.Survivable Land","53.House","54.Buisness","55.Storage","56.Public Building","57.Expansion"};
	public EconGUI(Kingdom king){
		super("Economy Toy");//self explanitary code
		p.setLayout(new BorderLayout());
		this.setSize(640, 480);
		this.setDefaultCloseOperation(EconGUI.EXIT_ON_CLOSE);
		surface = this.getContentPane();
		Windowsize = new Rectangle(640,480);
		this.setMaximizedBounds(Windowsize);
        this.setExtendedState(MAXIMIZED_BOTH);
        current=king;
        start();
	}
	public void start(){
		setVisible(false);
		String[] peo = new String[current.citizens.size()];
		for(int c=0;c<(current.citizens.size());c++){
			peo[c]= "Person " + Integer.toString((c+1));
		}
		peeps=new JComboBox(peo);
		peeps.setMaximumRowCount(10);
		lastscr=0;
		String[] choices1 = {"None","Professions","People","Items","Kingdom","Original"};
		choose = new JComboBox(choices1);
		choose.setMaximumRowCount(6);
		DropDownHandler yay = new DropDownHandler();
		choose.addItemListener(yay);
		north = new JPanel();
		south = new JPanel();
		center = new JPanel();
		center2=new JPanel();
		center3=new JPanel();
		center4=new JPanel();
		center5=new JPanel();
		runsim= new JButton("Run Simulation");
		runsim.addActionListener(this);
		back = new JButton("Last Screen");
		back.addActionListener(this);
		nam = new JLabel(current.name);
		center.setLayout(new GridLayout(0,2));
		center.add(choose);
		center.add(new JLabel("Population: " +Integer.toString(current.population)));
		center.add(new JLabel());
		center.add(new JLabel("Day: 1"));
		center.add(new JLabel());
		center.add(new JLabel("Families: 80"));
		center.add(new JLabel());
		ga =new JLabel("Items: "+Integer.toString(current.totalitems));
		center.add(ga);
		
		center2.setLayout(new GridLayout(0,4));
		int[] jnums={current.guards,current.laborers,current.metalsmiths,current.carpenters,current.farmers,current.taxcollectors,current.bandits,current.miners,
				current.loggers,current.clothiers,current.landlords,current.traders,current.builders,current.preists,current.doctors,current.police};
		JLabel[] cp1 = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),};
		for(int cnt=0;cnt<16;cnt++){
			cp1[cnt].setText(jobs[cnt] + Integer.toString(jnums[cnt]));
			center2.add(cp1[cnt]);
		}
		
		String[] i = new String[58];
		for(int cnt=1;cnt<58;cnt++){
			i[cnt] = Integer.toString(current.itms[cnt]);
			ic[cnt].setText((itmnames[cnt]+" "+i[cnt]));
			center4.add(ic[cnt]);
		}
		
		center5.setLayout(new GridLayout(0,2));
		String[] k = {"#Men: ","#Women: ","#Children: ","#Adults: ","#AverageMoney: ","#Money Circulating: ","#Average Age: ","#Oldest Citizen: ","#Average Possessions: ","#Average Inventory*: "};
		int[] stat = {current.men,current.wemen,current.children,current.adults,current.avgsavings,current.moneycirc,current.avgage,current.oldest,current.avgpossessions,current.avginventory};
		JLabel[] st= {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),};
		for(int c=0;c<10;c++){
			st[c].setText(k[c] + Integer.toString(stat[c]));
			center5.add(st[c]);
		}
		
        north.add(nam);
        south.setLayout(new GridLayout(0,2));
        south.add(runsim);
        south.add(back);
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==runsim){
			current.runsim();
			ga.setText("Items: "+Integer.toString(current.totalitems));
			center.repaint();
			Items();
			center4.repaint();
		}
		else if(e.getSource()==back){
			original();
		}
	}
	public class DropDownHandler implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox cbt = (JComboBox)e.getSource();
			String ch = (String)cbt.getSelectedItem();
			if(ch=="People"){
				People();
			}
			else if(ch=="Professions"){
				Professions();
			}
			else if(ch=="Items"){
				Items();
			}
			else if(ch=="Kingdom"){
				Kingdom();
			}
			else if(ch=="Original"){
				original();
			}
			if(cbt==peeps){
				for(int c=0;c<current.citizens.size();c++){
					if(ch == peo[c]){
						personscreen((Person)current.citizens.elementAt(c));
					}
				}
			}
		}
		
	}
	public void Professions(){
		setVisible(false);
		center2.removeAll();
		String[] jobs = {"Guards: ","Laborers: ","Metalsmiths: ","Carpenters: ","Farmers: ","Tax Collectors: ","Bandits: ","Miners: ","Loggers: ","Clothiers: ","Landlords: ",
				"Traders: ","Builders: ","Preists: ","Doctors: ","Police: "};
		int[] jnums={current.guards,current.laborers,current.metalsmiths,current.carpenters,current.farmers,current.taxcollectors,current.bandits,current.miners,
				current.loggers,current.clothiers,current.landlords,current.traders,current.builders,current.preists,current.doctors,current.police};
		JLabel[] cp1 = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),};
		for(int cnt=0;cnt<16;cnt++){
			cp1[cnt].setText(jobs[cnt] + Integer.toString(jnums[cnt]));
			cp1[cnt].repaint();
			center2.add(cp1[cnt]);
		}
		this.remove(center);
		this.add(center2, BorderLayout.CENTER);
		setVisible(true);
	}
	public void People(){//implement random name generator
		setVisible(false);
		center3.add(peeps);
		this.remove(center);
		this.add(center3, BorderLayout.CENTER);
		setVisible(true);
	}
	public void Items(){
		setVisible(false);
		center4.removeAll();
		center4.setLayout(new GridLayout(0,4));
		String[] i = new String[58];
		System.out.println(current.itms[1]);
		for(int cnt=1;cnt<58;cnt++){
			i[cnt] = Integer.toString(current.itms[cnt]);
			ic[cnt].setText((itmnames[cnt]+" "+i[cnt]));
			center4.add(ic[cnt]);
		}
		this.remove(center);
		this.add(center4, BorderLayout.CENTER);
		setVisible(true);
	}
	public void Kingdom(){
		setVisible(false);
		center5.removeAll();
		center5.setLayout(new GridLayout(0,2));
		String[] k = {"#Men: ","#Women: ","#Children: ","#Adults: ","#AverageMoney: ","#Money Circulating: ","#Average Age: ","#Oldest Citizen: ","#Average Possessions: ","#Average Inventory*: "};
		int[] stat = {current.men,current.wemen,current.children,current.adults,current.avgsavings,current.moneycirc,current.avgage,current.oldest,current.avgpossessions,current.avginventory};
		JLabel[] st= {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),};
		for(int c=0;c<10;c++){
			st[c].setText(k[c] + Integer.toString(stat[c]));
			center5.add(st[c]);
		}
		this.remove(center);
		this.add(center5, BorderLayout.CENTER);
		setVisible(true);
	}
	public void personscreen(Person tem){
		center.removeAll();
	}
	public void original(){
		this.removeAll();
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		this.repaint();
	}
}


