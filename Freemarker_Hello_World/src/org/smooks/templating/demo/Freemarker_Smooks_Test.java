package org.smooks.templating.demo;
/*
Milyn - Copyright (C) 2006 - 2010

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License (version 2.1) as published by the Free Software
Foundation.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU Lesser General Public License for more details:
http://www.gnu.org/licenses/lgpl.txt
 */

import org.milyn.Smooks;
import org.milyn.SmooksException;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 * 
 */
public class Freemarker_Smooks_Test {
	public static void main(String[] args) throws IOException, SAXException,
			SmooksException, InterruptedException {

	//	Smooks smooks = new Smooks("smooks-config_working.xml");
		Smooks smooks = new Smooks("smooks_config/smooks-config_generic.xml");
	//	Smooks smooks = new Smooks("smooks-config_try1.xml");
		StreamSource test = new StreamSource(new FileInputStream("input/input-message_test.xml"));
		try {
			smooks.filterSource(test, new StreamResult(System.out));
		} catch (Exception e) {
			System.out.println("Problem occured \n");
		} finally {
			smooks.close();
		}

		System.out.println("\n\n");
	}
}