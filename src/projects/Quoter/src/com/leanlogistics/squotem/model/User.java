package com.leanlogistics.squotem.model;

import com.leanlogistics.squotem.co.QuoteActionLevelCO;
import com.leanlogistics.squotem.co.UserTypeCO;

public class User {
    
    private Long id;    
    private String userName;
    private String firstName;
    private String lastName;
    private UserTypeCO userType;
    private Boolean active;
    private String password;
    private Long quoteVisibility;
    private Long priceVisibility;
    private QuoteActionLevelCO approvalLevel;
    private QuoteActionLevelCO notificationType;
    private String eMail;
    
    /*
    -- quote visibility 1 = ALL, 0 = only if you are sales director in search screen - previous user vs. admin
	-- price visibility 1 = detail level, 0 = summary only - in quote and business plan - previous user vs. admin
	-- approval ability 1 = approve baseline, 2 = approve business plan, 3 = approve all
	-- notification type 1 = notify on baseline approval, 2 = notify on business plan approval, 3 = notify on all approval

     */
    
    public User() {
        
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public UserTypeCO getUserType() {
        return userType;
    }
    public void setUserType(UserTypeCO userType) {
        this.userType = userType;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean isActive) {
        this.active = isActive;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

	public Long getQuoteVisibility() {
		return quoteVisibility;
	}

	public void setQuoteVisibility(Long quoteVisibility) {
		this.quoteVisibility = quoteVisibility;
	}

	public Long getPriceVisibility() {
		return priceVisibility;
	}

	public void setPriceVisibility(Long priceVisibility) {
		this.priceVisibility = priceVisibility;
	}

	public QuoteActionLevelCO getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(QuoteActionLevelCO approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public QuoteActionLevelCO getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(QuoteActionLevelCO notificationType) {
		this.notificationType = notificationType;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
}
