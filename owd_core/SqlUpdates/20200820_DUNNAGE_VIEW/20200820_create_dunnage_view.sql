USE [OWD]
GO

-- Create view

begin tran

IF EXISTS (select * from dbo.sysobjects where id = object_id(N'[dbo].[vw_dunnage_view]') and OBJECTPROPERTY(id, N'IsView') = 1)
	DROP VIEW [dbo].[vw_dunnage_view];
GO

CREATE VIEW [dbo].[vw_dunnage_view] AS
	SELECT
		dbo.owd_order.client_fkey,
		dbo.owd_order.order_num,
		dbo.owd_order.order_refnum,
		dbo.owd_order.group_name,
		dbo.package_order.pack_start as pack_date,
		dbo.package.pack_barcode as package_barcode,
		CASE WHEN dbo.package.dunnage_factor = 0 THEN 'no dunnage' 
			 WHEN dbo.package.dunnage_factor = 1 THEN 'small dunnage'
			 WHEN dbo.package.dunnage_factor = 2 THEN 'medium dunnage'
			 WHEN dbo.package.dunnage_factor = 3 THEN 'large dunnage'--,
		ELSE '' END AS dunnage_factor,
		dbo.package.dunnage_factor as dunnage_factor_id
	FROM
		dbo.owd_order
	INNER JOIN
		dbo.package_order
	ON
	dbo.owd_order.order_id = dbo.package_order.owd_order_fkey
	INNER JOIN
		dbo.package
	ON
		dbo.package_order.id = dbo.package.package_order_fkey
	WHERE
		dbo.package_order.is_void = 0;
GO

commit tran

-- drop view

begin tran

IF EXISTS (select * from dbo.sysobjects where id = object_id(N'[dbo].[vw_dunnage_view]') and OBJECTPROPERTY(id, N'IsView') = 1)
	DROP VIEW [dbo].[vw_dunnage_view];
GO

rollback tran