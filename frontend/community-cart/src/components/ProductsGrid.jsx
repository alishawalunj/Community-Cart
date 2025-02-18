import React from 'react';
import ProductCard from './ProductCard';

const ProductsGrid =(productList) => {
  return (
    <div>
      {productList.map((product) => (
        <ProductCard
          key={product.id}
          product={product}
          />
      ))}
    </div>
  );
}

export default ProductsGrid;