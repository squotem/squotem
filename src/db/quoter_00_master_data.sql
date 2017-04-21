-- Master Table Data

-- Products
insert into Product_Catg set description = 'TMS';
insert into Product_Catg set description = 'Procurement';

-- Metric categories
insert into Metric_Catg set description = 'Freight Spend By Mode ($M)';
insert into Metric_Catg set description = 'General information';

-- Metrics
insert into Metric set metric_catg_id = 1, description = 'LTL', mnemonic = 'LTL', sort_order = 1;
insert into Metric set metric_catg_id = 1, description = 'TL', mnemonic = 'TL', sort_order = 2;
insert into Metric set metric_catg_id = 1, description = 'IM', mnemonic = 'IM', sort_order = 3;
insert into Metric set metric_catg_id = 1, description = 'Parcel', mnemonic = 'PARCEL', sort_order = 4;
insert into Metric set metric_catg_id = 1, description = 'Rail', mnemonic ='RAIL', sort_order = 5;
insert into Metric set metric_catg_id = 1, description = 'Ocean', mnemonic = 'OCEAN', sort_order = 6;
insert into Metric set metric_catg_id = 2, description = 'Suppliers', mnemonic = 'SUPPLIERS', sort_order = 1;
insert into Metric set metric_catg_id = 2, description = 'Carriers', mnemonic = 'CARRIERS', sort_order = 2;
insert into Metric set metric_catg_id = 2, description = 'Trucks', mnemonic = 'TRUCKS', sort_order = 3;
insert into Metric set metric_catg_id = 2, description = 'Integration Points', mnemonic = 'INTEG_POINTS', sort_order = 4;

-- cost categories
insert into Cost_Item_Catg set description = 'Standard Planning';
insert into Cost_Item_Catg set description = 'Advanced Planning';
insert into Cost_Item_Catg set description = 'Standard Execution';
insert into Cost_Item_Catg set description = 'Advanced Execution';
insert into Cost_Item_Catg set description = 'Standard Settlement';
insert into Cost_Item_Catg set description = 'Advanced Settlement';
insert into Cost_Item_Catg set description = 'Standard Visibility';
insert into Cost_Item_Catg set description = 'Advanced Visibility';
insert into Cost_Item_Catg set description = 'Standard Reporting';
insert into Cost_Item_Catg set description = 'Advanced Reporting';
insert into Cost_Item_Catg set description = 'Advanced Procurement';
insert into Cost_Item_Catg set description = 'Standard Procurement';
insert into Cost_Item_Catg set description = 'Standard Integrations';
insert into Cost_Item_Catg set description = 'Additional Required Integrations';

-- review reason
insert into Review_Reason set description ='Incomplete Quote', status = True;
insert into Review_Reason set description ='Investigate Integration Further', status = True;
insert into Review_Reason set description ='I am having a bad day', status = True;

-- risk analysis category
insert into Risk_Analysis_Catg set description = 'Functionality Risk';
insert into Risk_Analysis_Catg set description = 'Business Process Risk';
insert into Risk_Analysis_Catg set description = 'Business Risk';

-- risk analysis items
insert into Risk_Analysis set risk_analysis_catg_id = 1, description = 'Parcel', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 1, description = 'Parcel', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 1, description = 'LeanFleet', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 1, description = 'LeanGlobal', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 1, description = 'Optimization', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 1, description = 'Any future functionality that we do not have today but are contractually obligated to provide', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 2, description = 'Order splitting at line item detail level', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 2, description = 'Customized solution to solve customer need', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 3, description = 'New division or venture', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 3, description = 'Volumes are unknown or very rough', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 3, description = 'Financial solvency', status = True;
insert into Risk_Analysis set risk_analysis_catg_id = 3, description = 'Payment of fees (Implementation upfront)', status = True;

-- cost items

insert into Cost_Item set cost_item_id = 1, product_catg_id = 1, description = 'TPT - (via Transportation Planning Tool)', description_detail = 'Functionality that supports manual execution of load building, rating, routing, as well as, review of optimization results.', cost_item_catg_id = 1, create_date = now(), sort_order = 1;
insert into Cost_Item set cost_item_id = 2, product_catg_id = 1, description = 'Auto Load Building', cost_item_catg_id = 1, create_date = now(), sort_order = 2;
insert into Cost_Item set cost_item_id = 3, product_catg_id = 1, description = 'Truckload, Intermodal, LTL Mode Planning', cost_item_catg_id = 1, create_date = now(), sort_order = 3;
insert into Cost_Item set cost_item_id = 4, product_catg_id = 1, description = 'Pooling/Multi Leg Shipment Planning', cost_item_catg_id = 1, create_date = now(), sort_order = 4;
insert into Cost_Item set cost_item_id = 5, product_catg_id = 1, description = 'Tours for Multi Load Planning', cost_item_catg_id = 2, create_date = now(), sort_order = 5;

insert into Cost_Item set cost_item_id = 6, product_catg_id = 1, description = 'Multi-Language and Multi-Currency', cost_item_catg_id = 2, create_date = now(), sort_order = 6;
insert into Cost_Item set cost_item_id = 7, product_catg_id = 1, description = 'Supplier Inbound Management - Requires Separate Integration', cost_item_catg_id = 2, create_date = now(), sort_order = 7;
insert into Cost_Item set cost_item_id = 8, product_catg_id = 1, description = 'Lean Fleet - Will be separately scoped and priced', cost_item_catg_id = 2, create_date = now(), sort_order = 8;
insert into Cost_Item set cost_item_id = 9, product_catg_id = 1, description = 'Lean Global - Will be separately scoped and priced', cost_item_catg_id = 2, create_date = now(), sort_order = 9;
insert into Cost_Item set cost_item_id = 10, product_catg_id = 1, description = 'Location Geo-Coding', cost_item_catg_id = 1, create_date = now(), sort_order = 10;
insert into Cost_Item set cost_item_id = 30, product_catg_id = 1, description = 'Small Parcel -Tracking & Visibility', cost_item_catg_id = 8, create_date = now(), sort_order = 31;

insert into Cost_Item set cost_item_id = 11, product_catg_id = 1, description = 'Routing Guides', cost_item_catg_id = 3, create_date = now(), sort_order = 11;
insert into Cost_Item set cost_item_id = 12, product_catg_id = 1, description = 'Payable Rating - Truckload, Intermodal, LTL', cost_item_catg_id = 3, create_date = now(), sort_order = 12;
insert into Cost_Item set cost_item_id = 13, product_catg_id = 1, description = 'Document Management', cost_item_catg_id = 3, create_date = now(), sort_order = 13;
insert into Cost_Item set cost_item_id = 14, product_catg_id = 1, description = 'Billable Rating', cost_item_catg_id = 3, create_date = now(), sort_order = 14;
insert into Cost_Item set cost_item_id = 15, product_catg_id = 1, description = 'Spot Market', cost_item_catg_id = 3, create_date = now(), sort_order = 15;
insert into Cost_Item set cost_item_id = 16, product_catg_id = 1, description = 'Appointment Scheduling - Basic (Non Applet)', cost_item_catg_id = 3, create_date = now(), sort_order = 16;
insert into Cost_Item set cost_item_id = 17, product_catg_id = 1, description = 'Transportation Lifecycle Tracking', cost_item_catg_id = 3, create_date = now(), sort_order = 17;

insert into Cost_Item set cost_item_id = 18, product_catg_id = 1, description = 'Claims Management', cost_item_catg_id = 4, create_date = now(), sort_order = 18;
insert into Cost_Item set cost_item_id = 19, product_catg_id = 1, description = '3rd Party Track and Trace', cost_item_catg_id = 4, create_date = now(), sort_order = 19;
insert into Cost_Item set cost_item_id = 20, product_catg_id = 1, description = 'LeanAppoint Applet - Dock scheduling', cost_item_catg_id = 4, create_date = now(), sort_order = 20;
insert into Cost_Item set cost_item_id = 21, product_catg_id = 1, description = '3rd Party Appointment Scheduling - Requires Additional Integration', cost_item_catg_id = 4, create_date = now(), sort_order = 21;

insert into Cost_Item set cost_item_id = 22, product_catg_id = 1, description = 'Settlement Accrual', cost_item_catg_id = 5, create_date = now(), sort_order = 22;
insert into Cost_Item set cost_item_id = 23, product_catg_id = 1, description = 'Standard Cost Allocations', cost_item_catg_id = 5, create_date = now(), sort_order = 23;
insert into Cost_Item set cost_item_id = 24, product_catg_id = 1, description = 'Billable Invoicing', cost_item_catg_id = 5, create_date = now(), sort_order = 24;

insert into Cost_Item set cost_item_id = 25, product_catg_id = 1, description = 'Websettle', cost_item_catg_id = 5, create_date = now(), sort_order = 1, sort_order = 25;

insert into Cost_Item set cost_item_id = 26, product_catg_id = 1, description = 'Standard Advisors', cost_item_catg_id = 7, create_date = now(), sort_order = 26;
insert into Cost_Item set cost_item_id = 27, product_catg_id = 1, description = 'Dispatch Console', cost_item_catg_id = 7, create_date = now(), sort_order = 27;
insert into Cost_Item set cost_item_id = 28, product_catg_id = 1, description = 'Transportation Mapping Interface', cost_item_catg_id = 7, create_date = now(), sort_order = 28;

insert into Cost_Item set cost_item_id = 29, product_catg_id = 1, description = 'Supply Chain Monitor', cost_item_catg_id = 8, create_date = now(), sort_order = 29;

insert into Cost_Item set cost_item_id = 31, product_catg_id = 1, description = 'Network Advisors', cost_item_catg_id = 8, create_date = now(), sort_order = 31;

insert into Cost_Item set cost_item_id = 32, product_catg_id = 1, description = 'Ad-Hoc Reporting (Web Focus)', cost_item_catg_id = 9, create_date = now(), sort_order = 32;
insert into Cost_Item set cost_item_id = 33, product_catg_id = 1, description = 'Standard Reporting (Predefined Reports)', cost_item_catg_id = 9, create_date = now(), sort_order = 33;

insert into Cost_Item set cost_item_id = 34, product_catg_id = 1, description = 'Premium Reporting Functionality', cost_item_catg_id = 10, create_date = now(), sort_order = 34;

insert into Cost_Item set cost_item_id = 35, product_catg_id = 2, description = 'LeanSource DIY Procurement Tool', cost_item_catg_id = 11, create_date = now(), sort_order = 1;
insert into Cost_Item set cost_item_id = 36, product_catg_id = 2, description = 'LeanDex', cost_item_catg_id = 11, create_date = now(), sort_order = 2;
insert into Cost_Item set cost_item_id = 37, product_catg_id = 2, description = 'RFP - Full Service Events (Priced Independently / Assumed on all)', cost_item_catg_id = 11, create_date = now(), sort_order = 3;

insert into Cost_Item set cost_item_id = 38, product_catg_id = 1, description = 'Standard Planning Base', description_detail = 'Included WebSettle for 1st 100 carries and LeanDex.', cost_item_catg_id = 1, create_date = now(), sort_order = 0;

insert into Cost_Item set cost_item_id = 39, product_catg_id = 2, description = 'Standard Procurement Base', description_detail = null, cost_item_catg_id = 12, create_date = now(), sort_order = 0;




-- cost adjustment
insert into Cost_Adjustment set product_catg_id = 1, description = 'Base Implementation Fee Adjustment', status = True, fee_catg = 1;
insert into Cost_Adjustment set product_catg_id = 1, description = 'Base Monthly Tech Adjustment', status = True, fee_catg = 2;
insert into Cost_Adjustment set product_catg_id = 1, description = 'Generous Gift', status = True, fee_catg = 1;
insert into Cost_Adjustment set product_catg_id = 1, description = 'Merry Christmas', status = True, fee_catg = 2;
insert into Cost_Adjustment set product_catg_id = 2, description = 'Procurement Implementation Adjustment', status = True, fee_catg = 1;
insert into Cost_Adjustment set product_catg_id = 2, description = 'Procurement Tech monthly adjustment', status = True, fee_catg = 2;

-- Users
insert into User set user_name = 'telzinga', first_name = 'Tod', last_name = 'Elzinga', user_type = 2, status = True;
insert into User set user_name = 'carias', first_name = 'Carlos', last_name = 'Arias', user_type = 2, status = True;
insert into User set user_name = 'jaraya', first_name = 'Jorge', last_name = 'Araya', user_type = 2, status = True;
insert into User set user_name = 'gmelis', first_name = 'Giovanni', last_name = 'Melis', user_type = 1, status = True;
insert into User set user_name = 'avalentine', first_name = 'Adrian', last_name = 'Valentine', user_type = 1, status = True;
insert into User set user_name = 'spietenpol', first_name = 'Steve', last_name = 'Pietenpol', user_type = 1, status = True;
insert into User set user_name = 'asmith', first_name = 'Adam', last_name = 'Smith', user_type = 1, status = True;

-- Sample Matrix Administration Data 
insert into Matrix set name = '2013 Test Matrix', description = 'You take the blue pill - the story ends...', is_current = True;

insert into Matrix_Cost_Adj set matrix_id = 1, cost_adjustment_id = 1;
insert into Matrix_Cost_Adj set matrix_id = 1, cost_adjustment_id = 2;
insert into Matrix_Cost_Adj set matrix_id = 1, cost_adjustment_id = 3;
insert into Matrix_Cost_Adj set matrix_id = 1, cost_adjustment_id = 4;
insert into Matrix_Cost_Adj set matrix_id = 1, cost_adjustment_id = 5;
insert into Matrix_Cost_Adj set matrix_id = 1, cost_adjustment_id = 6;

insert into Matrix_Quote_Reviewer set matrix_id = 1, user_id = 1, status = True;
insert into Matrix_Quote_Reviewer set matrix_id = 1, user_id = 3, status = True;

insert into Matrix_Risk_Analysis set risk_analysis_id = 1, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 2, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 3, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 4, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 5, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 6, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 7, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 8, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 9, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 10, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 11, matrix_id = 1;
insert into Matrix_Risk_Analysis set risk_analysis_id = 12, matrix_id = 1;

-- need to add more specifics here in terms of xml
insert into Cost_Model set name = 'flat', description = 'flat charge', status = True;

insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 1, cost_model_id = null, simple_cost = 2000, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 2, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 3, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 4, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 5, cost_model_id = null, simple_cost = 1000, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 6, cost_model_id = null, simple_cost = 4000, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 7, cost_model_id = null, simple_cost = 4560, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 8, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 9, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 10, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 11, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 12, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 13, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 14, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 15, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 16, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 17, cost_model_id = null, simple_cost = 6000, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 18, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 19, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 20, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 21, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 22, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 23, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 24, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 25, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 26, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 27, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 28, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 29, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 30, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 31, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 32, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 33, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 34, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 35, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 36, cost_model_id = null, simple_cost = 0, fee_catg = 1;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 37, cost_model_id = null, simple_cost = 0, fee_catg = 1;

insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 1, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 2, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 3, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 4, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 5, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 6, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 7, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 8, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 9, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 10, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 11, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 12, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 13, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 14, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 15, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 16, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 17, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 18, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 19, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 20, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 21, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 22, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 23, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 24, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 25, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 26, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 27, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 28, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 29, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 30, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 31, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 32, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 33, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 34, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 35, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 36, cost_model_id = null, simple_cost = 0, fee_catg = 2;
insert into Matrix_Cost_Item set matrix_id = 1, cost_item_id = 37, cost_model_id = null, simple_cost = 0, fee_catg = 2;








