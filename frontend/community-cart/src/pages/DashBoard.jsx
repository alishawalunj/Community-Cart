import React , {useRef} from 'react'
import Options from '../components/Options'
import Marketplace from '../components/Marketplace'
import Community from '../components/Community'


const DashBoard = () => {

    const marketplaceRef = useRef(null);
    const communityRef = useRef(null);
    const optionsRef = useRef(null);

    const handleScrollOptionsToMarketplace = () =>{
        marketplaceRef.current.scrollIntoView({behavior: 'smooth'});

    };

    const handleScrollOptionsToCommunity = () => {
        communityRef.current.scrollIntoView({behavior: 'smooth'});
    };

    const handleScrollToTop = () => {
        optionsRef.current.scrollIntoView({behavior: 'smooth'});
    };


  return (
    <div>
      <div ref={optionsRef}>
        <Options onMarketplaceClick ={handleScrollOptionsToMarketplace} onCommunityClick={handleScrollOptionsToCommunity}/>
      </div>
      <div>
        <div ref={communityRef}>
            <Community onBackClick={handleScrollToTop}/>
        </div>
      </div>
      <div>
        <div ref={marketplaceRef}>
            <Marketplace onBackClick={handleScrollToTop}/>
        </div>
      </div>
      
    </div>
  )
}

export default DashBoard