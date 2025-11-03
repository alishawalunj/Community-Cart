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
import AddIcon from '@mui/icons-material/Add';
import { useCommunity } from '../hooks/useCommunity';

const CommunityCard = ({ community, mode = 'member', refetchCommunities }) => {
  const { getCommunityUsers, removeUsersFromCommunity, addUsersToCommunity, deleteCommunity } = useCommunity();
  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(false);

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

  const fetchMembers = async () => {
    try {
      setLoading(true);
      const users = await getCommunityUsers(community.communityId);
      setMembers(users);
    } catch (error) {
      console.error("Error fetching members :", error);
    } finally {
      setLoading(false);
    }
  };

  const handleLeaveCommunity = async () => {
    const confirmed = window.confirm("Are you sure you want to leave the community?");
    if (!confirmed) return;

    try {
      const userId = localStorage.getItem("userId");
      await removeUsersFromCommunity(community.communityId, userId);
      alert("You have left the community");
      if (refetchCommunities) refetchCommunities();
    } catch (error) {
      console.error("Error leaving community", error);
    }
  };

  const handleJoinCommunity = async () => {
    try {
      const userId = localStorage.getItem("userId");
      await addUsersToCommunity(community.communityId, userId);
      alert("You joined the community!");
      if (refetchCommunities) refetchCommunities();
    } catch (error) {
      console.error("Error joining community", error);
    }
  };

  const handleDeleteCommunity = async () => {
    const confirmed = window.confirm("Are you sure you want to delete this community?");
    if (!confirmed) return;

    try {
      await deleteCommunity(community.communityId);
      alert("Community deleted successfully!");
      if (refetchCommunities) refetchCommunities();
    } catch (error) {
      console.error("Error deleting community", error);
    }
  };

  return (
    <div className="flex items-center justify-center">
      <div
        className="container"
        onMouseEnter={() => {
          if (mode === 'member' && members.length === 0) fetchMembers();
        }}
      >
        <div className="card">
          <div className="front">
            <div className="front-image h-1/2 w-full">
              <img
                src={community.image || "https://via.placeholder.com/300x150"}
                alt={community.name}
                className="w-full h-full object-cover rounded-t-lg"
              />
            </div>
            <div className="mb-4 flex flex-col items-center justify-center bg-white p-4">
              <h2 className="text-gray-900 font-bold text-xl mb-2">{community.name}</h2>
              <p className="text-gray-700 text-base">{community.description}</p>
            </div>
          </div>

          <div className="back h-full flex flex-col items-center justify-center">
            {mode === 'member' && (
              <>
                <h2>Members</h2>
                <div className="flex flex-col items-center justify-center w-full">
                  <TableContainer component={Paper} sx={{ boxShadow: 'none', width: '90%' }}>
                    <Table aria-label="customized table">
                      <TableHead>
                        <TableRow>
                          <StyledTableCell align="center">Role</StyledTableCell>
                          <StyledTableCell align="center">Name</StyledTableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {members.map((member, index) => (
                          <StyledTableRow key={index}>
                            <StyledTableCell align="center">{member.role || "Member"}</StyledTableCell>
                            <StyledTableCell align="center">{member.firstName}</StyledTableCell>
                          </StyledTableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </TableContainer>
                </div>
                <div className="flex flex-row items-center justify-center w-full mt-4">
                  <Button variant="outlined" startIcon={<DeleteIcon />} onClick={handleLeaveCommunity}>
                    Leave Community
                  </Button>
                </div>
              </>
            )}

            {mode === 'explore' && (
              <Button
                variant="contained"
                startIcon={<AddIcon />}
                onClick={handleJoinCommunity}
              >
                Join Community
              </Button>
            )}

            {mode === 'owned' && (
              <>
                <h2>Members</h2>
                <div className="flex flex-col items-center justify-center w-full">
                  <TableContainer component={Paper} sx={{ boxShadow: 'none', width: '90%' }}>
                    <Table aria-label="customized table">
                      <TableHead>
                        <TableRow>
                          <StyledTableCell align="center">Role</StyledTableCell>
                          <StyledTableCell align="center">Name</StyledTableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {members.map((member, index) => (
                          <StyledTableRow key={index}>
                            <StyledTableCell align="center">{member.role || "Member"}</StyledTableCell>
                            <StyledTableCell align="center">{member.firstName}</StyledTableCell>
                          </StyledTableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </TableContainer>
                </div>
                <Button
                  variant="outlined"
                  color="error"
                  startIcon={<DeleteIcon />}
                  onClick={handleDeleteCommunity}
                >
                  Delete Community
                </Button>
              </>              
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CommunityCard;
