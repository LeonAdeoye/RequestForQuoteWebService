package com.leon.ws.rfq.request;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OptionDetailListImpl", namespace = "com.leon.ws.rfq.request")
public final class OptionDetailListImpl implements Iterable<OptionDetailImpl>
{
	@XmlElementWrapper(name = "OptionDetailListImpl")
	@XmlElement(name = "OptionDetailImpl")

	private ArrayList<OptionDetailImpl> optionDetailArrayList;

	public OptionDetailListImpl() {}

	public OptionDetailListImpl(ArrayList<OptionDetailImpl> legs)
	{
		this.optionDetailArrayList = legs;
	}

	public void setOptionDetailList(ArrayList<OptionDetailImpl> legs)
	{
		this.optionDetailArrayList = legs;
	}

	public ArrayList<OptionDetailImpl> getOptionDetailList()
	{
		return this.optionDetailArrayList;
	}

	@Override
	public Iterator<OptionDetailImpl> iterator()
	{
		Iterator<OptionDetailImpl> it = new Iterator<OptionDetailImpl>() {
			private int currentIndex = 0;
			private final int currentSize = OptionDetailListImpl.this.optionDetailArrayList.size();

			@Override
			public boolean hasNext()
			{
				return (this.currentIndex < this.currentSize) && (OptionDetailListImpl.this.optionDetailArrayList.get(this.currentIndex) != null);
			}

			@Override
			public OptionDetailImpl next()
			{
				return OptionDetailListImpl.this.optionDetailArrayList.get(this.currentIndex++);
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
