import React from 'react'
import { Link } from 'react-router-dom'
const Navbar = () => {
    return(
        <>
        <div className='flex justify-between items-center bg-purple-300 py-2 px-4'>
            <h1 className='text-2xl font-bold text-black'>Community Cart</h1>
            <ul className='flex gap-4'>
                
            <Link to="/home">   
                <li className='text-lg text-black'>Home</li>
            </Link>
            <Link to="/about">
                <li className="text-lg text-black hover:text-purple-500">About</li>
            </Link>
            <Link to="/contact">
                <li className="text-lg text-black hover:text-purple-500">Contact</li>
            </Link>
            <Link to="/login">
                <li className="text-lg text-black hover:text-purple-500">Login</li>
            </Link>
            </ul>
        </div>
        </> 
    )
}

export default Navbar