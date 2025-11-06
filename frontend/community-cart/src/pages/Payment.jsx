import React, { useState } from "react";
import { useLocation } from "react-router-dom";

const Payment = () => {
    const location = useLocation();
    const summary = location.state?.summary;
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        cardNumber: "",
        expiry: "",
        cvv: "",
        amount: "",
    });

    const [submitted, setSubmitted] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
        ...prev,
        [name]: value,
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (
        !formData.name ||
        !formData.email ||
        !formData.cardNumber ||
        !formData.expiry ||
        !formData.cvv ||
        !formData.amount
        ) {
        alert("Please fill in all fields");
        return;
        }
        console.log("Payment submitted:", formData);
        setSubmitted(true);
    };

    if (submitted) {
        return (
        <div className="flex items-center justify-center h-screen text-center">
            <div className="p-8 rounded-2xl shadow-lg bg-white max-w-md">
            <h2 className="text-2xl font-semibold text-green-600">
                Payment Successful!
            </h2>
            <p className="mt-3 text-gray-600">
                Thank you, {formData.name}. Your payment of ${formData.amount} has been processed.
            </p>
            </div>
        </div>
        );
    }

    return (
        <div className="flex items-center justify-center h-screen
        bg-gradient-to-b from-sky-400 via-indigo-500 to-fuchsia-500 
    hover:from-fuchsia-500 via-indigo-500 to-sky-400 
    transition-all duration-500">
        <form
            onSubmit={handleSubmit}
            className="bg-white shadow-lg rounded-2xl p-8 w-full max-w-md"
        >
            <h2 className="text-2xl font-semibold text-center mb-6">
            💳 Payment Form
            </h2>

            <label className="block mb-2 text-sm font-medium">Full Name</label>
            <input
            type="text"
            name="name"
            placeholder="John Doe"
            value={formData.name}
            onChange={handleChange}
            className="w-full p-2 border rounded-lg mb-4"
            />

            <label className="block mb-2 text-sm font-medium">Email</label>
            <input
            type="email"
            name="email"
            placeholder="john@example.com"
            value={formData.email}
            onChange={handleChange}
            className="w-full p-2 border rounded-lg mb-4"
            />

            <label className="block mb-2 text-sm font-medium">Card Number</label>
            <input
            type="text"
            name="cardNumber"
            placeholder="1234 5678 9012 3456"
            value={formData.cardNumber}
            onChange={handleChange}
            className="w-full p-2 border rounded-lg mb-4"
            maxLength={16}
            />

            <div className="flex gap-4">
            <div className="flex-1">
                <label className="block mb-2 text-sm font-medium">Expiry</label>
                <input
                type="text"
                name="expiry"
                placeholder="MM/YY"
                value={formData.expiry}
                onChange={handleChange}
                className="w-full p-2 border rounded-lg mb-4"
                />
            </div>
            <div className="flex-1">
                <label className="block mb-2 text-sm font-medium">CVV</label>
                <input
                type="password"
                name="cvv"
                placeholder="123"
                value={formData.cvv}
                onChange={handleChange}
                className="w-full p-2 border rounded-lg mb-4"
                maxLength={3}
                />
            </div>
            </div>

            <label className="block mb-2 text-sm font-medium">Amount ($)</label>
            <input
            type="number"
            name="amount"
            placeholder="50"
            value={formData.amount}
            onChange={handleChange}
            className="w-full p-2 border rounded-lg mb-6"
            />

            <button
            type="submit"
            className="w-full bg-blue-600 text-white font-semibold py-2 rounded-lg hover:bg-blue-700 transition"
            >
            Pay Now
            </button>
        </form>
        </div>
    );
    };

    export default Payment;
