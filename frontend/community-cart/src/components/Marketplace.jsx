import React from "react";
import { use } from "react";
import { useNavigate } from "react-router-dom";

const Marketplace = () => {
    const navigate = useNavigate();

    const selectBuyOption = () => {
        navigate("/buy");
    }

    const selectSellOption = () => {
        navigate("/sell");
    }


    return (
        <div className="h-screen flex">
            <div className="w-1/2 bg-orange-400 hover:bg-orange-500 flex items-center justify-center">
                <h1 className="text-white text-6xl hover:text-8xl font-bold" onClick={selectBuyOption}>Buy</h1>
            </div>
            <div className="w-1/2 bg-pink-500 hover:bg-pink-600 flex items-center justify-center">
                <h1 className="text-white text-6xl hover:text-8xl font-bold" onClick={selectSellOption}>Sell</h1>
            </div>
        </div>
    );
};

export default Marketplace;
