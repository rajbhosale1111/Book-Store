<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<h5 class="p-2" style="border-bottom: 2px solid green;">Purchase Order List</h5>
<table class="table table-bordered table-sm">
	<thead>
		<tr>
			<th>PO #</th>
			<th>Vendor Id</th>
			<th>Vendor Name</th>
			<th>Purchase Date</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${items }" var="p">
			<tr>
				<td>${p.id}</td>
				<td>${p.vid}</td>
				<td>${p.vendor.name}</td>
				<td><fmt:parseDate value="${p.purdate}" pattern="yyyy-MM-dd"
						var="pdate" type="date" /> <fmt:formatDate pattern="dd-MMM-yyyy"
						value="${pdate}" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="footer.jsp"></jsp:include>