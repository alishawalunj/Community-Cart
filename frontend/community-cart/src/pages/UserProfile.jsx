// import React, { useEffect, useState } from 'react';
// import { FiEdit } from 'react-icons/fi';
// import { useUser } from '../hooks/useUser';

// const UserProfile = () => {
//   const [ user, setUser ] = useState(null);
//   const { getUserById, updateUser } = useUser();
//   const [isEditing, setIsEditing] = useState(false);
//   const [formData, setFormData] = useState({});
//   const [profileImage, setProfileImage] = useState("https://via.placeholder.com/150");
//   const [refresh, setRefresh ] = useState(false);

//   const handleEdit = () => {
//     setFormData(user);
//     setIsEditing(true);
//   };

//   const handleChange = (e) => {
//     setFormData({
//       ...formData,
//       [e.target.name]: e.target.value,
//     });
//   };

//   const handleCancel = () => {
//     setIsEditing(false);
//   };

//   const handleSave = async () => {
//     try{
//       // const userId = localStorage.getItem("userId");
//       const updatedUser = await updateUser(formData);
//       console.log("Updated User", updatedUser);
//       setUser(updatedUser);
//       setIsEditing(false);
//       alert("Profile updates successfully !!");
//     }catch(error){
//       console.error("Error updating user", error);
//       alert("Failed to update profile");
//     }
//   };
//   const handleImageChange = (e) => {
//     const file = e.target.files[0];
//     if (file) {
//       const imageUrl = URL.createObjectURL(file);
//       setProfileImage(imageUrl);
//     }
//   };

//   useEffect(() => {
//     const fetchUser = async () => {
//       const userId = localStorage.getItem("userId");
//       const user = await(getUserById(userId));
//       console.log("printing usssser",user);
//       if(!user){
//         return;
//       }
//       setUser(user);
//     }
//     fetchUser();
//   }, [])

//   return !user ? (
//     <p>Loading...</p>
//   ):(
//     <div className="flex min-h-screen bg-gradient-to-r from-orange-300  via-blue-500 to-purple-500 hover:from-purple-500 hover:via-blue-500 hover:to-purple-500">
//       <div className="w-1/4 h-screen flex items-center justify-center">
//         <div className="relative">
//           <img
//             src={profileImage}
//             alt="User Profile"
//             className="w-60 h-60 rounded-full object-cover border-4 border-white shadow-md"
//           /> 
//           <label
//             htmlFor="profileImageUpload"
//             className="absolute bottom-0 right-0 bg-blue-600 text-white rounded-full p-2 cursor-pointer hover:bg-blue-800"
//           >
//             <FiEdit className="h-5 w-5" />
//             <input
//               id="profileImageUpload"
//               type="file"
//               accept="image/*"
//               onChange={handleImageChange}
//               className="hidden"
//             />
//           </label>
//         </div>
//       </div>
//       <div className="w-3/4 p-8 flex flex-col items-center justify-center relative">
//         <div className="w-full flex justify-end absolute top-8 right-8">
//           {!isEditing && (
//             <button onClick={handleEdit} className="text-gray-500 hover:text-gray-700">
//               <FiEdit className="h-6 w-6" />
//             </button>
//           )}
//         </div>
//         <div className="w-full max-w-3xl bg-white bg-opacity-20 backdrop-blur-md rounded-xl p-8 flex flex-col gap-6 shadow-lg">
//           <div className="flex flex-col space-y-6 w-full max-w-3xl">
//             {/* Name Field */}
//             <div className="flex items-center space-x-6 ">
//               <label className="font-medium text-black-700 w-32 flex-shrink-0">First Name:</label>
//               {isEditing ? (
//                 <input
//                   type="text"
//                   name="firstName"
//                   value={formData.firstName}
//                   onChange={handleChange}
//                   className="flex-grow p-4 text-xl rounded-md border-gray-300 shadow-sm focus:ring-blue-500 focus:border-blue-500"
//                 />
//               ) : (
//                 <p className="flex-grow text-lg text-gray-900">{user.firstName}</p>
//               )}
//             </div>
//             {/* Email Field */}
//             <div className="flex items-center space-x-6">
//               <label className="font-medium text-black-700 w-32 flex-shrink-0">Last Name:</label>
//               {isEditing ? (
//                 <input
//                   type="text"
//                   name="lastName"
//                   value={formData.lastName}
//                   onChange={handleChange}
//                   className="flex-grow p-4 text-xl rounded-md border-gray-300 shadow-sm focus:ring-blue-500 focus:border-blue-500"
//                 />
//               ) : (
//                 <p className="flex-grow text-lg text-gray-900">{user.lastName}</p>
//               )}
//             </div>
//             {/* Contact Field */}
//             <div className="flex items-center space-x-6 ">
//               <label className="font-medium text-black-700 w-32 flex-shrink-0">Email:</label>
//               {isEditing ? (
//                 <input
//                   type="text"
//                   name="email"
//                   value={formData.email}
//                   onChange={handleChange}
//                   className="flex-grow p-4 text-xl rounded-md border-gray-300 shadow-sm focus:ring-blue-500 focus:border-blue-500"
//                 />
//               ) : (
//                 <p className="flex-grow text-lg text-gray-900">{user.email}</p>
//               )}
//             </div>
//             {/* Address Field */}
//             <div className="flex items-center space-x-6 ">
//               <label className="font-medium text-black-700 w-32 flex-shrink-0">Password :</label>
//               {isEditing ? (
//                 <input
//                   type="text"
//                   name="password"
//                   value={formData.password}
//                   onChange={handleChange}
//                   className="flex-grow p-4 text-xl rounded-md border-gray-300 shadow-sm focus:ring-blue-500 focus:border-blue-500"
//                 />
//               ) : (
//                 <p className="flex-grow text-lg text-gray-900">{user.password}</p>
//               )}
//             </div>
//           </div>
//           {isEditing && (
//             <div className="flex justify-end gap-4 mt-8 w-full max-w-3xl">
//               <button
//                 onClick={handleCancel}
//                 className="px-6 py-3 rounded-md border border-gray-200 text-gray-700 hover:bg-gray-100 text-xl"
//               >
//                 Cancel
//               </button>
//               <button
//                 onClick={handleSave}
//                 className="px-6 py-3 rounded-md border bg-blue-600 text-white hover:bg-blue-800 text-xl"
//               >
//                 Save
//               </button>
//             </div>
//           )}
//         </div>
//       </div>
//     </div>
//   );
// };

// export default UserProfile;
import React, { useState } from "react";
import UserProfileTab from "../components/UserProfileTab";
import UserProductsTab from "../components/UserProductsTab";
import UserOrdersTab from "../components/UserOrdersTab";

const UserProfile = () => {
  const [activeTab, setActiveTab] = useState("profile");

  return (
    <div className="min-h-screen w-full bg-gradient-to-b from-blue-400 via-purple-500 to-pink-500 flex flex-col">
      {/* Tabs Header */}
      <div className="bg-white/20 backdrop-blur-md py-6 shadow-md flex justify-center space-x-12">
        <span
          onClick={() => setActiveTab("profile")}
          className={`text-2xl font-bold text-white cursor-pointer transition-all ${
            activeTab === "profile"
              ? "underline underline-offset-8 decoration-4 decoration-white"
              : "opacity-70 hover:opacity-100"
          }`}
        >
          Profile
        </span>

        <span
          onClick={() => setActiveTab("products")}
          className={`text-2xl font-bold text-white cursor-pointer transition-all ${
            activeTab === "products"
              ? "underline underline-offset-8 decoration-4 decoration-white"
              : "opacity-70 hover:opacity-100"
          }`}
        >
          Products
        </span>

        <span
          onClick={() => setActiveTab("orders")}
          className={`text-2xl font-bold text-white cursor-pointer transition-all ${
            activeTab === "orders"
              ? "underline underline-offset-8 decoration-4 decoration-white"
              : "opacity-70 hover:opacity-100"
          }`}
        >
          Orders
        </span>
      </div>

      {/* Content Area */}
      <div className="flex-grow overflow-y-auto px-8 py-10">
        {activeTab === "profile" && <UserProfileTab />}
        {activeTab === "products" && <UserProductsTab />}
        {activeTab === "orders" && <UserOrdersTab />}
      </div>
    </div>
  );
};

export default UserProfile;
