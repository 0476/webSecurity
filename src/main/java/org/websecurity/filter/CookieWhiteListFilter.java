package org.websecurity.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.websecurity.SecurityFilter;
import org.websecurity.config.SecurityConstant;

/**
 * cookie����������,�������cookie��������֤����
 * @author weijian.zhongwj
 *
 */
public class CookieWhiteListFilter implements SecurityFilter{

	@Override
	public void doFilterInvoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request = new CookieWhiteFilterHttpServletRequest(request);
	}
	
	public class CookieWhiteFilterHttpServletRequest extends HttpServletRequestWrapper{

		public CookieWhiteFilterHttpServletRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public Cookie[] getCookies() {
			return filter(super.getCookies());
		}

	}
	
	private Cookie[] filter(Cookie[] cookies) {
		if(cookies == null || cookies.length == 0){
			return null;
		}
		List<Cookie> cookieList = new ArrayList<Cookie>();
		for(Cookie cookie: cookies){
			if(isInWhiteList(cookie)){
				cookieList.add(cookie);
			}
		}
		return cookieList.toArray(new Cookie[cookieList.size()]);
	}
	private boolean isInWhiteList(Cookie cookie) {
		if(cookie == null || cookie.getName() == null){
			return false;
		}
		for(String name : SecurityConstant.cookieWhiteList){
			if(name.equalsIgnoreCase(cookie.getName())){
				return true;
			}
		}
		return false;
	}

}
