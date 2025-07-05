import React, { useState } from 'react';
import "../styles/CardComponent.css";
import { styled } from '@mui/material';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';

const CommunityCard = ({ community }) => {
 
  // Table Styling
  const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
      backgroundColor: theme.palette.common.black,
      color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
      fontSize: 14,
    },
  }));
  
  const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.action.hover,
    },
    '&:last-child td, &:last-child th': {
      border: 0,
    },
  }));

  const leaveCommunity = () =>{
    alert("Are you sure you wan to leave the community?");
  }

  return (
    <div className="flex items-center justify-center">
      <div className="container ">
        <div className="card">
          <div className="front">
            <div className="front-image h-1/2 w-full">
            </div>
            <div className="mb-4 flex flex-col items-center justify-center bg-white p-4">
              <h2 className="text-gray-900 font-bold text-xl mb-2">{community.title}</h2>
              <p className="text-gray-700 text-base">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                Voluptatibus quia, nulla! Maiores et perferendis eaque,
                exercitationem praesentium nihil.
              </p>
            </div>
          </div>
          <div className="back h-full">
            <h2>Members</h2>
            <div className='flex flex-col items-center justify-center w-full'>
              <TableContainer component={Paper} sx={{boxShadow: 'none' , width: '90%'}}>
                <Table aria-label="customized table">
                  <TableHead>
                    <TableRow>
                      <StyledTableCell align="center">Role</StyledTableCell>
                      <StyledTableCell align="center">Name</StyledTableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {community.members.map((member,index) => (
                      <StyledTableRow key={index} sx={{'&:last-child td, &:last-child th': { border: 0 } }}>
                        <StyledTableCell align="center" component="th" scope="row">{member.role}</StyledTableCell>
                        <StyledTableCell align="center">{member.name}</StyledTableCell>
                      </StyledTableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </div>
            <div className='flex flex-row items-center justify-center w-full mt-4'>
                <Button variant="outlined" startIcon={<DeleteIcon/>} onClick={leaveCommunity}>Leave Community</Button>
            </div>
          </div>
          </div>
        </div>   
    </div>
  );
}

export default CommunityCard;
