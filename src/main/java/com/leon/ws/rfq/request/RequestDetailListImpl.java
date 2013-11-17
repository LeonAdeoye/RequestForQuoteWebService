package com.leon.ws.rfq.request;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RequestDetailListImpl", namespace = "com.leon.ws.rfq.request")
public final class RequestDetailListImpl implements Iterable<RequestDetailImpl>
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

	@Override
	public Iterator<RequestDetailImpl> iterator()
	{
		Iterator<RequestDetailImpl> it = new Iterator<RequestDetailImpl>() {
			private int currentIndex = 0;
			private final int currentSize = RequestDetailListImpl.this.requestDetailArrayList.size();

			@Override
			public boolean hasNext()
			{
				return (this.currentIndex < this.currentSize) && (RequestDetailListImpl.this.requestDetailArrayList.get(this.currentIndex) != null);
			}

			@Override
			public RequestDetailImpl next()
			{
				return RequestDetailListImpl.this.requestDetailArrayList.get(this.currentIndex++);
			}

			@Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};

		return it;
	}
}