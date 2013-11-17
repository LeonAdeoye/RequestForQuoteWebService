package com.leon.ws.rfq.request;

import javax.xml.bind.annotation.XmlRootElement;

import com.leon.ws.rfq.utilities.UtilityMethods;

@XmlRootElement(name="OptionDetailImpl", namespace = "com.leon.ws.rfq.request")
public final class OptionDetailImpl
{
	private int legId;
	private boolean isCall = true;
	private boolean isEuropean = true;

	private double delta;
	private double gamma;
	private double theta;
	private double vega;
	private double rho;
	private double premium;

	private String underlyingRIC;
	private double underlyingPrice;
	private double strike;

	private double strikePercentage;
	private String description;
	private double premiumPercentage;
	private int quantity;
	private double yearsToExpiry;
	private String maturityDate;

	private double daysToExpiry;
	private double dayCountConvention;
	private double volatility;
	private double interestRate;
	private String side;

	public OptionDetailImpl() {}

	public double getStrikePercentage()
	{
		return this.strikePercentage;
	}

	public void setStrikePercentage(double strikePercentage)
	{
		this.strikePercentage = strikePercentage;
	}

	public String getMaturityDate()
	{
		return this.maturityDate;
	}

	public void setMaturityDate(String maturityDate)
	{
		this.maturityDate = maturityDate;
	}

	public double getPremiumPercentage()
	{
		return this.premiumPercentage;
	}

	public void setPremiumPercentage(double premiumPercentage)
	{
		this.premiumPercentage = premiumPercentage;
	}

	public double getYearsToExpiry()
	{
		return this.yearsToExpiry;
	}

	public void setYearsToExpiry(double yearsToExpiry)
	{
		this.yearsToExpiry = yearsToExpiry;
	}

	public int getQuantity()
	{
		return this.quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

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

	public double getPremium()
	{
		return this.premium;
	}

	public void setPremium(double premium)
	{
		this.premium = premium;
	}

	public double getInterestRate()
	{
		return this.interestRate;
	}

	public void setInterestRate(double interestRate)
	{
		this.interestRate = interestRate;
	}

	public double getVolatility()
	{
		return this.volatility;
	}

	public void setVolatility(double volatility)
	{
		this.volatility = volatility;
	}

	public double getUnderlyingPrice()
	{
		return this.underlyingPrice;
	}

	public void setUnderlyingPrice(double underlyingPrice)
	{
		this.underlyingPrice = underlyingPrice;
	}

	public double getStrike()
	{
		return this.strike;
	}

	public void setStrike(double strike)
	{
		this.strike = strike;
	}

	public double getDaysToExpiry()
	{
		return this.daysToExpiry;
	}

	public void setDaysToExpiry(double daysToExpiry)
	{
		this.daysToExpiry = daysToExpiry;
	}

	public double getDayCountConvention()
	{
		return this.dayCountConvention;
	}

	public void setDayCountConvention(double dayCountConvention)
	{
		this.dayCountConvention = dayCountConvention;
	}

	public String getUnderlyingRIC()
	{
		return this.underlyingRIC;
	}

	public void setUnderlyingRIC(String underlyingRIC)
	{
		this.underlyingRIC = underlyingRIC;
	}

	public String getSide()
	{
		return this.side;
	}

	public void setSide(String side)
	{
		this.side = side;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder(" Leg ID: ");
		buf.append(this.legId);
		buf.append(", Is call: ");
		buf.append(this.isCall);
		buf.append(", Is european: ");
		buf.append(this.isEuropean);
		buf.append(", Underlying RIC: ");
		buf.append(this.underlyingRIC);
		buf.append(", Side: ");
		buf.append(this.side);
		buf.append(", Description: ");
		buf.append(this.description);
		buf.append(", Quantity: ");
		buf.append(this.quantity);

		buf.append(", Underlying price: ");
		buf.append(this.underlyingPrice);
		buf.append(", Interest rate: ");
		buf.append(this.interestRate);
		buf.append(", Volatility: ");
		buf.append(this.volatility);
		buf.append(", Days to expiry: ");
		buf.append(this.daysToExpiry);
		buf.append(", Years to expiry: ");
		buf.append(this.yearsToExpiry);
		buf.append(", MaturityDate: ");
		buf.append(this.maturityDate);
		buf.append(", Day count convention: ");
		buf.append(this.dayCountConvention);
		buf.append(", Strike: ");
		buf.append(this.strike);
		buf.append(", Strike Percentage: ");
		buf.append(this.strikePercentage);
		buf.append(", Strike Percentage: ");
		buf.append(this.strikePercentage);


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
		buf.append(", Premium: ");
		buf.append(this.premium);
		buf.append(", Premium Percentage: ");
		buf.append(this.premiumPercentage);


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

		return 	(this.legId == param.legId) &&
				(this.isCall == param.isCall) &&
				(this.isEuropean == param.isEuropean) &&
				(this.description.equals(param.description)) &&
				(this.strike == param.strike) &&
				(this.quantity == param.quantity) &&
				(this.strikePercentage == param.strikePercentage) &&
				(this.underlyingPrice == param.underlyingPrice) &&
				this.underlyingRIC.equals(param.underlyingRIC) &&
				(this.dayCountConvention == param.dayCountConvention) &&
				(this.daysToExpiry == param.daysToExpiry) &&
				(this.yearsToExpiry == param.yearsToExpiry) &&
				(this.maturityDate.equals(param.maturityDate)) &&
				(this.volatility == param.volatility) &&
				(this.interestRate == param.interestRate) &&
				(this.side.equals(param.side)) &&
				(this.premium == param.premium) &&
				(this.premiumPercentage == param.premiumPercentage) &&
				(this.delta == param.delta) &&
				(this.gamma == param.gamma) &&
				(this.theta == param.theta) &&
				(this.vega == param.vega) &&
				(this.rho == param.rho);
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + this.legId;
		result = (37 * result) + (this.isCall ? 0 : 1);
		result = (37 * result) + (this.isEuropean ? 0 : 1);
		result = (37 * result) + this.side.hashCode();
		result = (37 * result) + this.underlyingRIC.hashCode();
		result = (37 * result) + this.description.hashCode();
		result = (37 * result) + this.maturityDate.hashCode();
		result = (37 * result) + UtilityMethods.doubleHashCode(this.delta);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.gamma);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.vega);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.theta);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.rho);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.premiumPercentage);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.premium);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.interestRate);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.volatility);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.yearsToExpiry);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.daysToExpiry);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.underlyingPrice);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.strike);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.strikePercentage);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.quantity);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.dayCountConvention);
		return result;
	}
}
