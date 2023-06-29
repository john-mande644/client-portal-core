
drop view
vw_stock_by_facility_lot;
drop TABLE
owd_lot_inventory;
drop TABLE
owd_lot_packageline;
drop TABLE
owd_inventory_facility_lot;
drop table
owd_lot_receive_item;
drop table
owd_lot_owd_receive_item;
drop TABLE
owd_lot_manual_resets;

--/
IF COL_LENGTH('owd_inventory', 'lot_tracking')  IS NULL
  BEGIN
    ALTER TABLE
    OWD.dbo.owd_inventory ADD lot_tracking bigint DEFAULT 0;
  END;
/

--/
IF COL_LENGTH('owd_inventory', 'lot_pattern')  IS NULL
  BEGIN
    ALTER TABLE
    OWD.dbo.owd_inventory ADD lot_pattern VARCHAR(100) DEFAULT ''
  END;
/

-- allows inventory history inserts to designate lot values
--/
IF COL_LENGTH('owd_inventory_history', 'lot_fkey')  IS NULL
  BEGIN
    ALTER TABLE
    OWD.dbo.owd_inventory_history ADD lot_fkey bigint
  END;
/


--/
IF NOT EXISTS(SELECT 1 FROM sys.Tables WHERE  Name = N'owd_lot_inventory' AND Type = N'U')
  BEGIN
    CREATE TABLE
      owd_lot_inventory
    (
      id BIGINT NOT NULL IDENTITY,
      inventory_fkey BIGINT NOT NULL,
      lot_value varchar(100),
      date_created DATETIME DEFAULT GETDATE(),
      PRIMARY KEY (id),
      CONSTRAINT owd_lot_inventory_ix1 UNIQUE (inventory_fkey, lot_value)
    );
  END;
/

--/
IF NOT EXISTS(SELECT 1 FROM sys.Tables WHERE  Name = N'owd_lot_receive_item' AND Type = N'U')
  BEGIN
    CREATE TABLE
      owd_lot_receive_item
    (
      id BIGINT NOT NULL IDENTITY,
      qty_change INT NOT NULL,
      qty_damage INT NOT NULL,
      receive_item_fkey BIGINT,
      lot_value varchar(100)  NOT NULL,
      CONSTRAINT PK_owd_lot_receive_item PRIMARY KEY (id)
    );
  END;
/

--/
IF NOT EXISTS(SELECT 1 FROM sys.Tables WHERE  Name = N'owd_lot_owd_receive_item' AND Type = N'U')
  BEGIN
    CREATE TABLE
      owd_lot_owd_receive_item
    (
      id BIGINT NOT NULL IDENTITY,
      qty_change INT NOT NULL,
      owd_receive_item_fkey BIGINT,
      lot_fkey  bigint,
      CONSTRAINT PK_owd_lot_owd_receive_item PRIMARY KEY (id)
    );
  END;
/



-- tracks current quantities
--/
IF NOT EXISTS(SELECT 1 FROM sys.Tables WHERE  Name = N'owd_inventory_facility_lot' AND Type = N'U')
  BEGIN
    CREATE TABLE
      owd_inventory_facility_lot
    (
      id BIGINT NOT NULL IDENTITY,
      inventory_fkey BIGINT NOT NULL,
      facility_fkey BIGINT NOT NULL,
      lot_fkey BIGINT NOT NULL,
      qty BIGINT NOT NULL,
      PRIMARY KEY (id),
      CONSTRAINT owd_inventory_facility_lot_ix1 UNIQUE (inventory_fkey, facility_fkey,lot_fkey),
      CONSTRAINT owd_inventory_facility_lot_ck1 CHECK ([qty]>=(0))
    );
  END;
/


--/
IF NOT EXISTS(SELECT 1 FROM sys.Tables WHERE  Name = N'owd_lot_packageline' AND Type = N'U')
  BEGIN
    CREATE TABLE
      owd_lot_packageline
    (
      id BIGINT NOT NULL IDENTITY,
      qty INT NOT NULL,
      package_line_fkey BIGINT,
      lot_fkey  bigint,
      CONSTRAINT PK_owd_lot_packageline PRIMARY KEY (id)
    );
  END;
/

--/
CREATE TRIGGER owd_lot_packageline_insert_trigger ON [dbo].[owd_lot_packageline]
AFTER INSERT
AS

  BEGIN
    SET NOCOUNT ON


    MERGE [owd_inventory_facility_lot] AS [Target]
    USING (SELECT
             [l].[inventory_id],
             [f].[id] as facility_id,
             [src].[lot_fkey]
           FROM [INSERTED] as src join package_line pl join owd_line_item l join owd_order oo join owd_facilities f on f.loc_code=oo.facility_code on oo.order_id=l.order_fkey on owd_line_item_fkey=l.line_item_id on pl.id=package_line_fkey ) as src
    ON (Target.[inventory_fkey] = [src].[inventory_id] AND [Target].[facility_fkey] = [src].[facility_id] AND [Target].[lot_fkey] = [src].[lot_fkey]  )
    WHEN NOT MATCHED BY TARGET and src.facility_id is not null and src.lot_fkey is not null  THEN
    INSERT ([inventory_fkey], [facility_fkey], [lot_fkey],[qty])
      VALUES ([src].[inventory_id],[src].[facility_id],[src].[lot_fkey],0);

    update owd_inventory_facility_lot with (UPDLOCK,ROWLOCK) set qty=o.qty-i.qty
    from owd_inventory_facility_lot o
      join INSERTED i
      join package_line pl  join package p join package_order po on po.is_void=0 and po.id=package_order_fkey
        on  p.id=package_fkey
      join owd_line_item  l
      join owd_order oo
      join owd_facilities f on f.loc_code=oo.facility_code
        on oo.order_id=l.order_fkey
        on l.line_item_id=pl.owd_line_item_fkey
        on pl.id=i.package_line_fkey
        on  f.id=o.facility_fkey and l.inventory_id=o.inventory_fkey and i.lot_fkey=o.lot_fkey  and f.id is not null  and i.lot_fkey is not null

  END;
/

--/
CREATE TRIGGER owd_lot_packageline_delete_trigger ON [dbo].[owd_lot_packageline]
AFTER DELETE
AS

  BEGIN
    SET NOCOUNT ON


    update owd_inventory_facility_lot with (UPDLOCK,ROWLOCK) set qty=o.qty+i.qty
    from owd_inventory_facility_lot o
      join DELETED i
      join package_line pl  join package p join package_order po on  po.id=package_order_fkey
        on  p.id=package_fkey
      join owd_line_item  l
      join owd_order oo
      join owd_facilities f on f.loc_code=oo.facility_code
        on oo.order_id=l.order_fkey
        on l.line_item_id=pl.owd_line_item_fkey
        on pl.id=i.package_line_fkey
        on  f.id=o.facility_fkey and l.inventory_id=o.inventory_fkey and i.lot_fkey=o.lot_fkey  and f.id is not null  and i.lot_fkey is not null

  END;
/



CREATE VIEW
  vw_stock_by_facility_lot
(
    inventory_id,
    client_fkey,
    inventory_num,
    loc_code,
    lot_fkey,
    lot_value,
    qty,
    facility_fkey
) AS
  SELECT
    i.inventory_id,
    i.client_fkey,
    i.inventory_num,
    f.loc_code,
    ff.lot_fkey,
    isnull(lot.lot_value,'') as lot_value,
    ISNULL(ff.qty, 0) AS qty,
    f.id              AS facility_fkey
  FROM
    dbo.owd_inventory AS i
    CROSS JOIN
    dbo.owd_facilities AS f
    CROSS JOIN
    dbo.owd_lot_inventory AS lot
    left outer JOIN
    dbo.owd_inventory_facility_lot AS ff
      ON
        ff.inventory_fkey = i.inventory_id
        AND f.id = ff.facility_fkey
        and lot.id=ff.lot_fkey
  WHERE
    (
      f.is_active = 1) ;


update owd_inventory set lot_tracking=0,lot_pattern='' where lot_tracking is null;

CREATE TABLE
    owd_lot_manual_resets
    (
        id BIGINT NOT NULL IDENTITY,
        inventory_fkey BIGINT NOT NULL,
        facility_fkey BIGINT NOT NULL,
        lot_fkey BIGINT NOT NULL,
        oldQty BIGINT NOT NULL,
        newQty BIGINT NOT NULL,
        username VARCHAR(256) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
        event_date DATETIME DEFAULT GETDATE() NOT NULL,
        CONSTRAINT idx_lotmanualresets PRIMARY KEY (id),
        CONSTRAINT owd_lot_manual_resets_ck1 CHECK ([oldQty]>=(0)),
        CONSTRAINT owd_lot_manual_resets_ck2 CHECK ([newQty]>=(0))
    );


