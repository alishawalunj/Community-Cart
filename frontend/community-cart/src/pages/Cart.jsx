import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useProducts } from '../hooks/useProducts';
import { useCart } from '../hooks/useCart';
import ProductsGrid from '../components/ProductsGrid';

const Cart = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const cartId = location.state?.cartId || localStorage.getItem("cartId");
  const [cartData, setCartData] = useState(null);
  const [productList, setProductList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const { getCartById, checkoutCart } = useCart();
  const { getProductById } = useProducts();

  useEffect(() => {
    if (!cartId) return;

    const fetchCartItems = async () => {
      try {
        setLoading(true);
        const response = await getCartById(Number(cartId)); // returns data object
        setCartData(response);

        if (response?.cartItems?.length > 0) {
          const products = await Promise.all(
            response.cartItems.map(async (item) => await getProductById(item.productId))
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
  }, [cartId, getCartById, getProductById]);

  const handleCheckout = async () => {
    if (!cartData) {
      alert("Cart data is not available.");
      return;
    }

    try {
      setLoading(true);
      // ✅ use existing cartData, no need to fetch again
      const summary = await checkoutCart(cartData);

      if (!summary) {
        alert("Error fetching cart summary");
        return;
      }

      navigate("/payment", { state: { summary } });
    } catch (error) {
      console.error("Error during checkout:", error);
      alert("Error in checking out");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col min-h-screen 
        bg-gradient-to-b from-indigo-400 via-purple-500 to-pink-400
        hover:from-pink-400 hover:via-purple-500 hover:to-indigo-400
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

      <button
        onClick={handleCheckout}
        disabled={loading || productList.length === 0}
        className="bg-white/20 backdrop-blur-md border border-white/30 text-white/90 
                   font-medium py-2 px-4 rounded-lg shadow-md hover:shadow-lg
                   transition-all duration-300 hover:scale-105 disabled:opacity-50"
      >
        {loading ? "Processing..." : "Checkout"}
      </button>
    </div>
  );
};

export default Cart;
