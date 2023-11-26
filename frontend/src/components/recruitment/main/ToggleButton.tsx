import { useRecoilState } from 'recoil'
import { recruitfilterState } from '../../../utils/atoms'
import '../../../../public/css/toggle-button.css'

export default function ToggleButton() {
  const [recruitFilter, setRecruitFilter] = useRecoilState(recruitfilterState)

  const handleToggle = () => {
    setRecruitFilter(!recruitFilter)
  }

  return (
    <label className="switch">
      <input type="checkbox" checked={recruitFilter} onChange={handleToggle} />
      <span className="slider round"> </span>
    </label>
  )
}
