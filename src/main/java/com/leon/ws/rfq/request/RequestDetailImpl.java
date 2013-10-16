package com.leon.ws.rfq.request;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RequestDetailImpl", namespace = "com.leon.ws.rfq.request")
public final class RequestDetailImpl
{
	private String request;
	private String bookCode;
	private int identifier;
	private int clientId;
	private boolean isOTC;
	private String status;

	private int lotSize;
	private int multiplier;
	private int contracts;
	private int quantity;

	private String tradeDate;
	private String expiryDate;

	private double notionalMillions;
	private double notionalFXRate;
	private String notionalCurrency;

	private double delta;
	private double gamma;
	private double theta;
	private double vega;
	private double rho;

	private double deltaNotional;
	private double gammaNotional;
	private double thetaNotional;
	private double vegaNotional;
	private double rhoNotional;

	private double deltaShares;
	private double gammaShares;
	private double thetaShares;
	private double vegaShares;
	private double rhoShares;

	private String premiumSettlementCurrency;
	private String premiumSettlementDate;
	private int premiumSettlementDaysOverride;
	private double premiumSettlementFXRate;

	private double salesCreditAmount;
	private double salesCreditPercentage;
	private double salesCreditFXRate;
	private String salesCreditCurrency;

	private double bidImpliedVol;
	private double bidPremiumPercentage;
	private double bidPremiumAmount;
	private double bidFinalAmount;
	private double bidFinalPercentage;

	private double impliedVol;
	private double premiumAmount;
	private double premiumPercentage;

	private double askImpliedVol;
	private double askPremiumPercentage;
	private double askPremiumAmount;
	private double askFinalAmount;
	private double askFinalPercentage;

	private String salesComment;
	private String traderComment;
	private String clientComment;

	private String pickedUpBy;
	private String hedgeType;
	private double hedgePrice;
	private double totalPremium;

	private ArrayList<OptionDetailImpl> legs;

	public RequestDetailImpl() {}

	public OptionDetailListImpl getLegs()
	{
		OptionDetailListImpl optionlegs =  new OptionDetailListImpl();
		optionlegs.setOptionDetailList(this.legs);
		return optionlegs;
	}

	public void setLegs(OptionDetailListImpl optionLegs)
	{
		this.legs = optionLegs.getOptionDetailList();
	}

	public String getRequest()
	{
		return this.request;
	}

	public void setRequest(String request)
	{
		this.request = request;
	}

	public String getBookCode()
	{
		return this.bookCode;
	}

	public void setBookCode(String bookCode)
	{
		this.bookCode = bookCode;
	}

	public int getIdentifier()
	{
		return this.identifier;
	}

	public void setIdentifier(int identifier)
	{
		this.identifier = identifier;
	}

	public int getClientId()
	{
		return this.clientId;
	}

	public void setClientId(int clientId)
	{
		this.clientId = clientId;
	}

	public double getNotionalMillions()
	{
		return this.notionalMillions;
	}

	public void setNotionalMillions(double notionalMillions)
	{
		this.notionalMillions = notionalMillions;
	}

	public double getNotionalFXRate()
	{
		return this.notionalFXRate;
	}

	public void setNotionalFXRate(double notionalFXRate)
	{
		this.notionalFXRate = notionalFXRate;
	}

	public String getNotionalCurrency()
	{
		return this.notionalCurrency;
	}

	public void setNotionalCurrency(String notionalCurrency)
	{
		this.notionalCurrency = notionalCurrency;
	}

	public String getTradeDate()
	{
		return this.tradeDate;
	}

	public void setTradeDate(String tradeDate)
	{
		this.tradeDate = tradeDate;
	}

	public String getExpiryDate()
	{
		return this.expiryDate;
	}

	public void setExpiryDate(String expiryDate)
	{
		this.expiryDate = expiryDate;
	}

	public int getLotSize()
	{
		return this.lotSize;
	}

	public void setLotSize(int lotSize)
	{
		this.lotSize = lotSize;
	}

	public int getMultiplier()
	{
		return this.multiplier;
	}

	public void setMultiplier(int multiplier)
	{
		this.multiplier = multiplier;
	}

	public int getContracts()
	{
		return this.contracts;
	}

	public void setContracts(int contracts)
	{
		this.contracts = contracts;
	}

	public int getQuantity()
	{
		return this.quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public double getDeltaShares()
	{
		return this.deltaShares;
	}

	public void setDeltaShares(double deltaShares)
	{
		this.deltaShares = deltaShares;
	}

	public double getGammaShares()
	{
		return this.gammaShares;
	}

	public void setGammaShares(double gammaShares)
	{
		this.gammaShares = gammaShares;
	}

	public double getThetaShares()
	{
		return this.thetaShares;
	}

	public void setThetaShares(double thetaShares)
	{
		this.thetaShares = thetaShares;
	}

	public double getVegaShares()
	{
		return this.vegaShares;
	}

	public void setVegaShares(double vegaShares)
	{
		this.vegaShares = vegaShares;
	}

	public double getRhoShares()
	{
		return this.rhoShares;
	}

	public void setRhoShares(double rhoShares)
	{
		this.rhoShares = rhoShares;
	}

	public double getDeltaNotional()
	{
		return this.deltaNotional;
	}

	public void setDeltaNotional(double deltaNotional)
	{
		this.deltaNotional = deltaNotional;
	}

	public double getGammaNotional()
	{
		return this.gammaNotional;
	}

	public void setGammaNotional(double gammaNotional)
	{
		this.gammaNotional = gammaNotional;
	}

	public double getThetaNotional()
	{
		return this.thetaNotional;
	}

	public void setThetaNotional(double thetaNotional)
	{
		this.thetaNotional = thetaNotional;
	}

	public double getVegaNotional()
	{
		return this.vegaNotional;
	}

	public void setVegaNotional(double vegaNotional)
	{
		this.vegaNotional = vegaNotional;
	}

	public double getRhoNotional()
	{
		return this.rhoNotional;
	}

	public void setRhoNotional(double rhoNotional)
	{
		this.rhoNotional = rhoNotional;
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

	public String getPremiumSettlementCurrency()
	{
		return this.premiumSettlementCurrency;
	}

	public void setPremiumSettlementCurrency(String premiumSettlementCurrency)
	{
		this.premiumSettlementCurrency = premiumSettlementCurrency;
	}

	public String getPremiumSettlementDate()
	{
		return this.premiumSettlementDate;
	}

	public void setPremiumSettlementDate(String premiumSettlementDate)
	{
		this.premiumSettlementDate = premiumSettlementDate;
	}

	public double getPremiumSettlementFXRate()
	{
		return this.premiumSettlementFXRate;
	}

	public void setPremiumSettlementFXRate(double premiumSettlementFXRate)
	{
		this.premiumSettlementFXRate = premiumSettlementFXRate;
	}

	public int getPremiumSettlementDaysOverride()
	{
		return this.premiumSettlementDaysOverride;
	}

	public void setPremiumSettlementDaysOverride(int premiumSettlementDaysOverride)
	{
		this.premiumSettlementDaysOverride = premiumSettlementDaysOverride;
	}

	public String getSalesCreditCurrency()
	{
		return this.salesCreditCurrency;
	}

	public void setSalesCreditCurrency(String salesCreditCurrency)
	{
		this.salesCreditCurrency = salesCreditCurrency;
	}

	public double getSalesCreditPercentage()
	{
		return this.salesCreditPercentage;
	}

	public void setSalesCreditPercentage(double salesCreditPercentage)
	{
		this.salesCreditPercentage = salesCreditPercentage;
	}

	public double getSalesCreditFXRate()
	{
		return this.salesCreditFXRate;
	}

	public void setSalesCreditFXRate(double salesCreditFXRate)
	{
		this.salesCreditFXRate = salesCreditFXRate;
	}

	public double getSalesCreditAmount()
	{
		return this.salesCreditAmount;
	}

	public void setSalesCreditAmount(double salesCreditAmount)
	{
		this.salesCreditAmount = salesCreditAmount;
	}

	public double getBidImpliedVol()
	{
		return this.bidImpliedVol;
	}

	public void setBidImpliedVol(double bidImpliedVol)
	{
		this.bidImpliedVol = bidImpliedVol;
	}

	public double getBidPremiumAmount()
	{
		return this.bidPremiumAmount;
	}

	public void setBidPremiumAmount(double bidPremiumAmount)
	{
		this.bidPremiumAmount = bidPremiumAmount;
	}

	public double getBidPremiumPercentage()
	{
		return this.bidPremiumPercentage;
	}

	public void setBidPremiumPercentage(double bidPremiumPercentage)
	{
		this.bidPremiumPercentage = bidPremiumPercentage;
	}

	public double getBidFinalAmount()
	{
		return this.bidFinalAmount;
	}

	public void setBidFinalAmount(double bidFinalAmount)
	{
		this.bidFinalAmount = bidFinalAmount;
	}

	public double getBidFinalPercentage()
	{
		return this.bidFinalPercentage;
	}

	public void setBidFinalPercentage(double bidFinalPercentage)
	{
		this.bidFinalPercentage = bidFinalPercentage;
	}

	public double getAskImpliedVol()
	{
		return this.askImpliedVol;
	}

	public void setAskImpliedVol(double askImpliedVol)
	{
		this.askImpliedVol = askImpliedVol;
	}

	public double getAskPremiumAmount()
	{
		return this.askPremiumAmount;
	}

	public void setAskPremiumAmount(double askPremiumAmount)
	{
		this.askPremiumAmount = askPremiumAmount;
	}

	public double getAskPremiumPercentage()
	{
		return this.askPremiumPercentage;
	}

	public void setAskPremiumPercentage(double askPremiumPercentage)
	{
		this.askPremiumPercentage = askPremiumPercentage;
	}

	public double getAskFinalAmount()
	{
		return this.askFinalAmount;
	}

	public void setAskFinalAmount(double askFinalAmount)
	{
		this.askFinalAmount = askFinalAmount;
	}

	public double getAskFinalPercentage()
	{
		return this.askFinalPercentage;
	}

	public void setAskFinalPercentage(double askFinalPercentage)
	{
		this.askFinalPercentage = askFinalPercentage;
	}

	public double getImpliedVol()
	{
		return this.impliedVol;
	}

	public void setImpliedVol(double impliedVol)
	{
		this.impliedVol = impliedVol;
	}

	public double getPremiumAmount()
	{
		return this.premiumAmount;
	}

	public void setPremiumAmount(double premiumAmount)
	{
		this.premiumAmount = premiumAmount;
	}

	public double getPremiumPercentage()
	{
		return this.premiumPercentage;
	}

	public void setPremiumPercentage(double premiumPercentage)
	{
		this.premiumPercentage = premiumPercentage;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public boolean getIsOTC()
	{
		return this.isOTC;
	}

	public void setIsOTC(boolean isOTC)
	{
		this.isOTC = isOTC;
	}

	public String getSalesComment()
	{
		return this.salesComment;
	}

	public void setSalesComment(String salesComment)
	{
		this.salesComment = salesComment;
	}

	public String getTraderComment()
	{
		return this.traderComment;
	}

	public void setTraderComment(String traderComment)
	{
		this.traderComment = traderComment;
	}

	public String getClientComment()
	{
		return this.clientComment;
	}

	public void setClientComment(String clientComment)
	{
		this.clientComment = clientComment;
	}

	public double getHedgePrice()
	{
		return this.hedgePrice;
	}

	public void setHedgePrice(double hedgePrice)
	{
		this.hedgePrice = hedgePrice;
	}

	public double getTotalPremium()
	{
		return this.totalPremium;
	}

	public void setTotalPremium(double totalPremium)
	{
		this.totalPremium = totalPremium;
	}

	public String getPickedUpBy()
	{
		return this.pickedUpBy;
	}

	public void setPickedUpBy(String pickedUpBy)
	{
		this.pickedUpBy = pickedUpBy;
	}

	public String getHedgeType()
	{
		return this.hedgeType;
	}

	public void setHedgeType(String hedgeType)
	{
		this.hedgeType = hedgeType;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder("Request: ");
		buf.append(this.request);
		buf.append(", Identifier: ");
		buf.append(this.identifier);
		buf.append(", Book code: ");
		buf.append(this.bookCode);

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

		buf.append(", Delta notional: ");
		buf.append(this.deltaNotional);
		buf.append(", Gamma notional: ");
		buf.append(this.gammaNotional);
		buf.append(", Vega notional: ");
		buf.append(this.vegaNotional);
		buf.append(", Theta notional: ");
		buf.append(this.thetaNotional);
		buf.append(", Rho notional: ");
		buf.append(this.rhoNotional);

		buf.append(", Delta Shares: ");
		buf.append(this.deltaShares);
		buf.append(", Gamma shares: ");
		buf.append(this.gammaShares);
		buf.append(", Vega shares: ");
		buf.append(this.vegaShares);
		buf.append(", Theta shares: ");
		buf.append(this.thetaShares);
		buf.append(", Rho shares: ");
		buf.append(this.rhoShares);

		buf.append(", Bid final amount: ");
		buf.append(this.bidFinalAmount);
		buf.append(", Bid final percentage: ");
		buf.append(this.bidFinalPercentage);
		buf.append(", Bid implied vol: ");
		buf.append(this.bidImpliedVol);
		buf.append(", Bid premium amount: ");
		buf.append(this.bidPremiumAmount);
		buf.append(", Bid premium percentage: ");
		buf.append(this.bidPremiumPercentage);

		buf.append(", Ask final amount: ");
		buf.append(this.askFinalAmount);
		buf.append(", Ask final percentage: ");
		buf.append(this.askFinalPercentage);
		buf.append(", Ask implied vol: ");
		buf.append(this.askImpliedVol);
		buf.append(", Ask premium amount: ");
		buf.append(this.askPremiumAmount);
		buf.append(", Ask premium percentage: ");
		buf.append(this.askPremiumPercentage);

		buf.append(", Is OTC: ");
		buf.append(this.isOTC);
		buf.append(", ClientId: ");
		buf.append(this.clientId);
		buf.append(", Status: ");
		buf.append(this.status);

		if((this.legs != null) && (this.legs.size() > 0))
		{
			buf.append(", Legs: \n");
			for(OptionDetailImpl leg : this.legs)
			{
				if(leg != null)
					buf.append(leg.toString() + " \n");
			}
		}

		return buf.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(!(o instanceof RequestDetailImpl))
			return false;

		RequestDetailImpl param = (RequestDetailImpl) o;

		boolean isEqual = false;

		if(this.legs != null)
		{
			for(OptionDetailImpl leg : this.legs)
			{
				// TODO - check each leg and compare against param's legs
				isEqual = leg.equals(leg);
			}
		}

		return 	isEqual && (this.identifier == param.identifier) &&
				(this.request == param.request) &&
				(this.bookCode == param.bookCode) &&

				(this.delta == param.delta) &&
				(this.gamma == param.gamma) &&
				(this.theta == param.theta) &&
				(this.vega == param.vega) &&
				(this.rho == param.rho) &&

				(this.deltaNotional == param.deltaNotional) &&
				(this.gammaNotional == param.gammaNotional) &&
				(this.thetaNotional == param.thetaNotional) &&
				(this.vegaNotional == param.vegaNotional) &&
				(this.rhoNotional == param.rhoNotional) &&

				(this.bidFinalAmount == param.bidFinalAmount) &&
				(this.bidFinalPercentage == param.bidFinalPercentage) &&
				(this.bidImpliedVol == param.bidImpliedVol) &&
				(this.bidPremiumAmount == param.bidPremiumAmount) &&
				(this.bidPremiumPercentage == param.bidPremiumPercentage) &&

				(this.askFinalAmount == param.askFinalAmount) &&
				(this.askFinalPercentage == param.askFinalPercentage) &&
				(this.askImpliedVol == param.askImpliedVol) &&
				(this.askPremiumAmount == param.askPremiumAmount) &&
				(this.askPremiumPercentage == param.askPremiumPercentage) &&

				(this.impliedVol == param.impliedVol) &&
				(this.premiumAmount == param.premiumAmount) &&
				(this.premiumPercentage == param.premiumPercentage) &&

				(this.deltaShares == param.deltaShares) &&
				(this.gammaShares == param.gammaShares) &&
				(this.thetaShares == param.thetaShares) &&
				(this.vegaShares == param.vegaShares) &&
				(this.rhoShares == param.rhoShares);
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + this.identifier;
		result = (37 * result) + (this.request == null ? 0 : this.request.hashCode());
		result = (37 * result) + (this.bookCode == null ? 0 : this.bookCode.hashCode());

		/*result = 37 * result + (delta == null ? 0 : delta.hashCode());
		result = 37 * result + (gamma == null ? 0 : gamma.hashCode());
		result = 37 * result + (vega == null ? 0 : vega.hashCode());
		result = 37 * result + (theta == null ? 0 : theta.hashCode());
		result = 37 * result + (rho == null ? 0 : rho.hashCode());

		result = 37 * result + (deltaShares == null ? 0 : deltaShares.hashCode());
		result = 37 * result + (gammaShares == null ? 0 : gammaShares.hashCode());
		result = 37 * result + (vegaShares == null ? 0 : vegaShares.hashCode());
		result = 37 * result + (thetaShares == null ? 0 : thetaShares.hashCode());
		result = 37 * result + (rhoShares == null ? 0 : rhoShares.hashCode());

		result = 37 * result + (deltaNotional == null ? 0 : deltaNotional.hashCode());
		result = 37 * result + (gammaNotional == null ? 0 : gammaNotional.hashCode());
		result = 37 * result + (vegaNotional == null ? 0 : vegaNotional.hashCode());
		result = 37 * result + (thetaNotional == null ? 0 : thetaNotional.hashCode());
		result = 37 * result + (rhoNotional == null ? 0 : rhoNotional.hashCode());

		result = 37 * result + (bidImpliedVol == null ? 0 : bidImpliedVol.hashCode());
		result = 37 * result + (bidPremiumPercentage == null ? 0 : bidPremiumPercentage.hashCode());
		result = 37 * result + (bidPremiumAmount == null ? 0 : bidPremiumAmount.hashCode());
		result = 37 * result + (bidFinalAmount == null ? 0 : bidFinalAmount.hashCode());
		result = 37 * result + (bidFinalPercentage == null ? 0 : bidFinalPercentage.hashCode());

		result = 37 * result + (askImpliedVol == null ? 0 : askImpliedVol.hashCode());
		result = 37 * result + (askPremiumPercentage == null ? 0 : askPremiumPercentage.hashCode());
		result = 37 * result + (askPremiumAmount == null ? 0 : askPremiumAmount.hashCode());
		result = 37 * result + (askFinalAmount == null ? 0 : askFinalAmount.hashCode());
		result = 37 * result + (askFinalPercentage == null ? 0 : askFinalPercentage.hashCode());

		result = 37 * result + (impliedVol == null ? 0 : impliedVol.hashCode());
		result = 37 * result + (premiumPercentage == null ? 0 : premiumPercentage.hashCode());
		result = 37 * result + (premiumAmount == null ? 0 : premiumAmount.hashCode());*/

		if(this.legs != null)
		{
			for(OptionDetailImpl leg : this.legs)
			{
				if(leg != null )
					result = (37 * result) + leg.hashCode();
			}
		}
		return result;
	}
}
