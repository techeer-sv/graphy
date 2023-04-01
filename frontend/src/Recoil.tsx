import { atom } from 'recoil';

const titleState = atom<string>({
  key: 'titleState',
  default: '',
});
const tldrState = atom<string>({
  key: 'tldrState',
  default: '',
});
const selectedStackState = atom<string[]>({
  key: 'selectedStackState',
  default: [],
});
const contentsState = atom({
  key: 'contentsState',
  default: '',
});
const thumbnailUrlState = atom<string | null>({
  key: 'thumbnailUrlState',
  default: null,
});
const projectIdState = atom<number>({
  key: 'projectIdState',
  default: 0,
});

export {
  titleState,
  tldrState,
  selectedStackState,
  contentsState,
  thumbnailUrlState,
  projectIdState,
};
