package org.skyve.impl.web;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.skyve.util.Util;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;

public class UserAgent {
	/**
	 * Prevent instantiation
	 */
	private UserAgent() {
		// nothing to see here
	}
	
	private static Map<String, UserAgentType> typeCache = new TreeMap<>();

	public static UserAgentType getType(HttpServletRequest request) {
		// See if the UserAgentType is supplied as a cookie (from device.xhtml)
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0, l = cookies.length; i < l; i++) {
				Cookie cookie = cookies[i];
				if ("UserAgentType".equals(cookie.getName())) {
					String userAgentTypeCookieValue = Util.processStringValue(cookie.getValue());
					if (userAgentTypeCookieValue != null) {
						return UserAgentType.valueOf(userAgentTypeCookieValue);
					}
				}
			}
		}
		
		// Try the User-Agent header
		String agentString = request.getHeader("User-Agent");
		if (agentString == null) {
			agentString = "";
		}

		UserAgentType result = typeCache.get(agentString);
		if (result == null) {
			result = UserAgentType.other;
			
			eu.bitwalker.useragentutils.UserAgent agent = eu.bitwalker.useragentutils.UserAgent.parseUserAgentString(agentString);
			if (agent != null) {
				OperatingSystem os = agent.getOperatingSystem();
				if (os != null) {
					DeviceType dt = os.getDeviceType();
					if (dt != null) {
						switch (dt) {
						case COMPUTER:
							result = UserAgentType.desktop;
							break;
						case TABLET:
							result = UserAgentType.tablet;
							break;
						case MOBILE:
						case WEARABLE:
							result = UserAgentType.phone;
							break;
						default:
							result = UserAgentType.other;
						}
					}
				}
			}

			typeCache.put(agentString, result);
		}
		
		return result;
	}
}
