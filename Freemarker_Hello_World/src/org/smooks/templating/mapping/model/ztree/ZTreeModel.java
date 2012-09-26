package org.smooks.templating.mapping.model.ztree;

public class ZTreeModel {

	
	  private int id;
	  private int pId;
	  private String name;
	  private String xpath;
	  private boolean open;
	  private boolean isFolder;

	
	  public ZTreeModel(int id, int pId, String name, String xpath) {
	    this.id = id;
	    this.pId = pId;
	    this.name = name;
	    this.setXpath(xpath);
	    this.open = false;
	    this.isFolder = false;
	  }
	  
	  
	  public ZTreeModel(int id, int pId, String name, String xpath, boolean open,  boolean isFolder) {
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
