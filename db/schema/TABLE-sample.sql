USE [FAST_SAMPLE]
GO

/****** Object:  Table [dbo].[sample]    Script Date: 02/04/2015 14:40:00 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sample]') AND type in (N'U'))
DROP TABLE [dbo].[sample]
GO

USE [FAST_SAMPLE]
GO

/****** Object:  Table [dbo].[sample]    Script Date: 02/04/2015 14:40:01 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[sample](
	[id] [int] NOT NULL,
	[skey] [varchar](100) NOT NULL,
	[value] [varchar](100) NOT NULL,
	[updatedBy] [varchar](100) NOT NULL,
	[updatedDateTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


CREATE TABLE [dbo].[sample_audit](
	[version] [int] NOT NULL,
	[id] [int] NOT NULL,
	[skey] [varchar](100) NOT NULL,
	[value] [varchar](100) NOT NULL,
	[updatedBy] [varchar](100) NOT NULL,
	[updatedDateTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[version] ASC,
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

