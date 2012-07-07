package org.smooks.freemarker;
 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
 
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
 
public class Freemarker_External_Template {
     
    public static void main(String[] args) {
         
        //Freemarker configuration object
        Configuration cfg = new Configuration();
        try {
            //Load template from source folder
            Template     	template 	= cfg.getTemplate("src/freemarker_template.ftl");
            Map 			root 		= new HashMap();
            try {
				root.put("doc", freemarker.ext.dom.NodeModel.parse(new File("src/input-message.xml")));
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
            // Console output
            Writer out = new OutputStreamWriter(System.out);
            template.process(root, out);
            out.flush();
  
            
            
          
            	
            // File output
            Writer file = new FileWriter (new File("src/test.xml"));
            template.process(root, file);
            file.flush();
            file.close();
            
       
            
             
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}