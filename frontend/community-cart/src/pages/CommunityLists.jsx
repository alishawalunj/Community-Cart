import React, { useState, useEffect } from 'react';
import CommunityCard from '../components/CommunityCard';
import { useCommunity } from '../hooks/useCommunity';

const CommunityLists = () => {
  const [loading, setLoading] = useState(true);
  const [communities, setCommunities] = useState([]);
  const { getAllCommunities } = useCommunity();
  const storedUserId = localStorage.getItem('userId');

  const fetchCommunities = async () => {
    try {
      setLoading(true);
      const response = await getAllCommunities();
      if (response && response.length > 0) {
        const filtered = response.filter(
          (community) => community?.owner?.userId !== Number(storedUserId)
        );
        setCommunities(filtered);
      } else {
        setCommunities([]);
      }
      console.log("Fetched communities:", response);
    } catch (error) {
      console.error("Error fetching communities: ", error);
      setCommunities([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
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
    <div className="min-h-screen flex flex-col bg-gradient-to-b from-pink-400 via-purple-500 to-indigo-400 hover:from-indigo-400 via-purple-500 to-pink-400">
      <div className="item-center justify-center">
        <h1 className="text-3xl md:text-4xl font-bold text-gray-800 text-center my-8">
          Explore Communities
        </h1>
      </div>
      <div className="px-8 w-full flex justify-center">
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '50px', justifyContent: 'center' }}>
          {communities.map((community) => (
            <CommunityCard
              key={community.communityId}
              community={community}
              mode="explore"
              refetchCommunities={fetchCommunities} 
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default CommunityLists;
