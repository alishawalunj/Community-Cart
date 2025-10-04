import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage.jsx';
import DashBoard from './pages/DashBoard.jsx';
import Marketplace from './components/Marketplace.jsx';
import Intro from './components/Intro.jsx';
import Options from './components/Options.jsx';
import Community from './components/Community.jsx';
import CommunityRegistration from './pages/CommunityRegistration.jsx';
import SellProductForm from './pages/SellProductForm.jsx';
import LoginSignup from './pages/LoginSignup.jsx';
import Login from './components/Login.jsx';
import Signup from './components/Signup.jsx';
import Layout from './pages/Layout.jsx';
import UserProfile from './pages/UserProfile.jsx';
import Buy from './pages/BuyProduct.jsx';

const AppRoutes = () => {
  return (
    <Routes>
      {/* Public routes */}
      <Route path="/" element={<HomePage />} />
      <Route path="/intro" element={<Intro />} />
      <Route path="/loginsignup" element={<LoginSignup />} />
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />

      {/* Routes with Layout (sidebar/header persistent) */}
      <Route element={<Layout />}>
        <Route path="/dashboard" element={<DashBoard />} />
        <Route path="/marketplace" element={<Marketplace />} />
        <Route path="/options" element={<Options />} />
        <Route path="/community" element={<Community />} />
        <Route path="/communityregistration" element={<CommunityRegistration />} />
        <Route path="/sell" element={<SellProductForm />} />
        <Route path="/profile" element={<UserProfile />} />
        <Route path="/buy" element={<Buy />} />
      </Route>
    </Routes>
  );
};

export default AppRoutes;
