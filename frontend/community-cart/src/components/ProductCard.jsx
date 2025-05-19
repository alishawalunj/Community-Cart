import React, { useState } from 'react';
import {
  Card, Button, CardActions, CardContent, CardMedia, Typography,
  IconButton
} from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';

const ProductCard = ({ product }) => {
  const [isOpenProductView, setIsOpenProductView] = useState(false);

  return (
    <div>
      <Card
        sx={{width: 300,height: 300,display: 'flex',flexDirection: 'column',justifyContent: 'space-between',}}id={product.productId}>
        <CardMedia
          sx={{height: 140}}
          image={product.imageUrl || 'https://via.placeholder.com/300x140'}
          title={product.name}
        />
        <CardContent sx={{flexGrow: 1}}>
          <Typography gutterBottom variant="h6" component="div" noWrap>
            {product.name}
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ height: 60,overflow: 'hidden',textOverflow: 'ellipsis',display: '-webkit-box',WebkitLineClamp: 3,WebkitBoxOrient: 'vertical',}}>
            {product.description}
          </Typography>
          <Typography variant="subtitle2">
            ${product.price}
          </Typography>
        </CardContent>
        <CardActions>
          <Button size="small">View</Button>
          <IconButton aria-label="add to favorites">
            <FavoriteIcon />
          </IconButton>
          <IconButton aria-label="share">
            <ShareIcon />
          </IconButton>
        </CardActions>
      </Card>
    </div>
  );
};

export default ProductCard;
