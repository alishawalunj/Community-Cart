import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useProducts } from '../hooks/useProducts';
import { useCart } from '../hooks/useCart';
import ProductsGrid from '../components/ProductsGrid';

const Cart = () => {
  const location = useLocation();
  const cartId = location.state?.cartId || localStorage.getItem("cartId");

  const [productList, setProductList] = useState([]);
  const { getCartById } = useCart();
  const { getProductById } = useProducts();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
  if (!cartId) return;

  const fetchCartItems = async () => {
    try {
      setLoading(true);
      const cartResponse = await getCartById(Number(cartId));

      if (cartResponse?.cartItems?.length > 0) {
        const products = await Promise.all(
          cartResponse.cartItems.map(async (item) => {
            return await getProductById(item.productId);
          })
        );

        setProductList(products.filter(Boolean));
      } else {
        setProductList([]);
      }
    } catch (err) {
      console.error("Error fetching cart items:", err);
      setError("Failed to fetch cart items.");
    } finally {
      setLoading(false);
    }
  };

  fetchCartItems();
}, [cartId]);


  return (
    <div className="flex flex-col min-h-screen bg-gradient-to-b from-indigo-400 via-purple-500 to-pink-400
        hover:from-pink-400 via-purple-500 to-indigo-400
        transition-all duration-500
        justify-start items-center text-white p-8 space-y-4">

      <h1 className="text-4xl font-bold mb-8">
        {cartId ? `Cart Contents` : "No Cart Found"}
      </h1>

      {loading && <div className="text-2xl">Loading cart items...</div>}
      {error && <div className="text-2xl text-red-400">{error}</div>}
      {!loading && !error && productList.length === 0 && (
        <div className="text-2xl">Cart is empty</div>
      )}

      {!loading && !error && productList.length > 0 && (
        <ProductsGrid productList={productList} mode="cart" />
      )}
    </div>
  );
};

export default Cart;
