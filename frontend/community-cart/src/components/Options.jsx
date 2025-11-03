/* eslint-disable react/prop-types */
import { useEffect, useState } from "react";
import { useUser } from "../hooks/useUser";

const Options = ({ onMarketplaceClick, onCommunityClick }) => {
  const [user, setUser] = useState(null);
  const { getUserById } = useUser();

  useEffect(() => {
    const fetchUsers = async () => {
      const userId = localStorage.getItem("userId");
      const users = await getUserById(userId);
      setUser(users);
    };
    fetchUsers();
  }, []);

  return (
    <div className="h-screen flex flex-col bg-gradient-to-br from-green-400 to-cyan-500 hover:from-cyan-500 hover:to-green-400 transition-all duration-500">
      {/* Welcome Header */}
      <h1 className="text-4xl font-bold text-center mt-10 text-white drop-shadow-md">
        Welcome {user?.firstName}
      </h1>

      {/* Split Panels */}
      <div className="flex flex-1">
        {/* Community Section */}
        <div
          className="flex-1 flex flex-col justify-center items-center  transition-all duration-300 cursor-pointer"
          onClick={onCommunityClick}
        >
          <h2 className="text-3xl font-bold text-white mb-3 hover:underline">
            Community
          </h2>
          <p className="text-white text-lg">Let’s explore the Communities</p>
        </div>

        {/* Marketplace Section */}
        <div
          className="flex-1 flex flex-col justify-center items-center transition-all duration-300 cursor-pointer"
          onClick={onMarketplaceClick}
        >
          <h2 className="text-3xl font-bold text-white mb-3 hover:underline">
            Marketplace
          </h2>
          <p className="text-white text-lg">Explore the Marketplace</p>
        </div>
      </div>
    </div>
  );
};

export default Options;
