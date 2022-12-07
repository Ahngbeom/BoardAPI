<%--
  Created by IntelliJ IDEA.
  User: bbu0704
  Date: 2022-03-19
  Time: 오후 1:24
  To change this template use File | Settings | File Templates.
--%>
<html>
<body>
<div class="container-fluid w-100">
    <div class="d-flex justify-content-between w-100">
        <div class="w-100">
            <form method="get" action="${pageContext.request.contextPath}/board/search"
                  class="form-inline" id="boardSearchForm">

                <div class="d-flex">
                    <div class="form-check">
                        <input type="checkbox" name="checkTitle" class="form-check-input" id="checkTitle"
                               checked>
                        <label class="form-check-label" for="checkTitle">제목</label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" name="checkContent" class="form-check-input" id="checkContent">
                        <label class="form-check-label" for="checkContent">내용</label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" name="checkWriter" class="form-check-input" id="checkWriter">
                        <label class="form-check-label" for="checkWriter">작성자</label>
                    </div>
                </div>
                <div class="input-group mb-3">
                    <input class="form-control mr-sm-2" type="search" name="keyword" placeholder="Search"
                           aria-label="Search" required>
                    <button class="btn btn-outline-success my-2 my-sm-0" type="button">Search</button>
                </div>
            </form>

            <table class="table table-striped">
                <button type="button" onclick="location.href='/board/register'" class="btn btn-outline-secondary">게시물
                    작성
                </button>
                <thead>
                <tr>
                    <th>번호</th>
                    <th style="width: 200px">제목</th>
                    <th>작성자</th>
                    <th>업로드 날짜</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="post" items="${BoardList}" varStatus="status">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${post.type eq 'NOTICE'}">
                                    공지
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${post.bno}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="${pageContext.request.contextPath}/board/posts?bno=${post.bno}"><c:out
                                value="${post.title}"/></a></td>
                        <td><c:out value="${post.writer}"/></td>
                        <td>${post.dateToToday}</td>
                            <%--                    <fmt:parseDate value="${post.regDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedRegDate"--%>
                            <%--                                   boardAlertType="both"/>--%>
                            <%--                    <td><fmt:formatDate value="${parsedRegDate}" pattern="yyyy년 MM월 dd일 HH:mm:ss"/></td>--%>
                            <%--                    <fmt:parseDate value="${post.updateDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedUpdateDate"--%>
                            <%--                                   boardAlertType="both"/>--%>
                            <%--                    <td><fmt:formatDate value="${parsedUpdateDate}" pattern="yyyy년 MM월 dd일 HH:mm:ss"/></td>--%>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%--            <c:choose>--%>
            <%--                <c:when test="${pageAmount gt 10 and pageAmount % 10 le 9}">--%>
            <%--                    <c:set var="pageMin" value="${(pageAmount - pageAmount % 10) + 1}"/>--%>
            <%--                </c:when>--%>
            <%--                <c:otherwise>--%>
            <%--                    <c:set var="pageMin" value="${pageAmount - 9}"/>--%>
            <%--                </c:otherwise>--%>
            <%--            </c:choose>--%>
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <c:if test="${pagination.pageMin - 1 gt 0}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/board/list?page=${pagination.pageMin - 1}">Previous</a>
                        </li>
                    </c:if>
                    <c:forEach var="page" begin="${pagination.pageMin}" end="${pagination.pageMax}" varStatus="status">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}?keyword=${param.keyword}&page=${page}">${page}</a>
                        </li>
                    </c:forEach>
                    <%-- 유효하지않은 page 번호를 담아 url 요청할 경우 예외 처리 필요 --%>
                    <%-- page가 많을 경우 10개 단위로 페이지의 페이지 구현 (Prev, 1, 2.....10, Next) --%>

                    <c:if test="${pagination.pageMax + 1 lt pagination.pageAmount}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/board/list?page=${pagination.pageMax + 1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div class="border border-dark rounded w-25">
                <form method="post" action="${pageContext.request.contextPath}/board/removeAll">
                    <button>모든 게시물 삭제</button> <!-- 관리자 권한을 가지고있어야 함 -->
                </form>
                <form method="post" action="${pageContext.request.contextPath}/board/createDummy">
                    <label>
                        개수 :
                        <select name="dummyAmount">
                            <option value="1">1</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                        </select>
                    </label>
                    <button>게시물 더미 생성</button> <!-- 관리자 권한을 가지고있어야 함 -->
                </form>
            </div>
        </sec:authorize>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', () => {
        const boardSearchForm = document.querySelector("#boardSearchForm");
        const checkTitleInput = document.querySelector("input[name='checkTitle']");
        const checkWriterInput = document.querySelector("input[name='checkWriter']");
        const checkContentInput = document.querySelector("input[name='checkContent']");
        const checkKeywordInput = document.querySelector("input[name='keyword']");
        const boardSearchBtn = boardSearchForm.querySelector("button");

        if (${not empty param.checkTitle}) {
            checkTitleInput.setAttribute("checked", true);
        }
        if (${not empty param.checkWriter}) {
            checkWriterInput.setAttribute("checked", true);
        }
        if (${not empty param.checkContent}) {
            checkContentInput.setAttribute("checked", true);
        }
        if (${not empty param.keyword}) {
            checkKeywordInput.value = `${param.keyword}`;
        }

        boardSearchBtn.addEventListener('click', () => {
            if (checkTitleInput.checked || checkWriterInput.checked || checkContentInput.checked)
                boardSearchForm.submit();
            else
                putServerAlert("검색 조건을 하나 이상 선택해주세요.");
        });
    });
</script>

