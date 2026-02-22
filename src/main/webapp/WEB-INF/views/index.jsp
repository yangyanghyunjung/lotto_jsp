<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <title>메인 페이지</title>
        <script>
            (function () {
                const cookieName = 'lotto_event_seen';
                const visited = document.cookie.split('; ').find(row => row.startsWith(cookieName + '='));

                if (!visited) {
                    // Auto-redirect to event branch point
                    window.location.href = '/event/lotto';
                }
            })();
        </script>
    </head>

    <body>
        <h1>서비스 메인 화면</h1>
    </body>
</html>