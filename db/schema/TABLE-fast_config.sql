USE [FAST]
GO

/****** Object:  Table [dbo].[fast_config]    Script Date: 01/22/2015 18:14:40 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fast_config]') AND type in (N'U'))
DROP TABLE [dbo].[fast_config]
GO

USE [FAST]
GO

/****** Object:  Table [dbo].[fast_config]    Script Date: 01/22/2015 18:14:40 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[fast_config](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[sysCode] [varchar](10) NOT NULL,
	[updatedBy] [varchar](100) NOT NULL,
	[updatedDateTime] [datetime] NOT NULL,
	[fkey] [varchar](99) NOT NULL,
	[value] [varchar](500) NULL,
	[description] [varchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC,
	[sysCode] ASC,
	[fkey] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

USE [FAST]
GO

/****** Object:  Table [dbo].[fast_config_audit]    Script Date: 01/22/2015 18:14:46 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fast_config_audit]') AND type in (N'U'))
DROP TABLE [dbo].[fast_config_audit]
GO

USE [FAST]
GO

/****** Object:  Table [dbo].[fast_config_audit]    Script Date: 01/22/2015 18:14:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[fast_config_audit](
	[version] [int] NOT NULL,
	[id] [int] NOT NULL,
	[sysCode] [varchar](10) NOT NULL,
	[updatedBy] [varchar](100) NOT NULL,
	[updatedDateTime] [datetime] NOT NULL,
	[fkey] [varchar](99) NOT NULL,
	[value] [varchar](500) NULL,
	[description] [varchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[version] ASC,
	[id] ASC,
	[sysCode] ASC,
	[fkey] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

