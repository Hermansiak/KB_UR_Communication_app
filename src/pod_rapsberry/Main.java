package pod_rapsberry;

import java.io.File;
import java.io.IOException;

import moduly.Commands;
import moduly.ModNapiecia;

public class Main {

	private static boolean wyslij;

	public static void main(String[] args) {
		
		int len=args.length;
		if(len!=3)
		{
			System.out.println("Niewystarczająca liczba argumentów\nsskladnia: java -jar nazwa_programu.jar plik_wyjsciowy.txt odstep_miedzy_pomiarami_w_sekundach nazwa_portu_USB");
			System.exit(-1);
		}
		
		File output = new File(args[0]);
		try {
			output.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(!output.exists())
		{
			System.out.println(args.length);
			System.out.println("Nie udalo sie stworzyc pliku / plik juz istnieje\n"+ args[0]+"\n"+args[1]+"\n");
			System.exit(-1);
		}
		
		int seconds=Integer.parseInt(args[1]);
		
		
		int i=0;
		String tekst=null;
		Two_way_comm COM= new Two_way_comm();
		ModNapiecia m_nap1= new ModNapiecia(1);
		Auto_zapytanie auto=new Auto_zapytanie(output,seconds);
		
		try {
			COM.connect(args[2]);
		} catch (Exception e) {

			e.printStackTrace();
		}
		auto.wyslij(m_nap1,5, COM);
		
		
		
		
		
		while(true)
		{
		
			
			//tekst=null;

			/*System.out.println("Czekam");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		}
	}

}
