import React from 'react';
import { Button } from '@mui/material';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';

const BackButton = ({ onClick }) => (
  <Button
    startIcon={<ArrowUpwardIcon />}
    onClick={onClick}
    sx={{
      textTransform: 'none',
      padding: 0,
      minWidth: 0,
      '&:hover': {
        textDecoration: 'underline',
        backgroundColor: 'transparent', 
      },
    }}
  >
    Back
  </Button>
);

export default BackButton;
