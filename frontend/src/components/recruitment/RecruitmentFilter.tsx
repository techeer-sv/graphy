'use client'

import { useInfiniteQuery } from '@tanstack/react-query'
import Select, { StylesConfig } from 'react-select'
import { useRecoilState } from 'recoil'
import { FiSearch } from 'react-icons/fi'

import { selectedPositionsState, selectedSkillsState } from '../../utils/atoms'
import { PositionType } from './RecruitmentCard'
import { use, useEffect, useState } from 'react'

export enum SkillType {
  React = 'React',
  Spring = 'Spring',
  MongoDB = 'MongoDB',
  Redis = 'Redis',
  MySQL = 'MySQL',
}

export type FilterType = {
  position: PositionType | ''
  skill: SkillType | ''
  searchQuery: string
}

const positionOptions = Object.values(PositionType).map((position) => ({
  value: position,
  label: position,
}))

const skillOptions = Object.values(SkillType).map((skill) => ({
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
    width: '100%',
    padding: '13.5px',
  }),
  option: (provided, state) => ({
    ...provided,
    fontWeight: 'normal',
    color: '#3e3d3d',
    backgroundColor: state.isFocused ? '#e9eaedc5' : 'white', // 마우스 hover 시 배경색 변경
  }),
}

// eslint-disable-next-line react/prop-types
export default function RecruitmentFilter() {
  const [selectedPositions, setSelectedPositions] = useRecoilState(
    selectedPositionsState,
  )
  const [selectedSkills, setSelectedSkills] =
    useRecoilState(selectedSkillsState)

  const [currentSelected, setCurrentSelected] = useState<string[]>([])

  useEffect(() => {
    console.log(`current: ${currentSelected}`)
  }, [selectedPositions, selectedSkills, currentSelected])

  return (
    <div className="w-[1100px] h-[140px] mb-16 flex flex-col border-solid  border-gray-400 text-lightgray">
      <div className="w-full flex items-stretch h-1/2 text-lg border-solid border-b-[1.5px]">
        <div className="flex-1">
          <Select
            options={positionOptions}
            value={positionOptions.find(
              (option) =>
                option.value ===
                selectedPositions[selectedPositions.length - 1],
            )}
            onChange={(selectedOption) => {
              console.log(selectedOption?.value) // 선택된 옵션의 값을 콘솔에 출력
              setSelectedPositions((prev) => [
                ...prev,
                selectedOption?.value as PositionType,
              ])
              setCurrentSelected((prev) => [
                ...prev,
                selectedOption?.value as string,
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
                option.value === selectedSkills[selectedSkills.length - 1],
            )}
            onChange={(selectedOption) => {
              console.log(selectedOption?.value) // 선택된 옵션의 값을 콘솔에 출력
              setSelectedSkills((prev) => [
                ...prev,
                selectedOption?.value as SkillType,
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
        <div className="flex-1 h-full">
          <div className="w-full h-full flex justify-center items-center bg-white">
            <input
              onChange={(e) => {}}
              type="text"
              placeholder="검색어 입력"
              className="w-full h-full py-4 px-6 flex justify-between items-center"
            />
            <FiSearch
              style={{
                position: 'absolute',
                width: '17px',
                top: '10px',
                right: '12px',
                margin: '0',
              }}
            />
          </div>
        </div>
      </div>
      <div className="w-full h-1/2 flex items-center bg-neutral-300">
        {/* {selectedPositions.map((position) => (
          <span className="bg-red-300 mr-2">{position}</span>
        ))}
        {selectedSkills.map((skill) => (
          <span className="bg-blue-300 mr-2">{skill}</span>
        ))} */}
        {currentSelected.map((selected, index) => (
          <span key={index} className="bg-red-300 mr-2">
            {selected}
          </span>
        ))}
      </div>
    </div>
  )
}
