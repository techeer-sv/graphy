// import NavBar from '../components/NavBar';
// import ProjectCard from '../components/ProjectCard';

function MyPage() {
  return (
    <div className="relative h-auto min-h-screen w-screen bg-graphybg">
      {/* <NavBar /> */}
      <div className="inline-flex">
        <div className="h-48 w-64 rounded-[25px] bg-white font-lato">
          닉네임
        </div>
        <div className="font-lato text-[21px]">나의 작성 포스트</div>
        <div className="mx-10 border-b-2 border-b-neutral-300 pt-0 font-ng-b text-2xl sm:mx-28 sm:mb-5 sm:pt-5" />
      </div>
    </div>
  );
}

export default MyPage;
