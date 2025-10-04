/* eslint-disable react/prop-types */
import { Link, useNavigate } from 'react-router-dom';
import { MdHome } from 'react-icons/md';
import { TbLayoutSidebarRightCollapse, TbLayoutSidebarLeftExpand } from "react-icons/tb";
import { IoInformationCircleOutline } from "react-icons/io5";
import { CgProfile } from "react-icons/cg";
import { useAuth } from '../context/AuthContext';
import { RiLogoutCircleLine } from "react-icons/ri";

const Sidebar = ({ isExpanded, setIsExpanded}) => {
  const navigate = useNavigate();
  const { logout } = useAuth();

  const Menus = [
    { title: 'Dashboard', icon: <MdHome />, path: '/dashboard' },
    { title: 'Profile', icon: <CgProfile />, path: '/profile' },
    { title: 'Home', icon: <MdHome />, path: '/' },
    { title: 'About', icon: <IoInformationCircleOutline />, path: '/about' },
    { title: 'Logout', icon: <RiLogoutCircleLine />, action: () => { logout(); navigate('/'); } },
  ];

  return (
    <div className={`h-screen bg-zinc-900 pt-8 relative duration-300 ease-in-out`} style={{ width: isExpanded ? '18rem' : '5rem' }}>
      <div
        className={`absolute cursor-pointer -right-4 top-9 w-8 h-8 p-0.5 bg-zinc-50 border-2 
          rounded-full flex justify-center items-center border-zinc-50 
          ${!isExpanded && "rotate-180"} transition-all ease-in-out duration-300`}
        onClick={() => setIsExpanded(!isExpanded)}
      >
        {isExpanded ? <TbLayoutSidebarLeftExpand /> : <TbLayoutSidebarRightCollapse />}
      </div>

      <div className='flex gap-x-4 items-center px-4'>
        <h1 className={`text-white origin-left font-semibold text-xl duration-200 ease-in-out ${!isExpanded && "scale-0"}`}>
          Community Cart
        </h1>
      </div>

      {/* Menu List */}
      <ul className='pt-6 space-y-0.5'>
        {Menus.map((menu, index) => (
          <li key={index}>
            {menu.action ? (
              <div
                onClick={menu.action}
                className={`flex items-center gap-x-4 rounded-md py-3 px-3 cursor-pointer hover:text-white text-zinc-50 hover:bg-zinc-800/50 transition-all ease-in-out duration-300`}
              >
                <div className='text-2xl'>{menu.icon}</div>
                {isExpanded && <h1 className='origin-left font-medium text-base'>{menu.title}</h1>}
              </div>
            ) : (
              <Link
                to={menu.path}
                className={`flex items-center gap-x-4 rounded-md py-3 px-3 cursor-pointer hover:text-white text-zinc-50 hover:bg-zinc-800/50 transition-all ease-in-out duration-300 ${index === 0 && "bg-zinc-800/40"}`}
              >
                <div className='text-2xl'>{menu.icon}</div>
                {isExpanded && <h1 className='origin-left font-medium text-base'>{menu.title}</h1>}
              </Link>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Sidebar;
