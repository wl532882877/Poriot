package com.pay.poriot.entity;

public class TokenItem {
    public final TokenInfo tokenInfo;
    public boolean added;
    public int iconId;

    public TokenItem(TokenInfo tokenInfo, boolean added, int id) {
        this.tokenInfo = tokenInfo;
        this.added = added;
        this.iconId = id;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
