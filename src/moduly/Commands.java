package moduly;

public enum Commands {
GET("GET"),
VAL("VAL"),
VALS("VALS"),
NAME("NAME"),
OK("OK"),
ERROR("ERROR"),
IN("IN"),
COLON(":"), 
ON("ON");
	private String wzorek;
	private Commands(String wzor)
	{
		wzorek=wzor;
	}
	
	
	public static Commands getCommand(String o)
	{
		for (Commands  cmd : Commands.values()) {
			if(o.equalsIgnoreCase(cmd.wzorek))
					return cmd;
		}
	return null;	
	}
}
