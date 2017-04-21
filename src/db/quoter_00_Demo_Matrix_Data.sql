-- Sample Matrix Administration Data 

-- Matrix
insert into Matrix set matrix_id = 2, name = '2014 FUM Demo', description = 'Matrix based upon FUM for Demo', is_current = True;

--reviewers
insert into Matrix_Quote_Reviewer set matrix_id = 3, user_id = 1, status = True;
insert into Matrix_Quote_Reviewer set matrix_id = 3, user_id = 3, status = True;

-- Any new Metrics Categories?
insert into Metric_Catg(metric_catg_id, description, catg_option_type) values(5, 'Reasons for Change in Transportation Systems', 1);
insert into Metric_Catg(metric_catg_id, description, catg_option_type) values(6, 'Transportation Type', 1);
insert into Metric_Catg(metric_catg_id, description, catg_option_type) values(7, 'Geography in scope for TMS project', 1);
insert into Metric_Catg(metric_catg_id, description, catg_option_type) values(8, 'Freight Spend ($M)', 3);
insert into Metric_Catg(metric_catg_id, description, catg_option_type) values(10, 'Ocean - Functionality', 1);
insert into Metric_Catg(metric_catg_id, description, catg_option_type) values(11, 'Freight Statistics', 1);
insert into Metric_Catg(metric_catg_id, description, catg_option_type) values(13, 'Current System Profile', 1);
insert into Metric_Catg(metric_catg_id, description, catg_option_type) values(14, 'What business system and version would TMS be intetrated into?', 1);

-- Any new Metrics for these categories?

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(33, 'Pain Point 1:', 'PP1', 5, 1, 2, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(34, 'Pain Point 2:', 'PP2', 5, 2, 2, 0);

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(35, 'Inbound', 'IB', 6, 3, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(36, 'Outbound', 'OB', 6, 4, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(37, 'Decicated/Private Fleet', 'PRI_FLEET', 6, 5, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(38, 'HazMat', 'HAZMAT', 6, 6, 3, 0);

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(39, 'North America (U.S., Canada, Mexico)', 'NA', 7, 7, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(40, 'Latin America (South of Mexico)', 'LA', 7, 8, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(41, 'South America', 'SA', 7, 9, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(42, 'Europe', 'EU', 7, 10, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(43, 'Asia Pacific', 'AP', 7, 11, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(44, 'Australia', 'AU', 7, 12, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(45, 'Other', 'OTHER', 7, 13, 3, 0);

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(46, 'Inbound TL', 'IB_TL', 8, 14, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(47, 'Inbound Intermodal', 'IB_IM', 8, 15, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(48, 'Inbound Flatbed', 'IB_FLAT', 8, 16, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(49, 'Inbound LTL', 'IB_LTL', 8, 17, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(50, 'Inbound Rail', 'IB_RAIL', 8, 18, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(51, 'Inbound Parcel', 'IB_PARCEL', 8, 19, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(52, 'Inbound Air', 'IB_AIR', 8, 20, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(53, 'Inbound Bulk', 'IB_BULK', 8, 21, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(54, 'Inbound Ocean', 'OC_TOTAL', 8, 22, 1, 0);

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(56, 'Outbound TL', 'OB_TL', 8, 23, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(57, 'Outbound Intermodal', 'OB_IM', 8, 24, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(58, 'Outbound Flatbed', 'OB_FLAT', 8, 25, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(59, 'Outbound LTL', 'OB_LTL', 8, 26, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(60, 'Outbound Rail', 'OB_RAIL', 8, 27, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(61, 'Outbound Parcel', 'OB_PARCEL', 8, 28, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(62, 'Outbound Air', 'OB_AIR', 8, 29, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(63, 'Outbound Bulk', 'OB_BULK', 8, 30, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(64, 'Outbound Ocean', 'OC_TOTAL', 8, 31, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(65, 'Total', 'FS_TOTAL', 8, 32, 1, 1);

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(66, 'Ocean Bookings (Visibility to Sailing Schedules)', 'OCEAN_BOOKINGS', 10, 33, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(67, 'United States - Import/Export Document Filings', 'OCEAN_DOCS', 10, 34, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(68, 'Product Classification (Tariffs Codes)', 'OCEAN_TARIFFS', 10, 35, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(69, 'Import / Export Filings (Can I see ship? / What to pay?)', 'OCEAN_IMP_EXP', 10, 36, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(70, 'RPI (Restricted Party Screening)', 'OCEAN_RPI', 10, 37, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(71, 'Document Determination', 'OCEAN_DOC_DET', 10, 38, 3, 0);

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(72, 'Inbound Total Annual Orders', 'INB_TOT_ORDERS', 11, 39, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(73, 'Inbound Total Annual Loads', 'INB_TOT_LOADS', 11, 40, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(74, 'Inbound Number of Lanes', 'INB_NUM_LANES', 11, 41, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(75, 'Inbound Number of Facilities', 'INB_NUM_FAC', 11, 42, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(76, 'Inbound Number of Carriers', 'INB_NUM_CAR', 11, 43, 1, 0);

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(77, 'Outbound Total Annual Orders', 'OB_TOT_ORDERS', 11, 44, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(78, 'Outbound Total Annual Loads', 'OB_TOT_LOADS', 11, 45, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(79, 'Outbound Number of Lanes', 'OB_NUM_LANES', 11, 46, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(80, 'Outbound Number of Facilities', 'OB_NUM_FAC', 11, 47, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(81, 'Outbound Number of Carriers', 'OB_NUM_CAR', 11, 48, 1, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(87, 'Integration Points', 'INTG_PTS', 11, 49, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(88, 'Total Carriers', 'TOT_CARRIERS', 11, 49, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(89, 'Total Suppliers', 'TOT_SUPPLIERS', 11, 49, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(90, 'Total Trucks', 'TOT_TRUCKS', 11, 49, 1, 0);

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(91, 'Inbound Dedicated Private Fleet', 'IB_DED_PRI_FLT', 11, 43, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(92, 'Outbound Dedicated Private Fleet', 'OB_DED_PRI_FLT', 11, 48, 1, 0);


insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(82, 'TMS', 'TMS', 13, 49, 2, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(83, 'Global Trade System', 'GTS', 13, 50, 2, 0);

insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(84, 'ERP', 'ERP', 14, 51, 2, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(85, 'WMS', 'WMS', 14, 52, 2, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(86, 'YMS', 'YMS', 14, 53, 2, 0);


--Metrics for this Matrix

insert into Matrix_Metric(matrix_id, metric_id) values(2, 33);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 34);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 35);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 36);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 37);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 38);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 39);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 40);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 41);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 42);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 43);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 44);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 45);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 46);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 47);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 48);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 49);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 50);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 51);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 52);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 53);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 54);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 55);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 56);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 57);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 58);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 59);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 60);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 61);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 62);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 63);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 64);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 65);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 66);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 67);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 68);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 69);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 70);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 71);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 72);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 73);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 74);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 75);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 76);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 77);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 78);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 79);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 80);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 81);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 82);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 83);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 84);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 85);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 86);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 87);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 88);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 89);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 90);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 91);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 92);

-- Matrix cost items integration & tech are different fee categories fee_catg 1=INTG 2=TECH

--INTG
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 1, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 2, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 3, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 4, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 5, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 6, 39, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 7, 33, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 8, 34, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 9, null, 1, 0, 0);
--insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 10, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 11, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 12, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 13, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 14, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 15, null, 1, 0, 0);
--insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 16, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 17, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 18, 25, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 19, 26, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 20, 28, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 21, 27, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 22, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 23, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 24, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 25, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 26, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 27, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 28, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 29, 31, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 30, 38, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 31, 30, 1, 0, 0);
--insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 32, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 33, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 34, 36, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 35, null, 1, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 36, null, 1, 0, 0);

insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 38, 20, 1, 0, 1);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 39, null, 1, 0, 1);
--TECH
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 1, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 2, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 3, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 4, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 5, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 6, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 7, 21, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 8, 35, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 9, null, 2, 0, 0);
--insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 10, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 11, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 12, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 13, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 14, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 15, null, 2, 0, 0);
--insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 16, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 17, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 18, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 19, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 20, 23, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 21, 23, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 22, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 23, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 24, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 25, 24, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 26, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 27, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 28, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 29, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 30, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 31, null, 2, 0, 0);
--insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 32, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 33, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 34, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 35, null, 2, 0, 0);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 36, null, 2, 0, 0);

insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 38, 19, 2, 0, 1);
insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, required) values (2, 39, 22, 2, 0, 1);



--TMS base technology monthly fees
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(19, 'Technology Base', 'TECHNOLOGY MONTHLY BASE (included WebSettle & LeanDex)', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="8280" />  
		<tier from="26" to="50" expr="10440" />  
		<tier from="51" to="75" expr="16500" />
		<tier from="76" to="100" expr="16700" />  
		<tier from="101" to="150" expr="21000" />  
		<tier from="151" to="250" expr="28500" />
		<tier from="251" to="500" expr="40500" />  
		<tier from="501" to="750" expr="52520" />  
		<tier from="751" to="1000" expr="66500" />
	  </value>
	</cost-model>',
	now(), 1);

--TMS base integration	
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(20, 'Integration Base', 'Integration Implementation', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="98101 + 15000" />  
		<tier from="26" to="50" expr="134889 + 15000" />  
		<tier from="51" to="75" expr="179852 + 15000" />
		<tier from="76" to="100" expr="215822 + 15000" />  
		<tier from="101" to="150" expr="269778 + 15000" />  
		<tier from="151" to="1000" expr="359704 + 15000" />
	  </value>
	</cost-model>',
	now(), 1);
	
--SIM monthly fees
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(21, 'SIM', 'SIM Technology Fee', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="1500" />  
		<tier from="26" to="50" expr="1850" />  
		<tier from="51" to="75" expr="2500" />
		<tier from="76" to="100" expr="2800" />  
		<tier from="101" to="150" expr="3200" />  
		<tier from="151" to="250" expr="5700" />
		<tier from="251" to="500" expr="7980" />  
		<tier from="501" to="750" expr="10260" />  
		<tier from="751" to="1000" expr="12540" />
	  </value>
	</cost-model>',
	now(), 1);
	
--Leansource monthly fees
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(22, 'LeanSource', 'LeanSource Technology Fee', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="950" />  
		<tier from="26" to="50" expr="1350" />  
		<tier from="51" to="75" expr="1650" />
		<tier from="76" to="100" expr="1650" />  
		<tier from="101" to="150" expr="1850" />  
		<tier from="151" to="250" expr="1850" />
		<tier from="251" to="500" expr="2050" />  
		<tier from="501" to="750" expr="2050" />  
		<tier from="751" to="1000" expr="2050" />
	  </value>
	</cost-model>',
	now(), 1);	
	
--3rd Party & Advanced Appointment Scheduling
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(23, 'Adv Appt Scheduling', '3rd Party & Advanced Appointment Scheduling Tech Fee', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="1220" />  
		<tier from="26" to="50" expr="2160" />  
		<tier from="51" to="75" expr="2732" />
		<tier from="76" to="100" expr="3280" />  
		<tier from="101" to="150" expr="3930" />  
		<tier from="151" to="250" expr="6270" />
		<tier from="251" to="500" expr="8850" />  
		<tier from="501" to="750" expr="11430" />  
		<tier from="751" to="1000" expr="14010" />
	  </value>
	</cost-model>',
	now(), 1);	
	
--WebSettle
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(24, 'WebSettle', 'WebSettle Tech Fees', 
	'<cost-model>
	  <value eval="#{TOT_CARRIERS}">
		<tier from="0" to="100" expr="0 * 93" />  
		<tier from="101" to="300" expr="20 * 93" />  
		<tier from="301" to="600" expr="40 * 93" />
		<tier from="601" to="900" expr="60 * 93" />  
		<tier from="901" to="1200" expr="80 * 93" />  
	  </value>
	  <value  operator="/" eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="1 - 0.45" />  
		<tier from="26" to="50" expr="1 - 0.55" />  
		<tier from="51" to="75" expr="1 - 0.60" />
		<tier from="76" to="100" expr="1 - 0.65" />  
		<tier from="101" to="150" expr="1 - 0.70" />  
		<tier from="151" to="250" expr="1 - 0.75" />
		<tier from="251" to="500" expr="1 - 0.75" />  
		<tier from="501" to="750" expr="1 - 0.75" />  
		<tier from="751" to="1000" expr="1 - 0.75" />   
	  </value>
	</cost-model>',
	now(), 1);
	
	
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(25, 'Claims Management', 'Claims Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="2033" />  
		<tier from="26" to="50" expr="2796" />  
		<tier from="51" to="75" expr="3728" />
		<tier from="76" to="100" expr="4473" />  
		<tier from="101" to="150" expr="5591" />  
		<tier from="151" to="1000" expr="7455" />
	  </value>
	</cost-model>',
	now(), 1);
	
	
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(26, '3rd party Track & Trace (3PL)', 'T&T Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="2711" />  
		<tier from="26" to="50" expr="3728" />  
		<tier from="51" to="75" expr="4970" />
		<tier from="76" to="100" expr="5964" />  
		<tier from="101" to="150" expr="7455" />  
		<tier from="151" to="1000" expr="9940" />
	  </value>
	</cost-model>',
	now(), 1);
	
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(27, '3rd party Appointment Scheduling', '3rd party Appointment Scheduling Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="2709" />  
		<tier from="26" to="50" expr="3725" />  
		<tier from="51" to="75" expr="4967" />
		<tier from="76" to="100" expr="5960" />  
		<tier from="101" to="150" expr="7450" />  
		<tier from="151" to="1000" expr="9934" />
	  </value>
	</cost-model>',
	now(), 1);	
	
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(28, 'Advanced Appointment Scheduling', 'Advanced Appointment Scheduling Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="4066" />  
		<tier from="26" to="50" expr="5591" />  
		<tier from="51" to="75" expr="7455" />
		<tier from="76" to="100" expr="8946" />  
		<tier from="101" to="150" expr="11183" />  
		<tier from="151" to="1000" expr="14910" />
	  </value>
	</cost-model>',
	now(), 1);
		
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(29, 'Advanced Visibility SCM', 'Advanced Visibility Supply Chain Monitor Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="5422" />  
		<tier from="26" to="50" expr="7455" />  
		<tier from="51" to="75" expr="9940" />
		<tier from="76" to="100" expr="11928" />  
		<tier from="101" to="150" expr="14910" />  
		<tier from="151" to="1000" expr="19880" />
	  </value>
	</cost-model>',
	now(), 1);	
		
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(30, 'Advanced Visibility Network Advisors', 'Advanced Visibility Network Advisors Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="4066" />  
		<tier from="26" to="50" expr="5591" />  
		<tier from="51" to="75" expr="7455" />
		<tier from="76" to="100" expr="8946" />  
		<tier from="101" to="150" expr="11183" />  
		<tier from="151" to="1000" expr="14910" />
	  </value>
	</cost-model>',
	now(), 1);

insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(31, 'Visibility SCM', 'Visibility Supply Chain Monitor Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="5422" />  
		<tier from="26" to="50" expr="7455" />  
		<tier from="51" to="75" expr="9940" />
		<tier from="76" to="100" expr="11928" />  
		<tier from="101" to="150" expr="14910" />  
		<tier from="151" to="1000" expr="19880" />
	  </value>
	</cost-model>',
	now(), 1);	
		
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(32, 'Visibility Network Advisors', 'Visibility Network Advisors Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="4066" />  
		<tier from="26" to="50" expr="5591" />  
		<tier from="51" to="75" expr="7455" />
		<tier from="76" to="100" expr="8946" />  
		<tier from="101" to="150" expr="11183" />  
		<tier from="151" to="1000" expr="14910" />
	  </value>
	</cost-model>',
	now(), 1);
		
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(33, 'SIM', 'SIM Implementation Fees', 
	'<cost-model>
	  <value eval="#{TOT_SUPPLIERS}">
		<tier from="0" to="300" expr="75 * 93" />  
		<tier from="301" to="600" expr="85 * 93" />  
		<tier from="601" to="900" expr="95 * 93" />
		<tier from="901" to="1200" expr="105 * 93" />  
		<tier from="1201" to="1500" expr="115 * 93" />  
	  </value>
	  <value  operator="/" eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="1 - 0.45" />  
		<tier from="26" to="50" expr="1 - 0.55" />  
		<tier from="51" to="75" expr="1 - 0.60" />
		<tier from="76" to="100" expr="1 - 0.65" />  
		<tier from="101" to="150" expr="1 - 0.70" />  
		<tier from="151" to="250" expr="1 - 0.75" />
		<tier from="251" to="500" expr="1 - 0.75" />  
		<tier from="501" to="750" expr="1 - 0.75" />  
		<tier from="751" to="1000" expr="1 - 0.75" />   
	  </value>
	</cost-model>',
	now(), 1);	
	
--Fleet
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(34, 'Fleet Implementation', 'Fleet Implementation', 
	'<cost-model>
	  <value eval="#{TOT_TRUCKS}">
		<tier from="0" to="50" expr="140 * 93" />  
		<tier from="51" to="100" expr="150 * 93" />  
		<tier from="101" to="150" expr="160 * 93" />
		<tier from="151" to="200" expr="170 * 93" />  
		<tier from="201" to="250" expr="180 * 93" />  
	  </value>
	  <value  operator="/" eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="1 - 0.45" />  
		<tier from="26" to="50" expr="1 - 0.55" />  
		<tier from="51" to="75" expr="1 - 0.60" />
		<tier from="76" to="100" expr="1 - 0.65" />  
		<tier from="101" to="150" expr="1 - 0.70" />  
		<tier from="151" to="250" expr="1 - 0.75" />
		<tier from="251" to="500" expr="1 - 0.75" />  
		<tier from="501" to="750" expr="1 - 0.75" />  
		<tier from="751" to="1000" expr="1 - 0.75" />   
	  </value>
	</cost-model>',
	now(), 1);	
	
	
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(35, 'Fleet Technology', 'Fleet Technology Fee', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="1500" />  
		<tier from="26" to="50" expr="2500" />  
		<tier from="51" to="75" expr="3500" />
		<tier from="76" to="100" expr="4500" />  
		<tier from="101" to="150" expr="5500" />  
		<tier from="151" to="250" expr="6500" />
		<tier from="251" to="500" expr="7500" />  
		<tier from="501" to="750" expr="8500" />  
		<tier from="751" to="1000" expr="9500" />
	  </value>
	</cost-model>',
	now(), 1);
		
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(36, 'Advanced Reporting Impl', 'Advanced Reporting Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="10166" />  
		<tier from="26" to="50" expr="13978" />  
		<tier from="51" to="75" expr="18638" />
		<tier from="76" to="100" expr="22365" />  
		<tier from="101" to="150" expr="27956" />  
		<tier from="151" to="1000" expr="37275" />
	  </value>
	</cost-model>',
	now(), 1);
	
	
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(38, 'Small Parcel Impl', 'Small Parcel Implementation Fees', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="2711" />  
		<tier from="26" to="50" expr="3313" />  
		<tier from="51" to="75" expr="3728" />
		<tier from="76" to="100" expr="4260" />  
		<tier from="101" to="150" expr="4970" />  
		<tier from="151" to="1000" expr="5964" />
	  </value>
	</cost-model>',
	now(), 1);
	
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(39, 'Multi-language & Multi-currency Impl', 'Multi-language & Multi-currency Implementation', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="6777" />  
		<tier from="26" to="50" expr="9319" />  
		<tier from="51" to="75" expr="12425" />
		<tier from="76" to="100" expr="14910" />  
		<tier from="101" to="150" expr="18638" />  
		<tier from="151" to="1000" expr="24850" />
	  </value>
	</cost-model>',
	now(), 1);
	
	

--LeanGlobal monthly fees
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(40, 'LeanGlobal', 'LeanGlobal Technology Fee', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="2000" />  
		<tier from="26" to="50" expr="3200" />  
		<tier from="51" to="75" expr="4000" />
		<tier from="76" to="100" expr="6000" />  
		<tier from="101" to="150" expr="9000" />  
		<tier from="151" to="250" expr="10400" />
		<tier from="251" to="500" expr="12000" />  
		<tier from="501" to="750" expr="13400" />  
		<tier from="751" to="1000" expr="14700" />
	  </value>
	</cost-model>',
	now(), 1);	
	
	
	
--LeanSource DIY monthly fees
insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(43, 'LeanSource', 'LeanSource DIY Technology', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="950" />  
		<tier from="26" to="50" expr="1350" />  
		<tier from="51" to="75" expr="1650" />
		<tier from="76" to="100" expr="1650" />  
		<tier from="101" to="150" expr="1850" />  
		<tier from="151" to="250" expr="1850" />
		<tier from="251" to="500" expr="2050" />  
		<tier from="501" to="750" expr="2050" />  
		<tier from="751" to="1000" expr="2250" />
	  </value>
	</cost-model>',
	now(), 1);	



insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(40, 1, 'LeanSource DIY Procurement Tool', null, 11, now(), 35);
insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(41, 1, 'LeanDex - Stand Alone Query Tool (Separate Log In)', null, 11, now(), 36);
	
	
insert into Matrix_Cost_Item(matrix_cost_item_id, matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced) values (225, 2, 40, null, 1, 0, null);
insert into Matrix_Cost_Item(matrix_cost_item_id, matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced) values (226, 2, 40, 43, 2, 0, null);	

insert into Matrix_Cost_Item(matrix_cost_item_id, matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced) values (227, 2, 41, null, 1, 0, null);
insert into Matrix_Cost_Item(matrix_cost_item_id, matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced) values (228, 2, 41, null, 2, 0, null);	
	
	
	
--budgetary

insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
	values(44, 'Budgetary Impl', 'Budgetary Implementation', 
	'<cost-model>
	  <value eval="#{FS_TOTAL}">
		<tier from="0" to="25" expr="(138762 + 15000) * 1.3" />  
		<tier from="26" to="50" expr="(180385 + 15000) * 1.3" />  
		<tier from="51" to="75" expr="(253158 + 15000) * 1.3" />
		<tier from="76" to="100" expr="(303574 + 15000) * 1.3" />  
		<tier from="101" to="150" expr="(379114 + 15000) * 1.3" />  
		<tier from="151" to="250" expr="(504822 + 15000) * 1.3" />
		<tier from="251" to="500" expr="(504822 + 15000) * 1.3" />  
		<tier from="501" to="750" expr="(504822 + 15000) * 1.3" />  
		<tier from="751" to="1000" expr="(504822 + 15000) * 1.3" />
	  </value>
	  <value eval="#{FS_TOTAL}" operator="+" if="#{IB} == ''TRUE''">
		<tier from="0" to="25" expr="19445 * 1.3" />  
		<tier from="26" to="50" expr="23767 * 1.3" />  
		<tier from="51" to="75" expr="26738 * 1.3" />
		<tier from="76" to="100" expr="30557 * 1.3" />  
		<tier from="101" to="150" expr="35650 * 1.3" />  
		<tier from="151" to="1000" expr="42780 * 1.3" />
	  </value>	  
	</cost-model>',
	now(), 1);
		
	insert into Cost_Model(cost_model_id, name, description, xml, create_date, status) 
		values(45, 'Budgetary Tech', 'Budgetary Technology', 
		'<cost-model>
		  <value eval="#{FS_TOTAL}">
			<tier from="0" to="25" expr="10450 * 1.3" />  
			<tier from="26" to="50" expr="13950 * 1.3" />  
			<tier from="51" to="75" expr="20882 * 1.3" />
			<tier from="76" to="100" expr="21630 * 1.3" />  
			<tier from="101" to="150" expr="26780 * 1.3" />  
			<tier from="151" to="250" expr="36620 * 1.3" />
			<tier from="251" to="500" expr="51400 * 1.3" />  
			<tier from="501" to="750" expr="66000 * 1.3" />  
			<tier from="751" to="1000" expr="82760 * 1.3" />
		  </value>
		  <value eval="#{FS_TOTAL}" operator="+" if="#{IB} == ''TRUE''">
			<tier from="0" to="25" expr="1500 * 1.3" />  
			<tier from="26" to="50" expr="1850 * 1.3" />  
			<tier from="51" to="75" expr="2250 * 1.3" />
			<tier from="76" to="100" expr="2700 * 1.3" />  
			<tier from="101" to="150" expr="3200 * 1.3" />  
			<tier from="151" to="250" expr="5700 * 1.3" />
			<tier from="251" to="500" expr="7980 * 1.3" />
			<tier from="501" to="750" expr="10260 * 1.3" />
			<tier from="751" to="1000" expr="12540 * 1.3" />
	  	  </value>
		</cost-model>',
	now(), 1);
	
	
insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(47, 1, 'LeanOpt3- Will be separately scoped and priced', null, 2, now(), 10);
		
insert into Matrix_Cost_Item(matrix_cost_item_id, matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced) values (240, 2, 47, null, 1, 0, null);
insert into Matrix_Cost_Item(matrix_cost_item_id, matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced) values (241, 2, 47, null, 2, 0, null);	


insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(48, 1, 'Advanced Cost Accruals/Final Cost  - Line Item Cost Allocations', null, 6, now(), 26);
		
insert into Matrix_Cost_Item(matrix_cost_item_id, matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced) values (242, 2, 48, null, 1, 0, null);
insert into Matrix_Cost_Item(matrix_cost_item_id, matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced) values (243, 2, 48, null, 2, 0, null);	

















