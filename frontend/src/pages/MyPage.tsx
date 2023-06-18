import NavBar from '../components/NavBar';
// import ProjectCard from '../components/ProjectCard';

function MyPage() {
  return (
    <div className="relative h-auto min-h-screen w-screen bg-graphybg">
      <NavBar />
      <div className="flex">
        <div className="h-48 w-64 rounded-[25px] bg-white">닉네임</div>
        <div className="text-[21px]">나의 작성 포스트</div>
      </div>
    </div>
  );
}

export default MyPage;
