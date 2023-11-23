import { filterState } from '@/utils/atoms'
import { useEffect, useState } from 'react'
import { useRecoilState } from 'recoil'
import '../../../public/css/toggle-button.css'

export default function ToggleButton() {
  const [filter, setFilter] = useRecoilState(filterState)

  const [isChecked, setIsChecked] = useState(
    filter.find((f) => f.category === 'isRecruiting')?.name === 'true',
  )

  const handleToggle = () => {
    const newIsRecruitingValue = String(!isChecked)

    const updatedFilter = filter.map((f) =>
      f.category === 'isRecruiting' ? { ...f, name: newIsRecruitingValue } : f,
    )

    setFilter(updatedFilter)
    setIsChecked(!isChecked)
  }

  useEffect(() => {
    const recruitingFilter = filter.find((f) => f.category === 'isRecruiting')
    if (recruitingFilter) {
      setIsChecked(recruitingFilter.name === 'true')
    }
  }, [filter])

  return (
    <label className="switch">
      <input type="checkbox" checked={isChecked} onChange={handleToggle} />
      <span className="slider round"> </span>
    </label>
  )
}
