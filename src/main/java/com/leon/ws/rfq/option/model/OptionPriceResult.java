package com.leon.ws.rfq.option.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name="OptionPriceResult")
public class OptionPriceResult
{
	private double delta;
	private double gamma;
	private double rho;
	private double theta;
	private double vega;
	private double price;
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nDelta=");
		sb.append(delta);
		sb.append("\nGamma=");
		sb.append(gamma);
		sb.append("\nVega=");
		sb.append(vega);
		sb.append("\nTheta=");
		sb.append(theta);
		sb.append("\nRho=");
		sb.append(rho);
		sb.append("\nPrice=");
		sb.append(price);		
		return sb.toString();
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		
		if(!(o instanceof OptionPriceResult))
			return false;
		
		OptionPriceResult res = (OptionPriceResult) o;
		
		return this.delta == res.delta && this.gamma == res.gamma && this.vega == res.vega 
				&& this.theta == res.theta && this.rho == res.rho && this.price == res.price;  
	}
	
	public int hashCode()
	{
		int result = 17;
		long value = Double.doubleToLongBits(delta);
		result = 37 * result + (int) (value ^ value >>> 32);
		value = Double.doubleToLongBits(gamma);
		result = 37 * result + (int) (value ^ value >>> 32);
		value = Double.doubleToLongBits(theta);
		result = 37 * result + (int) (value ^ value >>> 32); 
		value = Double.doubleToLongBits(vega);
		result = 37 * result + (int) (value ^ value >>> 32); 
		value = Double.doubleToLongBits(rho);
		result = 37 * result + (int) (value ^ value >>> 32);
		value = Double.doubleToLongBits(price);
		result = 37 * result + (int) (value ^ value >>> 32); 		
		return result;
	}	
	
	public void setDelta(BigDecimal delta)
	{
		this.delta = delta.doubleValue();
	}
	
	public BigDecimal getDelta()
	{
		return new BigDecimal(this.delta);
	}
	
	public void setGamma(BigDecimal gamma)
	{
		this.gamma = gamma.doubleValue();
	}
	
	public BigDecimal getGamma()
	{
		return new BigDecimal(this.gamma);
	}
	
	public void setTheta(BigDecimal theta)
	{
		this.theta = theta.doubleValue();
	}
	
	public BigDecimal getTheta()
	{
		return new BigDecimal(this.theta);
	}
	
	public void setVega(BigDecimal vega)
	{
		this.vega = vega.doubleValue();
	}
	
	public BigDecimal getVega()
	{
		return new BigDecimal(this.vega);
	}
	
	public void setRho(BigDecimal rho)
	{
		this.rho = rho.doubleValue();
	}
	
	public BigDecimal getRho()
	{
		return new BigDecimal(this.rho);
	}
	
	public void setPrice(BigDecimal price)
	{
		this.price = price.doubleValue();
	}
	
	public BigDecimal getPrice()
	{
		return new BigDecimal(this.price);
	}	
}
