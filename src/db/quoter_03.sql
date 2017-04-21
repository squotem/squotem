-- DB fields for storing customer quote values

ALTER TABLE Quote ADD (
    terms_months INTEGER,
    mts_cust_quote_impl  DECIMAL(10,2),
    mts_cust_quote_monthly  DECIMAL(8,2),
    active_rev BOOL
);

-- Since up to this time all quotes are in draft mode, they are  all active revisions. 
-- Set value in database
UPDATE Quote set active_rev = true;

ALTER TABLE Quote_Product ADD (
    cust_quote_impl  DECIMAL(10,2),
    cust_quote_monthly  DECIMAL(8,2)
);

-- DB Field to store simple password for user
ALTER TABLE User ADD passw VARCHAR(128);


ALTER TABLE Matrix_Metric ADD (
    default_value VARCHAR(250),
    required BOOL
);

-- Adding secondary description for metric, used for "combo" metrics
ALTER TABLE Metric ADD (
    sec_description VARCHAR(50)
);

-- Rename required column in Matrix_Cost_Item to "forced"
-- Values may be:
-- true  (Force Enable=Yes)
-- false (Force Enable=No)
-- null (default, do not force yes or no)
ALTER TABLE Matrix_Cost_Item CHANGE required forced BOOL;

-- Update existing forced=false values to null
UPDATE Matrix_Cost_Item set forced = null where forced = false;

-- Risk analysis items for Quote: description for custom risk items and numeric risk level for all items
ALTER TABLE Quote_Risk_Analysis ADD (
    risk_level INTEGER    
)

ALTER TABLE Risk_Analysis ADD sort_order INTEGER;

ALTER TABLE Risk_Analysis_Catg ADD sort_order INTEGER;

ALTER TABLE Quote_Risk_Analysis MODIFY comment VARCHAR(2048);

ALTER TABLE Quote_Risk_Analysis ADD enabled BOOL;

ALTER TABLE Product_Catg ADD status BOOL;
-- By default, enable all product categories
UPDATE Product_Catg set status = true;
-- The following will disable Procurement 
UPDATE Product_Catg set status = false where description = 'Procurement';


-- Budgetary cost models per Matrix
ALTER TABLE Matrix ADD (
    budget_impl_cost_model_id INTEGER,
    budget_month_cost_model_id INTEGER
);

alter table Metric modify column description VARCHAR(150);

-- Add grouping for metrics at the category level
ALTER TABLE Metric_Catg ADD metric_group INTEGER;

