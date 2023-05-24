import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';

import Banner from '../../components/Banner';

describe('Banner', () => {
  test('Banner 테스트', () => {
    render(<Banner />);

    // Banner 컴포넌트가 정상적으로 렌더링되었는지 확인
    const bannerElement = screen.getByTestId('banner');
    expect(bannerElement).toBeInTheDocument();

    // 각 SwiperSlide 내부의 텍스트 내용이 정상적으로 렌더링되었는지 확인
    const slideText1 = screen.getByText('프로젝트 기록과 공유의 공간, Graphy');
    const slideText2 = screen.getByText('Graphy와 함께 성장해보세요.');
    const slideText3 = screen.getByText('프로젝트 공유하러 출발!');
    const slideText4 = screen.getByText(
      'Graphy에서 누구나 프로젝트를 작성하고 공유할 수 있습니다. 이제 출발하세요!',
    );
    const lottieAnimation = screen.getByTestId('lottie-animation');

    expect(slideText1).toBeInTheDocument();
    expect(slideText2).toBeInTheDocument();
    expect(slideText3).toBeInTheDocument();
    expect(slideText4).toBeInTheDocument();
    expect(lottieAnimation).toBeInTheDocument();
  });
});
