import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { selectedStackState } from '../Recoil';

const allStacks = [
  'React',
  'Vue',
  'Angular',
  'Node.js',
  'Express',
  'MongoDB',
  'PostgreSQL',
];

function TechStackSelection() {
  const [selectedStack, setSelectedStack] = useRecoilState(selectedStackState);

  // 검색어와 검색어 변경 이벤트 처리를 위한 상태 및 함수
  const [searchText, setSearchText] = useState('');

  const handleSearchTextChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setSearchText(event.target.value);
  };

  // 기존에 선택된 기술 스택 삭제 함수
  const handleDeleteStack = (stack: string) => {
    setSelectedStack(selectedStack.filter((s) => s !== stack));
  };

  // 새로운 기술 스택 추가 함수
  const handleAddStack = (stack: string) => {
    if (!selectedStack.includes(stack)) {
      setSelectedStack([...selectedStack, stack]);
    }
  };

  // 검색어에 따른 기술 스택 필터링
  const filteredStacks =
    searchText !== ''
      ? allStacks.filter((stack) =>
          stack.toLowerCase().includes(searchText.toLowerCase()),
        )
      : null;

  return (
    <div>
      <div>
        {selectedStack.map((stack) => (
          <span key={stack}>
            {stack} <button onClick={() => handleDeleteStack(stack)}>x</button>
          </span>
        ))}
      </div>
      <input
        type="text"
        placeholder="Search tech stack"
        value={searchText}
        onChange={handleSearchTextChange}
      />
      {filteredStacks === null ? null : (
        <>
          {filteredStacks.length > 0 ? (
            <ul>
              {filteredStacks.map((stack) => (
                <li key={stack}>
                  <button onClick={() => handleAddStack(stack)}>
                    Add {stack}
                  </button>
                </li>
              ))}
            </ul>
          ) : (
            <p>No matching tech stack found</p>
          )}
        </>
      )}
    </div>
  );
}

export default TechStackSelection;
