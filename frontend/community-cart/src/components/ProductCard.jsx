import React, { useState } from 'react';
import {
  Card,
  Button,
  CardActions,
  CardContent,
  CardMedia,
  Typography,
  IconButton,
  Modal,
  Box,
  TextField,
} from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { useProducts } from '../hooks/useProducts';
import { useCart } from '../hooks/useCart';

const ProductCard = ({ product, mode }) => {
  const [updateOwnedProductModal, setUpdateOwnedProductModal] = useState(false);
  const [buyProductModal, setBuyProductModal] = useState(false);
  const [editableProduct, setEditableProduct] = useState({ ...product });
  const [imagePreview, setImagePreview] = useState(product.imageUrl || product.image);
  const [quantity, setQuantity] = useState(product.quantity || 1);
  const { updateProduct } = useProducts();
  const { addItemToCart } = useCart();

  const handleViewProduct = () => setBuyProductModal(true);

  const handleClose = () => {
    setUpdateOwnedProductModal(false);
    setBuyProductModal(false);
    setEditableProduct({ ...product });
    setImagePreview(product.imageUrl || product.image);
    setQuantity(product.quantity || 1);
  };

  const handleAddToCart = async () => {
    try {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        alert('Please log in to add items to your cart.');
        return;
      }

      const cartItem = {
        productId: editableProduct.productId,
        quantity,
        price: editableProduct.price,
        addedAt: new Date(),
      };

      const response = await addItemToCart(userId, cartItem);
      if (response) {
        localStorage.setItem('cartId', response.cartId);
        alert('Item added to cart successfully!');
      } else {
        alert('Failed to add item to cart.');
      }
    } catch (error) {
      console.error('Error adding to cart:', error);
      alert('Something went wrong while adding to cart.');
    } finally {
      handleClose();
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => setImagePreview(reader.result);
      reader.readAsDataURL(file);
    }
  };

  return (
    <Card
      id={product.productId}
      sx={{
        width: 420,
        height: 500,
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'space-between',
        borderRadius: 3,
        overflow: 'hidden',
        backgroundColor: '#fff',
        border: '1px solid #e5e7eb',
        boxShadow: '0 3px 10px rgba(0,0,0,0.08)',
        transition: 'all 0.25s ease-in-out',
        '&:hover': {
          transform: 'translateY(-5px)',
          boxShadow: '0 8px 20px rgba(0,0,0,0.18)',
        },
      }}
    >
      <CardMedia
        sx={{
          height: 200,
          objectFit: 'cover',
        }}
        image={product.image || 'https://via.placeholder.com/320x200'}
        title={product.name}
      />

      <CardContent sx={{ p: 2 }}>
        <Typography variant="h6" fontWeight={600} noWrap>
          {product.name}
        </Typography>
        <Typography
          variant="body2"
          color="text.secondary"
          sx={{
            mt: 0.5,
            overflow: 'hidden',
            textOverflow: 'ellipsis',
            display: '-webkit-box',
            WebkitLineClamp: 2,
            WebkitBoxOrient: 'vertical',
          }}
        >
          {product.description}
        </Typography>
        <Typography
          variant="subtitle1"
          color="primary"
          fontWeight={700}
          sx={{ mt: 1 }}
        >
          ${product.price}
        </Typography>
      </CardContent>

      <CardActions sx={{ justifyContent: 'space-between', px: 2, pb: 2 }}>
        {mode === 'buy' && (
          <>
            <Button
              onClick={handleViewProduct}
              size="small"
              variant="contained"
              sx={{
                textTransform: 'none',
                borderRadius: 2,
                fontSize: '0.8rem',
                px: 2,
              }}
            >
              View
            </Button>
            <Box>
              <IconButton size="small">
                <FavoriteIcon fontSize="small" />
              </IconButton>
              <IconButton size="small">
                <ShareIcon fontSize="small" />
              </IconButton>
            </Box>
          </>
        )}
      </CardActions>

      {/* Product Modal */}
      {mode === 'buy' && (
        <Modal open={buyProductModal} onClose={handleClose}>
          <Box
            sx={{
              position: 'absolute',
              top: '50%',
              left: '50%',
              transform: 'translate(-50%, -50%)',
              bgcolor: 'background.paper',
              borderRadius: 3,
              boxShadow: 24,
              width: '90%',
              maxWidth: 500,
              maxHeight: '85vh',
              overflowY: 'auto',
              p: 4,
              display: 'flex',
              flexDirection: 'column',
              gap: 2,
            }}
          >
            <Typography variant="h5" fontWeight={700} gutterBottom>
              {editableProduct.name}
            </Typography>
            <img
              src={editableProduct.image || 'https://via.placeholder.com/400x300'}
              alt="Preview"
              className="w-full rounded-lg max-h-60 object-contain"
            />
            <Typography variant="body1">{editableProduct.description}</Typography>
            <Typography variant="h6" color="primary">${editableProduct.price}</Typography>

            <Box className="flex items-center justify-center gap-4 mt-4">
              <Button variant="outlined" onClick={() => setQuantity(Math.max(1, quantity - 1))}>-</Button>
              <Typography variant="h6">{quantity}</Typography>
              <Button variant="outlined" onClick={() => setQuantity(quantity + 1)}>+</Button>
            </Box>

            <div className="flex justify-end gap-2 mt-4">
              <Button onClick={handleAddToCart} variant="contained" color="primary">
                Add to Cart
              </Button>
              <Button variant="outlined" onClick={handleClose}>
                Cancel
              </Button>
            </div>
          </Box>
        </Modal>
      )}
    </Card>
  );
};

export default ProductCard;
