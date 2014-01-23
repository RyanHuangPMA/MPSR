CREATE TRIGGER mspr_id_trig AFTER INSERT ON MPSR 
   REFERENCING NEW ROW AS newrow
   FOR EACH ROW WHEN (newrow.mpsr_id > 1)
   BEGIN ATOMIC
   	INSERT INTO MPSR_EXE_SUM(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
	INSERT INTO MPSR_AUTH(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
	INSERT INTO MPSR_SAFE(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
	INSERT INTO MPSR_BUDGET(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
	INSERT INTO MPSR_STATUS_SUM(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
	INSERT INTO MPSR_FUNC_PERF(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
	INSERT INTO MPSR_SCHEDULE(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
	INSERT INTO MPSR_LPE(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
	INSERT INTO MPSR_PROCURE(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
	INSERT INTO MPSR_COST_PERF(mpsr_id, version) VALUES (newrow.mpsr_id, 0);
   END!