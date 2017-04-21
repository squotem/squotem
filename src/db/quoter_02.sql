DROP TABLE IF EXISTS Country_Code
;
DROP TABLE IF EXISTS Industry_Code
;
DROP TABLE IF EXISTS Country_Code_State
;

CREATE TABLE Country_Code
(
	country_code VARCHAR(2) NOT NULL,
	country_name VARCHAR(50),
	PRIMARY KEY (country_code)
) 
;

CREATE TABLE Industry_Code
(
	industry_code_id INTEGER NOT NULL,
	description VARCHAR(50),
	PRIMARY KEY (industry_code_id)
) 
;

ALTER TABLE Customer DROP COLUMN business_sector;
ALTER TABLE Customer ADD industry_code_id INTEGER after name;
 

CREATE TABLE Country_Code_State
(
	state_short_name VARCHAR(5) NOT NULL,
	country_code VARCHAR(2) NOT NULL,
	state_name VARCHAR(50),
	PRIMARY KEY (state_short_name)
) 
;

ALTER TABLE Customer ADD country_code VARCHAR(2);

ALTER TABLE Customer CHANGE state state_short_name VARCHAR(5);
-- Change data type for xml content in Cost_Model table

alter table Cost_Model drop column xml;

alter table Cost_Model add xml TEXT after description;

ALTER TABLE Customer ADD customer_type INTEGER;

-- Effective and expire date for Quote
ALTER TABLE Quote ADD (
  effective_date DATE,
  expire_date DATE
);

-- add cost item description detail
ALTER TABLE Cost_Item ADD description_detail VARCHAR(300) after description;

-- add metri data type and is total to Metric
ALTER TABLE Metric ADD metric_data_type INTEGER, ADD is_total BOOLEAN;

-- add catalog option type to metric catalog
ALTER TABLE Metric_Catg ADD catg_option_type INTEGER;


CREATE TABLE Matrix_Metric
(
	matrix_metric_id INTEGER NOT NULL AUTO_INCREMENT,
	matrix_id INTEGER NOT NULL,
	metric_id INTEGER NOT NULL,
	PRIMARY KEY (matrix_metric_id)
);

-- modify data type of quote values
ALTER TABLE Quote_Metric MODIFY metric_value VARCHAR(250);

-- MTS RELATED TABLES AND CHANGES BELOW

DROP TABLE IF EXISTS Quote_MTS_Role_Cost;

DROP TABLE IF EXISTS Quote_MTS_Scope;

DROP TABLE IF EXISTS MTS_Scope_Catg;

DROP TABLE IF EXISTS MTS_Role_Master;

DROP TABLE IF EXISTS Matrix_MTS_Scope;

DROP TABLE IF EXISTS Matrix_MTS_Role_Relation;

DROP TABLE IF EXISTS Matrix_MTS_Role_Cost;

DROP TABLE IF EXISTS Matrix_MTS;


CREATE TABLE MTS_Role_Master
(
    mts_role_master_id INTEGER NOT NULL AUTO_INCREMENT,
    description VARCHAR(50),
    PRIMARY KEY (mts_role_master_id)
) ENGINE=InnoDB
;

CREATE TABLE MTS_Scope_Catg
(
    mts_scope_catg_id INTEGER NOT NULL AUTO_INCREMENT,
    description VARCHAR(50),
    display_order INTEGER,
    PRIMARY KEY (mts_scope_catg_id)
) ENGINE=InnoDB 
;

CREATE TABLE Matrix_MTS
(
    matrix_mts_id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    description VARCHAR(250),
    create_date DATE,
    baseline_weekly_load_cnt INTEGER,
    PRIMARY KEY (matrix_mts_id)
) ENGINE=InnoDB  
;

CREATE TABLE Matrix_MTS_Scope
(
    matrix_mts_scope_id INTEGER NOT NULL AUTO_INCREMENT,
    matrix_mts_id INTEGER NOT NULL,
    mts_scope_catg_id INTEGER NOT NULL,
    display_order INTEGER,
    question VARCHAR(250),
    baseline_scope_impact DECIMAL(8,2),
    headcount_impact DECIMAL(8,2),
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (matrix_mts_scope_id)
)  ENGINE=InnoDB
;

CREATE TABLE Matrix_MTS_Role_Cost
(
    matrix_mts_role_cost_id INTEGER NOT NULL  AUTO_INCREMENT,
    matrix_mts_id INTEGER NOT NULL,
    mts_role_master_id INTEGER NOT NULL,
    cost_per  DECIMAL(8,2),
    margin  DECIMAL(8,2),
    PRIMARY KEY (matrix_mts_role_cost_id)
)  ENGINE=InnoDB 
;

CREATE TABLE Matrix_MTS_Role_Relation
(
    matrix_mts_role_relation_id INTEGER NOT NULL AUTO_INCREMENT,
    matrix_mts_id INTEGER NOT NULL,
    mts_role_master_id INTEGER NOT NULL,
    start INTEGER,
    end INTEGER,
    role_count  DECIMAL(8,2),
    PRIMARY KEY (matrix_mts_role_relation_id)
)   ENGINE=InnoDB
;


CREATE TABLE Quote_MTS_Role_Cost
(
    quote_mts_role_cost_id INTEGER NOT NULL AUTO_INCREMENT,
    matrix_mts_role_cost_id INTEGER NOT NULL,
    quote_id INTEGER NOT NULL,
    mts_role_master_id INTEGER NOT NULL,
    role_count_calc DECIMAL(8,2),
    role_count_additional DECIMAL(8,2),
    role_cost DECIMAL(10,2),
    PRIMARY KEY (quote_mts_role_cost_id)
)   ENGINE=InnoDB 
;


CREATE TABLE Quote_MTS_Scope
(
    quote_mts_scope_id INTEGER NOT NULL  AUTO_INCREMENT,
    matrix_mts_scope_id INTEGER NOT NULL,
    quote_id INTEGER NOT NULL,
    answer INTEGER,
    baseline_scope_impact DECIMAL(8,2),
    headcount_impact DECIMAL(8,2),
    PRIMARY KEY (quote_mts_scope_id)
)   ENGINE=InnoDB 
;


ALTER TABLE Matrix ADD matrix_mts_id INTEGER;

ALTER TABLE Matrix CHANGE is_current status INTEGER;

ALTER TABLE Quote ADD(
    mts_scope_weekly_load_cnt  DECIMAL(8,2),
    mts_annual_price  DECIMAL(10,2),
    mts_annual_load_cnt  DECIMAL(8,2),
    has_mts BOOL
);

ALTER TABLE Matrix_Cost_Item ADD required BOOL;

ALTER TABLE Quote_Cost_Item ADD required BOOL;

ALTER TABLE Metric_Catg ADD sort_order INTEGER;

