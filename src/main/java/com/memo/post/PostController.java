package com.memo.post;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;

@RequestMapping("/post")
@Controller
public class PostController {
	
	private Logger logger = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	private PostBO postBO;
	/**
	 * 글 목록 화면
	 * @param model
	 * @param request
	 * @return
	 */
	// 요청 URL: http://localhost/post/post_list_view
	@RequestMapping("/post_list_view")
	public String postListView(
			@RequestParam(value ="prevId", required = false) Integer prevIdParam,
			@RequestParam(value ="nextId", required = false) Integer nextIdParam,
			Model model, HttpServletRequest request) {
		// 글 목록들을 가져온다
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			logger.info("[post_list_view] userId is null. " + userId);
			return "redirect:/user/sign_in_view";
		}		
		
		List<Post> postList = postBO.getPostList(userId, prevIdParam, nextIdParam);
		int prevId = 0;
		int nextId = 0;
		
		if (CollectionUtils.isEmpty(postList) == false) {
			prevId = postList.get(0).getId();
			nextId = postList.get(postList.size() - 1).getId();
			
			// 이전이나 다음이 없는경우 0 으로 세팅한다 (jsp에서 0인지 검사)
			
			// 마지막 페이지(다음기준) 인 경우 0으로 세팅
			if (postBO.isLastPage(userId, nextId)) {
				nextId = 0;
			}
			
			// 첫번째 페이지(이전 기준)인 경우 0으로 세팅
			if (postBO.isFirstPage(userId, prevId)) {
				prevId = 0;
			}
		}
			
		// 모델에 담는다.
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);
		model.addAttribute("postList", postList);
		model.addAttribute("viewName","post/post_list");
		return "template/layout";
		
	}
	/**
	 * 글쓰기 화면
	 * @param model
	 * @return
	 */
	@RequestMapping("/post_create_view")
	public String postCreatrView(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			// 세션에 id가 없으면 로그인하는 페이지로 이동(redirect)
			return "redirect:/user/sign_in_view";
		}
		model.addAttribute("viewName", "post/post_create");
		return "template/layout";
	}
	
	
	@RequestMapping("/post_detail_view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			// 세션에 id가 없으면 로그인하는 페이지로 이동(redirect)
			return "redirect:/user/sign_in_view";
		}

		Post post = postBO.getPost(postId);
		
		model.addAttribute("post",post);  //	model.addAttribute("post", postBO.getPost(postId)); 요고도 가능
		model.addAttribute("viewName","post/post_detail");
		return "template/layout";
	}
	
}
