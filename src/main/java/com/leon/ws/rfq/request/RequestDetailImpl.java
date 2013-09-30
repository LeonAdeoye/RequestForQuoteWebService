package com.leon.ws.rfq.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RequestDetailImpl", namespace = "com.leon.ws.rfq.request")
public class RequestDetailImpl 
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
    
	private GregorianCalendar tradeDate;
	private GregorianCalendar expiryDate;    
    
	private BigDecimal notionalMillions;
	private BigDecimal notionalFXRate;
	private String notionalCurrency;
	
    private BigDecimal delta;
    private BigDecimal gamma;
    private BigDecimal theta;
    private BigDecimal vega;
    private BigDecimal rho;

    private BigDecimal deltaNotional; 
    private BigDecimal gammaNotional; 
    private BigDecimal thetaNotional;
    private BigDecimal vegaNotional; 
    private BigDecimal rhoNotional; 

    private BigDecimal deltaShares; 
    private BigDecimal gammaShares; 
    private BigDecimal thetaShares; 
    private BigDecimal vegaShares; 
    private BigDecimal rhoShares;
    
    private String premiumSettlementCurrency;
    private GregorianCalendar premiumSettlementDate;
    private int premiumSettlementDaysOverride;    
    private BigDecimal premiumSettlementFXRate;
    
    private BigDecimal salesCreditAmount;
    private BigDecimal salesCreditPercentage;
    private BigDecimal salesCreditFXRate;
    private String salesCreditCurrency;
    
    private BigDecimal bidImpliedVol;
    private BigDecimal bidPremiumPercentage;
    private BigDecimal bidPremiumAmount;
    private BigDecimal bidFinalAmount;
    private BigDecimal bidFinalPercentage;
    
    private BigDecimal impliedVol;
    private BigDecimal premiumAmount;
    private BigDecimal premiumPercentage;
    
    private BigDecimal askImpliedVol;
    private BigDecimal askPremiumPercentage;
    private BigDecimal askPremiumAmount;
    private BigDecimal askFinalAmount;
    private BigDecimal askFinalPercentage;
    
    private String salesComment;
    private String traderComment;
    private String clientComment;
    
    private String pickedUpBy;
    private String hedgeType;
    private BigDecimal hedgePrice;
    private BigDecimal totalPremium;
	
	private ArrayList<OptionDetailImpl> legs;
	
	public RequestDetailImpl() {}
	
	public OptionDetailListImpl getLegs()
	{
		OptionDetailListImpl optionlegs =  new OptionDetailListImpl();
		optionlegs.setOptionDetailList(legs);
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
	
	public BigDecimal getNotionalMillions()
	{
		return this.notionalMillions;
	}
	
	public void setNotionalMillions(BigDecimal notionalMillions)
	{
		this.notionalMillions = notionalMillions;
	}
	
	public BigDecimal getNotionalFXRate()
	{
		return this.notionalFXRate;
	}
	
	public void setNotionalFXRate(BigDecimal notionalFXRate)
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
	
	public GregorianCalendar getTradeDate()
	{
		return this.tradeDate;
	}
	
	public void setTradeDate(GregorianCalendar tradeDate)
	{
		this.tradeDate = tradeDate;
	}
	
	public GregorianCalendar getExpiryDate()
	{
		return this.expiryDate;
	}
	
	public void setExpiryDate(GregorianCalendar expiryDate)
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
	
	public BigDecimal getDeltaShares()
	{
		return this.deltaShares;
	}
	
	public void setDeltaShares(BigDecimal deltaShares)
	{
		this.deltaShares = deltaShares;
	}
	
	public BigDecimal getGammaShares()
	{
		return this.gammaShares;
	}
	
	public void setGammaShares(BigDecimal gammaShares)
	{
		this.gammaShares = gammaShares;
	}
	
	public BigDecimal getThetaShares()
	{
		return this.thetaShares;
	}
	
	public void setThetaShares(BigDecimal thetaShares)
	{
		this.thetaShares = thetaShares;
	}
	
	public BigDecimal getVegaShares()
	{
		return this.vegaShares;
	}
	
	public void setVegaShares(BigDecimal vegaShares)
	{
		this.vegaShares = vegaShares;
	}
	
	public BigDecimal getRhoShares()
	{
		return this.rhoShares;
	}
	
	public void setRhoShares(BigDecimal rhoShares)
	{
		this.rhoShares = rhoShares;
	}
	
	public BigDecimal getDeltaNotional()
	{
		return this.deltaNotional;
	}
	
	public void setDeltaNotional(BigDecimal deltaNotional)
	{
		this.deltaNotional = deltaNotional;
	}
	
	public BigDecimal getGammaNotional()
	{
		return this.gammaNotional;
	}
	
	public void setGammaNotional(BigDecimal gammaNotional)
	{
		this.gammaNotional = gammaNotional;
	}
	
	public BigDecimal getThetaNotional()
	{
		return this.thetaNotional;
	}
	
	public void setThetaNotional(BigDecimal thetaNotional)
	{
		this.thetaNotional = thetaNotional;
	}
	
	public BigDecimal getVegaNotional()
	{
		return this.vegaNotional;
	}
	
	public void setVegaNotional(BigDecimal vegaNotional)
	{
		this.vegaNotional = vegaNotional;
	}
	
	public BigDecimal getRhoNotional()
	{
		return this.rhoNotional;
	}
	
	public void setRhoNotional(BigDecimal rhoNotional)
	{
		this.rhoNotional = rhoNotional;
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
	
	public String getPremiumSettlementCurrency()
	{
		return this.premiumSettlementCurrency;
	}
	
	public void setPremiumSettlementCurrency(String premiumSettlementCurrency)
	{
		this.premiumSettlementCurrency = premiumSettlementCurrency;
	}
	
	public GregorianCalendar getPremiumSettlementDate()
	{
		return this.premiumSettlementDate;
	}
	
	public void setPremiumSettlementDate(GregorianCalendar premiumSettlementDate)
	{
		this.premiumSettlementDate = premiumSettlementDate;
	}
	
	public BigDecimal getPremiumSettlementFXRate()
	{
		return this.premiumSettlementFXRate;
	}
	
	public void setPremiumSettlementFXRate(BigDecimal premiumSettlementFXRate)
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
	
	public BigDecimal getSalesCreditPercentage()
	{
		return this.salesCreditPercentage;
	}
	
	public void setSalesCreditPercentage(BigDecimal salesCreditPercentage)
	{
		this.salesCreditPercentage = salesCreditPercentage;
	}
	
	public BigDecimal getSalesCreditFXRate()
	{
		return this.salesCreditFXRate;
	}
	
	public void setSalesCreditFXRate(BigDecimal salesCreditFXRate)
	{
		this.salesCreditFXRate = salesCreditFXRate;
	}
	
	public BigDecimal getSalesCreditAmount()
	{
		return this.salesCreditAmount;
	}
		
	public void setSalesCreditAmount(BigDecimal salesCreditAmount)
	{
		this.salesCreditAmount = salesCreditAmount;
	}
	
	public BigDecimal getBidImpliedVol()
	{
		return this.bidImpliedVol;
	}
		
	public void setBidImpliedVol(BigDecimal bidImpliedVol)
	{
		this.bidImpliedVol = bidImpliedVol;
	}
	
	public BigDecimal getBidPremiumAmount()
	{
		return this.bidPremiumAmount;
	}
		
	public void setBidPremiumAmount(BigDecimal bidPremiumAmount)
	{
		this.bidPremiumAmount = bidPremiumAmount;
	}	
	
	public BigDecimal getBidPremiumPercentage()
	{
		return this.bidPremiumPercentage;
	}
		
	public void setBidPremiumPercentage(BigDecimal bidPremiumPercentage)
	{
		this.bidPremiumPercentage = bidPremiumPercentage;
	}
	
	public BigDecimal getBidFinalAmount()
	{
		return this.bidFinalAmount;
	}
		
	public void setBidFinalAmount(BigDecimal bidFinalAmount)
	{
		this.bidFinalAmount = bidFinalAmount;
	}
	
	public BigDecimal getBidFinalPercentage()
	{
		return this.bidFinalPercentage;
	}
		
	public void setBidFinalPercentage(BigDecimal bidFinalPercentage)
	{
		this.bidFinalPercentage = bidFinalPercentage;
	}	
	
	public BigDecimal getAskImpliedVol()
	{
		return this.askImpliedVol;
	}
		
	public void setAskImpliedVol(BigDecimal askImpliedVol)
	{
		this.askImpliedVol = askImpliedVol;
	}
	
	public BigDecimal getAskPremiumAmount()
	{
		return this.askPremiumAmount;
	}
		
	public void setAskPremiumAmount(BigDecimal askPremiumAmount)
	{
		this.askPremiumAmount = askPremiumAmount;
	}	
	
	public BigDecimal getAskPremiumPercentage()
	{
		return this.askPremiumPercentage;
	}
		
	public void setAskPremiumPercentage(BigDecimal askPremiumPercentage)
	{
		this.askPremiumPercentage = askPremiumPercentage;
	}
	
	public BigDecimal getAskFinalAmount()
	{
		return this.askFinalAmount;
	}
		
	public void setAskFinalAmount(BigDecimal askFinalAmount)
	{
		this.askFinalAmount = askFinalAmount;
	}
	
	public BigDecimal getAskFinalPercentage()
	{
		return this.askFinalPercentage;
	}
		
	public void setAskFinalPercentage(BigDecimal askFinalPercentage)
	{
		this.askFinalPercentage = askFinalPercentage;
	}
	
	public BigDecimal getImpliedVol()
	{
		return this.impliedVol;
	}
		
	public void setImpliedVol(BigDecimal impliedVol)
	{
		this.impliedVol = impliedVol;
	}
	
	public BigDecimal getPremiumAmount()
	{
		return this.premiumAmount;
	}
		
	public void setPremiumAmount(BigDecimal premiumAmount)
	{
		this.premiumAmount = premiumAmount;
	}	
	
	public BigDecimal getPremiumPercentage()
	{
		return this.premiumPercentage;
	}
		
	public void setPremiumPercentage(BigDecimal premiumPercentage)
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
	
	public BigDecimal getHedgePrice()
	{
		return this.hedgePrice;
	}
		
	public void setHedgePrice(BigDecimal hedgePrice)
	{
		this.hedgePrice = hedgePrice;
	}
	
	public BigDecimal getTotalPremium()
	{
		return this.totalPremium;
	}
		
	public void setTotalPremium(BigDecimal totalPremium)
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
		StringBuffer buf = new StringBuffer();
		buf.append("Request: ");
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
		
		if(legs != null && legs.size() > 0)
		{
			buf.append(", Legs: \n");
			for(OptionDetailImpl leg : this.legs)
			{
				if(leg != null)
					buf.append(leg.toString() + "\n");
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
		
		if(legs != null)
		{
			for(OptionDetailImpl leg : this.legs)
			{
				// TODO - check each leg and compare against param's legs
				isEqual = leg.equals(leg);
			}					
		}		
		
		return 	isEqual && this.identifier == param.identifier &&	
				this.request.equals(param.request) &&
				this.bookCode.equals(param.bookCode) &&
				
				this.delta.equals(param.delta) &&
				this.gamma.equals(param.gamma) &&
				this.theta.equals(param.theta) &&
				this.vega.equals(param.vega) &&
				this.rho.equals(param.rho) &&
				
				this.deltaNotional.equals(param.deltaNotional) &&
				this.gammaNotional.equals(param.gammaNotional) &&
				this.thetaNotional.equals(param.thetaNotional) &&
				this.vegaNotional.equals(param.vegaNotional) &&
				this.rhoNotional.equals(param.rhoNotional) &&
				
				this.bidFinalAmount.equals(param.bidFinalAmount) &&
				this.bidFinalPercentage.equals(param.bidFinalPercentage) &&
				this.bidImpliedVol.equals(param.bidImpliedVol) &&
				this.bidPremiumAmount.equals(param.bidPremiumAmount) &&
				this.bidPremiumPercentage.equals(param.bidPremiumPercentage) &&
				
				this.askFinalAmount.equals(param.askFinalAmount) &&
				this.askFinalPercentage.equals(param.askFinalPercentage) &&
				this.askImpliedVol.equals(param.askImpliedVol) &&
				this.askPremiumAmount.equals(param.askPremiumAmount) &&
				this.askPremiumPercentage.equals(param.askPremiumPercentage) &&

				this.impliedVol.equals(param.impliedVol) &&
				this.premiumAmount.equals(param.premiumAmount) &&
				this.premiumPercentage.equals(param.premiumPercentage) &&				
				
				this.deltaShares.equals(param.deltaShares) &&
				this.gammaShares.equals(param.gammaShares) &&
				this.thetaShares.equals(param.thetaShares) &&
				this.vegaShares.equals(param.vegaShares) &&
				this.rhoShares.equals(param.rhoShares);				
	}
	
	@Override
	public int hashCode()
	{
		int result = 17;
		result = 37 * result + (int) identifier;
		result = 37 * result + (request == null ? 0 : request.hashCode());
		result = 37 * result + (bookCode == null ? 0 : bookCode.hashCode());
		
		result = 37 * result + (delta == null ? 0 : delta.hashCode());
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
		result = 37 * result + (premiumAmount == null ? 0 : premiumAmount.hashCode());		
		
		if(legs != null)
		{
			for(OptionDetailImpl leg : this.legs)
			{
				if(leg != null )
					result = 37 * result + leg.hashCode();
			}					
		}
		return result;
	}	
}
