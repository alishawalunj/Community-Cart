import { useState } from "react";
import UserProfileTab from "../components/UserProfileTab";
import UserProductsTab from "../components/UserProductsTab";
import UserOrdersTab from "../components/UserOrdersTab";

const UserProfile = () => {
  const [activeTab, setActiveTab] = useState("profile");

  return (
    <div className="min-h-screen w-full bg-gradient-to-r from-yellow-300 via-pink-300 to-orange-300 flex flex-col items-center">
      <div className="mt-16 flex justify-center space-x-12">
        {[
          { label: "Profile", key: "profile" },
          { label: "Products", key: "products" },
          { label: "Orders", key: "orders" },
        ].map(({ label, key }) => (
          <span
            key={key}
            onClick={() => setActiveTab(key)}
            className={`text-2xl font-bold cursor-pointer px-6 py-3 rounded-lg
              transition-all duration-300
              ${
                activeTab === key
                  ? "bg-white/20 backdrop-blur-md text-black underline underline-offset-8 decoration-4"
                  : "text-white/80 hover:text-white hover:bg-white/10 backdrop-blur-sm"
              }`}
          >
            {label}
          </span>
        ))}
      </div>

      <div className="flex-grow w-full flex justify-center items-start mt-10 px-6 md:px-12 pb-12">
        <div className="w-full md:w-11/12 lg:w-4/5 xl:w-3/4 flex flex-col h-full">
          <div className="flex-grow w-full bg-white/10 backdrop-blur-md rounded-xl p-8 md:p-12 shadow-lg">
            {activeTab === "profile" && <UserProfileTab />}
            {activeTab === "products" && <UserProductsTab />}
            {activeTab === "orders" && <UserOrdersTab />}
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfile;
