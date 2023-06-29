DECLARE @client_fkey int = 401;
DECLARE @DATE_FROM DATETIME = '2018-01-01';
DECLARE @DATE_TO DATETIME = '2019-01-01';

select 
	dunnage_factor,
	COUNT(distinct order_num) as order_num 
from [dbo].[vw_dunnage_view] 
where client_fkey = @client_fkey
	and pack_date between @DATE_FROM AND @DATE_TO
group by dunnage_factor,dunnage_factor_id
order by dunnage_factor_id;