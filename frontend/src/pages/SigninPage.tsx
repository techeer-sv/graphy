function Signin() {
  return (
    <div>
      <div>
        <span>로그인</span>
        <div>
          <input placeholder="이메일 주소" />
          <input placeholder="비밀번호 (8자리 이상)" />
          <button type="submit">
            <img alt="email" />
            <span>로그인</span>
          </button>
          <div>
            <input type="checkbox" />
            <button type="button">비밀번호 찾기</button>
          </div>
        </div>
        <div>
          <span>아직 회원이 아니세요?</span>
          <button type="button">회원가입</button>
        </div>
      </div>
    </div>
  );
}

export default Signin;
