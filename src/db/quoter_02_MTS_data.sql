--MTS initial data LOAD

--MASTER DATA
--scope categories
INSERT INTO MTS_Scope_Catg(mts_scope_catg_id, description, display_order) values (1, 'Carrier Influences', 1);
INSERT INTO MTS_Scope_Catg(mts_scope_catg_id, description, display_order) values (2, 'Manual Processes', 2);
INSERT INTO MTS_Scope_Catg(mts_scope_catg_id, description, display_order) values (3, 'Team Composition', 3);

--user roles
INSERT INTO MTS_Role_Master(mts_role_master_id, description) values (1, 'Logistics Coordinator');
INSERT INTO MTS_Role_Master(mts_role_master_id, description) values (2, 'Account Manager');
INSERT INTO MTS_Role_Master(mts_role_master_id, description) values (3, 'Operations Manager');
INSERT INTO MTS_Role_Master(mts_role_master_id, description) values (4, 'Engineer');
INSERT INTO MTS_Role_Master(mts_role_master_id, description) values (5, 'Systems Administrator');
INSERT INTO MTS_Role_Master(mts_role_master_id, description) values (6, 'Operations Analyst');

--MTS Matrix
INSERT INTO Matrix_MTS(matrix_mts_id, name, description, create_date, baseline_weekly_load_cnt) values (1, '2014 MTS', '2014 MTS Market Pricing', now(), 325);

--carrier influences
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(1, 1, 1, 1, 'Is LeanLogistics an active participant in establishing the carrier network & routing guides (e.g. procurement event)?',.02, 0, now());						
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(2, 1, 1, 2, 'Will LeanLogistics be viewed as an equal voice alongside the customer in capacity planning and performance?',.05, 0, now()); 								
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(3, 1, 1, 3, 'Will less than 10% of daily loads require white glove/ proactive track and trace?',.05, 0, now());								
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(4, 1, 1, 4, 'Will less than 10% of tenders be Multi-Stop TL?',.07, 0, now());
--manual processes							
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(5, 1, 2, 5, 'Will routine manual Order Entry be out of scope for LL?',0, 0.5, now());
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(6, 1, 2, 6, 'Will non-customer location appointment scheduling be out of scope for LL?',0, 0.3, now());
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(7, 1, 2, 7, 'Will OS&D management be out of scope for LL?',0, 0.5, now());
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(8, 1, 2, 8, 'Will managing LTL invoice matching be out of scope for LL?',0, 0.25, now());
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(9, 1, 2, 9, 'Does less than 25% of historical loads require rate change request or accessorial management?',.05, 0, now());
--team composition
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(10, 1, 3, 10, 'Will after-hours support be exception based? (as opposed to active T&T and or extensive transportation management)',0, 1, now());
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(11, 1, 3, 11, 'Will less than 25% of orders have less than 48 hrs. lead time?',0.05, 0, now());
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(12, 1, 3, 12, 'Will less than 10% of orders have order changes impacting carrier assignment?',.07, 0, now());
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(13, 1, 3, 13, 'Is this an inventory on hand environment?  (as opposed to JIT)',.02, 0, now());
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(14, 1, 3, 14, 'Base on scoping discussion will 30% or greater of orders allow for optimization?',0, .5, now());
INSERT INTO Matrix_MTS_Scope(matrix_mts_scope_id, matrix_mts_id, mts_scope_catg_id, display_order, question, baseline_scope_impact, headcount_impact, create_date) values
							(15, 1, 3, 15, 'Will CSRs be actively working to answer internal questions prior to escalating to LL?',.05, 0, now());

--team cost						
INSERT INTO Matrix_MTS_Role_Cost(matrix_mts_role_cost_id, matrix_mts_id, mts_role_master_id, cost_per, margin) values(1, 1, 1, 40000, .3);
INSERT INTO Matrix_MTS_Role_Cost(matrix_mts_role_cost_id, matrix_mts_id, mts_role_master_id, cost_per, margin) values(2, 1, 2, 100000, .2);
INSERT INTO Matrix_MTS_Role_Cost(matrix_mts_role_cost_id, matrix_mts_id, mts_role_master_id, cost_per, margin) values(3, 1, 3, 50000, .25);
INSERT INTO Matrix_MTS_Role_Cost(matrix_mts_role_cost_id, matrix_mts_id, mts_role_master_id, cost_per, margin) values(4, 1, 4, 105000, .2);
INSERT INTO Matrix_MTS_Role_Cost(matrix_mts_role_cost_id, matrix_mts_id, mts_role_master_id, cost_per, margin) values(5, 1, 5, 60000, .2);
INSERT INTO Matrix_MTS_Role_Cost(matrix_mts_role_cost_id, matrix_mts_id, mts_role_master_id, cost_per, margin) values(6, 1, 6, 75999, .2);

--team relationship calculation

--account manager to logistics coordinator
INSERT INTO Matrix_MTS_Role_Relation(matrix_mts_role_relation_id, matrix_mts_id, mts_role_master_id, start, end, role_count) values(1, 1, 2, 0, 4, .25);
INSERT INTO Matrix_MTS_Role_Relation(matrix_mts_role_relation_id, matrix_mts_id, mts_role_master_id, start, end, role_count) values(2, 1, 2, 5, 8, .5);
INSERT INTO Matrix_MTS_Role_Relation(matrix_mts_role_relation_id, matrix_mts_id, mts_role_master_id, start, end, role_count) values(3, 1, 2, 9, 11, .75);
INSERT INTO Matrix_MTS_Role_Relation(matrix_mts_role_relation_id, matrix_mts_id, mts_role_master_id, start, end, role_count) values(4, 1, 2, 12, 99, 1);

--operations manager to logistics coordinator
INSERT INTO Matrix_MTS_Role_Relation(matrix_mts_role_relation_id, matrix_mts_id, mts_role_master_id, start, end, role_count) values(5, 1, 3, 0, 8, 1);
INSERT INTO Matrix_MTS_Role_Relation(matrix_mts_role_relation_id, matrix_mts_id, mts_role_master_id, start, end, role_count) values(6, 1, 3, 9, 12, 1.5);
INSERT INTO Matrix_MTS_Role_Relation(matrix_mts_role_relation_id, matrix_mts_id, mts_role_master_id, start, end, role_count) values(7, 1, 3, 13, 99, 2);



