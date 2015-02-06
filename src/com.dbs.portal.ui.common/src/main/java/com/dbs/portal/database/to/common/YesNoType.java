package com.dbs.portal.database.to.common;


public enum YesNoType {
	Yes("Y","common.yes"), No("N","common.no");
	
	private String yesOrNo;
	private String messageKey;
	
	private YesNoType(String yesOrNo, String messageKey){
		this.yesOrNo = yesOrNo;
		this.messageKey = messageKey;
	}
	
	public String getMessageKey(){
		return this.messageKey;
	}
	
	public String getYesNo(){
		return this.yesOrNo;
	}
	
	public static YesNoType convert(String yesOrNo) {
        for (YesNoType type : values()) {
            if (type.getYesNo().equals(yesOrNo))
                return type;
        }
        return null;
    }
}
