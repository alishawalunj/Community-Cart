import React from "react";
import { useNavigate } from "react-router-dom";
import BackButton from "./BackButton";

const Marketplace = ({ onBackClick }) => {
  const navigate = useNavigate();

  const selectBuyOption = () => {
    navigate("/buy");
  };

  const selectSellOption = () => {
    navigate("/sell");
  };

  return (
    <div className="relative h-screen flex">
      {/* Back Button fixed at top-right of the screen */}
      <div className="absolute top-4 right-6 text-3xl z-10">
        <BackButton onClick={onBackClick} />
      </div>

      {/* Buy Section */}
      <div className="w-1/2 bg-orange-400 hover:bg-orange-500 flex items-center justify-center">
        <h1 className="text-white text-6xl hover:text-8xl font-bold cursor-pointer" onClick={selectBuyOption}>
          Buy
        </h1>
      </div>

      {/* Sell Section */}
      <div className="w-1/2 bg-pink-500 hover:bg-pink-600 flex items-center justify-center">
        <h1 className="text-white text-6xl hover:text-8xl font-bold cursor-pointer" onClick={selectSellOption}>
          Sell
        </h1>
      </div>
    </div>
  );
};

export default Marketplace;
