import React from 'react';
import { useNavigate } from 'react-router-dom';
import CommunityCarousal from './CommunityCarousal';
import BackButton from './BackButton';

const Community = ({ onBackClick }) => {
  const navigate = useNavigate();

  const onCommunityRegistrationClick = () => navigate('/communityregistration');
  const onExploreCommunitiesClick = () => navigate('/communitylists');
  const onUserOwnedCommunitiesClick = () => navigate('/userownedcommunities');

  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-br from-teal-400 via-cyan-500 to-blue-500
    hover:from-blue-500 hover:via-cyan-500 hover:to-teal-400 p-6 md:p-12">
      <div className="flex justify-between items-center mb-8 flex-wrap gap-4">
        <h1 className="text-3xl md:text-4xl font-bold text-gray-100 flex items-center gap-3">
          Your Communities
        </h1>

        <div className="flex flex-wrap gap-3 justify-end">
          {[{
            label: "Explore All",
            onClick: onExploreCommunitiesClick
          }, {
            label: "Add Community",
            onClick: onCommunityRegistrationClick
          }, {
            label: "My Communities",
            onClick: onUserOwnedCommunitiesClick
          }].map(({ label, onClick }) => (
            <button
              key={label}
              onClick={onClick}
              className="bg-white/20 backdrop-blur-md border border-white/30 text-white/90 
                         font-medium py-2 px-4 rounded-lg shadow-md hover:shadow-lg
                         transition-all duration-300 hover:scale-105"
            >
              {label}
            </button>
          ))}
        </div>
      </div>

      {/* Carousel Section */}
      <div className="flex-grow flex justify-center items-start">
        <div className="w-full md:w-4/5 lg:w-3/5">
          <CommunityCarousal />
        </div>
      </div>
    </div>
  );
};

export default Community;
