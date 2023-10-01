'use client'

import { useState } from 'react'
import { useRecoilState } from 'recoil'

import { selectedStackState } from '../../utils/atoms'
import AllStacks from '../../utils/stacks'

export default function TechStackSelection() {
  const [selectedStack, setSelectedStack] = useRecoilState(selectedStackState)
  const [searchText, setSearchText] = useState('')

  const handleSearchTextChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setSearchText(event.target.value)
  }

  const handleDeleteStack = (stack: string) => {
    setSelectedStack(selectedStack.filter((s) => s !== stack))
  }

  const handleAddStack = (stack: string) => {
    if (selectedStack.length < 6 && !selectedStack.includes(stack)) {
      setSelectedStack([...selectedStack, stack])
    }
  }

  const allStacks = AllStacks.map((x) => x.name)

  const filteredStacks =
    searchText !== ''
      ? allStacks.filter((stack) =>
          stack.toLowerCase().includes(searchText.toLowerCase()),
        )
      : null

  return (
    <div>
      <div className="focus:shadow-outline max:w-full m-0 mb-2.5 flex h-49 shrink-0 appearance-none flex-row overflow-hidden rounded border bg-[#F9F8F8] px-3 font-ng leading-tight text-gray-700 shadow hover:overflow-x-auto focus:outline-none sm:h-49">
        <div className="pointer-events-none flex-none border-r pt-3 pr-3">
          사용 기술{' '}
        </div>
        {selectedStack.map((stack) => (
          <div
            key={stack}
            className="ml-2 mt-3 h-6 w-auto shrink-0 rounded-full border bg-blue-500 px-2 font-ng-b text-white"
          >
            <span>{stack} </span>
            <button onClick={() => handleDeleteStack(stack)} type="button">
              x
            </button>
          </div>
        ))}
      </div>
      <input
        type="text"
        placeholder="기술 이름으로 검색"
        value={searchText}
        onChange={handleSearchTextChange}
        className="focus:shadow-outline m-0 h-49 w-full appearance-none rounded border bg-[#F9F8F8] px-3 font-ng text-gray-700 shadow focus:outline-none sm:h-49"
      />
      {filteredStacks === null ? null : (
        <div className=" max-h-40 overflow-auto border-b">
          {filteredStacks.length > 0 ? (
            <ul>
              {filteredStacks.map((stack) => (
                <li key={stack}>
                  <button
                    onClick={() => handleAddStack(stack)}
                    type="button"
                    className="focus:shadow-outline h-49 w-full appearance-none rounded border bg-[#F9F8F8] px-3 text-left font-ng leading-tight text-gray-700 shadow focus:outline-none"
                  >
                    {stack} 추가
                  </button>
                </li>
              ))}
            </ul>
          ) : (
            <ul>
              <li>
                <button
                  type="button"
                  className="focus:shadow-outline pointer-events-none h-14 w-full appearance-none rounded border bg-[#F9F8F8] px-3 text-left font-ng text-gray-700 shadow focus:outline-none"
                >
                  해당 기술이 없습니다. 영어로만 검색해 주세요.
                </button>
              </li>
            </ul>
          )}
        </div>
      )}
    </div>
  )
}
