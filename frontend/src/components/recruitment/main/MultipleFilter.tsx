'use client'

import { useEffect, useState } from 'react'
import { useRecoilState } from 'recoil'
import Select, { StylesConfig } from 'react-select'
import Image from 'next/image'
import { multiplefilterState, keywordfilterState } from '../../../utils/atoms'
import x from '../../../../public/images/svg/tag_x.svg'
import {
  Position,
  Skill,
  PositionType,
  SkillType,
  MultipleFilterType,
} from '../../../utils/types'

const positionOptions = Object.values(Position).map((position) => ({
  value: position,
  label: position,
}))

const skillOptions = Object.values(Skill).map((skill) => ({
  value: skill,
  label: skill,
}))

type OptionType = {
  value: string
  label: string
}

const styles: StylesConfig<OptionType, false> = {
  control: (provided) => ({
    ...provided,
    height: '40px',
    border: 'none',
    boxShadow: 'none',
    flex: 1,
    alignItems: 'center',
  }),
  option: (provided, state) => ({
    ...provided,
    fontWeight: 'normal',
    color: '#3e3d3d',
    backgroundColor: state.isFocused ? '#e9eaedc5' : 'white', // 마우스 hover 시 배경색 변경
  }),
}

export default function MultipleFilter() {
  const [multipleFilter, setMultipleFilter] =
    useRecoilState(multiplefilterState)
  const [, setKeywordFilter] = useRecoilState(keywordfilterState)
  const [keyword, setKeyword] = useState('')

  const handleKeywordChange = (e: any) => {
    setKeyword(e.target.value)
  }

  const handleKeywordSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setKeywordFilter(keyword)
    sessionStorage.setItem('keywordFilter', keyword)
  }

  const removeFilterItem = (itemToRemove: MultipleFilterType) => {
    setMultipleFilter((prevFilter) =>
      prevFilter.filter((item) => item !== itemToRemove),
    )
  }

  useEffect(() => {
    const searchText = sessionStorage.getItem('keywordFilter')
    if (searchText) {
      setKeyword(searchText)
    }
  }, [])

  return (
    <div className="w-[900px] h-[80px] mb-8 flex flex-col border-solid  border-gray-400 text-lightgray">
      <div className="flex w-full text-sm h-1/2">
        {/* 포지션 선택 드롭다운 메뉴 */}
        <div className="flex-1">
          <Select
            options={positionOptions}
            value={positionOptions.find(
              (option) =>
                option.value ===
                multipleFilter
                  .slice()
                  .reverse()
                  .find((f) => f.category === 'position')?.name,
            )}
            onChange={(selectedOption) => {
              setMultipleFilter((prevFilter) => [
                ...prevFilter,
                {
                  category: 'position',
                  name: selectedOption?.value as PositionType,
                },
              ])
            }}
            placeholder="포지션을 선택해주세요"
            styles={styles}
            components={{
              IndicatorSeparator: () => null,
            }}
          />
        </div>
        {/* 스킬 선택 드롭다운 메뉴 */}
        <div className="flex-1">
          <Select
            options={skillOptions}
            value={skillOptions.find(
              (option) =>
                option.value ===
                multipleFilter
                  .slice()
                  .reverse()
                  .find((f) => f.category === 'skill')?.name,
            )}
            onChange={(selectedOption) => {
              setMultipleFilter((prevFilter) => [
                ...prevFilter,
                { category: 'skill', name: selectedOption?.value as SkillType },
              ])
            }}
            placeholder="스킬을 선택해주세요"
            styles={styles}
            components={{
              IndicatorSeparator: () => null,
            }}
          />
        </div>
        {/* 검색창 */}
        <div className="flex-1">
          <form onSubmit={handleKeywordSubmit} className="w-full">
            <div className="relative flex items-center justify-center w-full h-full bg-white rounded-lg">
              <input
                onChange={handleKeywordChange}
                value={keyword}
                type="text"
                placeholder="검색어 입력"
                className="w-full h-[40px] ml-[0.5px] px-4 flex justify-between items-center rounded-sm"
              />
              <button
                type="button"
                onClick={() => {
                  sessionStorage.removeItem('keywordFilter')
                  setKeyword('')
                  setKeywordFilter('')
                }}
                className="absolute mr-2 text-black right-2"
              >
                <Image src={x} alt="x" className="w-2 h-2" />
              </button>
            </div>
          </form>
        </div>
      </div>
      {/* 필터 태그 항목들 */}
      <div className="flex items-center w-full h-1/2 bg-slate-100">
        {multipleFilter.map((item) => (
          <div
            key={item.name}
            className="flex items-center justify-center h-[25px] px-2 ml-2.5 rounded-md bg-white text-[10px]"
          >
            {item.name}
            <button
              type="button"
              onClick={() => removeFilterItem(item)}
              className="ml-2 text-black"
            >
              <Image src={x} alt="x" className="w-1.5 h-1.5" />
            </button>
          </div>
        ))}
      </div>
    </div>
  )
}
