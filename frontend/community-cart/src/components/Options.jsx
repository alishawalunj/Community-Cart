/* eslint-disable react/prop-types */
import { useEffect, useState } from "react";
import { useUser } from "../hooks/useUser";
const Options = ({onMarketplaceClick, onCommunityClick}) => {

    const [ user, setUser ] = useState(null);
    const { getUserById } = useUser();

    useEffect(() => {
        const fetchUsers = async() => {
            const userId = localStorage.getItem("userId");
            const users = await getUserById(userId);
            setUser(users);
        }
        fetchUsers();
    }, [])

    return(
        <>
            <div className="min-h-screen">
                <h1 className="text-4xl font-bold flex justify-center items-center">Welcome {user?.firstName}</h1>
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