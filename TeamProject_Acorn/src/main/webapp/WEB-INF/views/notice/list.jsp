<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** banapresso **</title>
<link rel="shortcut icon" type="image/x-icon" href="https://www.banapresso.com/ico_logo.ico">
<jsp:include page="../include/resource_boot4.jsp" />
<style>
h1{color: #F1648A;}
thead{
	background-color: #F1A4BA;
	vertical-align: inherit;
  	font-weight: bold;
}
.table th,
.table td {
	border-top: 1px solid #F1A4BA;
}
.table thead th {
  	border-bottom: 2px solid #F1A4BA;
}
.table td a{
	color:#212529;
}
.table-hover tbody tr:hover {
	background-color: #f5d7e0;
}
.btn-primary{
	background-color:#F1648A;
	border:0;
	outline:0;
}
.btn-primary:hover{
	background-color:#F1A4BA;
	border:0;
	outline:0;
}
.btn-primary:focus, .btn-primary.focus {
	color: #fff;
	background-color: #F1648A;
	border: 0;
	box-shadow: 0 0 0 0.1rem #F1A4BA;
}
.btn-primary:not(:disabled):not(.disabled):active, .btn-primary:not(:disabled):not(.disabled).active,
.show > .btn-primary.dropdown-toggle {
  color: #fff;
  background-color: #F1648A;
  border-color: #F1A4BA;
}
.btn-primary:not(:disabled):not(.disabled):active:focus, .btn-primary:not(:disabled):not(.disabled).active:focus,
.show > .btn-primary.dropdown-toggle:focus {
  box-shadow: 0 0 0 0.1rem #F1A4BA;
}
.page-link {
  color: #F1648A;
  border: 1px solid #F1A4BA;
}
.page-link:hover {
  color: #F1648A;
  background-color: #F1A4BA;
  border-color: #F1A4BA;
}
.page-item.active .page-link {
  color: #fff;
  background-color: #F1648A;
  border-color: #F1A4BA;
}
.page-item.disabled .page-link {
  border-color: #F1A4BA;
}
</style>
</head>
<body>
<jsp:include page="../include/navbar2.jsp">
	<jsp:param value="notice" name="category"/>
</jsp:include>

<div class="container">

	
	<h1>공지사항</h1>
	<p>각종 공지사항을 확인하실 수 있는 게시판 입니다.</p> <br/>
	
	<form action="list.go" method="get">
		<div class="input-group input-group-sm" style="width:35%;">
			<select class="custom-select" name="condition" id="condition" style="max-width:35%;">
				<option selected>검색 조건 선택</option>
				<option value="title"<c:if test="${condition eq 'title' }">selected</c:if>>제목</option>
				<option value="titlecontent"<c:if test="${condition eq 'titlecontent' }">selected</c:if> >제목+내용</option>
			</select>
			<input class="form-control" type="text" name="keyword" 
				placeholder="키워드를 입력하세요..." value="${keyword }"/>
			<div class="input-group-append">
				<button class="btn btn-primary btn-sm" type="submit">검색</button>
			</div>
		</div>
	</form>
	
	<c:if test="${not empty keyword }">
		<span style="float:right;font-size:0.7rem;">
			<strong>${keyword }</strong> 라는 검색어로 
			<strong>${totalRow }</strong> 개의 글이 검색 되었습니다.
		</span>
	</c:if>
	
	<table class="table table-sm table-hover mt-3">
		<colgroup>
			<col class="col-xs-2"/>
			<col class="col-xs-6"/>
			<col class="col-xs-2"/>
			<col class="col-xs-2"/>
		</colgroup>
		<thead>
			<tr>
				<th>글번호</th>
				<th>[분류] 제목</th>
				<th>조회수</th>
				<th>등록일</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="tmp" items="${requestScope.list }">
			<tr>
				<td>${tmp.num }</td>
				<td>
					<a class="title" href="detail.go?num=${tmp.num }&condition=${condition }&keyword=${encodedKeyword }">${tmp.title }</a>
				</td>
				<td>${tmp.viewCount }</td>
				<td>${tmp.regdate }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<c:if test="${not empty isAdmin}">
		<div class="text-right">
			<a href="insertform.go" class="btn btn-primary btn-sm">공지사항 작성</a>
		</div>
	</c:if>
	
	<nav aria-label="Search pages" class="d-flex">
		<ul class="pagination pagination-sm mx-auto">
		<c:choose>
			<c:when test="${startPageNum ne 1 }">
				<li class="page-item">
					<a class="page-link" href="list.go?pageNum=${startPageNum-1 }&condition=${condition}&keyword=${encodedKeyword}">&laquo;</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="page-item disabled">
					<a class="page-link" href="javascript:">&laquo;</a>
				</li>
			</c:otherwise>
		</c:choose>
		<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1">
			<c:choose>
				<c:when test="${i eq pageNum }">
					<li class="page-item active">
						<a class="page-link" href="list.go?pageNum=${i }&condition=${condition}&keyword=${encodedKeyword}">${i }</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item">
						<a class="page-link" href="list.go?pageNum=${i }&condition=${condition}&keyword=${encodedKeyword}">${i }</a>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
			<c:choose>
				<c:when test="${endPageNum lt totalPageCount }">
					<li class="page-item">
						<a class="page-link" href="list.go?pageNum=${endPageNum+1 }&condition=${condition}&keyword=${encodedKeyword}">&raquo;</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item disabled">
						<a class="page-link" href="javascript:">&raquo;</a>
					</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
</div>
<br/><br/><br/>
<jsp:include page="../include/footer2.jsp"/>
<jsp:include page="../include/msgbtn.jsp"/>
</body>
</html>