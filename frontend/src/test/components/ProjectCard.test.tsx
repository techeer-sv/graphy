import '@testing-library/jest-dom';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import ProjectCard from '../../components/ProjectCard';
import { projectIdState } from '../../Recoil';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';

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

describe('ProjectCard', () => {
  // 테스트 끝난후 온라인 상태로 다시 변경
  afterEach(() => {
    Object.defineProperty(navigator, 'onLine', {
      value: true,
      writable: true,
      configurable: true,
    });
  });

  test('ProjectCard 테스트', () => {
    render(
      <RecoilRoot>
        <BrowserRouter>
          <RecoilObserver<number> node={projectIdState} onchange={onChange} />
          <ProjectCard items={mockData} />
        </BrowserRouter>
      </RecoilRoot>,
    );
    const toReadButton = screen.getByRole('button', { name: 'toReadPage' });
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
  test('Offline 테스트', async () => {
    render(
      <RecoilRoot>
        <BrowserRouter>
          <RecoilObserver<number> node={projectIdState} onchange={onChange} />
          <ProjectCard items={mockData} />
        </BrowserRouter>
      </RecoilRoot>,
    );
    const toReadButton = screen.getByRole('button', { name: 'toReadPage' });
    expect(toReadButton).toBeInTheDocument();

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

    interface Port {
      onmessage: jest.Mock;
      postMessage: jest.Mock;
      onmessageerror: () => void;
      close: () => void;
      start: () => void;
      addEventListener: () => void;
      removeEventListener: () => void;
      dispatchEvent: () => boolean;
    }

    // mock 메시지 채널
    global.MessageChannel = class {
      port1: Port;

      port2: Port;

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
