package pod_rapsberry;
//import java.io.PrintStream;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import moduly.Parser;
import moduly.ParserException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Flushable;
//import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;



public class Two_way_comm implements SerialPortEventListener {

	private BufferedReader buff_in;
	private BufferedReader do_wysylu;
	private BufferedWriter buff_out;
	private InputStream in;
	private OutputStream out;
	private PipedOutputStream pos;
	private boolean odbior_gotowy=false;
	public final int TIMEOUT=1000;
	public Two_way_comm()
	{
		super();
	}

	void connect ( String portName ) throws Exception
	{
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if ( portIdentifier.isCurrentlyOwned() )
		{
			System.out.println("Error: Port is currently in use");
		}
		else
		{
			CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

			if ( commPort instanceof SerialPort )
			{
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(38400,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

				in=serialPort.getInputStream();
				out = serialPort.getOutputStream();
				System.out.println("connected");

				PipedInputStream pis = new PipedInputStream();
				pos = new PipedOutputStream(pis);

				buff_in= new BufferedReader(new InputStreamReader(pis, StandardCharsets.UTF_8));
				buff_out= new BufferedWriter(new OutputStreamWriter(serialPort.getOutputStream(), StandardCharsets.UTF_8));
				do_wysylu=new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
				serialPort.addEventListener(this);
				serialPort.notifyOnDataAvailable(true);

				//(new Thread(new SerialReader(in))).start();
				//(new Thread(new SerialWriter(out))).start();

			}
			else
			{
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}     
	}


	public void putString(String tekst)
	{		//String tekst="blad wysylu";
		Parser ps= new Parser(tekst);

		//	tekst=ps.o;

		try {
			buff_out.write(tekst);
			buff_out.flush();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




	public synchronized String getString () throws InterruptedException
	{ 
		String txt="blad";

		try {
			wait(TIMEOUT);

			txt= buff_in.readLine();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return txt;
	}


	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		odbior_serialEvent();

	}

	private synchronized void odbior_serialEvent()
	{
		int data;
		try {
			while((data=in.read())>-1)
			{

				pos.write(data);
				if(data=='\n')
				{
					
					this.odbior_gotowy=true; 
					notifyAll();
					break;
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public synchronized boolean isOdbiorReady()
	{
		return odbior_gotowy;
	}
	/** 

	/** 
	public static class SerialWriter implements Runnable 
	{
		OutputStream out;

		public SerialWriter ( OutputStream out )
		{
			this.out = out;
		}

		public void run ()
		{
			try
			{                
				int c = 0;
				while ( ( c = System.in.read()) > -1 )
				{
					this.out.write(c);
					/*                if(flag==1)
                  {
                    	 this.out.write("Napiecie zmierzono");
                    }

				}                
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}            
		}*/

	public synchronized void makeOdbiorReady() 
	{
		// TODO Auto-generated method stub
		odbior_gotowy=false;
		
	}
}


