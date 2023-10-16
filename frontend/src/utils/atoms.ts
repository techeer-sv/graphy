import { atom } from 'recoil'
import { recoilPersist } from 'recoil-persist'

const { persistAtom } = recoilPersist()

const titleState = atom<string>({
  key: 'titleState',
  default: '',
  effects_UNSTABLE: [persistAtom],
})

const tldrState = atom<string>({
  key: 'tldrState',
  default: '',
  effects_UNSTABLE: [persistAtom],
})

const thumbnailUrlState = atom<string | null>({
  key: 'thumbnailUrlState',
  default: null,
})

const selectedStackState = atom<string[]>({
  key: 'selectedStackState',
  default: [],
})

const contentsState = atom<string>({
  key: 'contentsState',
  default: '',
})

const autoLoginState = atom<boolean>({
  key: 'autoLoginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
})

const projectIdState = atom<number>({
  key: 'projectIdState',
  default: 0,
  effects_UNSTABLE: [persistAtom],
})

const searchTextState = atom<string>({
  key: 'searchTextState',
  default: '',
})

const usernameState = atom<string>({
  key: 'usernameState',
  default: '',
})

export {
  titleState,
  tldrState,
  thumbnailUrlState,
  selectedStackState,
  contentsState,
  autoLoginState,
  projectIdState,
  searchTextState,
  usernameState,
}
