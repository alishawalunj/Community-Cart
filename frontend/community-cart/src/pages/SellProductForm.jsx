import React, { useState } from 'react';
import { Button } from '@mui/material';

const SellProductForm = ({ onProductUploadClick, communities }) => {
    const [formData, setFormData] = useState({
        communityName: '',
        productName: '',
        productType: '',
        productDescription: '',
        price: '',
        productImage: '',
        quantity: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onProductUploadClick(formData);
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-50">
            <form onSubmit={handleSubmit} className="w-full max-w-lg bg-white p-10 rounded-xl shadow-xl">
                <h2 className="text-2xl font-bold mb-6 text-center">Add a Product</h2>

                {[
                    { inputFor : 'Product Name', name: 'productName', placeholder: 'Product Name' },
                    { inputFor : 'Product Type', name: 'productType', placeholder: 'Product Type' },
                    { inputFor : 'Price', name: 'price', placeholder: 'Price' },
                    { inputFor : 'Product Description', name: 'productDescription', placeholder: 'Product Description' },
                    { inputFor : 'Quantity', name: 'quantity', placeholder: 'Quantity' },
                ].map(({ inputFor, name, placeholder }) => (
                    <div className="mb-4" key={name}>
                     {inputFor} : <input
                            type="text"
                            name={name}
                            id={name}
                            value={formData[name]}
                            onChange={handleChange}
                            // placeholder={placeholder}
                            className="w-full bg-transparent border-b-2 border-teal-500 py-2 px-2 text-gray-700 leading-tight focus:outline-none focus:border-blue-500"
                            required
                        />
                    </div>
                ))}

                <div className="flex justify-between mt-6">
                    <Button type="submit" variant="contained" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded-full">
                        Add Product
                    </Button>
                    <Button type="button" variant="contained" className="bg-gray-400 hover:bg-gray-600 text-white font-bold py-2 px-6 rounded-full" onClick={() => window.history.back()}>
                        Back
                    </Button>
                </div>
            </form>
        </div>
    );
};

export default SellProductForm;
