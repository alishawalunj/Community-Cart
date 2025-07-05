import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import { EffectCoverflow, Pagination, Navigation } from 'swiper/modules';
import CommunityCard from './CommunityCard';
import communityList from './communityList';
// import { getAllUsers } from '../hooks/useUser';
// import { getAllCommunities } from '../hooks/useCommunity';

const CommunityCarousal = () => {
  return (
      <div className="w-full h-full flex flex-col items-center justify-center">
        <h1 className="heading">Your Alisha Communties</h1>
        <Swiper effect={'coverflow'} grabCursor= {true} centeredSlides={true} loop={true} slidesPerView={'auto'} spaceBetween={50} coverflowEffect={{ rotate:0,stretch:0,depth:150,slideShadows:true,modifier:2.5}}  
        modules={[EffectCoverflow, Pagination, Navigation]}
        pagination ={{ el: '.swiper-pagination',clickable: true}}
        navigation ={{ prevEl: '.swiper-button-prev',nextEl: '.swiper-button-next',clickable:true}}
        className="swiper_container w-full h-full" >
            {communityList.map((community) => (
                <SwiperSlide key={community.id} className="flex justify-center items-center w-auto h-auto" style={{width: '300px'}}>
                    <CommunityCard community={community} />
                </SwiperSlide>
            ))}
            {/* <SwiperSlide><CommunityCard/></SwiperSlide> */}
            <div className="slider-controller">
            <div className="swiper-button-prev slider-arrow">
                <ion-icon name="arrow-back-outline"></ion-icon>
            </div>
            <div className="swiper-button-next slider-arrow">
                <ion-icon name="arrow-forward-outline"></ion-icon>
            </div>
            <div className="swiper-pagination">
            </div>
        </div>
        </Swiper>
        
      </div>

  )
}

export default CommunityCarousal;
