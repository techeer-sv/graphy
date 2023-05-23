import '@testing-library/jest-dom';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import ImageUploader from '../../components/ImageUploader';
import { RecoilRoot } from 'recoil';
import { thumbnailUrlState } from '../../Recoil';
import { onChange, RecoilObserver } from '../jest/RecoilObserver';

jest.mock('react-aws-s3-typescript', () => ({
  __esModule: true,
  default: jest.fn().mockImplementation(() => ({
    uploadFile: jest.fn().mockResolvedValue({ location: 'mock-image-url' }),
  })),
}));

beforeEach(() => {
  window.alert = jest.fn();
});

test('ImageUploader 테스트', async () => {
  render(
    <RecoilRoot>
      <RecoilObserver node={thumbnailUrlState} onChange={onChange} />
      <ImageUploader />
    </RecoilRoot>,
  );

  const file = new File(['(⌐□_□)'], 'chucknorris.png', { type: 'image/png' });

  const fileInput = screen.getByTestId('image');
  expect(fileInput).toBeInTheDocument();

  fireEvent.change(fileInput, { target: { files: [file] } });

  await waitFor(() => {
    expect(onChange).toHaveBeenCalledWith(null);
    expect(onChange).toHaveBeenCalledWith('mock-image-url');
    expect(onChange).toHaveBeenCalledTimes(2);
  });
});
