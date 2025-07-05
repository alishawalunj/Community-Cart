import React from 'react'
import { useNavigate } from 'react-router-dom'
import CommunityCarousal from './CommunityCarousal'
import BackButton from './BackButton'

const Community = ({onBackClick}) => {

    const navigate = useNavigate();

    const onCommunityRegistrationclick = () => {
        navigate('/communityregistration');
    }

  return (
    <div>
        <div className="min-h-screen flex flex-col bg-gray-100 w-full">
            <div className="flex h-1/4 justity-left item-center text-5xl font-bold px-8 py-4">
                <h1> Your Communities</h1>
                <div className="absolute right-8  transform -translate-y-1/2 text-3xl">
                    <BackButton onClick={onBackClick} />
                </div>
            </div>
            <div className="flex h-3/4 justify-center items-center flex-grow w-full px-4">
                {/* <CommunityCard/> */}
                <CommunityCarousal/>
            </div>
            <div className='flex justify-center items-center text-5xl font-bold px-8 py-4'>
                <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full" onClick={onCommunityRegistrationclick}>Add Community</button>
            </div>

        </div>
        
        
        
    </div>
  )
}

export default Community;