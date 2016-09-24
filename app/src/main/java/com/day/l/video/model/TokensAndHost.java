package com.day.l.video.model;

import java.util.List;

public class TokensAndHost {
	/**
     * Token : 7C26642A4F204DF48791F8C2B776D046
     * Host : cache301.video.v.zhuovi.cn
     * Hosts : ["cache4.video.v.zhuovi.cn","cache300.video.v.zhuovi.cn","cache502.v.video.zhuovi.net"]
     */

    private String Token;
    private String Host;
    private List<String> Hosts;

    public void setToken(String Token) {
        this.Token = Token;
    }

    public void setHost(String Host) {
        this.Host = Host;
    }

    public void setHosts(List<String> Hosts) {
        this.Hosts = Hosts;
    }

    public String getToken() {
        return Token;
    }

    public String getHost() {
        return Host;
    }

    public List<String> getHosts() {
        return Hosts;
    }

	@Override
	public String toString() {
		return "TokensAndHost [Token=" + Token + ", Host=" + Host + ", Hosts="
				+ Hosts + "]";
	}
    
	
}