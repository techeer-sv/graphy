import { useEffect, useState } from 'react'
import { useRecoilState } from 'recoil'
import { recruitfilterState } from '../../../utils/atoms'
import '../../../../public/css/toggle-button.css'

export default function ToggleButton() {
  const [recruitFilter, setRecruitFilter] = useRecoilState(recruitfilterState)
  const [recruiting, setRecruiting] = useState<boolean>(false)

  const handleToggle = () => {
    setRecruiting(!recruiting)
    setRecruitFilter(!recruitFilter)
  }

  useEffect(() => {
    // if (recruitFilter === true) {
    //   handleToggle()
    // }
  }, [])

  return (
    <label className="switch">
      <input type="checkbox" checked={recruiting} onChange={handleToggle} />
      <span className="slider round"> </span>
    </label>
  )
}
