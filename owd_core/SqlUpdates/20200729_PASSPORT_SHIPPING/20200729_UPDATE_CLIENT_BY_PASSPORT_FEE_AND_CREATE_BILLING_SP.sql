USE [OWD]
GO

BEGIN TRAN

	--CREATE COLUMN
	IF NOT EXISTS (SELECT 1 FROM SYSCOLUMNS C WHERE NAME = N'passport_fee' AND ID = OBJECT_ID('owd_client'))
	BEGIN
		ALTER TABLE [dbo].[owd_client] ADD passport_fee float NOT NULL
			CONSTRAINT [DF_OWD_CLIENT_PASSPORT_FEE]
			DEFAULT (0)
	END
	GO

	--CREATE FUNCTION FOR SP THAT UPDATES BILLING INFO
	IF OBJECT_ID(N'dbo.udf_owd_service_passportAddOnForTrackingId'
				 , N'FN') IS NOT NULL
	BEGIN
		DROP FUNCTION [dbo].[udf_owd_service_passportAddOnForTrackingId];
	END
	GO

	CREATE FUNCTION [dbo].[udf_owd_service_passportAddOnForTrackingId]
	(@trackingId VARCHAR(20))
	RETURNS money
	AS
	BEGIN
	declare @cost as money
		SELECT
  
		@cost = dbo.owd_client.passport_fee
	FROM
		dbo.owd_order_track
	INNER JOIN
		dbo.owd_order
	ON
		(
			dbo.owd_order_track.order_fkey = dbo.owd_order.order_id)
	INNER JOIN
		dbo.owd_client
	ON
		(
			dbo.owd_order.client_fkey = dbo.owd_client.client_id)
	WHERE
		dbo.owd_order_track.order_track_id =@trackingId
    
		return  @cost
	END
	GO

	--CREATE SP THAT UPDATES BILLING INFO FOR PASSPORT SHIPPING
	IF EXISTS (select * from dbo.sysobjects where id = object_id(N'[dbo].[processPassportBilling]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
		DROP PROCEDURE [dbo].[processPassportBilling]
	GO

	CREATE PROCEDURE [dbo].[processPassportBilling]
	@shipMethod VARCHAR(100) 
	AS
	/* Procedure body */

	update owd_order_track set bundle_id = 1, total_billed = total_billed + dbo.udf_owd_service_passportAddOnForTrackingId(order_track_id) 
	where order_track_id in 

	(
	SELECT
		dbo.owd_order_track.order_track_id
   
	FROM
		dbo.owd_order
	INNER JOIN
		dbo.owd_order_ship_info
	ON
		(
			dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey)
	INNER JOIN
		dbo.package_order
	ON
		(
			dbo.owd_order.order_id = dbo.package_order.owd_order_fkey)
	INNER JOIN
		dbo.package
	ON
		(
			dbo.package_order.id = dbo.package.package_order_fkey)
	INNER JOIN
		dbo.owd_order_track
	ON
		(
			dbo.package.order_track_fkey = dbo.owd_order_track.order_track_id)
	WHERE
		dbo.package_order.is_void = 0
	AND dbo.owd_order_track.is_void = 0
	AND dbo.owd_order_track.bundle_id IS NULL
	AND dbo.owd_order_ship_info.carr_service = @shipMethod and is_billed = 0 )
	GO

ROLLBACK TRAN



-----------------------------------------------
-- ROLLBACK CHGANGES
-----------------------------------------------

BEGIN TRAN


	IF	EXISTS (SELECT 1 FROM SYSCOLUMNS C WHERE NAME = N'passport_fee' AND ID = OBJECT_ID('owd_client'))
	BEGIN
		ALTER TABLE [dbo].[owd_client] DROP CONSTRAINT [DF_OWD_CLIENT_PASSPORT_FEE];
		ALTER TABLE [dbo].[owd_client] DROP COLUMN  passport_fee;
	END
	GO


	IF EXISTS (select * from dbo.sysobjects where id = object_id(N'[dbo].[processPassportBilling]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
		DROP PROCEDURE [dbo].[processPassportBilling]
	GO

	IF OBJECT_ID(N'dbo.udf_owd_service_passportAddOnForTrackingId'
				 , N'FN') IS NOT NULL
	BEGIN
		DROP FUNCTION [dbo].[udf_owd_service_passportAddOnForTrackingId];
	END
	GO

ROLLBACK TRAN