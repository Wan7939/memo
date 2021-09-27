<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div class="container">
		<div class="mt-3"><b>*아이디:</b>
			<label for="userId"><input id="userId" type="text" class="form-control" placeholder="아이디를 입력하세요."></label>
			<button id="nameCheckBtn" type="button" class="btn btn-info">중복확인</button><br>
		</div>
		<div class="mt-3"><b >*비밀번호:</b>
			<label for="password"><input id="password" type="password" class="form-control" placeholder="＊＊＊＊＊"></label>
		</div>
		<div class="mt-1"><b >*비밀번호 확인:</b>
			<label for="passwordCheck"><input id="passwordCheck" type="password" class="form-control" placeholder="＊＊＊＊＊"></label>
		</div>
		<div class="mt-1"><b >*이름:</b>
			<label for="name"><input id="name" type="text" class="form-control" placeholder="이름을 입력하세요."></label>
		</div>
		<div class="mt-1"><b >*이메일:</b>
			<label for="email"><input id="email" type="text" class="form-control" placeholder="이메일을 입력하세요."></label>
		</div>
		<button id="joinBtn" type="submit" class="btn btn-secondary mb-3">가입하기</button>
	</div>