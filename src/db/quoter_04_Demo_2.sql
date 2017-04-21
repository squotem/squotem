
insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(15, 'Transportation Type', 1, 1);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(100, 'Inbound', 'IB', 15, 1, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(101, 'Outbound', 'OB', 15, 2, 3, 0);

--insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(16, 'Geographies requested by customer', 1, 2);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(102, 'North America (U.S., Canada, Mexico)', 'REQ_NA', 16, 1, 3, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(103, 'Europe', 'REQ_EU', 16, 2, 3, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(104, 'Other', 'REQ_OTHER', 16, 3, 5, 0, 'Countries');

insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(17, 'Geographies quoted to customer', 1, 3);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(105, 'North America (U.S., Canada, Mexico)', 'QUOTE_NA', 17, 1, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(106, 'Europe', 'QUO_EU', 17, 2, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(107, 'Other', 'QUO_OTHER', 17, 3, 5, 0, 'Countries');

insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(20, 'Modes and FUM Profile ($M)', 3, 4);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(126, 'TL', 'FUM_TL', 20, 1, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(127, 'Intermodal', 'FUM_IM', 20, 2, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(128, 'Flatbed', 'FUM_FB', 20, 3, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(129, 'LTL', 'FUM_LTL', 20, 4, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(130, 'Rail', 'FUM_RAIL', 20, 5, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(131, 'Parcel', 'FUM_PARCEL', 20, 6, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(132, 'Air', 'FUM_AIR', 20, 7, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(133, 'Bulk', 'FUM_BULK', 20, 8, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(134, 'Dedicated / Private Fleet', 'FUM_DED', 20, 9, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(135, 'HazMat', 'FUM_HAZMAT', 20, 10, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(136, 'Ocean', 'FUM_OCEAN', 20, 11, 4, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(137, 'Sub-Total', 'FS_TOTAL', 20, 12, 1, 1);

insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(21, 'Competition', 1, 7);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(138, 'Saas Competitor', 'COM_SAAS', 21, 1, 5, 0, 'Name:');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(139, 'Installed Competitor', 'COM_INSTAL', 21, 2, 5, 0, 'Name:');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(140, '3PL Competitor', 'COM_3PL', 21, 3, 5, 0, 'Name:');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(141, 'Other', 'COM_OTHER', 21, 4, 5, 0, 'Name:');

insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(22, 'Current TMS or Transporation Providor', 1, 8);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(142, 'Provider Name', 'EX_PROVIDER', 22, 1, 2, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(143, 'Existing TMS', 'EX_TMS', 22, 2, 5, 0, 'Name:');

insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(23, 'Value Assessment: Biz. Consulting 1st Year (16 months) Savings Est. (Value $)', 3, 9);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(145, 'Rate Savings', 'VA_BIZ_RATE', 23, 1, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(146, 'Productivity Savings', 'VA_BIZ_PROD', 23, 2, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(147, 'Settlement Savings', 'VA_BIZ_SET', 23, 3, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(148, 'Other Savings', 'VA_BIZ_OTHER', 23, 4, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(149, 'Customer Cost Increases', 'VA_BIZ_COST', 23, 5, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(150, 'Sub-Total', 'VA_BIZ_TOTAL', 23, 6, 1, 1);


insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(24, 'Value Assessment: Customer 1st Year (16 months) Savings Est. (Value $)', 3, 10);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(151, 'Rate Savings', 'VA_CU_RATE', 24, 1, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(152, 'Productivity Savings', 'VA_CU_PROD', 24, 2, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(153, 'Settlement Savings', 'VA_CU_SET', 24, 3, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(154, 'Other Savings', 'VA_CU_OTHER', 24, 4, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(155, 'Customer Cost Increases', 'VA_CU_COST', 24, 5, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(156, 'Sub-Total', 'VA_CU_TOTAL', 24, 6, 1, 1);

--insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(25, 'Value Assessment: Actual 1st Year (16 months) Savings Est. (Value $)', 3, 11);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(157, 'Rate Savings', 'VA_AC_RATE', 25, 1, 1, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(158, 'Productivity Savings', 'VA_AC_PROD', 25, 2, 1, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(159, 'Settlement Savings', 'VA_AC_SET', 25, 3, 1, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(160, 'Other Savings', 'VA_AC_OTHER', 25, 4, 1, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(161, 'Customer Cost Increases', 'VA_AC_COST', 25, 5, 1, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(162, 'Sub-Total', 'VA_AC_TOTAL', 25, 6, 1, 1);


--insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(26, 'Risk Score (Overall Quantifications)', 1, 12);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(163, 'Type', 'RISK_SCORE_TYPE', 26, 1, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(164, 'Risk Defined', 'RISK_SCORE_DEF', 26, 2, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(165, 'Remedy to Risk', 'RISK_SCORE_REMEDY', 26, 3, 2, 0);

--insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(27, 'TMS Mode or Functionality Risk', 1, 13);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(166, 'Type', 'TMS_TYPE', 27, 1, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(167, 'Risk Defined', 'TMS_DEF', 27, 2, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(168, 'Remedy to Risk', 'TMS_REMEDY', 27, 3, 2, 0);

--insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(28, 'Business Process Risk', 1, 14);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(169, 'Type', 'BP_TYPE', 28, 1, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(170, 'Risk Defined', 'BP_DEF', 28, 2, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(171, 'Remedy to Risk', 'BP_REMEDY', 28, 3, 2, 0);

--insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(29, 'Integration Complexity', 1, 15);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(172, 'Type', 'INTG_TYPE', 29, 1, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(173, 'Risk Defined', 'INTG_DEF', 29, 2, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(174, 'Remedy to Risk', 'INTG_REMEDY', 29, 3, 2, 0);

--insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(30, 'Business Risk (Data Credibility, Resources, Timeline, etc.)', 1, 16);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(175, 'Type', 'BUS_TYPE', 30, 1, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(176, 'Risk Defined', 'BUS_DEF', 30, 2, 2, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(177, 'Remedy to Risk', 'BUS_REMEDY', 30, 3, 2, 0);




insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order) values(19, 'Ocean Mode (Functionality Below)', 1, 5);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(189, 'Ocean (Visibility into Sailing Schedules / Carrier Connectivity)', 'QUO_OCEAN_VIS', 19, 12, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(190, 'Document Filings - United States Import/Export Document Filings', 'QUO_OCEAN_DOC', 19, 13, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(191, 'Trade Compliance: (Amber Road)', 'QUO_OCEAN_TRADE', 19, 14, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(192, '&nbsp;&nbsp;1)  Product Classification (Tariffs Codes)', 'QUO_TRADE_1', 19, 15, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(193, '&nbsp;&nbsp;2)  Import / Export Filings (Can I ship? / What to pay?)', 'QUO_TRADE_2', 19, 16, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(194, '&nbsp;&nbsp;3)  RPI (Restricted Party Screening) ', 'QUO_TRADE_3', 19, 17, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(195, '&nbsp;&nbsp;4)  Document Determination', 'QUO_TRADE_4', 19, 18, 3, 0);


insert into Matrix_Metric(matrix_id, metric_id) values(2, 100);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 101);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 102);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 103);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 104);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 105);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 106);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 107);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 108);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 109);

--insert into Matrix_Metric(matrix_id, metric_id) values(2, 110);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 111);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 112);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 113);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 114);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 115);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 116);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 117);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 118);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 119);

--insert into Matrix_Metric(matrix_id, metric_id) values(2, 120);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 121);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 122);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 123);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 124);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 125);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 126);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 127);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 128);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 129);

insert into Matrix_Metric(matrix_id, metric_id) values(2, 130);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 131);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 132);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 133);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 134);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 135);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 136);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 137);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 138);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 139);

insert into Matrix_Metric(matrix_id, metric_id) values(2, 140);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 141);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 142);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 143);

insert into Matrix_Metric(matrix_id, metric_id) values(2, 145);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 146);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 147);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 148);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 149);

insert into Matrix_Metric(matrix_id, metric_id) values(2, 150);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 151);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 152);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 153);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 154);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 155);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 156);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 157);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 158);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 159);

--insert into Matrix_Metric(matrix_id, metric_id) values(2, 160);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 161);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 162);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 163);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 164);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 165);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 166);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 167);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 168);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 169);

--insert into Matrix_Metric(matrix_id, metric_id) values(2, 170);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 171);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 172);
--insert into Matrix_Metric(matrix_id, metric_id) values(2, 173);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 174);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 175);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 176);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 177);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 178);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 179);

insert into Matrix_Metric(matrix_id, metric_id) values(2, 180);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 181);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 182);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 183);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 184);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 185);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 186);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 187);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 188);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 189);

insert into Matrix_Metric(matrix_id, metric_id) values(2, 190);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 191);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 192);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 193);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 194);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 195);

insert into Matrix_Metric(matrix_id, metric_id) values(2, 196);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 197);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 198);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 199);

insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order, metric_group) values(34, 'Risk', 1, 12, 1);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(210, 'Risk Score (Overall Quantifications)', 'EX_TMS', 34, 1, 5, 0, 'Describe:');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(211, 'TMS Mode or Functionality Risk', 'EX_TMS', 34, 2, 5, 0, 'Describe:');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(212, 'Business Process Risk', 'EX_TMS', 34, 3, 5, 0, 'Describe:');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(213, 'Integration Risk', 'EX_TMS', 34, 4, 5, 0, 'Describe:');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(214, 'Business Risk (Data Credibility, Resources, Timeline, etc.)', 'EX_TMS', 34, 5, 5, 0, 'Describe:');

insert into Matrix_Metric(matrix_id, metric_id) values(2, 210);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 211);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 212);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 213);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 214);


--biz scoping items

insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order, metric_group) values(35, 'Implementation Timeline (weeks)', 1, 17, 2);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(215, 'TMS', 'IMPL_TMS', 35, 1, 1, 0);


insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order, metric_group) values(36, 'What business system (ERP, WMS, or YMS) and version would TMS be integrated into?', 1, 20, 2);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(216, 'ERP', 'SYS_ERP', 36, 1, 4, 0, 'Name of System');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(217, 'WMS', 'SYS_WMS', 36, 2, 4, 0, 'Name of System');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(218, 'YMS', 'SYS_YMS', 36, 3, 4, 0, 'Name of System');
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total, sec_description) values(219, 'Other', 'SYS_OTHER', 36, 4, 4, 0, 'Name of System');

insert into Metric_Catg(metric_catg_id, description, catg_option_type, sort_order, metric_group) values(31, 'Profile', 1, 18, 2);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(220, 'Total Annual Orders', 'TOT_AN_ORDERS', 31, 1, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(221, 'Total Annual Loads', 'TOT_AN_LOADS', 31, 2, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(222, 'Number of Lanes', 'NUM_LANES', 31, 3, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(223, 'Number of MFG Facilities', 'NUM_MFG', 31, 4, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(224, 'Number of Distribution Centers', 'NUM_DC', 31, 5, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(196, 'Number of Carriers', 'TOT_CARRIERS', 31, 6, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(197, 'Number of Suppliers', 'TOT_SUPPLIERS', 31, 7, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(198, 'Number of Trucks', 'TOT_TRUCKS', 31, 8, 1, 0);
--insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(199, 'Number of Integration Points', 'INTG_PTS', 31, 9, 1, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(225, 'Is Customer Transportation Team Centralized?', 'INTG_PTS', 31, 10, 3, 0);
insert into Metric(metric_id, description, mnemonic, metric_catg_id, sort_order, metric_data_type, is_total) values(226, 'Number of Customer (Shipper) Users', 'NUM_CUST_USERS', 31, 11, 1, 0);

insert into Matrix_Metric(matrix_id, metric_id) values(2, 215);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 216);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 217);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 218);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 219);

insert into Matrix_Metric(matrix_id, metric_id) values(2, 220);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 221);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 222);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 223);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 224);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 225);
insert into Matrix_Metric(matrix_id, metric_id) values(2, 226);

--standard integration points
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(42, 1, 'Order Entry', null, 13, now(), 37);
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(43, 1, 'Customer Master Data', null, 13, now(), 38);
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(44, 1, 'Load/Order Plan', null, 13, now(), 39);
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(45, 1, 'Websettle Batch Transmit', null, 13, now(), 40);
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(46, 1, 'Payable Accrual', null, 13, now(), 41);

Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 42, null, 1, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 42, null, 2, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 43, null, 1, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 43, null, 2, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 44, null, 1, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 44, null, 2, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 45, null, 1, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 45, null, 2, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 46, null, 1, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 46, null, 2, 0, null, null);

--Additional required Integrations
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(49, 1, 'Order Request (SIM)', null, 14, now(), 42);
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(50, 1, 'Load Status', null, 14, now(), 43);
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(51, 1, 'Billable Batch Transmit', null, 14, now(), 44);
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(52, 1, 'AP Payment Status Information for Payable', null, 14, now(), 45);
Insert into Cost_Item(cost_item_id, product_catg_id, description, description_detail, cost_item_catg_id, create_date, sort_order) values(53, 1, 'Data Extract', null, 14, now(), 46);

Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 49, null, 1, 15000, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 49, null, 2, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 50, null, 1, 15000, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 50, null, 2, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 51, null, 1, 15000, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 51, null, 2, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 52, null, 1, 15000, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 52, null, 2, 0, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 53, null, 1, 15000, null, null);
Insert into Matrix_Cost_Item(matrix_id, cost_item_id, cost_model_id, fee_catg, simple_cost, forced, alternate_color) values(2, 53, null, 2, 0, null, null);







