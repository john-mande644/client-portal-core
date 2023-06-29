--Create List
BEGIN TRAN

delete from owd_lists where list_name = 'Intl tax and duty';

insert into owd_lists (list_name,list_value,list_seq,reference_num,is_default,td_reference,is_inactive)
values ('Intl tax and duty','DDU',0,'',0,'',0),('Intl tax and duty','DDP',0,'',0,'',0);

ROLLBACK TRAN

--Rollback list
BEGIN TRAN

delete from owd_lists where list_name = 'Intl tax and duty';

ROLLBACK TRAN