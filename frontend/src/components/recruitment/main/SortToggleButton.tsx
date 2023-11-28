import { useRecoilState } from 'recoil'
import { directionState } from '../../../utils/atoms'

export default function SortToggleButton() {
  const [direction, setDirection] = useRecoilState(directionState)

  return (
    <button
      type="button"
      onClick={() => {
        if (direction === 'ASC') {
          setDirection('DESC')
        } else {
          setDirection('ASC')
        }
      }}
    >
      {direction === 'ASC' ? '오래된 순 ▲' : '최신 순 ▼'}
    </button>
  )
}
