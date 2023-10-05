'use client'

import '../../../public/css/toastui-editor.css'
import '@toast-ui/editor/dist/i18n/ko-kr'
import 'tui-color-picker/dist/tui-color-picker.css'
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css'
import 'prismjs/themes/prism.css'
import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css'

import { Editor } from '@toast-ui/react-editor'
import colorSyntax from '@toast-ui/editor-plugin-color-syntax'
import Prism from 'prismjs'
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight'
import { useEffect, useRef, useState } from 'react'
import { useRecoilState } from 'recoil'
import { contentsState } from '../../utils/atoms'

export default function ToastUIEditor() {
  const toastRef = useRef<Editor>(null)
  const [, setContents] = useRecoilState(contentsState)
  const [previewStyle, setPreviewStyle] = useState(
    window.innerWidth < 1000 ? 'tab' : 'vertical',
  )

  const toolbarItems = [
    ['heading', 'bold', 'italic', 'strike'],
    ['hr'],
    ['ul', 'ol'],
    ['link'],
    ['image'],
    ['code', 'codeblock'],
    ['scrollSync'],
  ]

  const onUploadImage = async (
    image: Blob,
    callback: (arg0: string, arg1: string) => void,
  ) => {
    const formData = new FormData()
    formData.append('image', image as Blob)
    const res = await fetch('/api/v1/s3', {
      method: 'POST',
      body: formData,
    })

    const resData = await res.json()

    if (!res.ok) {
      throw new Error(resData.message)
    }

    callback(resData.data.location, 'image')
  }

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth < 1000) {
        setPreviewStyle('tab')
      } else {
        setPreviewStyle('vertical')
      }
    }

    window.addEventListener('resize', handleResize)
    return () => {
      window.removeEventListener('resize', handleResize)
    }
  }, [])

  return (
    <Editor
      className=" font-lef"
      ref={toastRef}
      onChange={() =>
        setContents(toastRef.current?.getInstance()?.getHTML() || '')
      }
      initialValue=" "
      placeholder="내용을 입력해주세요."
      previewStyle={previewStyle}
      height="600px"
      initialEditType="markdown"
      toolbarItems={toolbarItems}
      hooks={{ addImageBlobHook: onUploadImage }}
      useCommandShortcut
      usageStatistics
      hideModeSwitch
      language="ko-KR"
      plugins={[colorSyntax, [codeSyntaxHighlight, { highlighter: Prism }]]}
    />
  )
}
