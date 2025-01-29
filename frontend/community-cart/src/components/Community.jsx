import React from 'react'
import CommunityCard from './CommunityCard'
import { useNavigate } from 'react-router-dom'
const Community = () => {

    const navigate = useNavigate();
    const onCommunityRegistrationclick = () => {
        navigate("/communityregistration");
    }

  return (
    <div>
        <div className="min-h-screen flex flex-col bg-gray-100">
            <div className="flex justity-left item-center text-5xl font-bold px-8 py-4">
                <h1> Your Communities</h1>
            </div>
            <div className="flex justify-center items-center flex-grow">
                <CommunityCard/>
            </div>
            <div className='flex justify-center items-center text-5xl font-bold px-8 py-4'>
                <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full" onClick={onCommunityRegistrationclick}>Add Community</button>
            </div>
        </div>
        
        
        
    </div>
  )
}

export default Community