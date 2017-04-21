-- create database
DROP DATABASE IF EXISTS squotem;
CREATE DATABASE squotem;

-- use DB
USE squotem;

-- create tables
DROP TABLE IF EXISTS Risk_Analysis_Catg
;
DROP TABLE IF EXISTS Risk_Analysis
;
DROP TABLE IF EXISTS Matrix_Risk_Analysis
;
DROP TABLE IF EXISTS Matrix_Cost_Item
;
DROP TABLE IF EXISTS Matrix_Cost_Catg CASCADE 
;
DROP TABLE IF EXISTS Matrix_Cost_Adj
;
DROP TABLE IF EXISTS Review_Reason
;
DROP TABLE IF EXISTS Product_Catg
;
DROP TABLE IF EXISTS Matrix
;
DROP TABLE IF EXISTS Quote_Review
;
DROP TABLE IF EXISTS Quote_Note
;
DROP TABLE IF EXISTS Quote_Metric
;
DROP TABLE IF EXISTS Quote
;
DROP TABLE IF EXISTS Matrix_Quote_Reviewer
;
DROP TABLE IF EXISTS Cost_Model
;
DROP TABLE IF EXISTS Cost_Item
;
DROP TABLE IF EXISTS Cost_Adjustment
;
DROP TABLE IF EXISTS User
;
DROP TABLE IF EXISTS Quote_Risk_Analysis
;
DROP TABLE IF EXISTS Quote_Product
;
DROP TABLE IF EXISTS Quote_Cost_Item
;
DROP TABLE IF EXISTS Quote_Cost_Adj
;
DROP TABLE IF EXISTS Metric
;
DROP TABLE IF EXISTS Customer
;
DROP TABLE IF EXISTS Cost_Item_Catg
;

CREATE TABLE Risk_Analysis_Catg
(
	risk_analysis_catg_id INTEGER NOT NULL AUTO_INCREMENT,
	description VARCHAR(50),
	PRIMARY KEY (risk_analysis_catg_id)
) ENGINE=InnoDB
;


CREATE TABLE Risk_Analysis
(
	risk_analysis_id INTEGER NOT NULL AUTO_INCREMENT,
	risk_analysis_catg_id INTEGER NOT NULL,
	description VARCHAR(250),
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status BOOL,
	PRIMARY KEY (risk_analysis_id)
) ENGINE=InnoDB
;


CREATE TABLE Matrix_Risk_Analysis
(
	matrix_risk_analysis_id INTEGER NOT NULL AUTO_INCREMENT,
	risk_analysis_id INTEGER NOT NULL,
	matrix_id INTEGER NOT NULL,
	PRIMARY KEY (matrix_risk_analysis_id)
) ENGINE=InnoDB
;


CREATE TABLE Matrix_Cost_Item
(
	matrix_cost_item_id INTEGER NOT NULL AUTO_INCREMENT,
	matrix_id INTEGER NOT NULL,
	cost_item_id INTEGER NOT NULL,
	cost_model_id INTEGER,
	fee_catg INTEGER,
	simple_cost DECIMAL(8,2),
	PRIMARY KEY (matrix_cost_item_id)
) ENGINE=InnoDB
;


CREATE TABLE Matrix_Cost_Catg ( 
	matrix_cost_catg_id integer NOT NULL AUTO_INCREMENT,
	matrix_id INTEGER NOT NULL,
	cost_catg_id INTEGER NOT NULL,
	PRIMARY KEY (matrix_cost_catg_id)
) ENGINE=InnoDB
;

CREATE TABLE Matrix_Cost_Adj
(
	matrix_cost_adj_id INTEGER NOT NULL AUTO_INCREMENT,
	matrix_id INTEGER NOT NULL,
	cost_adjustment_id INTEGER NOT NULL,
	PRIMARY KEY (matrix_cost_adj_id)
) ENGINE=InnoDB
;


CREATE TABLE Review_Reason
(
	review_reason_id INTEGER NOT NULL AUTO_INCREMENT,
	description VARCHAR(250),
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status BOOL,
	PRIMARY KEY (review_reason_id)
) ENGINE=InnoDB
;


CREATE TABLE Product_Catg
(
	product_catg_id INTEGER NOT NULL AUTO_INCREMENT,
	description VARCHAR(250),
	PRIMARY KEY (product_catg_id)
) ENGINE=InnoDB
;


CREATE TABLE Matrix
(
	matrix_id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	description VARCHAR(250),
	is_current BOOL,
	PRIMARY KEY (matrix_id)
) ENGINE=InnoDB
;


CREATE TABLE Quote_Review
(
	quote_review_id INTEGER NOT NULL AUTO_INCREMENT,
	quote_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	reason_id INTEGER,
	comments VARCHAR(50),
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	response INTEGER,
	override_user_id INTEGER,
	PRIMARY KEY (quote_review_id)
) ENGINE=InnoDB
;


CREATE TABLE Quote_Note
(
	quote_note_id INTEGER NOT NULL AUTO_INCREMENT,
	quote_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	comment VARCHAR(250),
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (quote_note_id)
) ENGINE=InnoDB
;


CREATE TABLE Quote_Metric
(
	quote_metric_id INTEGER NOT NULL AUTO_INCREMENT,
	quote_id INTEGER NOT NULL,
	metric_id INTEGER NOT NULL,
	metric_value DECIMAL(8,2),
	PRIMARY KEY (quote_metric_id)
) ENGINE=InnoDB
;


CREATE TABLE Quote
(
	quote_id INTEGER NOT NULL AUTO_INCREMENT,
	quote_num INTEGER,
	revision_num INTEGER,
	matrix_id INTEGER NOT NULL,
	customer_id INTEGER NOT NULL,
	author_user_id INTEGER NOT NULL,
	sales_director VARCHAR(50),
	business_consultant VARCHAR(50),
	partner_reference VARCHAR(50),
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modified_date DATETIME,
	status TINYINT,
	PRIMARY KEY (quote_id)
) ENGINE=InnoDB
;

delimiter |

CREATE TRIGGER quote_num_val BEFORE INSERT ON Quote
  FOR EACH ROW
  BEGIN
    DECLARE num INT;
	IF NEW.quote_num IS NULL THEN
		SELECT MAX(quote_num) INTO num FROM Quote;
		IF num IS NULL THEN
			SET num = 0;
		END IF;
		SET NEW.quote_num = num + 1;
		SET NEW.revision_num = 1;
	ELSE
		SELECT MAX(revision_num) INTO num FROM Quote WHERE quote_num = NEW.quote_num;
		IF num IS NULL THEN
			SET num = 0;
		END IF;
		SET NEW.revision_num = num + 1;
	END IF;
  END;
|

delimiter ;

CREATE TABLE Matrix_Quote_Reviewer
(
	matrix_quote_reviewer_id INTEGER NOT NULL AUTO_INCREMENT,
	matrix_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status BOOL,
	PRIMARY KEY (matrix_quote_reviewer_id)
) ENGINE=InnoDB
;


CREATE TABLE Cost_Model
(
	cost_model_id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	description VARCHAR(250),
	xml BLOB,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status BOOL,
	PRIMARY KEY (cost_model_id)
) ENGINE=InnoDB
;


CREATE TABLE Cost_Item
(
	cost_item_id INTEGER NOT NULL AUTO_INCREMENT,
	product_catg_id INTEGER NOT NULL,
	description VARCHAR(150),
	cost_item_catg_id INTEGER,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	sort_order INTEGER,
	PRIMARY KEY (cost_item_id)
) ENGINE=InnoDB
;


CREATE TABLE Cost_Adjustment
(
	cost_adjustment_id INTEGER NOT NULL AUTO_INCREMENT,
	product_catg_id INTEGER NOT NULL,
	description VARCHAR(250),
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status BOOL,
	fee_catg INTEGER,
	PRIMARY KEY (cost_adjustment_id)
) ENGINE=InnoDB
;


CREATE TABLE User
(
	user_id INTEGER NOT NULL AUTO_INCREMENT,
	user_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	user_type INTEGER,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status BOOL,
	PRIMARY KEY (user_id)
) ENGINE=InnoDB
;


CREATE TABLE Quote_Risk_Analysis
(
	quote_risk_analysis_id INTEGER NOT NULL AUTO_INCREMENT,
	quote_id INTEGER NOT NULL,
	risk_analysis_id INTEGER NOT NULL,
	comment VARCHAR(250),
	PRIMARY KEY (quote_risk_analysis_id)
) ENGINE=InnoDB
;


CREATE TABLE Quote_Product
(
	quote_product_id INTEGER NOT NULL AUTO_INCREMENT,
	quote_id INTEGER  NOT NULL,
	product_catg_id INTEGER NOT NULL,
	PRIMARY KEY (quote_product_id)
) ENGINE=InnoDB
;


CREATE TABLE Quote_Cost_Item
(
	quote_cost_item_id INTEGER NOT NULL AUTO_INCREMENT,
	quote_id INTEGER NOT NULL,
	cost_item_id INTEGER NOT NULL,
	enabled BOOL,
	special INTEGER,
	fee_catg INTEGER,
	cost DECIMAL(8,2),
	cost_model_id INTEGER,
	PRIMARY KEY (quote_cost_item_id)
) ENGINE=InnoDB
;


CREATE TABLE Quote_Cost_Adj
(
	quote_cost_adj_id INTEGER NOT NULL AUTO_INCREMENT,
	quote_id INTEGER NOT NULL,
	cost_adjustment_id INTEGER NOT NULL,
	comment VARCHAR(250),
	fee_catg INTEGER,
	cost DECIMAL(8,2),
	PRIMARY KEY (quote_cost_adj_id)
) ENGINE=InnoDB
;


CREATE TABLE Metric_Catg
(
    metric_catg_id INTEGER NOT NULL AUTO_INCREMENT,
    description VARCHAR(100),
    PRIMARY KEY (metric_catg_id)
) ENGINE=InnoDB
;


CREATE TABLE Metric
(
	metric_id INTEGER NOT NULL AUTO_INCREMENT,
	description VARCHAR(50),
	mnemonic VARCHAR(20),
	metric_catg_id INTEGER,
	sort_order INTEGER, 
	PRIMARY KEY (metric_id)
) ENGINE=InnoDB
;


CREATE TABLE Customer
(
	customer_id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	business_sector VARCHAR(50),
	city VARCHAR(150),
	state VARCHAR(10),
	project_sponsor VARCHAR(50),
	sponsor_phone VARCHAR(50),
	PRIMARY KEY (customer_id)
) ENGINE=InnoDB
;


CREATE TABLE Cost_Item_Catg
(
	cost_item_catg_id INTEGER NOT NULL AUTO_INCREMENT,
	description VARCHAR(50) NOT NULL,
	PRIMARY KEY (cost_item_catg_id)
) ENGINE=InnoDB
;


-- create user
-- DROP USER 'squotem'@'%';
CREATE USER 'squotem'@'%' IDENTIFIED BY 'Squ0t3m';   

-- grant access
 GRANT ALL PRIVILEGES ON squotem.* TO 'squotem'@'%' WITH GRANT OPTION;
