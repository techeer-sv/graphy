import { useEffect, useRef, useState } from 'react'
import { useRecoilState } from 'recoil'

import { refreshState } from '../../../utils/atoms'

type PutReplyProps = {
  contents: {
    commentId: number
    childCount?: number
    content: string
    createdAt: string
  }
  setSelectedValue: React.Dispatch<React.SetStateAction<string>>
  changeCommentRef: () => void
  changePutVis: () => void
}

export default function PutReply({
  contents,
  setSelectedValue,
  changeCommentRef,
  changePutVis,
}: PutReplyProps) {
  const textAreaRef = useRef<HTMLTextAreaElement>(null)
  const [value, setValue] = useState<string>(contents.content)
  const [refresh, setrefresh] = useRecoilState(refreshState)
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const inputValue = e.target.value
    if (textAreaRef.current) {
      textAreaRef.current.style.height = 'auto'
      textAreaRef.current.style.height = `${textAreaRef.current.scrollHeight}px`
    }
    if (inputValue.length > 255) {
      setValue(inputValue.substring(0, 255))
      alert('대댓글은 255자까지 입력하실 수 있습니다.')
      return
    }
    setValue(inputValue)
  }

  async function putData() {
    const data = {
      content: value,
    }

    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/comments/${contents.commentId}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${accessToken || persistToken}`,
        },
        body: JSON.stringify(data),
      },
    )

    setrefresh(!refresh)
    if (!('childCount' in contents)) {
      changeCommentRef()
    }
    setValue('')

    if (!res.ok) {
      if (!navigator.onLine) {
        alert('오프라인 상태입니다. 네트워크 연결을 확인해주세요.')
      } else if (value.length === 0) {
        alert('수정할 댓글을 입력해주세요.')
      } else {
        alert('댓글 수정 실패')
        throw new Error('댓글 수정 실패')
      }
    }
  }

  function put() {
    putData()
    changePutVis()
    setSelectedValue('regist_order')
  }

  useEffect(() => {
    if (textAreaRef.current) {
      textAreaRef.current.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
      })
    }
  }, [])

  return (
    <div>
      {/* 대댓글 입력창 */}
      <div className="relative">
        <div className="mt-3 flex h-auto flex-col rounded-xl border-2 border-gray-400">
          <textarea
            className="min-h-24 h-auto w-full resize-none appearance-none rounded-xl bg-graphybg py-2 px-3 font-ng leading-tight text-gray-700 focus:outline-none"
            id="reply"
            placeholder="수정할 댓글을 입력하세요."
            ref={textAreaRef}
            value={value}
            onChange={handleChange}
          />
          <button
            className="focus:shadow-outline m-auto my-2 mr-2 h-8 w-16 appearance-none place-items-end rounded-lg border-2 border-gray-400 bg-graphybg font-ng hover:bg-gray-200"
            onClick={() => put()}
            type="submit"
          >
            수정
          </button>
        </div>
      </div>
    </div>
  )
}
