import React, { useState, useEffect } from 'react';
import CommunityCard from '../components/CommunityCard';
import { useCommunity } from '../hooks/useCommunity';


const CommunityLists = () => {

const [ loading, setLoading ] = useState(true);
const [ communities, setCommunities ] = useState([]);
const { getAllCommunities } = useCommunity();

useEffect(() => {
  const fetchCommunities = async () => {
    try{
      const response = await getAllCommunities();
      console.log("Fetched communities: ", response);
      if(!response || response.length === 0){
        setCommunities([]);
      } else {
        setCommunities(response);
      }
    }catch(error){
      console.error("Error fetching communities: ", error);
    } finally { 
    setLoading(false);
    }
  };
  fetchCommunities();
}, []);

if (loading) {
  return (
    <div className="flex items-center justify-center min-h-screen">
      <div className="text-xl">Loading communities...</div>
    </div>
  );
}

  return (
    <div className='min-h-screen flex flex-col'>
      <div className='item-center justify-center'>
        <h1 className='text-3xl md:text-4xl font-bold text-gray-800 text-center my-8'>Explore Communities</h1>
      </div>
      <div className="px-8 w-full flex justify-center">
        <div style={{display:'flex', flexWrap:'wrap', gap:'50px', justifyContent:'center'}}>
          {communities.map((community) => (
            <CommunityCard key={community.communityId} community={community} />
          ))}

        </div>
      </div>
    </div>
  )
}

export default CommunityLists;