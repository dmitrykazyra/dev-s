select * from core_entities order by entity_id desc;
select * from core_entityContracts order by contract_id desc;
select * from rlc_LoanContracts order by contract_id desc;
select * from core_actions order by action_id desc;
select * from liasdebts order by contract_id desc;
select * from liases order by debt_id desc;
select * from liasactions order by lias_id desc;
SELECT * FROM liasdebtrests order by debt_id desc;
SELECT * FROM liasrests order by lias_id desc;
SELECT * FROM tariffplans order by tariff_plan_id desc;
SELECT * FROM tariffrates order by rate_id desc;
select * from documents order by doc_id desc;
select * from docattrs order by doc_id desc;
SELECT * FROM core_pmtschedules order by contract_id desc;
SELECT * FROM core_pmtschedulelines order by schedule_id desc;
select * from core_CounterParties order by counterParty_id desc;
select * from core_entityMarks order by action_id desc;
select * from core_EntityTypesRef;
