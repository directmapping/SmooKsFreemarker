package org.smooks.templating.mapping.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.smooks.templating.mapping.model.ztree.ZTreeModel;
import org.smooks.templating.model.ModelBuilder;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;

public class JSONMappingModelBuilder {

	Collection<ZTreeModel> collection;

		public JSONMappingModelBuilder(Node root) {
		super();
		this.collection =  new ArrayList<ZTreeModel>();
		
		addNodeToModel(new ZTreeModel(1, 0, root.getNodeName(), "/" + root.getNodeName(), true, true));
		
		vsTraverse(root, 1 , "/" + root.getNodeName());
		
		if(root.hasAttributes())
		{
			vsTraverseAttr(root,  1 , "/" + root.getNodeName());
		}
		
	}
		
		public String getJSON()
		{
			Gson gson = new Gson();
			return gson.toJson(collection);
			
		}

		/** recursive processing of xml elements bottom down 
		*
		* @input1 node to process
		* @input2 json object to push data into
		* @input3 parent xpath 
		*
		**/
		 private void vsTraverse(Node node, int pId, String xpath) {
			 
			    NodeList nodeList = node.getChildNodes();
			    for (int i = 0; i < nodeList.getLength(); i++) {
			       
			    	Node currentNode = nodeList.item(i);
			        String currentxpath = xpath +  "/" + currentNode.getNodeName();
			        
			        
			        
			        if(currentNode.hasChildNodes() )
			        {
			        	int id = collection.size() + 1;
			        	addNodeToModel(new ZTreeModel(id, pId, currentNode.getNodeName(),currentxpath , true, true));
			        	
			            		if(assertAddNodeToTemplate(currentNode)) {
								
								
								vsTraverse(currentNode,  id , currentxpath);
								
						    	if(currentNode.hasAttributes())
								{				
					        	vsTraverseAttr(currentNode, id , currentxpath);
								}
				
						    	
			            		}
						
					 
			        	        }
			        else
			        {
			        	if(currentNode.hasAttributes())
						{
			        		int id = collection.size() + 1;
			            	addNodeToModel(new ZTreeModel(id, pId, currentNode.getNodeName(),currentxpath , true, true));
			            	
			            	vsTraverseAttr(currentNode, id , currentxpath);
						}
			        	else if(currentNode.getNodeType() == Node.ELEMENT_NODE)
			        	{
			        		int id = collection.size() + 1;
			        		addNodeToModel(new ZTreeModel(id, pId, currentNode.getNodeName(),currentxpath));
			     		   
			        	}
			        	
			        }
			             
			        
			        
			        
			    }
			}
		 
		
		 
		
		 
		 private boolean assertAddNodeToTemplate(Node currentNode) {
			// TODO Auto-generated method stub
			return !ModelBuilder.isInReservedNamespace(currentNode);
		}

		/** processing  attributes of xml element
		 *
		 * @input1 node to process
		 * @input2 parent xpath 
		 * @output array of attributes in json format
		 *
		 **/
		 private void vsTraverseAttr(Node node,  int pId, String xpath) {
			  //only when attributes exists else return null
				if(node.hasAttributes()){
				    NamedNodeMap nodeList = node.getAttributes();
					
				    int length = nodeList.getLength();
				    for( int i=0; i<length; i++) {
				        Attr attr = (Attr) nodeList.item(i);
				        String name = attr.getName();
				        
				        if(assertAddNodeToTemplate(attr))
				        {
				        	addNodeToModel(new ZTreeModel(collection.size()+1, pId, name,xpath + "/@" +  name));
				        }
				       
				    }
				        
				 
				}
		 
		 }
		 
		 private void  addNodeToModel(ZTreeModel node)
		 {
			 
			
			  int i = 0;
			  Iterator<ZTreeModel> itrnode = collection.iterator();
			  while(itrnode.hasNext()) {
		        ZTreeModel element = itrnode.next();
		
		        if(node.getXpath().equalsIgnoreCase(element.getXpath()))
		        {
		       	 i++;
		        }
		        
		        
		        
		     }
		     
		     if(i==0)
		     {
		   	  collection.add(node);
		     }
		
		 }
}