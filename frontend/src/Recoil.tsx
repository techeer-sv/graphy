import { atom, RecoilRoot } from 'recoil';

const titleState = atom({
  key: 'titleState',
  default: '제목',
});
const tldrState = atom({
  key: 'tldrState',
  default: '한 줄 소개',
});
const imageState = atom<File | null>({
  key: 'imageState',
  default: null,
});
const selectedStackState = atom<string[]>({
  key: 'selectedStackState',
  default: [],
});
const quillContentsState = atom({
  key: 'quillContentsState',
  default: '',
});
const thumbnailUrlState = atom<string | null>({
  key: 'thumbnailUrlState',
  default: null,
});

export {
  titleState,
  tldrState,
  imageState,
  selectedStackState,
  quillContentsState,
  thumbnailUrlState,
};
