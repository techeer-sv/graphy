import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import mainbanner from '../assets/image/mainbanner.svg';
import subbanner from '../assets/image/subbanner.svg'
import Lottie from 'lottie-react';
import { lottie } from '../assets/lottie';

function Banner() {

  return <div>
<Swiper
      spaceBetween={50}
      slidesPerView={1}
      onSlideChange={() => console.log('slide change')}
      onSwiper={(swiper) => console.log(swiper)}
      className="bg-subbanner h-56 "
    >
      <SwiperSlide className="bg-gradient-to-r from-mainbannerleft to-mainbannerright h-56 ">
        <p className='mt-16 ml-20 font-lef-b text-4xl text-white border-b-4 pb-3 pr-0 w-fit '>프로젝트 기록과 공유의 공간, Graphy</p>
        <p className='mt-3 ml-20 font-lef text-2xl text-white'>Graphy와 함께 성장해보세요.</p>
      </SwiperSlide>
      <SwiperSlide className='bg-subbanner flex'>
        <div><p className='mt-16 ml-20 font-lef-b text-4xl text-zinc-800 '>프로젝트 공유하러 출발!</p>
        <p className='mt-6 ml-20 font-lef text-2xl text-stone-800'>Graphy에서 누구나 쉽게 프로젝트를 작성하고 공유할 수 있습니다. 이제 출발하세요!</p>
        </div>
        <Lottie className='sm:w-72 sm:h-72 h-0 w-0 bg-subbanner mt-8 mr-28 mx-auto' animationData={lottie} />
      </SwiperSlide>
    </Swiper>
  </div>;
}

export default Banner;
