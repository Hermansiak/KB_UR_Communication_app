package moduly;

public class ModNapiecia {
	private static final Typ typ=Typ.MOD_NAP_6W; //final to prawie jak const- nie mozna zmieniać wartości zmiennej
	private int lowest_adress=0;
	private int highest_adress=1;
	private int i=0;
	int wartosc;
	Commands cmd;
	int input_number=0;
	int adress=0;
	int adress1=0;
	int values[]= new int [6];
	public String cmdGetIn(int nr_wej)
	{
		return "GET IN "+ nr_wej +" ON "+ adress+"\n";
	}
	
	public String cmdGetAll(int adres_urz)
	{
		return "GET ALL ON " + adres_urz+ "\n";
	}
	public boolean cmdAnswer(String answer)
	{
		Parser ps=new Parser(answer);

		try {
			
			cmd=ps.findCommand();
				switch(cmd)
				{
			case VAL:
				if(!(ps.findCommand().equals(Commands.IN))) 
					return false;
				
				int temp_val=ps.findInt();		
					if(!(temp_val>=1 && temp_val<=5))
						{
						return false;
				
						}else input_number=temp_val;
					
					if(!(ps.findCommand().equals(Commands.ON))) 
						return false;
					
					temp_val=ps.findInt();		
					if(!(temp_val>=lowest_adress && temp_val<=highest_adress))
						{
						return false;
				
						}else adress1=temp_val;
					
					if(!(ps.findCommand().equals(Commands.COLON))) 
						return false;
					
					//System.out.print(cmd.VAL + " " + cmd.IN + " " );
					return true;
					
			
			
			case ERROR:
				break;
			case NAME:
				break;
			case OK:
				break;
			case VALS:
				if(!(ps.findCommand().equals(Commands.ON))) 
					return false;
				temp_val=ps.findInt();		
				if(!(temp_val>=lowest_adress && temp_val<=highest_adress))
					{
					return false;
			
					}else adress1=temp_val;
				
				if(!(ps.findCommand().equals(Commands.COLON))) 
					return false;
				
				return true;
				
					
				
			
//			for(int i=0;i<5;i++)
//			{
//				int temp_vals=ps.findInt();
//
//				values[i]=temp_vals;
//				System.out.println(cmd.VALS + " " +values[i] +" IN "+ (i+1) +"\n");
//			}
//			return true;

			//break;
		default:
			return false;
		}
	} catch (ParserException e) {
		// TODO Auto-generated catch block
		System.out.println("Nieoczekiwana wartosc/polecenie w metodzie cmdAnswer w ModNapiecia.java");
		return false;

	}
	return false;

}
public String cmdGetType()
{
	return typ.name();
}

public ModNapiecia(int adres)
{
	this.adress=adres;
}
}
