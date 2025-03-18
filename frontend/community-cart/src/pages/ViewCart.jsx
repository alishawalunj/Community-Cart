import React from "react";
import ProductsGrid from "../components/ProductsGrid";

const ViewCart = (cartProductList) =>{

return(
    <>
        <ProductsGrid productList={cartProductList} />
        <div className="flex justify-center items-center bg-purple-300 py-2 px-4">
            Total Payment : 
            <div className="text-2xl font-bold text-black">
                <span> Cost </span>
                <span> Service Tax </span>
                <span> Total Cost </span>
            </div>
            <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">Checkout</button>
        </div>
    </>
)

}

export default ViewCart;