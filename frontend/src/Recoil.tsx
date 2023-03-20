import { atom, RecoilRoot } from 'recoil';

const titleState = atom({
  key: 'titleState',
  default: '',
});
const tldrState = atom({
  key: 'tldrState',
  default: '',
});
const imageState = atom<File | null>({
  key: 'imageState',
  default: null,
});
const selectedStackState = atom<string[]>({
  key: 'selectedStackState',
  default: [],
});

export { titleState, tldrState, imageState, selectedStackState };
