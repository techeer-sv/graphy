import { atom } from 'recoil'

const searchTextState = atom<string>({
  key: 'searchTextState',
  default: '',
})

export default searchTextState
