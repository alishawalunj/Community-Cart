import React from "react";

const Options = () => {
    return (
        <div className="h-screen flex">
            <div className="w-1/2 bg-orange-400 hover:bg-orange-500 flex items-center justify-center">
                <h1 className="text-white text-6xl hover:text-8xl font-bold ">Buy</h1>
            </div>
            <div className="w-1/2 bg-pink-500 hover:bg-pink-600 flex items-center justify-center">
                <h1 className="text-white text-6xl hover:text-8xl font-bold">Sell</h1>
            </div>
        </div>
    );
};

export default Options;
