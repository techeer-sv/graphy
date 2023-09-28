import { atom } from 'recoil'
import { recoilPersist } from 'recoil-persist'

const { persistAtom } = recoilPersist()

const projectIdState = atom<number>({
  key: 'projectIdState',
  default: 0,
  effects_UNSTABLE: [persistAtom],
})

const searchTextState = atom<string>({
  key: 'searchTextState',
  default: '',
})

export { projectIdState, searchTextState }
