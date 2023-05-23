import DotLoader from 'react-spinners/DotLoader';

function LoadingSpinner() {
  return (
    <div className="flex h-screen w-screen flex-col items-center justify-center overflow-y-auto pb-20">
      <DotLoader color="#505F9A" className="mx-auto">
        스피너
      </DotLoader>
      <div className="mt-20 pt-10 text-center text-2xl">로딩중 입니다. . .</div>
    </div>
  );
}

export default LoadingSpinner;
