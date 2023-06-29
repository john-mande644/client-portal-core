USE [OWD]
GO

-- Add record
begin tran


IF NOT EXISTS (SELECT 1 FROM [dbo].[owd_ship_methods] WHERE method_name = 'Passport Priority DDU')
BEGIN
	INSERT INTO [dbo].[owd_ship_methods]
			   ([method_name]
			   ,[carrier_code]
			   ,[carrier_name]
			   ,[method_code]
			   ,[label_doc_name]
			   ,[label_doc_code]
			   ,[intl_doc_name]
			   ,[intl_doc_code]
			   ,[intl_doc2_name]
			   ,[intl_doc2_code]
			   ,[intl_doc3_name]
			   ,[intl_doc3_code]
			   ,[label_count]
			   ,[intl_doc_count]
			   ,[intl_doc2_count]
			   ,[intl_doc3_count]
			   ,[is_international]
			   ,[request_tracking]
			   ,[rate_only]
			   ,[old_carrier_code]
			   ,[track_type]
			   ,[pickup_time]
			   ,[cutoff_time]
			   ,[divisor])
		 VALUES
			   ('Passport Priority DDU'
			   ,'CONNECTSHIP_GLOBAL.APC'
			   ,'Passport'
			   ,'CONNECTSHIP_GLOBAL.APC.PRIDDUDC'
			   ,'Standard'
			   ,'APC_SHIPPING_LABEL.STANDARD'
			   ,''
			   ,''
			   ,''
			   ,''
			   ,''
			   ,''
			   ,1
			   ,0
			   ,0
			   ,0
			   ,0
			   ,0
			   ,0
			   ,'CONNECTSHIP_GLOBAL.APC.PRIDDUDC'
			   ,3
			   ,'1900-01-01 17:00:00.000'
			   ,'1900-01-01 13:00:00.000'
			   ,0)
END
GO

rollback tran


-- Remove record

begin tran

IF EXISTS (SELECT 1 FROM [dbo].[owd_ship_methods] WHERE method_name = 'Passport Priority DDU')
BEGIN
	DELETE FROM [dbo].[owd_ship_methods] WHERE method_name = 'Passport Priority DDU'
END
GO

rollback tran


