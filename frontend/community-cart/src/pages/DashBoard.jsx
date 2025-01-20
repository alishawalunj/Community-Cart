import React , {useRef} from 'react'
import Options from '../components/Options'
import Marketplace from '../components/Marketplace'
import Community from '../components/Community'


const DashBoard = () => {

    const marketplaceRef = useRef(null);
    const communityRef = useRef(null);


    const handleScrollOptionsToMarketplace = () =>{
        marketplaceRef.current.scrollIntoView({ behavior: 'smooth' });

    };

    const handleScrollOptionsToCommunity = () => {
        communityRef.current.scrollIntoView({ behavior: 'smooth' });
    }


  return (
    <div>
      <div>
        <Options onMarketplaceClick ={handleScrollOptionsToMarketplace} onCommunityClick={handleScrollOptionsToCommunity}/>
      </div>
      <div>
        <div ref={communityRef}>
            <Community/>
        </div>
      </div>
      <div>
        <div ref={marketplaceRef}>
            <Marketplace/>
        </div>
      </div>
      
    </div>
  )
}

export default DashBoard