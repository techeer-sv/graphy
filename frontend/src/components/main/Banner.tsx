import Lottie from 'lottie-react'
import { Pagination, Autoplay } from 'swiper/modules'
import { Swiper, SwiperSlide } from 'swiper/react'
import 'swiper/css'
import 'swiper/scss/pagination'
import lotties from '../../utils/lotties'

export default function Banner() {
  return (
    <div className="pt-16 pb-9">
      <Swiper
        modules={[Autoplay, Pagination]}
        spaceBetween={50}
        slidesPerView={1}
        pagination={{ clickable: true }}
        className="bg-subbanner h-40 sm:h-56 "
        data-testid="banner"
        autoplay={{ delay: 4000, disableOnInteraction: false }}
        loop
      >
        <SwiperSlide className="bg-gradient-to-r from-mainbannerleft to-mainbannerright">
          <div className="mx-auto ml-6 whitespace-nowrap sm:ml-20 ">
            <p className="text-md mt-10 w-fit border-b pb-3 pr-0 font-lef-b text-white sm:mt-16 sm:border-b-4 sm:text-3xl md:text-4xl ">
              프로젝트 기록과 공유의 공간, Graphy
            </p>
            <p className="mt-3 font-lef text-sm text-white sm:text-xl md:text-2xl">
              Graphy와 함께 성장해보세요.
            </p>
          </div>
        </SwiperSlide>
        <SwiperSlide className="bg-subbanner">
          <div className="flex">
            <div className="mx-auto ml-6 sm:ml-20 ">
              <p className="mt-8 font-lef-b text-xl text-zinc-800 sm:mt-14 lg:text-3xl xl:text-4xl ">
                프로젝트 공유하러 출발!
              </p>
              <p className="sm:text-md mr-10 mt-6 shrink-0 font-lef text-sm text-stone-800 md:text-lg lg:text-xl xl:text-2xl">
                Graphy에서 누구나 프로젝트를 작성하고 공유할 수 있습니다. 이제
                출발하세요!
              </p>
            </div>
            <Lottie
              className="mx-auto hidden bg-subbanner pb-20 sm:block sm:h-80 sm:min-h-56 sm:w-80"
              animationData={lotties}
              data-testid="lottie-animation"
            />
          </div>
        </SwiperSlide>
      </Swiper>
    </div>
  )
}
