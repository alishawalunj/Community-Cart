import React, { useEffect, useState } from "react";
import { useProducts } from "../hooks/useProducts";
import ProductsGrid from "./ProductsGrid";

const UserProductsTab = () => {
  const { getProductsByUserId } = useProducts();
  const [productsList, setProductsList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProducts = async () => {
      const storedUserId = localStorage.getItem("userId");
      if (!storedUserId) {
        setLoading(false);
        return;
      }

      try {
        const userId = parseInt(storedUserId);
        const response = await getProductsByUserId(userId);
        if (response) setProductsList(response);
      } catch (error) {
        console.error("Error fetching user's products:", error);
        alert("Error fetching your products");
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  if (loading) {
    return (
      <div className="bg-white/20 p-8 rounded-xl backdrop-blur-md text-black shadow-md text-center">
        <p className="text-lg">Loading your products...</p>
      </div>
    );
  }

  return (
    <div className="bg-white/20 p-8 rounded-xl backdrop-blur-md text-black shadow-md">
      <h2 className="text-2xl font-semibold mb-4">Your Products</h2>
      {productsList.length > 0 ? (
        <ProductsGrid productList={productsList} mode="owned" />
      ) : (
        <p className="text-lg">No products added yet.</p>
      )}
    </div>
  );
};

export default UserProductsTab;
