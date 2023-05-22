import DotLoader from 'react-spinners/DotLoader';

function LoadingSpinner() {
  return (
    <>
      <div className="flex min-h-screen w-full flex-col items-center justify-center overflow-y-auto pb-20"></div>
      <DotLoader color="#505F9A" className="mt-8 mb-8 ml-48">
        스피너
      </DotLoader>
      <div className="mt-20 pt-10 text-center text-2xl">로딩중 입니다. . .</div>
    </>
  );
}

export default LoadingSpinner;
