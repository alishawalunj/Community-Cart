import React from 'react';

export default function CommunityCard() {
  return (
    <div className="flex justify-center items-center py-6">
      <div className="max-w-2xl w-2/3 flex border rounded-lg shadow-lg bg-white overflow-hidden transition-all duration-500 group">
        {/* Front face */}
        <div className="relative w-full backface-hidden">
          <div className="w-full min-h-[200px] bg-cover bg-center" style={{ backgroundImage: "url('/src/assets/community1.jpg')" }}/>
          <div className="p-6 flex flex-col justify-between">
            <div className="mb-4">
              <h2 className="text-gray-900 font-bold text-xl mb-2">Purdue Community</h2>
              <p className="text-gray-700 text-base">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                Voluptatibus quia, nulla! Maiores et perferendis eaque,
                exercitationem praesentium nihil.
              </p>
            </div>
            <div className="flex items-center mt-4">
              <span className="px-5">
                <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">View</button>
              </span>
              <span className="px-5">
                <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">Leave</button>
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
