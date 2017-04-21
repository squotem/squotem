-- Add alternate color for cost items
ALTER TABLE Matrix_Cost_Item ADD alternate_color VARCHAR(20);

-- add modified by column, to keep track of modifications
ALTER TABLE Quote ADD modified_by INTEGER NOT NULL;

-- so far, only author can modify a quote, updating modified_by column
UPDATE Quote set modified_by = author_user_id where modified_by = 0;

-- modify sales director to  handle user id
ALTER TABLE Quote MODIFY sales_director INTEGER;

UPDATE Quote SET sales_director = author_user_id;

-- status 4, 5, 6 are not exist anymore
UPDATE Quote SET status = 1 WHERE status > 3;

-- quote visibility 1 = ALL, 0 = only if you are sales director
-- price visibility 1 = detail level, 0 = summary only
-- approval ability 1 = approve baseline, 2 = approve business plan, 3 = approve all
-- notification type 1 = notify on baseline approval, 2 = notify on business plan approval, 3 = notify on all approval
ALTER TABLE User ADD (
	quote_visibility INTEGER,
	price_visibility INTEGER,
	approval_level INTEGER,
	notification_type INTEGER
);


ALTER TABLE Quote ADD (
    budgetary_quote_impl  DECIMAL(10,2),
    budgetary_quote_monthly  DECIMAL(8,2)
);

ALTER TABLE Quote ADD (
    market_quote_impl  DECIMAL(10,2),
    market_quote_monthly  DECIMAL(8,2)
);

-- adding boolean value to quote metric table
ALTER TABLE Quote_Metric ADD boolean_value BOOL;

-- script to convert complex fields to new format, complex fields type 4, 5
-- for true values
UPDATE Quote_Metric set metric_value = REPLACE(metric_value, 'true||', ''), boolean_value = 1 WHERE INSTR(metric_value, "||") > 0;
-- just in case we miss some true value
UPDATE Quote_Metric SET boolean_value = 1 WHERE metric_id IN (SELECT metric_id FROM Metric WHERE metric_data_type in (4, 5)) AND metric_value <> 'false';
-- now false values
UPDATE Quote_Metric SET metric_value = null, boolean_value = 0 WHERE metric_id IN (SELECT metric_id FROM Metric WHERE metric_data_type in (4, 5)) AND metric_value = 'false';
-- script to convert complex fields ends here

-- add email address to user table
ALTER TABLE User ADD email VARCHAR(200);

-- add boolean to make a cost item required
ALTER TABLE Matrix_Cost_Item ADD required BOOL;

