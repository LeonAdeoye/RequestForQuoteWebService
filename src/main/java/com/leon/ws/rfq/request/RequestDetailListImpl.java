package com.leon.ws.rfq.request;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "com.leon.ws.rfq.request")
public class RequestDetailListImpl
{
	@XmlElementWrapper(name = "RequestDetailListImpl")
	@XmlElement(name = "RequestDetailImpl")
	  
	private ArrayList<RequestDetailImpl> requestDetailList;
	
	public RequestDetailListImpl(ArrayList<RequestDetailImpl> requests)
	{
		this.requestDetailList = requests;
	}
	
	public RequestDetailListImpl() {}
	  
	public void setRequestDetailList(ArrayList<RequestDetailImpl> requests) 
	{
		this.requestDetailList = requests;
	}
	
	public ArrayList<RequestDetailImpl> getRequestDetailList() 
	{
		return this.requestDetailList;
	}	  
}