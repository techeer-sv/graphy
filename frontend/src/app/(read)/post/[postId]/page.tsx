'use client'

import QuillWrtten from '@/components/read/QuillWritten'
import { useState } from 'react'
// import Reply from '../../../../components/read/Reply'
import { set } from 'react-hook-form'
import { Quill } from 'react-quill'

// type ReadReplyObject = {
//   commentId: number
//   childCount: number
//   content: string
//   createdAt: string
// }

export default function ReadingPage() {
  // const [readReply, setReadReply] = useState<ReadReplyObject[]>([])
  return (
    <div className="mt-4">
      {/* <Reply contents={readReply} setReadReply={setReadReply} /> */}
    </div>
  )
}
