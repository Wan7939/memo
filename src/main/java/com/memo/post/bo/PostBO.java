package com.memo.post.bo;

import java.io.IOException;
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
	
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	public List<Post> getPostList(int userId){
		return postDAO.selectPostList(userId);
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

}
