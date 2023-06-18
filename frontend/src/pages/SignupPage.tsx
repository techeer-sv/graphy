import { yupResolver } from '@hookform/resolvers/yup';
import axios from 'axios';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';

import Email from '../assets/image/email.svg';

type DataObject = {
  email: string;
  password: string;
  confirmPassword: string;
  nickname: string;
  introduction: string | undefined;
};

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
  confirmPassword: yup
    .string()
    .oneOf([yup.ref('password'), ''], '비밀번호가 일치하지 않습니다.')
    .required('비밀번호 확인이 필요합니다.'),
  nickname: yup
    .string()
    .max(10, '닉네임은 최대 10자리까지 가능합니다.')
    .required('닉네임이 필요합니다.'),
  introduction: yup
    .string()
    .max(20, '한 줄 소개는 최대 20자리까지 가능합니다.'),
});

function Signup() {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<DataObject>({
    resolver: yupResolver(schema),
  });

  function toMain() {
    navigate('/');
  }

  const onSubmit: SubmitHandler<DataObject> = async (data: DataObject) => {
    console.log(data);
    try {
      const res = await axios.post(
        'http://localhost:8080/api/v1/members/join',
        {
          email: data.email,
          password: data.password,
          nickname: data.nickname,
          introduction: data.introduction,
        },
      );
      console.log(res);
    } catch (err) {
      console.log(err);
    }
    navigate('/');
  };

  return (
    <div className=" h-auto min-h-screen w-screen bg-zinc-200 pt-10 pb-10">
      <button
        onClick={() => toMain()}
        className="mx-auto mb-10 block font-lato text-5xl text-graphyblue"
        type="button"
      >
        Graphy
      </button>
      <div className="mx-auto flex h-auto w-[450px] flex-col rounded-xl border bg-white pt-12 ">
        <span className=" ml-8 font-ng-eb text-3xl text-graphyblue">
          회원가입
        </span>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="mx-auto flex w-[360px] flex-col"
        >
          <input
            {...register('email')}
            type="email"
            className="mt-8 h-[48px] rounded-3xl border pl-4 text-lg"
            placeholder="이메일 주소"
            autoComplete="email"
          />
          <p className="text-sm">{errors.email?.message}</p>
          <input
            {...register('password')}
            type="password"
            className="mt-4 h-[48px] rounded-3xl border pl-4 text-lg"
            placeholder="비밀번호 (8자리 이상)"
            autoComplete="new-password"
          />
          <p className="text-sm">{errors.password?.message}</p>
          <input
            {...register('confirmPassword')}
            type="password"
            className="mt-4 h-[48px] rounded-3xl border pl-4 text-lg"
            placeholder="비밀번호 확인"
            autoComplete="new-password"
          />
          <p className="text-sm">{errors.confirmPassword?.message}</p>
          <input
            {...register('nickname')}
            type="text"
            className="mt-4 h-[48px] rounded-3xl border pl-4 text-lg"
            placeholder="닉네임"
            autoComplete="nickname"
          />
          <p className="text-sm">{errors.nickname?.message}</p>
          <input
            {...register('introduction')}
            type="text"
            className="mt-4 h-[48px] rounded-3xl border pl-4 text-lg"
            placeholder="한 줄 소개"
          />
          <p className="text-sm">{errors.introduction?.message}</p>
          <button
            className=" mt-4 mb-8 flex h-[48px] items-center rounded-3xl border bg-graphyblue"
            type="submit"
          >
            <img src={Email} alt="email" className="ml-4" />
            <span className=" mx-auto pr-7 font-ng-eb text-xl text-white">
              회원가입
            </span>
          </button>
        </form>
        <div className="m-auto mb-0 flex h-20 w-[450px] items-center justify-center border-t">
          <span>이미 회원이세요?</span>
          <button className="mx-4 font-ng-eb text-graphyblue" type="button">
            로그인
          </button>
        </div>
      </div>
    </div>
  );
}

export default Signup;
