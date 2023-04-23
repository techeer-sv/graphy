import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import mainbanner from '../assets/image/mainbanner.svg';
import subbanner from '../assets/image/subbanner.svg'

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
      <p className='mt-20 ml-20 font-lef-b text-4xl text-white'>프로젝트 기록과 공유의 공간, Graphy</p>
      <p className='ml-20 font-lef text-2xl text-white'>Graphy와 함께 성장해보세요.</p>
      </SwiperSlide>
      <SwiperSlide className='bg-subbanner'></SwiperSlide>
    </Swiper>
  </div>;
}

export default Banner;
