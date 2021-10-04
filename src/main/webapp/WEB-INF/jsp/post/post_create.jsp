<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글쓰기</h1>
		
		<input type="text" id="subject" class="form-control mt-2" placeholder="제목을 입력해주세요.">
		<textarea id="content" rows="10" cols="100" class="form-control mt-2" placeholder="내용을 입력해주세요."></textarea>
		
		<div class="d-flex justify-content-end">
			<input type="file" id="file" class="m-2" accept=".jpg,.jpeg,.png,.gif">
		</div>
			
		<div class="clearfix">
			<a href="/post/post_list_view" class="btn btn-dark float-left mb-2">목록</a>
			
			<div class="float-right">
				<button type="button" id="clearBtn" class="btn bnt-secondary mb-2">모두 지우기</button>
				<button type="button" id="saveBtn" class="btn bnt-success mb-2">저장</button>
			</div>
			
		</div>
	</div>
</div>

<script>
$(document).ready(function(){
	// 모두지우기 버튼 클릭
	$('#clearBtn').on('click', function(){
		// 제목 input, 내용 textarea 영역을 빈칸으로 만든다.
		if (confirm("내용을 지우겠습니까?")){
			$('#subject').val('');  // .val('') => 비우기
			$('#content').val('');
		}
		
	});
	
	// 글 내용 저장버튼 클릭
	$('#saveBtn').on('click',function(){
		let subject = $('#subject').val().trim();
		console.log(subject);
		if (subject == ''){
			alert('제목을 입력해주세요.');
			return;
		}
		
		let content = $('#content').val();
		console.log(content);
		if (content == ''){
			alert('내용을 입력해주세요.');
			return;
		}
		
		
		// 파일이 업로드 된 경우에 확장자 검사
		let file = $('#file').val();
		console.log("file:" + file);
		
		if (file != ''){
			//console.log(file.split('.')); // 파일명을 . 기준으로 자른다.(배열에 저장)
			let ext = file.split('.').pop().toLowerCase()  //  split를 사용해서 .을 기준으로 나누고 pop()를 사용해서 마지막 배열을 가져온뒤toLowerCase()로 소문자로 만듬 // toLowerCase = 소문자로 바꿈, 대문자 => toUpperCase
			if ($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) == -1 ){
				alert("이미지 파일만 업로드 할 수 있습니다.");
				$('#file').val('') // 잘못된 파일을 비운다.
				return;
			}
		}
		
		// 폼태그를 자바스크립트에서 만든다.
		let formData = new FormData();
		formData.append('subject', subject);
		formData.append('content', content);
		formData.append('file', $('#file')[0].files[0]);
		
		// ajax
		$.ajax({
			type:'post' // get은 body가 없어서 url로 받아버림 그래서 무조건 post로 해야함!
			, url: '/post/create'
			, data: formData
			, enctype: 'mulitpart/form-data'// 파일 업로드 필수 설정
			, processData: false   // 파일 업로드 필수 설정
			, contentType: false   // 파일 업로드 필수 설정
			, success: function(data){
				if (data.result == 'success'){
					alert("메모가 저장 됐습니다.");
					location.href ="/post/post_list_view";
				}
			}, error: function(e){
				alert("메모 저장에 실패했습니다." + e);
			}
		});
	});
});
</script>




