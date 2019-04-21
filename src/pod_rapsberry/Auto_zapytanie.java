package pod_rapsberry;

import moduly.ModNapiecia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;



import java.util.concurrent.*;
public class Auto_zapytanie {

	private final ExecutorService executor;
	File plik;
	PrintWriter zapis;
	int seconds;
	private String ans;
	Date dat = new Date();
	public Auto_zapytanie(File out1, int _seconds)
	{


		plik=out1;
		seconds=_seconds;
		executor=Executors.newScheduledThreadPool(1);
		try {
			zapis = new PrintWriter(out1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private static boolean wyslij;
	ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	String tekst=null;
	public void wyslij(ModNapiecia modul, int nr_wejscia,Two_way_comm COM) 
	{
		// TODO Auto-generated method stub
		executorService.scheduleAtFixedRate(new Runnable() {
			public void run() {


				//COM.putString(modul.cmdGetIn(nr_wejscia));
				//System.out.println(modul.cmdGetIn(nr_wejscia));
				COM.makeOdbiorReady();
				COM.putString(modul.cmdGetAll(1)); // gdzie 1 to adres urządzenia
				System.out.println(modul.cmdGetAll(1));



				try {
					ans=COM.getString();
					System.out.println(ans+"\n\n");
					if(modul.cmdAnswer(ans))
					{
						dat = new Date();
						zapis.println(getDateDescr(dat)+ " " +  ans);

						zapis.flush();

					}	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				



			}
		}, 0, seconds, TimeUnit.SECONDS);
	}

	private static String getDateDescr(Date data){
		if (data == null) return "brak";
		Locale currentLocale = Locale.getDefault();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", currentLocale);
		return formatter.format(data);
	}


}


