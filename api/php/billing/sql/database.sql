USE [BILLING]
GO
/****** Object:  User [billingadmin]    Script Date: 05/28/2009 12:02:27 ******/
CREATE USER [billingadmin] FOR LOGIN [billingadmin] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  Table [dbo].[billable_exceptions]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[billable_exceptions](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pricing_rule_id] [int] NOT NULL,
	[field1_value] [varchar](64) NOT NULL,
	[field1_comparator] [varchar](16) NOT NULL,
	[field2_value] [varchar](64) NULL,
	[field2_comparator] [varchar](16) NULL,
	[price] [float] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[billable_units]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[billable_units](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](64) NOT NULL,
	[value_type] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[clients]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[clients](
	[id] [int] NOT NULL,
	[name] [varchar](50) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[contracts]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[contracts](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[client_id] [int] NOT NULL,
	[name] [varchar](50) NOT NULL,
	[start_date] [datetime] NOT NULL,
	[end_date] [datetime] NULL,
	[active] [tinyint] NULL,
	[invoice_interval] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[department_minimums]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[department_minimums](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[contract_id] [int] NOT NULL,
	[department_id] [int] NOT NULL,
	[amount] [float] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[departments]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[departments](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [varchar](10) NOT NULL,
	[name] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[gl_accounts]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[gl_accounts](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [varchar](8) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[taxable] [binary](1) NOT NULL,
	[apply_to_minimum] [binary](1) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[invoices]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[invoices](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[invoice_number] [int] NOT NULL,
	[client_id] [int] NOT NULL,
	[contract_id] [int] NOT NULL,
	[start_date] [datetime] NOT NULL,
	[end_date] [datetime] NOT NULL,
	[closed] [binary](1) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[locations]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[locations](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [varchar](10) NOT NULL,
	[name] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[priced_events]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[priced_events](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[client_id] [int] NOT NULL,
	[event_date] [datetime] NOT NULL,
	[name] [varchar](50) NOT NULL,
	[units] [float] NOT NULL,
	[unit_price] [float] NOT NULL,
	[gl_account_id] [int] NOT NULL,
	[department_id] [int] NOT NULL,
	[location_id] [int] NOT NULL,
	[ad_hoc] [binary](1) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pricing_rules]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pricing_rules](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[contract_id] [int] NOT NULL,
	[billable_type_id] [int] NOT NULL,
	[unit_price] [float] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[recurring_items]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[recurring_items](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[display_name] [varchar](50) NOT NULL,
	[money_value] [float] NOT NULL,
	[contract_id] [int] NOT NULL,
	[gl_account_id] [int] NOT NULL,
	[department_id] [int] NOT NULL,
	[location_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[billable_events]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[billable_events](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[client_id] [int] NOT NULL,
	[event_date] [datetime] NOT NULL,
	[billable_type_id] [bigint] NOT NULL,
	[units] [float] NOT NULL,
	[exception_field1_value] [varchar](64) NULL,
	[exception_field2_value] [varchar](64) NULL,
	[pricing_rule_id] [int] NULL,
	[exception_id] [int] NULL,
	[unit_price] [float] NULL,
	[ad_hoc] [binary](1) NULL,
 CONSTRAINT [PK_billable_events] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[billable_types]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[billable_types](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[billable_unit_id] [int] NOT NULL,
	[location_id] [int] NOT NULL,
	[department_id] [int] NOT NULL,
	[gl_account_id] [int] NOT NULL,
	[description] [text] NULL,
	[exception_field1_name] [varchar](32) NULL,
	[exception_field1_type] [varchar](16) NULL,
	[exception_field2_name] [varchar](32) NULL,
	[exception_field2_type] [varchar](16) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[test]    Script Date: 05/28/2009 12:02:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[test](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[avalue] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Default [DF__billable___field__0AD2A005]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[billable_exceptions] ADD  DEFAULT (NULL) FOR [field2_value]
GO
/****** Object:  Default [DF__billable___field__0BC6C43E]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[billable_exceptions] ADD  DEFAULT (NULL) FOR [field2_comparator]
GO
/****** Object:  Default [DF__billable___excep__36B12243]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[billable_types] ADD  DEFAULT (NULL) FOR [exception_field1_name]
GO
/****** Object:  Default [DF__billable___excep__37A5467C]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[billable_types] ADD  DEFAULT (NULL) FOR [exception_field1_type]
GO
/****** Object:  Default [DF__billable___excep__38996AB5]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[billable_types] ADD  DEFAULT (NULL) FOR [exception_field2_name]
GO
/****** Object:  Default [DF__billable___excep__398D8EEE]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[billable_types] ADD  DEFAULT (NULL) FOR [exception_field2_type]
GO
/****** Object:  Default [DF__clients__name__164452B1]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[clients] ADD  DEFAULT (NULL) FOR [name]
GO
/****** Object:  Default [DF__contracts__end_d__1920BF5C]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[contracts] ADD  DEFAULT (NULL) FOR [end_date]
GO
/****** Object:  Default [DF__contracts__activ__1A14E395]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[contracts] ADD  DEFAULT (NULL) FOR [active]
GO
/****** Object:  Default [DF__priced_ev__ad_ho__267ABA7A]    Script Date: 05/28/2009 12:02:27 ******/
ALTER TABLE [dbo].[priced_events] ADD  DEFAULT (NULL) FOR [ad_hoc]
GO
