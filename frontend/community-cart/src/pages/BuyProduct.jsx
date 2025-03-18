import React from 'react'
import ProductsGrid from '../components/ProductsGrid';
export default function BuyProduct() {

    const products = [
        {
            id: '1',
            name : 'Product 1',
            price : 4.00,
            imageUrl :'xyz',
        },
        {
            id: '2',
            name : 'Product 2',
            price : 4.00,
            imageUrl :'xyz',
        },
        {
            id: '3',
            name : 'Product 3',
            price : 4.00,
            imageUrl :'xyz',
        },
        {
            id: '4',
            name : 'Product 4',
            price : 4.00,
            imageUrl :'xyz',
        },
        {
            id: '5',
            name : 'Product 5',
            price : 4.00,
            imageUrl :'xyz',
        },
        {
            id: '6',
            name : 'Product 6',
            price : 4.00,
            imageUrl :'xyz',
        }
    ];



  return (
    <div>
      <div className='min-h-screen bg-gray-100'>
        <ProductsGrid/>
      </div>
    </div>
  )
}
