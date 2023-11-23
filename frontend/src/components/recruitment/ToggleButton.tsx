import { filterState } from '@/utils/atoms'
import { useEffect, useState } from 'react'
import { useRecoilState } from 'recoil'
import '../../../public/css/toggle-button.css'

export default function ToggleButton() {
  const [filter, setFilter] = useRecoilState(filterState)
  const [recruiting, setRecruiting] = useState(false)

  const handleToggle = () => {
    const recuitingFilter = filter.find((f) => f.category === 'isRecruiting')
    const updatedFilter = filter.map((f) =>
      f === recuitingFilter ? { ...f, name: String(!recruiting) } : f,
    )
    setFilter(updatedFilter)
    setRecruiting(!recruiting)
  }

  useEffect(() => {
    if (filter.find((f) => f.category === 'isRecruiting')?.name === 'true') {
      handleToggle()
    }
  }, [])

  return (
    <label className="switch">
      <input type="checkbox" checked={recruiting} onChange={handleToggle} />
      <span className="slider round"> </span>
    </label>
  )
}
