import { filterState } from '@/utils/atoms'
import '../../../public/css/toggle-button.css' // 스타일시트 임포트
import { useRecoilState } from 'recoil'
import { useState } from 'react'

export default function ToggleButton() {
  const [isToggled, setIsToggled] = useState(false)
  const [filter, setFilter] = useRecoilState(filterState)

  const handleClick = () => {
    setIsToggled(!isToggled)
    setFilter({
      ...filter,
      isRecruiting: String(!isToggled),
    })
  }

  return (
    <div className="toggle-switch">
      <input
        type="checkbox"
        className="toggle-switch-checkbox"
        id="toggleSwitch"
        checked={isToggled}
        onChange={handleClick}
      />
      <label className="toggle-switch-label" htmlFor="toggleSwitch">
        <span className="toggle-switch-inner" />
        <span className="toggle-switch-switch" />
      </label>
    </div>
  )
}
