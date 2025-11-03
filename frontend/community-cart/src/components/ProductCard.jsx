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
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { useProducts } from '../hooks/useProducts';
import { useCart } from '../hooks/useCart';

const ProductCard = ({ product, mode }) => {
  const [ isOpenProductView, setIsOpenProductView] = useState(false);
  const [ updateOwnedProductModal, setUpdateOwnedProductModal ] = useState(false);
  const [ buyProductModal, setBuyProductModal] = useState(false);
  const [ editableProduct, setEditableProduct] = useState({ ...product });
  const [ imagePreview, setImagePreview] = useState(product.imageUrl || product.image);
  const [ quantity, setQuantity] = useState(product.quantity || 1);
  const { updateProduct } = useProducts();
  const { addItemToCart } = useCart();

  const handleViewProduct = () => setBuyProductModal(true);

  const handleClose = () => {
    setIsOpenProductView(false);
    setUpdateOwnedProductModal(false);
    setBuyProductModal(false);
    setEditableProduct({ ...product });
    setImagePreview(product.imageUrl || product.image);
    setQuantity(product.quantity || 1);
  };

  const handleAddToCart = async () => {
    try {
      const userId = localStorage.getItem("userId");

      if (!userId) {
        alert("Please log in to add items to your cart.");
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
        console.log(response);
        localStorage.setItem("cartId", response.cartId);
        alert("Item added to cart successfully!");
      } else {
        alert("Failed to add item to cart.");
      }
    } catch (error) {
      console.error("Error adding to cart:", error);
      alert("Something went wrong while adding to cart.");
    } finally {
      handleClose();
    }
  };


  const handleUpdateOwnedProduct = () => {
    setEditableProduct({ ...product });
    setImagePreview(product.imageUrl || product.image);
    setQuantity(product.quantity || 1); 
    setUpdateOwnedProductModal(true);
  };

  const handleSaveOwnedProduct = async () => {
    try {
      const updatedProduct = { ...editableProduct, imageUrl: imagePreview, quantity };
      await updateProduct(updatedProduct);
      console.log('Product updated:', updatedProduct);
      setUpdateOwnedProductModal(false);
    } catch (error) {
      alert('Failed to update product');
    }
  };

  const handleDeleteOwnedProduct = () => {
    console.log(`Deleting product: ${product.name}`);
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => setImagePreview(reader.result);
      reader.readAsDataURL(file);
    }
  };

  const increaseQuantity = () => setQuantity((prev) => prev + 1);
  const decreaseQuantity = () => setQuantity((prev) => (prev > 1 ? prev - 1 : 1));

  return (
    <div className="flex justify-center">
      <Card
        id={product.productId}
        sx={{
          width: 300,
          height: 340,
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'space-between',
          borderRadius: 4,
          boxShadow: '0 4px 12px rgba(0,0,0,0.15)',
          transition: 'transform 0.2s ease, box-shadow 0.2s ease',
          '&:hover': {
            transform: 'translateY(-5px)',
            boxShadow: '0 8px 20px rgba(0,0,0,0.25)',
          },
        }}
      >
        <CardMedia
          sx={{
            height: 200,
            borderTopLeftRadius: '16px',
            borderTopRightRadius: '16px',
            objectFit: 'cover',
          }}
          image={product.image || 'https://via.placeholder.com/300x160'}
          title={product.name}
        />
        <CardContent sx={{ flexGrow: 1 }}>
          <Typography gutterBottom variant="h6" component="div" noWrap>
            {product.name}
          </Typography>
          <Typography
            variant="body2"
            color="text.secondary"
            sx={{
              height: 60,
              overflow: 'hidden',
              textOverflow: 'ellipsis',
              display: '-webkit-box',
              WebkitLineClamp: 3,
              WebkitBoxOrient: 'vertical',
            }}
          >
            {product.description}
          </Typography>
          <Typography variant="subtitle1" color="text.primary" sx={{ mt: 1, fontWeight: 600 }}>
            ${product.price}
          </Typography>
        </CardContent>

        <CardActions className="flex justify-between px-2">
          {mode === 'buy' && (
            <>
              <Button onClick={handleViewProduct} size="small" variant="outlined">
                View
              </Button>
              <IconButton aria-label="add to favorites" size="small">
                <FavoriteIcon />
              </IconButton>
              <IconButton aria-label="share" size="small">
                <ShareIcon />
              </IconButton>
            </>
          )}
          
          {mode === 'owned' && (
            <>
              <IconButton color="primary" onClick={handleUpdateOwnedProduct}>
                <EditIcon />
              </IconButton>
              <IconButton color="error" onClick={handleDeleteOwnedProduct}>
                <DeleteIcon />
              </IconButton>
            </>
          )}
          {mode === 'cart' && (
            <>
              <IconButton color="primary" onClick={() => console.log('Update cart')}>
                <ShoppingCartIcon />
              </IconButton>
              <IconButton color="error" onClick={() => console.log('Remove from cart')}>
                <DeleteIcon />
              </IconButton>
            </>
          )}
        </CardActions>
      </Card>

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
        maxWidth: 600,
        maxHeight: '85vh',
        overflowY: 'auto',
        p: 4,
        display: 'flex',
        flexDirection: 'column',
        gap: 2,
      }}
    >
      <Typography variant="h5" fontWeight={700} gutterBottom>
        Product Details
      </Typography>

      {/* Image Preview */}
      <Box className="flex flex-col items-center gap-2">
        <img
          src={editableProduct.image || 'https://via.placeholder.com/400x300'}
          alt="Preview"
          className="w-full rounded-lg max-h-44 object-contain"
        />
      </Box>

      {/* Product Info */}
      <Box className="flex flex-col gap-2">
        <Typography variant="subtitle1"><b>Name:</b> {editableProduct.name}</Typography>
        <Typography variant="subtitle1"><b>Description:</b> {editableProduct.description}</Typography>
        <Typography variant="subtitle1"><b>Price:</b> ${editableProduct.price}</Typography>
        <Typography variant="subtitle1"><b>Tag:</b> {editableProduct.tag}</Typography>
        <Typography variant="subtitle1"><b>Color:</b> {editableProduct.color}</Typography>
        <Typography variant="subtitle1"><b>Available Count:</b> {editableProduct.count}</Typography>
      </Box>

      {/* Quantity Counter */}
      <Box className="flex items-center justify-center gap-4 mt-4">
        <Button variant="outlined" onClick={() => setQuantity(Math.max(1, quantity - 1))}>-</Button>
        <Typography variant="h6">{quantity}</Typography>
        <Button variant="outlined" onClick={() => setQuantity(quantity + 1)}>+</Button>
      </Box>

      {/* Action Buttons */}
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

      {/* Owned Mode Modal */}
      {mode === 'owned' && (
        <Modal open={updateOwnedProductModal} onClose={handleClose}>
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
              maxWidth: 600,
              maxHeight: '85vh',
              overflowY: 'auto',
              p: 4,
              display: 'flex',
              flexDirection: 'column',
              gap: 2,
            }}
          >
            <Typography variant="h5" fontWeight={700} gutterBottom>
              Edit Product
            </Typography>

            {/* Image preview & upload */}
            <Box className="flex flex-col items-center gap-2">
              <img
                src={imagePreview || 'https://via.placeholder.com/400x300'}
                alt="Preview"
                className="w-full rounded-lg mb-2 max-h-44 object-contain"
              />
              <Button variant="outlined" component="label">
                Upload New Image
                <input type="file" hidden accept="image/*" onChange={handleImageChange} />
              </Button>
            </Box>

            <TextField
              label="Name"
              value={editableProduct.name}
              onChange={(e) => setEditableProduct({ ...editableProduct, name: e.target.value })}
              fullWidth
            />
            <TextField
              label="Description"
              value={editableProduct.description}
              onChange={(e) =>
                setEditableProduct({ ...editableProduct, description: e.target.value })
              }
              multiline
              rows={3}
              fullWidth
            />
            <TextField
              label="Price"
              type="number"
              value={editableProduct.price}
              onChange={(e) =>
                setEditableProduct({ ...editableProduct, price: parseFloat(e.target.value) })
              }
              fullWidth
            />
            <TextField
              label="Tag"
              value={editableProduct.tag}
              onChange={(e) => setEditableProduct({ ...editableProduct, tag: e.target.value })}
              fullWidth
            />
            <TextField
              label="Color"
              value={editableProduct.color}
              onChange={(e) => setEditableProduct({ ...editableProduct, color: e.target.value })}
              fullWidth
            />
            <TextField
              label="Count"
              type="number"
              value={editableProduct.count}
              onChange={(e) =>
                setEditableProduct({ ...editableProduct, count: parseFloat(e.target.value) })
              }
              fullWidth
            />

            <div className="flex justify-end gap-2 mt-4">
              <Button onClick={handleSaveOwnedProduct} variant="contained" color="primary">
                Save Changes
              </Button>
              <Button variant="outlined" onClick={handleClose}>
                Cancel
              </Button>
            </div>
          </Box>
        </Modal>
      )}

    </div>
  );
};

export default ProductCard;
