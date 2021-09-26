<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
  <!-- bootstrap CDN link -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

  <%-- AJAX를 사용하려면 더 많은 함수가 있는 js를 포함해야 한다. --%>
  <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
  <!-- <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script> -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
	<div class="container">
	
		<div class="mt-4"><b >아이디:</b>
			<label for="userId"><input id="userId" type="text" class="form-control" placeholder="아이디를 입력하세요."></label>
			<button id="nameCheckBtn" type="button" class="btn btn-info">중복확인</button><br>
		</div>
		<div class="mt-3"><b >비밀번호:</b>
			<label for="password"><input id="password" type="password" class="form-control" placeholder="＊＊＊＊＊"></label>
		</div>
		<div class="mt-1"><b >비밀번호 확인:</b>
			<label for="passwordCheck"><input id="passwordCheck" type="password" class="form-control" placeholder="＊＊＊＊＊"></label>
		</div>
		<div class="mt-1"><b >이름:</b>
			<label for="name"><input id="name" type="text" class="form-control" placeholder="이름을 입력하세요."></label>
		</div>
		<div class="mt-1"><b >이메일:</b>
			<label for="email"><input id="email" type="text" class="form-control" placeholder="이메일을 입력하세요."></label>
		</div>
		<button id="joinBtn" type="submit" class="btn btn-secondary">가입하기</button>
	</div>
	
</body>
</html>