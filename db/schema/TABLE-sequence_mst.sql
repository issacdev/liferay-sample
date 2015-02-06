USE [FAST]
GO

/****** Object:  Table [dbo].[sequence_mst]    Script Date: 02/04/2015 14:40:00 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sequence_mst]') AND type in (N'U'))
DROP TABLE [dbo].[sequence_mst]
GO

USE [FAST]
GO

/****** Object:  Table [dbo].[sequence_mst]    Script Date: 02/04/2015 14:40:01 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[sequence_mst](
	[sequenceName] [varchar](100) NOT NULL,
	[sequenceValue] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[sequenceName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

