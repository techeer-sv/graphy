import { useEffect, useRef, useState } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';
import { projectIdState, refreshState } from '../Recoil';
import ReadReply from './ReadReply';
import axios from 'axios';

function Reply(props: any) {
  const [count, SetCount] = useState(0);
  const [selectedValue, setSelectedValue] = useState<string>('regist_order');
  const textAreaRef = useRef<HTMLTextAreaElement>(null);
  const [value, setValue] = useState('');
  const projectId = useRecoilValue(projectIdState);
  const [refresh, setRefresh] = useRecoilState(refreshState);
  const [visible, setVisible] = useState(true);

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const inputValue = e.target.value;
    if (textAreaRef.current) {
      textAreaRef.current.style.height = 'auto';
      textAreaRef.current.style.height = `${textAreaRef.current.scrollHeight}px`;
    }
    if (inputValue.length > 255) {
      setValue(inputValue.substring(0, 255));
      alert('대댓글은 255자까지 입력하실 수 있습니다.');
      return;
    }
    setValue(inputValue);
  };

  function MoveToTop() {
    // top:0 >> 맨위로  behavior:smooth >> 부드럽게 이동할수 있게 설정하는 속성
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  async function postData() {
    const url = 'http://localhost:8080/api/v1/comments';
    const data = {
      content: value,
      projectId: projectId,
    };

    try {
      const res = await axios.post(url, data);
      console.log(res.data);
      setRefresh(!refresh);
      setValue('');
      setSelectedValue('regist_order');
    } catch (error) {
      if (value.trim().length === 0) {
        alert('댓글을 입력해주세요.');
      } else {
        alert('네트워크 오류');
      }
    }
  }

  // function myFunction(selectedValue: any) {
  //   switch (selectedValue) {
  //     case 'newest_order':
  //       console.log(props);
  //       const sortedContents = props.contents.slice().sort((a: any, b: any) => {
  //         const aTime = new Date(a.createdAt).getTime();
  //         const bTime = new Date(b.createdAt).getTime();
  //         return bTime - aTime;
  //       });
  //       props.setReadReply(sortedContents);
  //       break;
  //     case 'reply_order':
  //       console.log(props);
  //       // 답글순 정렬
  //       const replyedContents = props.contents
  //         .slice()
  //         .sort((a: any, b: any) => {
  //           return b.childComments.length - a.childComments.length;
  //         });
  //       props.setReadReply(replyedContents);
  //       break;
  //     default:
  //       console.log(props);
  //       // 등록순 정렬
  //       const regisedContents = props.contents
  //         .slice()
  //         .sort((a: any, b: any) => {
  //           const aTime = new Date(a.createdAt).getTime();
  //           const bTime = new Date(b.createdAt).getTime();
  //           return aTime - bTime;
  //         });
  //       props.setReadReply(regisedContents);
  //       break;
  //   }
  // }

  // function handleselectChange(event: { target: { value: any } }) {
  //   const selectedValue = event.target.value; // 선택된 값 가져오기
  //   setSelectedValue(selectedValue); // 선택된 값 상태 업데이트
  //   myFunction(selectedValue); // 선택된 값 전달하여 실행할 함수 호출
  // }

  function handleRefresh() {
    setRefresh(!refresh);
    setSelectedValue('regist_order');
  }

  // useEffect(() => {
  //   let childlength = 0;
  //   props.contents.length != 0
  //     ? props.contents.map((x: any) => (childlength += x.childComments.length))
  //     : null;
  //   SetCount(props.contents.length + childlength);
  // }, [props]);

  return (
    <div>
      {/*댓글 개수, 댓글 나열 카테고리*/}
      <div className="mb-2 flex flex-row whitespace-nowrap border-b-2 border-graphyblue">
        <span className="mr-2 flex flex-row font-ng-b text-sm sm:text-lg">
          전체 댓글 <p className="ml-1 text-graphyblue">{count}</p>개
        </span>
        <select
          className="mb-1 hidden rounded border border-black bg-graphybg font-ng text-sm sm:block"
          // value={selectedValue}
          // onChange={handleselectChange}
        >
          <option value="regist_order">등록순</option>
          <option value="newest_order">최신순</option>
          <option value="reply_order">답글순</option>
        </select>
        <div className="mx-auto mr-0 mb-2">
          <button
            className="mr-2 border-r border-gray-500 pr-2 font-ng-b text-xs sm:text-sm"
            onClick={() => MoveToTop()}
          >
            본문 보기
          </button>
          <button
            className="mr-2 border-r border-gray-500 pr-2 font-ng-b text-xs sm:text-sm"
            onClick={() => setVisible(!visible)}
          >
            {visible ? '댓글 닫기' : '댓글 열기'}
          </button>
          <button
            className="mr-1 font-ng-b text-xs sm:text-sm"
            onClick={() => handleRefresh()}
          >
            새로고침
          </button>
        </div>
      </div>
      {/*댓글 표시*/}
      <div className="my-2 border-graphyblue">
        {visible ? (
          <>
            {props.contents.map((x: object, y: number) => (
              <ReadReply
                contents={x}
                key={props.contents[y].commentId}
                setSelectedValue={setSelectedValue}
              />
            ))}
          </>
        ) : null}
        {/*댓글 입력창*/}
        <div className="mb-8 mt-3 border-t-2 border-graphyblue py-3">
          <div className="min-h-24 flex h-auto flex-col rounded-xl border-2 border-gray-400">
            <textarea
              className="h-auto w-full resize-none appearance-none rounded-xl bg-graphybg py-2 px-3 font-ng leading-tight text-gray-700 focus:outline-none"
              id="reply"
              placeholder="댓글을 입력하세요."
              ref={textAreaRef}
              value={value}
              onChange={handleChange}
            />
            <button
              className="focus:shadow-outline m-auto my-2 mr-2 h-8 w-16 appearance-none place-items-end rounded-lg border-2 border-gray-400 bg-graphybg font-ng hover:bg-gray-200"
              onClick={() => postData()}
            >
              등록
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Reply;
