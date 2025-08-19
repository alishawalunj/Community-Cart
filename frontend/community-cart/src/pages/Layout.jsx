import  { useState } from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../components/Sidebar';

const Layout = () => {
  const [isExpanded, setIsExpanded] = useState(false);

  return (
    <div className="flex">
      {/* Sidebar */}
      <Sidebar isExpanded={isExpanded} setIsExpanded={setIsExpanded} />

      {/* Main content */}
      <div className="flex-1 bg-gray-100 min-h-screen overflow-y-auto transition-all duration-300">
        <Outlet />
      </div>
    </div>
  );
};

export default Layout;
