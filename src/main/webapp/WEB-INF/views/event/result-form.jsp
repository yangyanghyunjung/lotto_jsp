<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
    <h2>로또 당첨 결과 확인</h2>
    <form action="/event/lotto/result" method="post">
        전화번호: <input type="text" name="phoneNum" required placeholder="010-0000-0000">
        <button type="submit">결과 보기</button>
    </form>
</body>
</html>