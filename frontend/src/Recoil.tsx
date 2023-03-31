import { atom } from 'recoil';

const selectedStackState = atom<string[]>({
  key: 'selectedStackState',
  default: [],
});
const writeContentsState = atom({
  key: 'quillContentsState',
  default: '',
});
const readContentsState = atom({
  key: 'quillContentsState',
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
