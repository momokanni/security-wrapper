package com.security.core.social.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author sca
 * 绑定状态视图，查询已绑定和未绑定的三方都有哪些
 */
@Component("conncet/status")
public class ConnectStatusView extends AbstractView {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, 
				   HttpServletRequest req, HttpServletResponse rep)throws Exception {
		
		//源码: SecuritySocial将查询到的信息put到一个connections(map)中，然后又model.addAttribute("connectionMap",connections);	
		Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");
		
		Map<String, Boolean> result = new HashMap<>(6);
		for (String key : connections.keySet()) {
			result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
		}
		
		rep.setContentType("application/json;charset=UTF-8");
		rep.getWriter().write(objectMapper.writeValueAsString(result));
	}

}
