import React, { useState, useEffect } from 'react';
import CommunityCard from '../components/CommunityCard';
import { useCommunity } from '../hooks/useCommunity';

const CommunityLists = () => {
  const [loading, setLoading] = useState(true);
  const [communities, setCommunities] = useState([]);
  const { getExplorecommunities } = useCommunity();
  const storedUserId = localStorage.getItem('userId');

  const exploreCommunities = async () => {
    try{
      setLoading(true);
      const response = await getExplorecommunities(storedUserId);
      if(response && response.length > 0){
        setCommunities(response);
      }else{
        setCommunities([]);
      }
    } catch(error){
      setCommunities([]);
    }finally{
      setLoading(false);
    }
  }

  useEffect(() => {
    exploreCommunities();
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-xl">Loading communities...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-b from-pink-400 via-purple-500 to-indigo-400 hover:from-indigo-400 via-purple-500 to-pink-400">
      <div className="flex flex-col h-1/4 justify-left px-10 mt-6 mb-8">
        <h1 className="text-7xl md:text-4xl font-bold text-white mt-16">
          Explore New Communities
        </h1>
        <p className="text-sm text-white">Come find your new community explore and join the new community</p>
      </div>
      <div className="px-10 w-full flex justify-center mt-14">
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '70px', justifyContent: 'center' }}>
          {communities.map((community) => (
            <CommunityCard
              key={community.communityId}
              community={community}
              mode="explore"
              refetchCommunities={exploreCommunities}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default CommunityLists;
