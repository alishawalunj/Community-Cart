import React, { useEffect, useState} from 'react'
import ProductsGrid from '../components/ProductsGrid';
import BackButton from '../components/BackButton';
import { useProducts } from '../hooks/useProducts';

const BuyProduct = () => {

  const [ productList, setProductList ] = useState([]);
  const [ loading, setloading ] = useState(true);
  const [ error, setError ] = useState(null);

  const userId = localStorage.getItem("userId");
  const { getProductsByUserCommunities } = useProducts();

  useEffect(() => {
  if (!userId) return;

  const getAllProducts = async () => {
    try {
      const products = await getProductsByUserCommunities(userId); 
      setProductList(products || []); 
      console.log("Products for user communities:", JSON.stringify(products, null, 2));
    } catch (error) {
      console.error("Error from backend", error);
      setError("Failed to load products.");
    } finally {
      setloading(false);
    }
  };

  getAllProducts();
}, [userId]);


  return (
    <div>
      <div className='flex flex-col items-center justify-center min-h-screen bg-gray-100 space-y-4'>
        <div className="self-end mr-8 text-3xl">
          <BackButton onClick={() => {window.history.back() }} />
        </div>
        <div className='text-7xl font-bold px-8 py-5'>
          <h1>Products</h1>
        </div>
        {loading && <div className="text-2xl text-gray-600">Loading...</div>}
        {error && <div className="text-2xl text-red-500">Failed to load products.</div>}
  
        {!loading && !error && <ProductsGrid productList={productList} />}

      </div>
    </div>
  )
}

// export default BuyProduct;
export default BuyProduct;
