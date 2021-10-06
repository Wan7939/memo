package com.memo.post.bo;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	
	private Logger logger = LoggerFactory.getLogger(PostBO.class);
	
	private static final int POST_MAX_SIZE = 3;
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	public List<Post> getPostList(int userId, Integer prevId, Integer nextId){
//		 10 9 8 / 7 6 5 / 4 3 2 / 1
//		
//		 1 2 3 4 5 6 7 8 9 10
//		 이전: 7 보다 큰 3개 -> 오름차순 8 9 10 
//				 코드에서 역순으로 변경해줌 10 9 8
//		 다음: 5 보다 작은 3개 -> 내림차순 4 3 2
		String direction = null; // null || next || prev
		Integer standardId = null;
		
		if (prevId != null) {
			// 이전 클릭
			direction = "prev";
			standardId = prevId;
			
			// 7 보다 큰 3개 ->  8 9 10 reverse 시켜애 힌다. -> 10 9 8 
			List<Post> postList = postDAO.selectPostList(userId, direction, standardId, POST_MAX_SIZE);
			Collections.reverse(postList);
			return postList;
			
		} else if (nextId  != null) {
			// 다음 클릭
			direction = "next";
			standardId = nextId;
		}
		
		return postDAO.selectPostList(userId, direction, standardId, POST_MAX_SIZE);
	}
	
	// 다음 기준으로 마지막 페이지인가?
	public boolean isLastPage(int userId, int nextId) {
		// 오름차순 limit 1 제일 작은값 nextId가 같으면 마지막 페이지
		return nextId == postDAO.selectIdByAndSort(userId, "ASC");
	}
	
	// 이전 기준으로 첫번째 페이지인가?
	public boolean isFirstPage(int userId, int prevId) {
		// 내림차순 limit 1 제일 작은값 prevId가 같으면 첫번째 페이지
		return prevId == postDAO.selectIdByAndSort(userId, "DESC");
		
	}
	
	public int createPost(int userId,String userLoginId, String subject, 
			String content, MultipartFile file) {
		
		String imagePath = null;
		if (file != null) {
			try {
				imagePath = fileManagerService.saveFile(userLoginId, file);
			} catch (IOException e) {
				imagePath = null;
			}
		}
		
		return postDAO.insertPost(userId, subject, content, imagePath);
		
	}
	
	public Post getPost(int postId) {
		return postDAO.selectPost(postId);
	}
	
	public void updatePost(int postId, String loginId,
			String subject, String content, MultipartFile file) {
		
		// postId로 게시물이 있는지 확인
		Post post = getPost(postId);
		if (post == null) {
			logger.error("[글 수정] post in null. postId:{}", postId);
			return;
		}
		
		// file 이 있으면 업로드후 imagePath 를 얻어온다.
		String imagePath = null;
		if (file != null) {
			// 파일 업로드
			try {
				imagePath = fileManagerService.saveFile(loginId, file);
				
				// 기존에 있던 파일 제거 - imagePath 존재(업로드 성공) && 기존에 파일이 있으면
				if (imagePath != null && post.getImagePath() != null) {
					// 업로드가 실패할수도 있으므로 업로드 성공후 제거
					fileManagerService.deleteFile(post.getImagePath());
				}
			} catch (IOException e) {
			
			}
			
	
		}
		
		// DB update
		postDAO.updatePost(postId, subject, content, imagePath);
	}
	
	public void deletePost(int postId) {
		// postId로 post를 가져온다.
		Post post = getPost(postId);
		if (post == null) {
			logger.error("[delete post] 삭제할 게시물이 없습니다. {}", postId);
			return;
		}
		
		// 그림이 있으면 삭제한다.
		String imagePath = post.getImagePath();
		if (imagePath != null) {
			try {
				fileManagerService.deleteFile(imagePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("[delete post] 그림 삭제 실패 postId: {}, imagePath{}", postId, imagePath);
			}
		}
		
		// 포스트를 삭제한다.
		postDAO.deletePost(postId);
	}

}
