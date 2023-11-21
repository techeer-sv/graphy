'use client'

import { useInfiniteQuery } from '@tanstack/react-query'
import { useState } from 'react'
import { useRecoilState } from 'recoil'
import Select, { StylesConfig } from 'react-select'
import Image from 'next/image'
import { FiSearch } from 'react-icons/fi'
import { filterState } from '../../utils/atoms'
import x from '../../../public/images/svg/tag_x.svg'

const Position = [
  'FRONTEND',
  'BACKEND',
  'DESIGNER',
  'PM',
  'DEVOPS',
  'AI',
] as const

export const Skill = ['React', 'Spring', 'MongoDB', 'Redis', 'MySQL'] as const

export type PositionType = (typeof Position)[number]
export type SkillType = (typeof Skill)[number]

export type FilterType = {
  category: 'position' | 'skill' | 'keyword'
  name: PositionType | SkillType | string
}

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
    height: '60px',
    border: 'none',
    boxShadow: 'none',
  }),
  option: (provided, state) => ({
    ...provided,
    fontWeight: 'normal',
    color: '#3e3d3d',
    backgroundColor: state.isFocused ? '#e9eaedc5' : 'white', // 마우스 hover 시 배경색 변경
  }),
}

// eslint-disable-next-line react/prop-types
export default function MultipleFilter() {
  const [filter, setFilter] = useRecoilState(filterState)
  const [keyword, setKeyword] = useState('')

  const handleKeywordChange = (e: any) => {
    setKeyword(e.target.value)
  }

  const handleKeywordSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setFilter((prevFilter) => [
      ...prevFilter,
      { category: 'keyword', name: keyword },
    ])
    setKeyword('')
  }

  const removeFilterItem = (itemToRemove: FilterType) => {
    setFilter((prevFilter) =>
      prevFilter.filter((item) => item !== itemToRemove),
    )
  }

  return (
    <div className="w-[900px] h-[120px] mb-16 flex flex-col border-solid  border-gray-400 text-lightgray">
      <div className="w-full flex h-1/2 text-sm">
        <div className="flex-1">
          <Select
            options={positionOptions}
            value={positionOptions.find(
              (option) =>
                option.value ===
                filter
                  .slice()
                  .reverse()
                  .find((f) => f.category === 'position')?.name,
            )}
            onChange={(selectedOption) => {
              setFilter((prevFilter) => [
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
        <div className="flex-1">
          <Select
            options={skillOptions}
            value={skillOptions.find(
              (option) =>
                option.value ===
                filter
                  .slice()
                  .reverse()
                  .find((f) => f.category === 'skill')?.name,
            )}
            onChange={(selectedOption) => {
              setFilter((prevFilter) => [
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
        {/* Search Input Field */}
        <div className="flex-1">
          <form onSubmit={handleKeywordSubmit}>
            <div className="w-full h-full flex justify-center items-start bg-white rounded-lg">
              <input
                onChange={handleKeywordChange}
                value={keyword}
                type="text"
                placeholder="검색어 입력"
                className="w-full h-[60px] px-6 flex justify-between items-center rounded-sm"
              />
            </div>
          </form>
        </div>
      </div>
      <div className="w-full h-1/2 flex items-center bg-slate-100">
        {filter.map((item) => (
          <div
            key={item.name}
            className="flex items-center justify-center h-8 px-4 ml-2.5 rounded-md bg-white text-[15px] font-semibold"
          >
            {item.name}
            <button
              type="button"
              onClick={() => removeFilterItem(item)}
              className="ml-2 text-black hover:text-gray-500"
            >
              <Image src={x} alt="x" className="w-3 h-3" />
            </button>
          </div>
        ))}
      </div>
    </div>
  )
}
