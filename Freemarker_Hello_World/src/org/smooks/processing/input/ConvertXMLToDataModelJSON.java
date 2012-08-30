package org.smooks.processing.input;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.NodeIteratorImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;

import com.google.gson.Gson;

public class ConvertXMLToDataModelJSON {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		  try {
				File fXmlFile = new File("c:\\Users\\Misko\\file.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				
				//traversDOMXML(doc);
				
				Collection<ZTree_JSON_Node> collection = new ArrayList<ZTree_JSON_Node>();
				Node root = doc.getDocumentElement();
				
				collection.add(new ZTree_JSON_Node(1, 0, root.getNodeName(), "/" + root.getNodeName(), true, true));
				
				vsTraverse(root, collection, 1 , "/" + root.getNodeName());
				
				if(root.hasAttributes())
				{
					vsTraverseAttr(root, collection, 1 , "/" + root.getNodeName());
				}
				
				
				Gson gson = new Gson();
				String json = gson.toJson(collection);
				
				System.out.println(json);
		
		  } catch (Exception e) {
				e.printStackTrace();
			  }
	
	
	}
	/** recursive processing of xml elements bottom down 
	 *
	 * @input1 node to process
	 * @input2 json object to push data into
	 * @input3 parent xpath 
	 *
	 **/
	  public static void vsTraverse(Node node, Collection<ZTree_JSON_Node> collection, int pId, String xpath) {
		 
		    NodeList nodeList = node.getChildNodes();
		    for (int i = 0; i < nodeList.getLength(); i++) {
		       
		    	Node currentNode = nodeList.item(i);
		        String currentxpath = xpath +  "/" + currentNode.getNodeName();
		        
		        
		        
		        if(currentNode.hasChildNodes() )
		        {
		        	int id = collection.size() + 1;
		        	collection =  addNodeToModel(new ZTree_JSON_Node(id, pId, currentNode.getNodeName(),currentxpath , true, true),collection);
		        	vsTraverse(currentNode, collection, id , currentxpath);

			    	if(currentNode.hasAttributes())
					{				
		        	vsTraverseAttr(currentNode, collection, id , currentxpath);
					}
		        }
		        else
		        {
		        	if(currentNode.hasAttributes())
					{
		        		int id = collection.size() + 1;
		            	collection =  addNodeToModel(new ZTree_JSON_Node(id, pId, currentNode.getNodeName(),currentxpath , true, true), collection);
		            	
		            	vsTraverseAttr(currentNode, collection, id , currentxpath);
					}
		        	else if(currentNode.getNodeType() == Node.ELEMENT_NODE)
		        	{
		        		int id = collection.size() + 1;
		        		collection =  addNodeToModel(new ZTree_JSON_Node(id, pId, currentNode.getNodeName(),currentxpath),collection);
		     		   
		        	}
		        	
		        }
		             
		        
		        
		        
		    }
		}
	  

	  
 
	  
	  /** processing  attributes of xml element
	  *
	  * @input1 node to process
	  * @input2 parent xpath 
	  * @output array of attributes in json format
	  *
	  **/
	  public static void vsTraverseAttr(Node node, Collection<ZTree_JSON_Node> collection, int pId, String xpath) {
		  //only when attributes exists else return null
			if(node.hasAttributes()){
			    NamedNodeMap nodeList = node.getAttributes();
				
			    int length = nodeList.getLength();
			    for( int i=0; i<length; i++) {
			        Attr attr = (Attr) nodeList.item(i);
			        String name = attr.getName();
			        
			    	collection =  addNodeToModel(new ZTree_JSON_Node(collection.size()+1, pId, name,xpath + "/@" +  name),collection);
			    	
			       
			    }
			        
			 
			}
	  
	  }
	  
	  public static Collection<ZTree_JSON_Node>  addNodeToModel(ZTree_JSON_Node node, Collection<ZTree_JSON_Node> collection)
	  {
		  int i = 0;
		  Iterator<ZTree_JSON_Node> itrnode = collection.iterator();
	      while(itrnode.hasNext()) {
	         ZTree_JSON_Node element = itrnode.next();

	         if(node.getXpath().equalsIgnoreCase(element.getXpath()))
	         {
	        	 i++;
	         }
	         
	         
	         
	      }
	      
	      if(i==0)
	      {
	    	  collection.add(node);
	      }
	      
		  return collection;
	  }



	
}





/*


private static void traversDOMXML(Document document){
	 
	  try{
	  //get the root of the XML document
    Node root = document.getLastChild();
    //create an object of the NodeIterator implementation class
    NodeIteratorImpl iterator =			(NodeIteratorImpl)((DocumentImpl) document).createNodeIterator(root,NodeFilter.SHOW_ELEMENT,  null, true);
    //recursively print all elements of the XML document
    printElements(iterator);

  }
  catch (Exception e)
  {
    System.out.println("error: " + e);
    e.printStackTrace();
    System.exit(0);
  }

}


  private static String getTagValue(String sTag, Element eElement) {
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		 
		        Node nValue = (Node) nlList.item(0);
		 
			return nValue.getNodeValue();
		  }
	  
	  

//recursive function that prints all elements of the XML document
public static void printElements(NodeIteratorImpl iter)
{
  Node n;
  while ((n = iter.nextNode()) != null)
  {
    System.out.println(n.getNodeName());
  }
}
*/