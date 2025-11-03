import React, { useEffect, useState } from 'react';
import { Button, MenuItem, TextField } from '@mui/material';
import sellProductImage from '../assets/sellproduct.png';
import { useCommunity } from '../hooks/useCommunity';
import { useProducts } from '../hooks/useProducts';

const SellProductForm = () => {
  const [formData, setFormData] = useState({
    communityId: '',
    name: '',
    description: '',
    tag: '',
    color: '',
    size: '',
    price: '',
    count: '',
    image: ''
  });

  const [userId, setUserId] = useState(null);
  const { getUserOwnedCommunities } = useCommunity();
  const { createProduct, uploadProductImage } = useProducts();
  const [communitiesList, setCommunitiesList] = useState([]);

  useEffect(() => {
    const storedUserId = localStorage.getItem('userId');
    if (!storedUserId) return;

    const id = parseInt(storedUserId);
    setUserId(id);

    const fetchCommunities = async () => {
      try {
        const communities = await getUserOwnedCommunities(id);
        if (communities) setCommunitiesList(communities);
      } catch (error) {
        alert("Error fetching user's communities");
      }
    };

    fetchCommunities();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setFormData(prev => ({ ...prev, image: file }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!userId) {
      alert("User not found. Please log in again.");
      return;
    }

    try {
      const productData = { ...formData, userId, image: null };
      const createdProduct = await createProduct(productData);

      if (!createdProduct || !createdProduct.productId) {
        alert("Error creating product");
        return;
      }

      if (formData.image) {
        const imageFormData = new FormData();
        imageFormData.append("file", formData.image);
        try {
          await uploadProductImage(createdProduct.productId, imageFormData);
        } catch (error) {
          console.error("Error uploading image", error);
          alert("Failed to upload image");
        }
      }

      alert("Product created successfully!");
      setFormData({
        communityId: '',
        name: '',
        description: '',
        tag: '',
        color: '',
        size: '',
        price: '',
        count: '',
        image: ''
      });
    } catch (error) {
      console.error(error);
      alert("Error creating product");
    }
  };

  return (
    <div className="flex flex-col md:flex-row h-screen">
      {/* Left Panel */}
      <div className="hidden md:flex w-1/2 bg-gradient-to-br from-cyan-400 to-blue-600 text-white flex-col justify-center items-center p-10">
        <h1 className="text-4xl font-extrabold mb-4 text-center">Register Your Product</h1>
        <p className="text-lg max-w-md text-center leading-relaxed">
          Create listings, showcase your products, and connect with your community buyers instantly.
        </p>
        <img
          src={sellProductImage}
          alt="Product registration illustration"
          className="mt-5 w-4/5 max-w-md rounded-xl shadow-xl opacity-95"
        />
      </div>

      {/* Right Panel (Form) */}
      <div className="flex w-full md:w-1/2 justify-center items-center bg-gray-50">
        <form onSubmit={handleSubmit} className="w-full bg-white p-10 rounded-2xl shadow-2xl">
          <h2 className="text-3xl font-bold mb-6 mt-2 text-center text-gray-800">
            Add a New Product
          </h2>

          {/* Community Select */}
          <div className="mb-6">
            <label className="block text-gray-700 mb-2 font-medium">Select Community</label>
            <TextField
              select
              fullWidth
              name="communityId"
              value={formData.communityId}
              onChange={handleChange}
              required
              variant="outlined"
            >
              {communitiesList.length > 0 ? (
                communitiesList.map(c => (
                  <MenuItem key={c.communityId} value={c.communityId}>
                    {c.name}
                  </MenuItem>
                ))
              ) : (
                <MenuItem disabled>No communities found</MenuItem>
              )}
            </TextField>
          </div>

          {/* Product Fields */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {[
              { label: 'Product Name', name: 'name' },
              { label: 'Tag', name: 'tag' },
              { label: 'Color', name: 'color' },
              { label: 'Size', name: 'size' },
              { label: 'Price ($)', name: 'price', type: 'number' },
              { label: 'Quantity', name: 'count', type: 'number' },
            ].map(({ label, name, type = 'text' }) => (
              <div key={name}>
                <label className="block text-gray-700 mb-2 font-medium">{label}</label>
                <input
                  type={type}
                  name={name}
                  value={formData[name]}
                  onChange={handleChange}
                  className="w-full border-2 border-gray-200 rounded-lg py-2 px-3 text-gray-700 focus:outline-none focus:border-cyan-500"
                  required
                />
              </div>
            ))}
          </div>

          {/* Description */}
          <div className="mt-6">
            <label className="block text-gray-700 mb-2 font-medium">Description</label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              rows="4"
              className="w-full border-2 border-gray-200 rounded-lg py-2 px-3 text-gray-700 focus:outline-none focus:border-cyan-500"
              required
            ></textarea>
          </div>

          {/* Image */}
          <div className="mt-6">
            <label className="block text-gray-700 mb-2 font-medium">Product Image</label>
            <input
              type="file"
              accept="image/*"
              onChange={handleImageChange}
              className="w-full text-gray-700"
              required
            />
          </div>

          {/* Buttons */}
          <div className="flex justify-between mt-10">
            <Button
              type="submit"
              variant="contained"
              sx={{ bgcolor: '#06b6d4', '&:hover': { bgcolor: '#0891b2' } }}
            >
              Add Product
            </Button>
            <Button
              type="button"
              variant="contained"
              sx={{ bgcolor: '#9ca3af', '&:hover': { bgcolor: '#6b7280' } }}
              onClick={() => window.history.back()}
            >
              Back
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SellProductForm;
