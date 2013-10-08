package com.leon.ws.rfq.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OptionDetailImpl", namespace = "com.leon.ws.rfq.request")
public final class OptionDetailImpl
{
	private int legId;
	private boolean isCall;
	private boolean isEuropean;
	
    private double delta;
    private double gamma;
    private double theta;
    private double vega;
    private double rho;	
	
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
	
	public double getDelta()
	{
		return this.delta;
	}
	
	public void setDelta(double delta)
	{
		this.delta = delta;
	}
	
	public double getGamma()
	{
		return this.gamma;
	}
	
	public void setGamma(double gamma)
	{
		this.gamma = gamma;
	}
	
	public double getTheta()
	{
		return this.theta;
	}
	
	public void setTheta(double theta)
	{
		this.theta = theta;
	}
	
	public double getVega()
	{
		return this.vega;
	}
	
	public void setVega(double vega)
	{
		this.vega = vega;
	}
	
	public double getRho()
	{
		return this.rho;
	}
	
	public void setRho(double rho)
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
				
				this.delta == param.delta &&
				this.gamma == param.gamma &&
				this.theta == param.theta &&
				this.vega == param.vega &&
				this.rho == param.rho;
	}
	
	@Override
	public int hashCode()
	{
		int result = 17;
		result = 37 * result + (int) legId;
		result = 37 * result + (isCall ? 0 : 1);
		result = 37 * result + (isEuropean ? 0 : 1);
		/*result = 37 * result + (delta == null ? 0 : delta.hashCode());
		result = 37 * result + (gamma == null ? 0 : gamma.hashCode());
		result = 37 * result + (vega == null ? 0 : vega.hashCode());
		result = 37 * result + (theta == null ? 0 : theta.hashCode());
		result = 37 * result + (rho == null ? 0 : rho.hashCode());	*/	
		return result;
	}

	public double getStrike()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public double getDaysToExpiry()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public double getDayCountConvention()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public String getUnderlyingRIC()
	{
		// TODO Auto-generated method stub
		return "";
	}

	public String getCurrency()
	{
		// TODO Auto-generated method stub
		return "";
	}	
}
