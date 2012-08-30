package org.smooks.processing.input;

public class ZTree_JSON_Node {

	
	  private int id;
	  private int pId;
	  private String name;
	  private String xpath;
	  private boolean open;
	  private boolean isFolder;

	
	  public ZTree_JSON_Node(int id, int pId, String name, String xpath) {
	    this.id = id;
	    this.pId = pId;
	    this.name = name;
	    this.setXpath(xpath);
	    this.open = false;
	    this.isFolder = false;
	  }
	  
	  
	  public ZTree_JSON_Node(int id, int pId, String name, String xpath, boolean open,  boolean isFolder) {
		    this.id = id;
		    this.pId = pId;
		    this.name = name;
		    this.setXpath(xpath);
		    this.open = open;
		    this.isFolder = isFolder;
		  }


	public String getXpath() {
		return xpath;
	}


	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	 	  
	  
}
