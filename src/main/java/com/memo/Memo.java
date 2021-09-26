package com.memo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class Memo {
	
	// 요청 URL: http://localhost/user/sgin_in_view
	@RequestMapping("/user/sgin_in_view")
	public String memo() {
		return "memo/login";
	}
	
	
	
	// 요청 URL: http://localhost/user/sgin_in_view
		@RequestMapping("/user/sgin_up_view")
		public String join() {
			return "memo/join";
		}
	
}
