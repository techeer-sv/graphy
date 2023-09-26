import { atom } from 'recoil'
import { recoilPersist } from 'recoil-persist'

const { persistAtom } = recoilPersist()

const autoLoginState = atom({
  key: 'autoLoginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
})

const exampleState = atom({
  key: 'exampleState',
  default: 'example',
})

export { autoLoginState, exampleState }
