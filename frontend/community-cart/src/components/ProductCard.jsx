import React from 'react'
import { Card, Button } from '@mui/material';
import { CardActions, CardContent, CardMedia, Typography } from '@mui/material';
const  ProductCard = ({ product, isBuyProductModel, isViewCartModel }) => {
  return (
    <div>
       <Card sx={{ maxWidth: 345 }}>
        <CardMedia
          component="img"
          alt="product type"
          height="140"
          image=""
          />
          <CardContent>
            <Typography gutterButton variant='h5' component="div">
              {product.name}
            </Typography>
            <Typography variant='body2' color='text.secondary'>
              {product.description}
            </Typography>
            <Typography variant='body2' color='text.secondary'>
              {product.price}
            </Typography>
            <Typography variant='body2' color='text.secondary'>
              {product.quantity}
            </Typography>
          </CardContent>
          <CardActions>
            <Button size='small'>Add to Favorites</Button>
            <Button size='small'>Add to cart</Button> 
          </CardActions>
      </Card>
    </div>
  )
}


export default ProductCard