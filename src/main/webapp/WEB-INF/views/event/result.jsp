<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <script>
                (function () {
                    const cookieName = 'lotto_event_seen';
                    const now = new Date();
                    const endOfDay = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 23, 59, 59, 999);
                    document.cookie = cookieName + "=true; expires=" + endOfDay.toUTCString() + "; path=/";
                })();
            </script>
        </head>

        <body>
            <h3>조회 결과</h3>
            <c:choose>
                <c:when test="${result.viewCnt == 1}">
                    <p>당첨 순위: <strong>${result.winRank}</strong> 등</p>
                </c:when>
                <c:otherwise>
                    <p>${result.message}</p>
                </c:otherwise>
            </c:choose>
            <a href="/">홈으로 돌아가기</a>
        </body>

        </html>