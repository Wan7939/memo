package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;

@RequestMapping("/post")
@Controller
public class PostController {
	
	private Logger logger = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	private PostBO postBO;
	// 요청 URL: http://localhost/post/post_list_view
	@RequestMapping("/post_list_view")
	public String postListView(Model model, HttpServletRequest request) {
		// 글 목록들을 가져온다
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			logger.info("[post_list_view] userId is null. " + userId);
			return "redirect:/user/sign_in_view";
		}
		
		
		List<Post> postList = postBO.getPostList(1);
		// 모델에 담는다.
		model.addAttribute("postList", postList);
		model.addAttribute("viewName","post/post_list");
		return "template/layouts";
		
	}
	
}