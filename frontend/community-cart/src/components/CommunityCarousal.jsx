import React, { useEffect, useState, useRef } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import { EffectCoverflow, Pagination, Navigation } from 'swiper/modules';
import CommunityCard from './CommunityCard';
import { useUser } from '../hooks/useUser';

const CommunityCarousel = () => {
    const userId = localStorage.getItem("userId");
    const { getUserCommunities } = useUser();

    const [communities, setCommunities] = useState([]);
    const [loading, setLoading] = useState(true);
    const [fetched, setFetched] = useState(false);
    const swiperRef = useRef(null);

    // Fetch communities once
    useEffect(() => {
        if (!userId || fetched) return;

        const fetchCommunities = async () => {
            const communityList = await getUserCommunities(userId);
            setCommunities(communityList);
            setLoading(false);
            setFetched(true);
        };

        fetchCommunities();
    }, [userId, fetched, getUserCommunities]);

    // Update swiper after slides are loaded
    useEffect(() => {
        if (!loading && swiperRef.current) {
            setTimeout(() => swiperRef.current.update(), 0);
        }
    }, [loading, communities]);

    if (loading) {
        return <div>Loading communities...</div>;
    }

    return (
        <div className="w-full h-full flex flex-col items-center justify-center">
            <Swiper
                onSwiper={(swiper) => (swiperRef.current = swiper)}
                effect="coverflow"
                grabCursor={true}
                centeredSlides={true}
                loop={true}
                loopedSlides={communities.length} // ensures seamless infinite loop
                slidesPerView="auto"
                spaceBetween={50}
                coverflowEffect={{
                    rotate: 0,
                    stretch: 0,
                    depth: 150,
                    slideShadows: true,
                    modifier: 2.5
                }}
                modules={[EffectCoverflow, Pagination, Navigation]}
                pagination={{ el: '.swiper-pagination', clickable: true }}
                navigation={{ prevEl: '.swiper-button-prev', nextEl: '.swiper-button-next', clickable: true }}
                className="swiper_container w-full h-full"
            >
                {communities.map((community) => (
                    <SwiperSlide
                        key={community.communityId}
                        className="flex justify-center items-center w-auto h-auto"
                        style={{ width: '300px' }}
                    >
                        <CommunityCard
                            community={community}
                            mode="member"
                            refetchCommunities={() => setFetched(false)}
                        />
                    </SwiperSlide>
                ))}

                <div className="slider-controller">
                    <div className="swiper-button-prev slider-arrow">
                        <ion-icon name="arrow-back-outline"></ion-icon>
                    </div>
                    <div className="swiper-button-next slider-arrow">
                        <ion-icon name="arrow-forward-outline"></ion-icon>
                    </div>
                    <div className="swiper-pagination"></div>
                </div>
            </Swiper>
        </div>
    );
};

export default CommunityCarousel;
