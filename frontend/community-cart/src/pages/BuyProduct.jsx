import React from 'react'
import ProductsGrid from '../components/ProductsGrid';
// import mockData from '../mockData';
import useProducts  from '../hooks/useProducts';
import BackButton from '../components/BackButton';
const BuyProduct = () => {

  const { allProducts, allProductsLoading, allProductsError } = useProducts();
  

  return (
    <div>
      <div className='flex flex-col items-center justify-center min-h-screen bg-gray-100 space-y-4'>
        <div className="self-end mr-8 text-3xl">
          <BackButton onClick={() => {window.history.back() }} />
        </div>
        <div className='text-7xl font-bold px-8 py-5'>
          <h1>Products</h1>
        </div>
        {allProductsLoading && <div className="text-2xl text-gray-600">Loading...</div>}
        {allProductsError && <div className="text-2xl text-red-500">Failed to load products.</div>}
  
        {!allProductsLoading  && <ProductsGrid productList={allProducts} />}
        {/* <ProductsGrid productList={mockData.products}/> */}
      </div>
    </div>
  )
}

// export default BuyProduct;
export default BuyProduct;
