import React, { useEffect, useState } from "react";
import { FiEdit } from "react-icons/fi";
import { useUser } from "../hooks/useUser";

const UserProfileTab = () => {
  const [user, setUser] = useState(null);
  const { getUserById, updateUser } = useUser();
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({});
  const [profileImage, setProfileImage] = useState("https://via.placeholder.com/150");

  const handleEdit = () => {
    setFormData(user);
    setIsEditing(true);
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleCancel = () => setIsEditing(false);

  const handleSave = async () => {
    try {
      const updatedUser = await updateUser(formData);
      setUser(updatedUser);
      setIsEditing(false);
      alert("Profile updated successfully!");
    } catch (error) {
      console.error("Error updating user", error);
      alert("Failed to update profile");
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) setProfileImage(URL.createObjectURL(file));
  };

  useEffect(() => {
    const fetchUser = async () => {
      const userId = localStorage.getItem("userId");
      const user = await getUserById(userId);
      if (user) setUser(user);
    };
    fetchUser();
  }, []);

  if (!user) return <p className="text-center text-white text-xl">Loading...</p>;

  return (
    <div className="flex flex-col md:flex-row bg-white bg-opacity-20 backdrop-blur-md rounded-xl shadow-lg overflow-hidden p-8 gap-8">
      {/* Profile Image */}
      <div className="flex flex-col items-center justify-center w-full md:w-1/3">
        <div className="relative">
          <img
            src={profileImage}
            alt="User"
            className="w-40 h-40 md:w-60 md:h-60 rounded-full object-cover border-4 border-white shadow-lg"
          />
          <label
            htmlFor="profileImageUpload"
            className="absolute bottom-0 right-0 bg-blue-600 text-white rounded-full p-2 cursor-pointer hover:bg-blue-800"
          >
            <FiEdit className="h-5 w-5" />
            <input
              id="profileImageUpload"
              type="file"
              accept="image/*"
              onChange={handleImageChange}
              className="hidden"
            />
          </label>
        </div>
      </div>

      {/* User Info */}
      <div className="flex flex-col justify-center w-full md:w-2/3 space-y-5 text-gray-900">
        <div className="flex justify-end">
          {!isEditing && (
            <button onClick={handleEdit} className="text-gray-700 hover:text-gray-900">
              <FiEdit className="h-6 w-6" />
            </button>
          )}
        </div>

        {["firstName", "lastName", "email", "password"].map((field) => (
          <div key={field} className="flex items-center space-x-4">
            <label className="font-semibold w-32 capitalize">{field}:</label>
            {isEditing ? (
              <input
                type="text"
                name={field}
                value={formData[field]}
                onChange={handleChange}
                className="flex-grow p-3 rounded-md border border-gray-300 focus:ring-blue-500 focus:border-blue-500"
              />
            ) : (
              <p className="flex-grow">{user[field]}</p>
            )}
          </div>
        ))}

        {isEditing && (
          <div className="flex justify-end gap-4 mt-6">
            <button
              onClick={handleCancel}
              className="px-6 py-2 rounded-md border text-gray-700 hover:bg-gray-100"
            >
              Cancel
            </button>
            <button
              onClick={handleSave}
              className="px-6 py-2 rounded-md bg-blue-600 text-white hover:bg-blue-800"
            >
              Save
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default UserProfileTab;
