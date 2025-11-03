import React, { useState, useEffect} from 'react';
import { useCommunity } from '../hooks/useCommunity';
import CommunityCard from '../components/CommunityCard';

const UserOwnedCommunities = () => {
    const [ loading, setLoading ] = useState(true);
    const [ communities, setCommunities ] = useState([]);
    const { getUserOwnedCommunities } = useCommunity();

    useEffect(() => {
      const fetchUserOwnedCommunities = async () => {
        try{
          const userId = localStorage.getItem('userId');
          const response = await getUserOwnedCommunities(userId);
          console.log("Fetched user owned communities: ", response);
          if(!response || response.length === 0){
            setCommunities([]);
          } else {
            setCommunities(response);
          }
        }catch(error){
          console.error("Error fetching user owned communities: ", error);
        } finally { 
        setLoading(false);
        }
      };
      fetchUserOwnedCommunities();
    }, []);

    if (loading) {
      return (
        <div className="flex items-center justify-center min-h-screen">
          <div className="text-xl">Loading your communities...</div>
        </div>
      );
    }
    return (
        <div className="min-h-screen flex flex-col bg-gradient-to-b from-indigo-400 via-purple-500 to-pink-400 hover:from-pink-400 via-purple-500 to-indigo-400 w-full">
            <div className="flex flex-col h-1/4 justity-left item-center text-5xl font-bold px-8 py-4 relative">
                <h1>Your Created Communities</h1>
                </div>
            <div className="px-8 w-full flex justify-center">
                <div style={{display:'flex', flexWrap:'wrap', gap:'50px', justifyContent:'center'}}>
                    {communities.length === 0 ? (
                        <div className="text-xl">You have not created any communities yet.</div>
                    ) : (
                        communities.map((community) => (
                            <CommunityCard key={community.communityId} community={community} />
                        ))
                    )}
                </div>
            </div>
        </div>
    );
}
export default UserOwnedCommunities;