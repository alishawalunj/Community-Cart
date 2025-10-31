import React from 'react';
import { useNavigate } from 'react-router-dom';
import CommunityCarousal from './CommunityCarousal';
import BackButton from './BackButton';

const Community = ({ onBackClick }) => {
  const navigate = useNavigate();

  const onCommunityRegistrationclick = () => navigate('/communityregistration');
  const onExploreCommunitiesClick = () => navigate('/communitylists');
  const onUserOwnedCommunitiesClick = () => navigate('/userownedcommunities');

  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-br from-gray-50 to-gray-200">
      {/* Header Section */}
      <div className="relative flex flex-col justify-center px-8 py-6">
        <div className="flex flex-wrap gap-4 justify-center md:justify-start mb-6">
          <button
            onClick={onExploreCommunitiesClick}
            className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-full shadow-md transition-transform transform hover:scale-105"
          >
            Explore All Communities
          </button>

          <button
            onClick={onCommunityRegistrationclick}
            className="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-6 rounded-full shadow-md transition-transform transform hover:scale-105"
          >
            Add Community
          </button>

          <button
            onClick={onUserOwnedCommunitiesClick}
            className="bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-6 rounded-full shadow-md transition-transform transform hover:scale-105"
          >
            Communities You Created
          </button>
        </div>

        <div className="flex items-center justify-between">
          <h1 className="text-3xl md:text-4xl font-bold text-gray-800">
            Your Communities
          </h1>

          <div className="text-2xl md:text-3xl">
            <BackButton onClick={onBackClick} />
          </div>
        </div>
      </div>

      {/* Carousel Section */}
      <div className="flex justify-center items-center flex-grow px-4 py-6">
        <div className="w-full md:w-3/4 lg:w-2/3">
          <CommunityCarousal />
        </div>
      </div>
    </div>
  );
};

export default Community;
