<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글상세/ 수정</h1>
		
		<input type="text" id="subject" class="form-control mt-2" placeholder="제목을 입력해주세요." value="${post.subject}">
		<textarea id="content" rows="10" cols="100" class="form-control mt-2" placeholder="내용을 입력해주세요. ">${post.content}</textarea>
		
		<div class="d-flex justify-content-end">
			<input type="file" id="file" class="m-2" accept=".jpg,.jpeg,.png,.gif">
		</div>
		
		<%-- 이미지가 있을 때만 이미지 영역 추가 --%>
		<c:if test="${not empty post.imagePath}">
			<div class="m-2">
				<img src="${post.imagePath}" alt="업로드 이미지" width="300">
			</div>
		</c:if>
			
		<div class="clearfix">
			<a href="#" id="deleteBtn" class="btn btn-secondary float-left mb-2" data-post-id="${post.id}">삭제</a>
			
			<div class="float-right">
				<button type="button" id="listBtn" class="btn btn-dark mb-2">목록으로</button>
				<button type="button" id="saveBtn" class="btn btn-success mb-2" data-post-id="${post.id}">수정</button>                 
			</div>
			
		</div>
	</div>
</div>


<script>
$(document).ready(function(){
	// 목록으로 버튼 클릭 => 목록으로 이동
	$('#listBtn').on('click', function(){
		location.href= "/post/post_list_view";
	});
	
	// 글 수정
	$('#saveBtn').on('click',function(){
		let subject = $('#subject').val().trim();
		if (subject == ''){
			alert("제목을 입력해주세요");
			return;
		}
		
		let content = $('#content').val();
		if (content == ''){
			alert("내용을 입력해주세요");
			return;
		}
		
		let fileName = $('#file').val();
		if (fileName != ''){
			let fileArr = fileName.split('.');
			console.log(fileArr);
			
			let ext = fileArr.pop().toLowerCase();
			if ($.inArray(ext, ['gif', 'png','jpg','jpeg']) == -1){
				alert("이미지 파일만 업로드 할 수 있습니다.");
				$('file').val(''); // 잘못된 파일을 비운다.
				return;
			}
		}
		
		let postId = $(this).data('post-id');
		console.log("postId:" + postId);
		
		// 폼태그를 자바스크립트에서 만든다.
		let formData = new FormData();
		formData.append('postId', postId);
		formData.append('subject', subject);
		formData.append('content', content);
		formData.append('file', $('#file')[0].files[0]);
		
		// ajax 통신으로 서버에 전송한다.
		$.ajax({
			type:'put'  // post,put 둘다가능
			, url: '/post/update'
			, data: formData
			, enctype: 'multipart/form-data'  // 파일업로드를 위한 필수 설정
			, processData: false  // 파일업로드를 위한 필수 설정
			, contentType: false  // 파일업로드를 위한 필수 설정
			, success: function(data){
				if (data.result == 'success'){
					alert("메모가 수정되었습니다.");
					location.reload(true); // reload() == 새로고침
				}
			},error: function(e){
				alert("메모 수정에 실패했습니다. 관리자에게 문의해주세요." + e)
			}
		});
	});
	<%-- 삭제 로직 --%>
	$('#deleteBtn').on('click', function(e){
		e.preventDefault();  // 화면 상단으로 올라가는것 방지 
			
		let postId = $('#deleteBtn').data('post-id');
		
		// ajax 통신으로 삭제 요청
		$.ajax({
			type: 'delete'
			, url:'/post/delete'
			, data: {'postId' : postId}
			, success: function(data){
				if(data.result == 'success') {
					alert("삭제됐습니다.");
					location.href= "/post/post_list_view";
				}
			}, error: function(e) {
				alert("메모를 삭제하는데 실패했습니다" + e); 
			}
		});
	});
});
</script>









