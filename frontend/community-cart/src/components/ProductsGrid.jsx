import React from 'react';
import ProductCard from './ProductCard';

const ProductsGrid = ({productList}) => {
    return(
      <div className="px-8 w-full flex justify-center">
        <div style={{display:'flex', flexWrap:'wrap', gap:'50px'}}>
          {productList.map((product) => (
            <ProductCard key={product.productId} product={product} />
          ))}
        </div>
      </div>
    )
}

export default ProductsGrid;