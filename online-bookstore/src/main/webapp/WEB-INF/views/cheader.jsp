<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>Online Shopping</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport"
		content="width=device-width, initial-scale=1, maximum-scale=1">
	<link href="/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="/css/menu.css" rel="stylesheet" type="text/css" media="all" />
	<link href="/css/all.css" rel="stylesheet" type="text/css" />
	<link href="/css/flexslider.css" rel='stylesheet' type='text/css' />
	<link href="/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/css/shop.css" rel="stylesheet" type="text/css" />
	<script src="/js/jquery.min.js"></script>
	<script src="/js/script.js" type="text/javascript"></script>
	<link rel="icon" href="/images/logo.png" />
</head>
<body>
	<div class="container-fluid">
		<div class="header">
			<div class="header_top position-relative">
			<c:if test="${userid ne null }">
				<h5 class="text-center float-right p-3 font-weight-bold">Welcome ! ${uname }</h5>
			</c:if>
			<div class="position-absolute" style="top:20px;left:40%;">
			<form class="form-inline">
				<input type="text" name="name" value="${param.name }" class="form-control">
				<button class="btn btn-primary">Search</button>
			</form>
			</div>
			<div class="position-absolute w-100 font-weight-bold" style="top:60px">
				<jsp:include page="msg.jsp"></jsp:include>
			</div>
						
				<div class="logo">
					<a href="/"><img style="width: 180px; height: 100px;"
						src="/images/logo.jpg" alt="" /></a>
				</div>
				<div class="clear"></div>
				
			</div>
			
		</div>

		<div class="h_menu sticky-top">
			<a id="touch-menu" class="mobile-menu" href="#">Menu</a>
			<nav>
				<ul class="menu list-unstyled">
					<li><a href="/">Home</a></li>
					<li><a href="/product">Books</a></li>
					<li class="activate"><a href="#">Categories</a>
						<ul class="sub-menu list-unstyled sub-menu2">
							<c:forEach items="${cats }" var="cat">
							<li style="width: 190px;"><a href="/cats/${cat.catid}">
									${cat.catname}</a></li>
							</c:forEach>
						</ul></li>
						<c:choose>
					<c:when test="${ sessionScope.userid ne null }">
					<li class="activate float-right" style="width:190px;">
					<a href="#" class="d-block w-100">Hi! ${sessionScope.uname }</a>
					<ul class="sub-menu list-unstyled sub-menu2">
					<li style="width: 190px;"><a href="/cchangepwd">Change Password</a></li>
					<li style="width: 190px;"><a href="/logout">Logout</a></li>
					</ul>
					</li>
					<li class="float-right"><a href="/cart">Cart 
					<c:if test="${cqty > 0 }">
					<i class="badge badge-warning">${cqty}</i></c:if>
							</a></li>
					<li class="float-right"><a href="/history">History</a></li>
															
					</c:when>
					<c:otherwise>
					<li class="float-right"><a href="/login">Login or Register</a></li>
					</c:otherwise>
					</c:choose>
					<div class="clear"></div>
				</ul>
			</nav>

		</div>