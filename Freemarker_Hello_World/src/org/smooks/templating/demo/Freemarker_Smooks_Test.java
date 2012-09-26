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

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.eclipse.emf.common.util.URI;
import org.milyn.Smooks;
import org.milyn.SmooksException;
import org.milyn.xml.XmlUtil;
import org.smooks.templating.mapping.model.JSONMappingModelBuilder;
import org.smooks.templating.model.ModelBuilder;
import org.smooks.templating.model.ModelBuilderException;
import org.smooks.templating.model.ModelNodeResolver;
import org.smooks.templating.model.xml.XMLSampleModelBuilder;
import org.smooks.templating.template.TemplateBuilder;
import org.smooks.templating.template.exception.InvalidMappingException;
import org.smooks.templating.template.exception.TemplateBuilderException;
import org.smooks.templating.template.freemarker.FreeMarkerTemplateBuilder;
import org.smooks.templating.template.xml.XMLFreeMarkerTemplateBuilder;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathExpressionException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/**
 * 
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 * 
 */
public class Freemarker_Smooks_Test {
	public static void main(String[] args) throws IOException, SAXException,
			SmooksException, InterruptedException {
		Smooks smooks =null;
		
		try {
			 StreamSource test = new StreamSource(new FileInputStream("source/source.xml"));
			 
			String template = getModelBuilderTarget("target/target.xml");
			smooks = new Smooks(getSmooksConfiguration(template));
			smooks.filterSource(test, new StreamResult(System.out));
			
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ModelBuilderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TemplateBuilderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally { smooks.close(); }
		
		 System.out.println("\n\n");
		
	}
	public static InputStream getSmooksConfiguration(String template) throws IOException
	{
		Writer templateWriter = new StringWriter();
		
		templateWriter.write("<?xml version=\"1.0\"?>");
		templateWriter.write('\n');
		templateWriter.write("<smooks-resource-list xmlns=\"http://www.milyn.org/xsd/smooks-1.1.xsd\" xmlns:core=\"http://www.milyn.org/xsd/smooks/smooks-core-1.3.xsd\" xmlns:ftl=\"http://www.milyn.org/xsd/smooks/freemarker-1.1.xsd\">");
		templateWriter.write('\n');
		templateWriter.write("<ftl:freemarker applyOnElement=\"#document\">");
		templateWriter.write('\n');	
		templateWriter.write("<ftl:template>");
		templateWriter.write("<![CDATA[");
		templateWriter.write(template);
		templateWriter.write("]]>");
		templateWriter.write("</ftl:template>");
		templateWriter.write('\n');
		templateWriter.write("</ftl:freemarker>");
		templateWriter.write('\n');
		templateWriter.write("<resource-config selector=\"#document\">");
		templateWriter.write('\n');
		templateWriter.write("<resource>org.milyn.delivery.DomModelCreator</resource>");
		templateWriter.write('\n');
		templateWriter.write("</resource-config>");
		templateWriter.write('\n');
		templateWriter.write("</smooks-resource-list>");
		
		InputStream is = new ByteArrayInputStream(templateWriter.toString().getBytes());
		return is;
	}
	
	/**
	 * @param contents
	 * @param newFilePath
	 * @return
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws ModelBuilderException
	 * @throws TemplateBuilderException
	 * @throws XPathExpressionException 
	 */
	public static String getModelBuilderTarget(String newFilePath)
			throws InvocationTargetException, IOException,
			ModelBuilderException, TemplateBuilderException, XPathExpressionException {
		FreeMarkerTemplateBuilder templateBuilder = null;
		ModelBuilder builder;

		builder = new XMLSampleModelBuilder(URI.createFileURI(newFilePath),false);
				
		JSONMappingModelBuilder destination = new JSONMappingModelBuilder(builder.buildModel().getDocumentElement());
		//System.out.println(destination.getJSON());
		
		
		builder.configureModel();
		
	
		//	printDocument(builder.buildModel(), System.out);
	


			templateBuilder = new XMLFreeMarkerTemplateBuilder(builder);

			 String mapping = 	"[\n  {\n    \"id\": \"1\",\n    \"from\": \"/shiporder/@orderid\",\n    \"to\": \"/catalog/book/author\",\n    \"rowid\": \"1\"\n  },\n  {\n    \"id\": \"2\",\n    \"from\": \"/shiporder\",\n    \"to\": \"/catalog\",\n    \"rowid\": \"2\"\n  },\n  {\n    \"id\": \"3\",\n    \"from\": \"/shiporder/shipto/name\",\n    \"to\": \"/catalog/book/author\",\n    \"rowid\": \"3\"\n  },\n  {\n    \"id\": \"4\",\n    \"from\": \"/shiporder/item/title\",\n    \"to\": \"/catalog/book/@id\",\n    \"rowid\": \"4\"\n  },\n  {\n    \"id\": \"5\",\n    \"from\": \"/shiporder/shipto/country\",\n    \"to\": \"/catalog/book/publish_date\",\n    \"rowid\": \"5\"\n  },\n  {\n    \"id\": \"6\",\n    \"from\": \"/shiporder/shipto/address\",\n    \"to\": \"/catalog/book/description\",\n    \"rowid\": \"6\"\n  },\n  {\n    \"id\": \"7\",\n    \"from\": \"/shiporder/shipto/city\",\n    \"to\": \"/catalog/book/title\",\n    \"rowid\": \"7\"\n  },\n  {\n    \"id\": \"8\",\n    \"from\": \"/shiporder/shipto/name\",\n    \"to\": \"/catalog/book/genre\",\n    \"rowid\": \"8\"\n  }\n]";	
			 templateBuilder = addMappping(templateBuilder, mapping);
			// printDocument(templateBuilder.getModel(), System.out);
		
			
		


		templateBuilder.setNodeModelSource(true);
	
		
		return templateBuilder.buildTemplate();
	}

	public static FreeMarkerTemplateBuilder addMappping(FreeMarkerTemplateBuilder templateBuilder,  String mapping) throws InvalidMappingException, XPathExpressionException{
		 
		
		JsonParser parser = new JsonParser();
		JsonArray o = (JsonArray)parser.parse(mapping);

		Iterator<JsonElement> iterator = o.iterator();
		
		while(iterator.hasNext()){
			JsonObject json = (JsonObject)iterator.next();
		    String from = json.get("from").getAsString();
		    String to = json.get("to").getAsString();
		    
			templateBuilder.addValueMapping(from,templateBuilder.getModelNode(to));
			   
		}
	
				
		return templateBuilder;
			
			
		
	}
	
	
	
	public static void printDocument(org.w3c.dom.Document document,
			OutputStream out) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(document), new StreamResult(
				new OutputStreamWriter(out, "UTF-8")));
	}
}