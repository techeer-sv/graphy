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
        <p className='mt-16 ml-20 font-lef-b text-4xl text-white border-b-4 pb-3 pr-0 w-fit '>프로젝트 기록과 공유의 공간, Graphy</p>
        <p className='mt-3 ml-20 font-lef text-2xl text-white'>Graphy와 함께 성장해보세요.</p>
      </SwiperSlide>
      <SwiperSlide className='bg-subbanner'></SwiperSlide>
    </Swiper>
  </div>;
}

export default Banner;
