package com.laetienda.utilities;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;

public class CharArrayWriterResponse extends HttpServletResponseWrapper{
	
	private final CharArrayWriter charArray = new CharArrayWriter();
	
	public CharArrayWriterResponse(HttpServletResponse response){
		super(response);
	}
	
}
