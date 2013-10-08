package com.leon.ws.rfq.request;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RequestDetailListImpl", namespace = "com.leon.ws.rfq.request")
public final class RequestDetailListImpl
{
	@XmlElementWrapper(name = "RequestDetailListImpl")
	@XmlElement(name = "RequestDetailImpl")
	  
	private ArrayList<RequestDetailImpl> requestDetailArrayList;
	
	public RequestDetailListImpl(ArrayList<RequestDetailImpl> requests)
	{
		this.requestDetailArrayList = requests;
	}
	
	public RequestDetailListImpl() {}
	  
	public void setRequestDetailList(ArrayList<RequestDetailImpl> requests) 
	{
		this.requestDetailArrayList = requests;
	}
	
	public ArrayList<RequestDetailImpl> getRequestDetailList() 
	{
		return this.requestDetailArrayList;
	}	  
}