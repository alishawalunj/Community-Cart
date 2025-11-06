import React, { useEffect, useState } from 'react';
import ProductsGrid from '../components/ProductsGrid';
import BackButton from '../components/BackButton';
import { useProducts } from '../hooks/useProducts';
import { useCart } from '../hooks/useCart';
import { IconButton, Badge } from '@mui/material';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { useNavigate } from "react-router-dom";

const BuyProduct = () => {
  const [productList, setProductList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [cartCount, setCartCount] = useState(0);
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");
  const { getProductsByUserCommunities } = useProducts();
  const { getOpenCart, getCartItemCount } = useCart(); // make sure this hook returns count

  useEffect(() => {
    if (!userId) return;

    const fetchProducts = async () => {
      try {
        const products = await getProductsByUserCommunities(userId);
        setProductList(products || []);
      } catch (err) {
        console.error("Error fetching products:", err);
        setError("Failed to load products.");
      } finally {
        setLoading(false);
      }
    };

    const initCart = async () => {
      let cartId = localStorage.getItem("cartId");
      if (!cartId) {
        const response = await getOpenCart(userId);
        if (response) {
          localStorage.setItem("cartId", response.cartId);
          cartId = response.cartId;
        }
      }

      if (cartId) {
        const count = await getCartItemCount(cartId);
        setCartCount(count || 0);
      }
    };

    initCart();
    fetchProducts();
  }, [userId, getProductsByUserCommunities, getOpenCart, getCartItemCount]);

  const handleCartClick = () => {
    const cartId = localStorage.getItem("cartId");
    navigate("/cart", { state: { cartId } });
  };

  return (
    <div className="relative min-h-screen bg-gradient-to-br from-blue-600 via-indigo-600 to-purple-700 hover:from-purple-700 via-indigo-600 to-blue-600">
      <div className="flex justify-between items-center p-6">
        <BackButton onClick={() => window.history.back()} />

        <IconButton onClick={handleCartClick} sx={{ color: 'white' }}>
          <Badge badgeContent={cartCount} color="error">
            <ShoppingCartIcon fontSize="large" />
          </Badge>
        </IconButton>
      </div>

      <h1 className="text-center text-6xl font-extrabold  text-white py-6 mb-7">Products</h1>

      <div className="flex flex-col items-center space-y-3">
        {loading && <div className="text-2xl text-gray-100 mt-20">Loading...</div>}
        {error && <div className="text-2xl text-red-400 mt-20">{error}</div>}

        {!loading && !error && productList.length > 0 && (
          <ProductsGrid productList={productList} mode="buy" />
        )}

        {!loading && !error && productList.length === 0 && (
          <div className="text-2xl text-gray-100 mt-10">No products available in your communities.</div>
        )}
      </div>
    </div>
  );
};

export default BuyProduct;
