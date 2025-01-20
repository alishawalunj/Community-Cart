import React from "react";
import { Link } from "react-router-dom";

const Options = ({onMarketplaceClick, onCommunityClick}) => {

    return(
        <>
            <div className="min-h-screen">
                <h1 className="text-4xl font-bold flex justify-center items-center">Welcome Alisha</h1>
                <div className="h-screen flex">
                    <div className="h-full w-1/2 flex justify-center items-center">
                    <div className="text-black text-center">
                        <h1 className="text-3xl font-bold hover:underline cursor-pointer" onClick={onCommunityClick}>Community</h1>
                        <p className="mb-6 mt-3">Lets explore the Communities</p>
                    </div>
                    </div>
                    <div className="h-full w-1/2 flex justify-center items-center ">
                    <div className="text-black text-center">
                        {/* <Link to={"/marketplace"}> */}
                            <h1 className="text-3xl font-bold hover:underline cursor-pointer" onClick={onMarketplaceClick}>Marketplace</h1>
                        {/* </Link> */}
                        <p className="mb-6 mt-3">Explore the Marketplace</p>
                    </div>
                    </div>
                </div>
            </div>
            

        </>
    )

}

export default Options;