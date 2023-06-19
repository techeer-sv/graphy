import { useNavigate } from 'react-router-dom';

import email from '../assets/image/email.svg';

function Signup() {
  const navigate = useNavigate();
  function toMain() {
    // react-router-dom을 이용한 글쓰기 페이지로 이동 함수
    navigate('/');
  }
  return (
    <div className=" h-auto min-h-screen w-screen bg-zinc-200 pt-10 pb-10">
      <button
        onClick={() => toMain()}
        className="mx-auto mb-10 block font-lato text-5xl text-graphyblue"
        type="button"
      >
        Graphy
      </button>
      <div className="mx-auto flex h-[600px] w-[450px] flex-col rounded-xl border bg-white pt-12 ">
        <span className=" ml-8 font-ng-eb text-3xl text-graphyblue">
          회원가입
        </span>
        <div className="mx-auto flex w-[360px] flex-col">
          <input
            className="mt-12 h-[60px] rounded-3xl border pl-4 text-lg"
            placeholder="이메일 주소"
          />
          <input
            className="mt-8 h-[60px] rounded-3xl border pl-4 text-lg"
            placeholder="비밀번호 (8자리 이상)"
          />
          <input
            className="mt-8 h-[60px] rounded-3xl border pl-4 text-lg"
            placeholder="비밀번호 확인"
          />
          <button
            className=" mt-8 flex h-[60px] items-center rounded-3xl border bg-graphyblue"
            type="submit"
          >
            <img src={email} alt="email" className="ml-4" />
            <span className=" mx-auto pr-7 font-ng-eb text-xl text-white">
              회원가입
            </span>
          </button>
        </div>
        <div className="m-auto mb-0 flex h-20 w-[450px] items-center justify-center border-t">
          <span>이미 회원이세요?</span>
          <button className="mx-4 font-ng-eb text-graphyblue" type="button">
            로그인
          </button>
        </div>
      </div>
    </div>
  );
}

export default Signup;
