package org.smooks.freemarker;

import org.milyn.Smooks;
import org.milyn.SmooksException;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;



public class Freemarker_Smooks_External_Template {

	/**
	 * @param argsx
	 */
	 public static void main(String[] args) throws IOException, SAXException, SmooksException, InterruptedException
	    {
	
	           Smooks smooks = new Smooks("smooks-config.xml");
	        try {
	            smooks.filterSource(new StreamSource(new FileInputStream("src/input-message.xml")), new StreamResult(System.out));
		        } finally {
	            smooks.close();
	        }	        
	    }

	  
}
