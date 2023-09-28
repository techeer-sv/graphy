import { atom } from 'recoil'
import { recoilPersist } from 'recoil-persist'

const { persistAtom } = recoilPersist()

const autoLoginState = atom({
  key: 'autoLoginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
})

const selectedStackState = atom<string[]>({
  key: 'selectedStackState',
  default: []
})

export { autoLoginState, selectedStackState }
