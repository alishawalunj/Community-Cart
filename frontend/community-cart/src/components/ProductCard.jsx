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
} from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';

const ProductCard = ({ product }) => {
  const [isOpenProductView, setIsOpenProductView] = useState(false);
  const [quantity, setQuantity] = useState(1);

  const handleViewProduct = () => setIsOpenProductView(true);

  const handleClose = () => {
    setIsOpenProductView(false);
    setQuantity(1);
  };
  const handleAddToCart = () => {
    console.log(`Added ${quantity} of ${product.name} to cart.`);
    handleClose();
  }
  
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
            height: 160,
            borderTopLeftRadius: '16px',
            borderTopRightRadius: '16px',
            objectFit: 'cover',
          }}
          image={product.imageUrl || 'https://via.placeholder.com/300x160'}
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
          <Typography
            variant="subtitle1"
            color="text.primary"
            sx={{ mt: 1, fontWeight: 600 }}
          >
            ${product.price}
          </Typography>
        </CardContent>

        <CardActions className="flex justify-between px-2">
          <Button onClick={handleViewProduct} size="small" variant="outlined">
            View
          </Button>
          <div>
            <IconButton aria-label="add to favorites" size="small">
              <FavoriteIcon />
            </IconButton>
            <IconButton aria-label="share" size="small">
              <ShareIcon />
            </IconButton>
          </div>
        </CardActions>
      </Card>

      {/* Modal */}
      <Modal open={isOpenProductView} onClose={handleClose}>
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
          }}
        >
          <Typography variant="h5" component="h2" fontWeight={700} gutterBottom>
            {product.name}
          </Typography>
          <img
            src={product.imageUrl || 'https://via.placeholder.com/600x400'}
            alt={product.name}
            className="w-full rounded-lg mb-4"
          />
          <Typography variant="body1" paragraph>
            {product.description}
          </Typography>
          <Typography variant="h6" fontWeight={600} gutterBottom>
            ${product.price}
          </Typography>

          {/* Quantity Counter */}
          <div className="flex items-center gap-4 my-4">
            <Typography variant="subtitle1" fontWeight={600}>
              Quantity:
            </Typography>
            <div className="flex items-center border border-gray-300 rounded-lg px-2">
              <IconButton onClick={decreaseQuantity} size="small">
                <RemoveIcon />
              </IconButton>
              <Typography variant="body1" sx={{ mx: 1, minWidth: 20, textAlign: 'center' }}>
                {quantity}
              </Typography>
              <IconButton onClick={increaseQuantity} size="small">
                <AddIcon />
              </IconButton>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="flex justify-end gap-2 mt-6">
            <Button onClick={handleAddToCart} variant="contained" color="primary">
              Add {quantity} to Cart
            </Button>
            <Button variant="outlined" onClick={handleClose}>
              Close
            </Button>
          </div>
        </Box>
      </Modal>
    </div>
  );
};

export default ProductCard;
