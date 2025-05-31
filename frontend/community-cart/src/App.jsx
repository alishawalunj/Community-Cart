import './App.css'
import Navbar from './components/Navbar.jsx'
import { Routes, Route } from 'react-router-dom'
import HomePage from  './pages/HomePage.jsx'
import DashBoard from './pages/DashBoard.jsx'
import Marketplace from './components/Marketplace.jsx'
import Intro from './components/Intro.jsx'
import Options from './components/Options.jsx'
import Community from './components/Community.jsx'
import CommunityRegistration from './pages/CommunityRegistration.jsx'
import SellProductForm from './pages/SellProductForm.jsx'
import LoginSignup from './pages/LoginSignup.jsx'
import Login from './components/Login.jsx'
import Signup from './components/Signup.jsx'
import BuyProduct from './pages/BuyProduct.jsx'
import client from './apollo/client.jsx'
import { ApolloProvider } from '@apollo/client';

  const App = () => {
    return (
      // <ApolloProvider client={client}>
        <Routes>
          <Route path="/" element={<HomePage/>} />
          <Route path="/dashboard" element={<DashBoard />} />
          <Route path="/intro" element={<Intro />} />
          <Route path="/marketplace" element={<Marketplace />} />
          <Route path="/login" element={<Login></Login>}/>
          <Route path="/options" element={<Options/>}></Route>
          <Route path="/community" element={<Community/>}></Route>
          <Route path="/communityregistration" element={<CommunityRegistration/>}></Route>
          <Route path="/sell" element={<SellProductForm/>}></Route>
          <Route path="/loginsignup" element={<LoginSignup/>}></Route>
          <Route path="/login" element={<Login/>}></Route>
          <Route path="/signup" element={<Signup/>}></Route>
          <Route path="/buy" element={<BuyProduct/>}></Route>
        </Routes> 

      // </ApolloProvider>
    );
  };
  
  export default App;