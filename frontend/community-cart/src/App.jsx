import './App.css'
// import { BrowserRouter } from 'react-router-dom';
import  AppRoutes from './AppRoutes';
import { AuthProvider } from './context/AuthContext';

  const App = () => {
    return (
      <div className="App">
        <AuthProvider>
            <AppRoutes />
        </AuthProvider>
       
      </div>
    );
  };
  
  export default App;