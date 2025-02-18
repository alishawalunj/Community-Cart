import  { React, useState } from 'react'

const SellProductForm = ({onProductUploadClick, communities}) => {

    const [formData, setFormData] = useState({
        communityName:'',
        productName:'',
        productType:'',
        productDescription:'',
        price:'',
        productImage:'',
        quantity:''
    })

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData({
            ...formData,
            [name]:value
        });
    }

    const handleSubmit = (e) =>{
       const {communityName, productName, productType, productDescription, price, productImage, quantity} = formData;
       e.preventDefault();
       onProductUploadClick(formData);
    };


    return(
        <>
            <form onSubmit={handleSubmit}>
                <div className='mb-5'>
                    <label htmlFor='communityName' className='block text-gray-700 font-bold mb-2'>Community Name</label>
                    <select name='communityName' id='communityName' value={formData.communityName} onChange={handleChange} className='border rounded-lg px-4 py-2 w-full' required>
                        <option value={''}>Select a community</option>
                        {communities.map((community) => (
                            <option key={community.communityId} value={community.communityName}>{community.communityName}</option>
                        ))}
                    </select>

                    <label htmlFor="productName" className='block text-gray-700 font-bold mb-2'>Product Name</label>
                    <input type='text' name='productName' id='productName' value={formData.productName} onChange={handleChange} className='border rounded-lg px-4 py-2 w-full' required/>

                    <label htmlFor="productType" className='block text-gray-700 font-bold mb-2'>Product type</label>
                    <input type='text' name='productType' id='productType' value={formData.productType} onChange={handleChange} className='border rounded-lg px-4 py-2 w-full' required/>

                    <label htnlFor="price" className='block text-gray-700 font-bold mb-2'>Price</label>
                    <input type='number' name='price' id='price' value={formData.price} onChange={handleChange} className='border rounded-lg px-4 py-2 w-full' required/> 

                    <label htmlFor='productImage' className='block text-gray-700 font-bold mb-2'>Product Image</label>

                    <label htmlFor='productDescription' className='block text-gray-700 font-bold mb-2'>Product Description</label>
                    <input type='text' name='productDescription' id='productDescription' value={formData.productDescription} onChange={handleChange} className='border rounded-lg px-4 py-2 w-full' required/>

                    <label htmlFor='quantity' className='block text-gray-700 font-bold mb-2'>Quantity</label>
                    <input type='number' name='quantity' id='quantity' value={formData.quantity} onChange={handleChange} className='border rounded-lg px-4 py-4 w-full' required/>
                </div>
            </form>
        </>
    )
}

export default SellProductForm;