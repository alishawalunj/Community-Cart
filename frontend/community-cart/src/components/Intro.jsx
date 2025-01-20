import React from "react";
import { useNavigate } from "react-router-dom";
const Intro = () => {

  const navigate = useNavigate();

  const handleGetStatrted = () => {
    navigate("/dashboard");
  }

  return (
    <div className="h-screen flex">
       <div className="h-full w-1/2 bg-cover bg-center" style={{ backgroundImage: "url('/src/assets/community2.jpg')" }}/>
      <div className="h-full w-1/2 flex bg-gradient-to-r from-purple-500 to-pink-500  hover:from-pink-500 hover:to-purple-500 items-center justify-center">
        <div className="text-white text-center">
          <h1 className="text-4xl font-bold mb-4">Welcome to Community Cart</h1>
          <p className="mb-6">Join us and be part of something amazing!</p>
          <button className="px-6 py-2 bg-blue-500 hover: bg-blue-700 text-white font-semibold rounded-full hover:bg-blue-600" onClick={handleGetStatrted}>
            Get Started 
          </button>
        </div>
      </div>
    </div>
  );
};

export default Intro;
