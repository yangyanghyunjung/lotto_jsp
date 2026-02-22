<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <script>
        window.onload = function() {
            var errorMsg = "${errorMessage}";
            if (errorMsg) {
                alert(errorMsg);
            }
        };
    </script>
</head>
<body>
    <h2>로또 이벤트 참여</h2>
    <form action="/event/lotto/participate" method="post">
        전화번호: <input type="text" name="phoneNum" required placeholder="010-0000-0000">
        <button type="submit">참여하기</button>
    </form>
</body>
</html>