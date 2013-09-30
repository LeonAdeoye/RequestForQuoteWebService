package com.leon.ws.rfq.request;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OptionDetailImpl", namespace = "com.leon.ws.rfq.request")
public class OptionDetailImpl
{
	private int legId;
	private boolean isCall;
	private boolean isEuropean;
	
    private BigDecimal delta;
    private BigDecimal gamma;
    private BigDecimal theta;
    private BigDecimal vega;
    private BigDecimal rho;	
	
	public OptionDetailImpl() {}
	
	public int getLegId()
	{
		return this.legId;
	}
	
	public void setLegId(int legId)
	{
		this.legId = legId;
	}
	
	public boolean getIsCall()
	{
		return this.isCall;
	}
	
	public void setIsCall(boolean isCall)
	{
		this.isCall = isCall;
	}
	
	public boolean getIsEuropean()
	{
		return this.isEuropean;
	}
	
	public void setIsEuropean(boolean isEuropean)
	{
		this.isEuropean = isEuropean;
	}
	
	public BigDecimal getDelta()
	{
		return this.delta;
	}
	
	public void setDelta(BigDecimal delta)
	{
		this.delta = delta;
	}
	
	public BigDecimal getGamma()
	{
		return this.gamma;
	}
	
	public void setGamma(BigDecimal gamma)
	{
		this.gamma = gamma;
	}
	
	public BigDecimal getTheta()
	{
		return this.theta;
	}
	
	public void setTheta(BigDecimal theta)
	{
		this.theta = theta;
	}
	
	public BigDecimal getVega()
	{
		return this.vega;
	}
	
	public void setVega(BigDecimal vega)
	{
		this.vega = vega;
	}
	
	public BigDecimal getRho()
	{
		return this.rho;
	}
	
	public void setRho(BigDecimal rho)
	{
		this.rho = rho;
	}
	
	
	@Override
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append(" Leg ID: ");
		buf.append(this.legId);
		buf.append(", Is Call: ");
		buf.append(this.isCall);
		buf.append(", Is European: ");
		buf.append(this.isEuropean);
		
		buf.append(", Delta: ");
		buf.append(this.delta);
		buf.append(", Gamma: ");
		buf.append(this.gamma);
		buf.append(", Vega: ");
		buf.append(this.vega);
		buf.append(", Theta: ");
		buf.append(this.theta);
		buf.append(", Rho: ");
		buf.append(this.rho);
		
		return buf.toString();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		
		if(!(o instanceof OptionDetailImpl))
			return false;
		
		OptionDetailImpl param = (OptionDetailImpl) o;
		
		return 	this.legId == param.legId &&	
				this.isCall == param.isCall &&
				this.isEuropean == param.isEuropean &&
				
				this.delta.equals(param.delta) &&
				this.gamma.equals(param.gamma) &&
				this.theta.equals(param.theta) &&
				this.vega.equals(param.vega) &&
				this.rho.equals(param.rho);
	}
	
	@Override
	public int hashCode()
	{
		int result = 17;
		result = 37 * result + (int) legId;
		result = 37 * result + (isCall ? 0 : 1);
		result = 37 * result + (isEuropean ? 0 : 1);
		result = 37 * result + (delta == null ? 0 : delta.hashCode());
		result = 37 * result + (gamma == null ? 0 : gamma.hashCode());
		result = 37 * result + (vega == null ? 0 : vega.hashCode());
		result = 37 * result + (theta == null ? 0 : theta.hashCode());
		result = 37 * result + (rho == null ? 0 : rho.hashCode());		
		return result;
	}	
}
