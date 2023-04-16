import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

const titleState = atom<string>({
  key: 'titleState',
  default: '',
  effects_UNSTABLE: [persistAtom],
});
const tldrState = atom<string>({
  key: 'tldrState',
  default: '',
  effects_UNSTABLE: [persistAtom],
});
const selectedStackState = atom<string[]>({
  key: 'selectedStackState',
  default: [],
  effects_UNSTABLE: [persistAtom],
});
const contentsState = atom({
  key: 'contentsState',
  default: '',
  effects_UNSTABLE: [persistAtom],
});
const thumbnailUrlState = atom<string | null>({
  key: 'thumbnailUrlState',
  default: null,
});
const projectIdState = atom<number>({
  key: 'projectIdState',
  default: 0,
  effects_UNSTABLE: [persistAtom],
});
const writeReplyState = atom({
  key: 'writeReplyState',
  default: '',
});
const writeReReplyState = atom({
  key: 'writeReReplyState',
  default: '',
});
const refreshState = atom({
  key: 'refreshState',
  default: false,
});
const searchTextState = atom<string>({
  key: 'searchTextState',
  default: '',
});

export {
  titleState,
  tldrState,
  selectedStackState,
  contentsState,
  thumbnailUrlState,
  projectIdState,
  writeReplyState,
  writeReReplyState,
  refreshState,
  searchTextState,
};
