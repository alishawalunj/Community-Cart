import React from 'react'

export default function ItemCard() {
  return (
    <div>
      <div className="max-w-sm rounded overflow-hidden shadow-lg">
        <img class="w-full" src="/src/assets/community1.jpg" />
        <div className="px-4 py-6">
          <div className="font-bold text-xl mb-2">Item name</div>
          <p className="text-gray-700 text-base"> item description : this is a description</p>
        </div>
        <div className="px-4 pt-4 pb-2">
            <span className="px-3 font-bold text-2xl text-black"> $50 
                {/* <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">Buy</button> */}
            </span>
            <span className="px-3">
                <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">Add to cart</button>
            </span>
        </div>
      </div>
    </div>
  )
}
