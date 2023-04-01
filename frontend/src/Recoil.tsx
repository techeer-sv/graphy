import { atom } from 'recoil';

const selectedStackState = atom<string[]>({
  key: 'selectedStackState',
  default: [],
});
const writeContentsState = atom({
  key: 'writeContentsState',
  default: '',
});
const readContentsState = atom({
  key: 'readContentsState',
  default: '',
});
const thumbnailUrlState = atom<string | null>({
  key: 'thumbnailUrlState',
  default: null,
});

export {
  selectedStackState,
  writeContentsState,
  readContentsState,
  thumbnailUrlState,
};
