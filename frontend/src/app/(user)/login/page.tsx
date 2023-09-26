'use client'

/* eslint-disable react/jsx-props-no-spreading */

import { yupResolver } from '@hookform/resolvers/yup'
import { useEffect } from 'react'
import { SubmitHandler, useForm } from 'react-hook-form'
import { useRouter } from 'next/navigation'
import { useRecoilState } from 'recoil'
import * as yup from 'yup'

import Image from 'next/image'
import Email from '../../../../public/images/svg/email.svg'
import { autoLoginState } from '../../../utils/atoms'

type DataObject = {
  email: string
  password: string
}

const schema = yup.object().shape({
  email: yup
    .string()
    .email('이메일 형식이 맞지 않습니다.')
    .required('이메일이 필요합니다.'),
  password: yup
    .string()
    .min(8, '비밀번호는 최소 8자리 이상이여야 합니다.')
    .matches(
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/,
      '1개 이상의 대소문자, 숫자, 특수문자가 포함되어야 합니다.',
    )
    .required('비밀번호가 필요합니다.'),
})

export default function Login() {
  const router = useRouter()
  const accessToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null
  const persistToken =
    typeof window !== 'undefined' ? localStorage.getItem('persistToken') : null
  const [autoLogin, setAutoLogin] = useRecoilState(autoLoginState)

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setAutoLogin(event.target.checked)
  }

  const handleButtonClick = () => {
    setAutoLogin(!autoLogin)
  }

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<DataObject>({
    resolver: yupResolver(schema),
  })

  function toMain() {
    router.push('/')
  }

  function toRegistration() {
    router.push('/registration')
  }

  const onSubmit: SubmitHandler<DataObject> = async (data: DataObject) => {
    const res = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/auth/signin`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: data.email,
        password: data.password,
      }),
    })

    if (!res.ok) {
      throw new Error('로그인에 실패했습니다.')
    }

    const resData = await res.json()

    if (autoLogin) {
      localStorage.setItem('persistToken', resData.data.accessToken)
    } else {
      sessionStorage.setItem('accessToken', resData.data.accessToken)
    }
    router.push('/')
  }

  useEffect(() => {
    if (!navigator.onLine) {
      throw new Error('오프라인 상태입니다. 네트워크 연결을 확인해주세요.')
    }
    if (accessToken || persistToken) {
      // eslint-disable-next-line no-alert
      alert('이미 로그인 상태입니다.')
      router.push('/')
    }
  }, [])

  return (
    <div className=" h-auto min-h-screen w-screen bg-zinc-200 pt-10 pb-10">
      <button
        onClick={() => toMain()}
        className="mx-auto mb-10 block font-lato-b text-5xl text-graphyblue "
        type="button"
      >
        Graphy
      </button>
      <div className="mx-auto flex h-auto w-[450px] flex-col rounded-xl border bg-white pt-12 ">
        <span className=" ml-8 font-ng-eb text-3xl text-graphyblue">
          로그인
        </span>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="mx-auto flex w-[360px] flex-col"
        >
          <input
            {...register('email')}
            type="email"
            className="mt-12 h-[60px] rounded-3xl border pl-4 text-lg"
            placeholder="이메일 주소"
            autoComplete="email"
          />
          <p className="text-sm">{errors.email?.message}</p>
          <input
            {...register('password')}
            type="password"
            className="mt-10 h-[60px] rounded-3xl border pl-4 text-lg"
            placeholder="비밀번호 (8자리 이상)"
            autoComplete="current-password"
          />
          <p className="text-sm">{errors.password?.message}</p>
          <button
            className=" mt-10 flex h-[60px] items-center rounded-3xl border bg-graphyblue"
            type="submit"
          >
            <Image src={Email} alt="email" className="ml-4" />
            <span className=" mx-auto pr-7 font-ng-eb text-xl text-white">
              로그인
            </span>
          </button>
        </form>
        <div className="my-8 ml-12 flex">
          <div>
            <input
              type="checkbox"
              className="h-4 w-4"
              checked={autoLogin}
              onChange={handleCheckboxChange}
            />
            <button
              className="ml-2 text-xl"
              type="button"
              onClick={handleButtonClick}
            >
              로그인 상태 유지
            </button>
          </div>
          {/* <button className="ml-16 mr-0 text-xl" type="button">
            비밀번호 찾기
          </button> */}
        </div>
        <div className="m-auto mb-0 flex h-20 w-[450px] items-center justify-center border-t">
          <span>아직 회원이 아니세요?</span>
          <button
            className="mx-4 font-ng-eb text-graphyblue"
            type="button"
            onClick={() => toRegistration()}
          >
            회원가입
          </button>
        </div>
      </div>
    </div>
  )
}
