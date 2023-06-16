import email from '../assets/image/email.svg';

function Signin() {
  return (
    <div className="bg- h-screen w-screen bg-zinc-200 pt-10">
      <div className="mx-auto flex h-[600px] w-[450px] flex-col rounded-xl border bg-white pt-12 ">
        <span className=" ml-8 font-ng-eb text-3xl text-graphyblue">
          로그인
        </span>
        <div className="mx-auto flex w-[360px] flex-col">
          <input
            className="mt-12 h-[60px] rounded-3xl border pl-4 text-lg"
            placeholder="이메일 주소"
          />
          <input
            className="mt-10 h-[60px] rounded-3xl border pl-4 text-lg"
            placeholder="비밀번호 (8자리 이상)"
          />
          <button
            className=" mt-10 flex h-[60px] items-center rounded-3xl border bg-graphyblue"
            type="submit"
          >
            <img src={email} alt="email" className="ml-4" />
            <span className=" mx-auto pr-7 font-ng-eb text-xl text-white">
              로그인
            </span>
          </button>
          <div className="mt-10 flex">
            <div>
              <input type="checkbox" />
              <span className="ml-2 text-xl">로그인 상태 유지</span>
            </div>
            <button className="mx-auto mr-0 text-xl" type="button">
              비밀번호 찾기
            </button>
          </div>
        </div>
        <div className="m-auto mb-0 flex h-20 w-[450px] items-center justify-center border-t">
          <span>아직 회원이 아니세요?</span>
          <button className="mx-4 font-ng-eb text-graphyblue" type="button">
            회원가입
          </button>
        </div>
      </div>
    </div>
  );
}

export default Signin;
