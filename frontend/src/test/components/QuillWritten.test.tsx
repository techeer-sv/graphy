import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';
import { MutableSnapshot, RecoilRoot } from 'recoil';

import QuillWritten from '../../components/QuillWritten';
import { contentsState } from '../../Recoil';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';

describe('QuillWritten', () => {
  test('noContents 테스트', () => {
    render(
      <RecoilRoot>
        <RecoilObserver node={contentsState} onChange={onChange} />
        <QuillWritten />
      </RecoilRoot>,
    );
    const noContents = screen.getByText('게시글을 불러오는 중입니다...');
    expect(noContents).toBeInTheDocument();
  });
  test('Contents 테스트', () => {
    render(
      <RecoilRoot
        initializeState={(snapshot: MutableSnapshot) => {
          snapshot.set(contentsState, '테스트 데이터');
        }}
      >
        <RecoilObserver node={contentsState} onChange={onChange} />
        <QuillWritten />
      </RecoilRoot>,
    );
    const Contents = screen.getByText('테스트 데이터');
    expect(Contents).toBeInTheDocument();
  });
});
