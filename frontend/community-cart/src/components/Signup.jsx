import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useUser } from "../hooks/useUser";
import { useAuth } from "../context/AuthContext";

const Signup = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [emailId, setEmailId] = useState('');
  const [password, setPassword] = useState('');
  const { createUser, login } = useUser();
  const { setAuth, setIsLoggedIn } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Create user
      await createUser({ firstName, lastName, emailId, password });

      // Clear fields
      setFirstName('');
      setLastName('');
      setEmailId('');
      setPassword('');

      // Automatically login
      const user = await login({ emailId, password });
      if (user) {
        const userData = { userId: user.userId, token: user.token };
        setAuth(userData);
        localStorage.setItem("auth", user.token);
        localStorage.setItem("userId", user.userId);
        setIsLoggedIn(true);
        navigate("/dashboard");
      } else {
        alert("Signup successful! Please log in.");
      }
    } catch (error) {
      console.error("Signup error:", error);
      alert("Error during signup: " + (error.message || error));
    }
  };

  return (
    <div className="flex justify-center items-center bg-transparent">
      <div className="w-96 bg-transparent p-6 rounded-lg shadow-lg">
        <div className="mb-4 flex gap-4">
          <div className="w-1/2">
            <label className="block mb-1 font-medium">First Name</label>
            <input
              type="text"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2"
            />
          </div>
          <div className="w-1/2">
            <label className="block mb-1 font-medium">Last Name</label>
            <input
              type="text"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2"
            />
          </div>
        </div>
        <div className="mb-4">
          <label className="block mb-1 font-medium">Email Id</label>
          <input
            type="email"
            value={emailId}
            onChange={(e) => setEmailId(e.target.value)}
            className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2"
          />
        </div>
        <div className="mb-4">
          <label className="block mb-1 font-medium">Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2"
          />
        </div>
        <div className="text-center">
          <button
            className="w-1/4 bg-blue-500 text-white py-3 rounded-lg hover:bg-blue-600 transition"
            onClick={handleSubmit}
          >
            Signup
          </button>
        </div>
      </div>
    </div>
  );
};

export default Signup;
