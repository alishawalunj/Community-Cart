import './App.css'
import Navbar from './components/Navbar/Navbar.jsx'
import { Routes, Route } from 'react-router-dom'
import Options from './pages/options.jsx'
import Intro from './pages/Intro.jsx'
import Login from './components/Login/Login.jsx'
  const App = () => {
    return (
      <Routes>
        <Route path="/" element={<Intro />} />
        <Route path="/options" element={<Options />} />
        <Route path="/login" element={<Login></Login>}/>
      </Routes>
    );
  };
  
  export default App;