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

const contentsState = atom<string>({
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

const refreshState = atom<boolean>({
  key: 'refreshState',
  default: false,
});

const searchTextState = atom<string>({
  key: 'searchTextState',
  default: '',
});

const persistTokenState = atom<string | null>({
  key: 'persistTokenState',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

const autoLoginState = atom<boolean>({
  key: 'autoLoginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
});

const techStacksState = atom<string[]>({
  key: 'techStacksState',
  default: [],
});

const topicState = atom<string>({
  key: 'topicState',
  default: '',
});

const featuresState = atom<string[]>({
  key: 'featuresState',
  default: [],
});

const plansState = atom<string[]>({
  key: 'plansState',
  default: [],
});

const gptLoadingState = atom<boolean>({
  key: 'gptLoadingState',
  default: false,
});

const statusOpenState = atom<boolean>({
  key: 'statusOpenState',
  default: false,
});

const modalContentState = atom<string>({
  key: 'modalContentState',
  default: '',
});

const nicknameState = atom<string>({
  key: 'nicknameState',
  default: '',
});

const projectDataState = atom<string>({
  key: 'projectDataState',
  default: '',
});

export {
  titleState,
  tldrState,
  selectedStackState,
  contentsState,
  thumbnailUrlState,
  projectIdState,
  refreshState,
  searchTextState,
  persistTokenState,
  autoLoginState,
  techStacksState,
  topicState,
  featuresState,
  plansState,
  gptLoadingState,
  statusOpenState,
  modalContentState,
  nicknameState,
  projectDataState,
};
