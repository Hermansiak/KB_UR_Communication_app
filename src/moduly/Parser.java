package moduly;

public class Parser {
	String to_parse;
	int index=0;
	public String o;
	public Parser(String parse)
	{
		this.to_parse=parse;

	}

	public boolean findText(String wzor) throws ParserException
	{

		return  wzor.equalsIgnoreCase(getSubstring());
	}

	private String getSubstring() throws ParserException
	{
		int temp_index_begin=0;
		while(index<to_parse.length())
		{
			char x= to_parse.charAt(index);
			if(Character.isWhitespace(x))
				index++;
			else break;
		}



		temp_index_begin=index;

		if(temp_index_begin>=to_parse.length())
			throw new ParserException("index na koncu stringa, pusty substring w metodzie getSubstring Parser.java");
		while(index<to_parse.length())
		{	int cos =to_parse.length();
		char y= to_parse.charAt(index);
		if(!Character.isWhitespace(y))
			index++;
		else break;
		}


		return to_parse.substring(temp_index_begin, index);
	}

	public int findInt() throws ParserException
	{
		try {
			return Integer.parseInt(getSubstring());
		}catch(Exception e)
		{
			throw new ParserException("exception w metodzie FindInt - PArser.java");
		}

	}

	public Commands findCommand() throws ParserException
	{

		o=getSubstring();
		Commands cmd= Commands.getCommand(o);
		if (cmd==null)
			throw new ParserException("cmd=null - metoda FindCommand w Parser.java");

		return cmd;
	}
	public boolean isAtEnd()
	{
		return index>=to_parse.length();
	}
}

