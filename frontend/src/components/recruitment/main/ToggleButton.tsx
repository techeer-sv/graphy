import { useRecoilState } from 'recoil'
import { recruitfilterState } from '../../../utils/atoms'

export default function ToggleButton() {
  const [recruitFilter, setRecruitFilter] = useRecoilState(recruitfilterState)

  const handleToggle = () => {
    setRecruitFilter(!recruitFilter)
  }

  return (
    <label className="relative inline-flex items-center cursor-pointer">
      <input
        type="checkbox"
        checked={recruitFilter}
        onChange={handleToggle}
        className="sr-only peer"
      />
      <div className="w-12 h-[18px] bg-gray-200 rounded-full peer-checked:after:translate-x-7 after:absolute after:top-[2px] after:start-[3px] after:bg-white after:rounded-full after:h-3.5 after:w-3.5 after:transition-all peer-checked:bg-graphyblue">
        {' '}
      </div>
    </label>
  )
}
