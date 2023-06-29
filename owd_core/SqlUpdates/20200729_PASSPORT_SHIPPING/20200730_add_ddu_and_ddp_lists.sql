USE [OWD]
GO

-- Add records

begin tran

IF NOT EXISTS (SELECT 1 FROM [owd_lists] WHERE [list_value] = 'Passport Priority DDP')
BEGIN
INSERT INTO [dbo].[owd_lists]
           ([list_name]
           ,[list_value]
           ,[list_seq]
           ,[reference_num]
           ,[is_default]
           ,[td_reference]
           ,[is_inactive])
     VALUES
           ('Service'
           ,'Passport Priority DDP'
           ,254
           ,'CONNECTSHIP_GLOBAL.APC.PRIDDPDC'
           ,0
           ,'CONNECTSHIP_GLOBAL.APC.PRIDDPDC'
           ,0)
END
GO

IF NOT EXISTS (SELECT 1 FROM [owd_lists] WHERE [list_value] = 'Passport Priority DDU')
BEGIN
INSERT INTO [dbo].[owd_lists]
           ([list_name]
           ,[list_value]
           ,[list_seq]
           ,[reference_num]
           ,[is_default]
           ,[td_reference]
           ,[is_inactive])
     VALUES
           ('Service'
           ,'Passport Priority DDU'
           ,254
           ,'CONNECTSHIP_GLOBAL.APC.PRIDDUDC'
           ,0
           ,'CONNECTSHIP_GLOBAL.APC.PRIDDUDC'
           ,0)
END
GO

rollback tran


-- Remove records

BEGIN TRAN

IF EXISTS (SELECT 1 FROM [owd_lists] WHERE [list_value] = 'Passport Priority DDP')
BEGIN
	DELETE FROM [dbo].[owd_lists] WHERE [list_value] = 'Passport Priority DDP'
END

IF EXISTS (SELECT 1 FROM [owd_lists] WHERE [list_value] = 'Passport Priority DDU')
BEGIN
	DELETE FROM [dbo].[owd_lists] WHERE [list_value] = 'Passport Priority DDU'
END

ROLLBACK TRAN




