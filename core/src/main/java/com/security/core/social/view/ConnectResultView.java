package com.security.core.social.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * @author sca
 * 绑定结果视图: 绑定操作触发后回执结果（绑定成功/失败）
 */
public class ConnectResultView extends AbstractView {

	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, 
					HttpServletRequest req, HttpServletResponse rep)throws Exception {
		
		rep.setContentType("text/html;charset=UTF-8");
		
		if(model.get("connections") == null) {
			rep.getWriter().write("<h3>解绑成功</h3>");
		} else {
			rep.getWriter().write("<h3>绑定成功</h3>");
		}
	}

}
