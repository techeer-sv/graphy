import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import ProjectCard from '../../components/ProjectCard';
import { RecoilRoot, useRecoilValue } from 'recoil';
import { useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';
import { projectIdState } from '../../Recoil';

const onChange = jest.fn();

export const RecoilObserver = ({ node, onChange }: any) => {
  const value = useRecoilValue(node);
  useEffect(() => onChange(value), [onChange, value]);
  return null;
};

describe('ProjectCard', () => {
  let toReadButton: HTMLElement;

  beforeEach(() => {
    const mockData = {
      commentsList: [],
      content: '<p>test</p>',
      createdAt: '2000-00-00T00:00:00.000000',
      description: 'description',
      id: 1,
      projectName: 'projectName',
      techTags: ['React', 'TypeScript'],
      thumbNail: '',
    };
    render(
      <RecoilRoot>
        <BrowserRouter>
          <RecoilObserver node={projectIdState} onChange={onChange} />
          <ProjectCard items={mockData} />
        </BrowserRouter>
      </RecoilRoot>,
    );
    toReadButton = screen.getByRole('button', { name: 'toReadPage' });
  });
  // 테스트 끝난후 온라인 상태로 다시 변경
  afterEach(() => {
    Object.defineProperty(navigator, 'onLine', {
      value: true,
      writable: true,
      configurable: true,
    });
  });

  test('ProjectCard 테스트', () => {
    expect(toReadButton).toBeInTheDocument();

    const thumbNail = screen.getByAltText('프로젝트 이미지');
    expect(thumbNail).toBeInTheDocument();

    const projectName = screen.getByText('projectName');
    expect(projectName).toBeInTheDocument();

    const description = screen.getByText('description');
    expect(description).toBeInTheDocument();

    const techTags = screen.getAllByAltText('stack');
    expect(techTags.length).toBeGreaterThanOrEqual(2);
  });
  test('오프라인 테스트', async () => {
    const mockHandler = jest.fn();
    // mock serviceWorker
    Object.defineProperty(navigator, 'serviceWorker', {
      value: {
        controller: {
          postMessage: jest.fn(),
        },
      },
      writable: true,
      configurable: true,
    });

    // mock 오프라인
    Object.defineProperty(navigator, 'onLine', {
      value: false,
      writable: true,
      configurable: true,
    });

    // mock 메시지 채널
    global.MessageChannel = class {
      port1: any;
      port2: any;

      constructor() {
        this.port1 = {
          onmessage: mockHandler,
          postMessage: jest.fn(),
          onmessageerror: () => {},
          close: () => {},
          start: () => {},
          addEventListener: () => {},
          removeEventListener: () => {},
          dispatchEvent: () => false,
        };
        this.port2 = {
          onmessage: jest.fn(),
          postMessage: jest.fn(),
          onmessageerror: () => {},
          close: () => {},
          start: () => {},
          addEventListener: () => {},
          removeEventListener: () => {},
          dispatchEvent: () => false,
        };
      }
    };
    fireEvent.click(toReadButton);
    await waitFor(() =>
      expect(
        navigator.serviceWorker.controller?.postMessage,
      ).toHaveBeenCalled(),
    );
    mockHandler();
    expect(mockHandler).toHaveBeenCalled();
  });
});
