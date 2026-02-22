<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <h3>${message}</h3>
        <p>참여 순서: <strong>${resOrder}</strong> 번</p>
        <a href="/">홈으로 돌아가기</a>
    </body>

    </html>