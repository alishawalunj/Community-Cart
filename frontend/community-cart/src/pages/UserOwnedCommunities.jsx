const UserOwnedCommunities = () => {
    return (
        <div className="min-h-screen flex flex-col bg-gray-100 w-full">
            <div className="flex flex-col h-1/4 justity-left item-center text-5xl font-bold px-8 py-4 relative">
                <h1>Your Created Communities</h1>
                <div className="absolute right-8  transform -translate-y-1/2 text-3xl">
                    <p>List of communities you have created will be displayed here.</p>
                </div>
            </div>
        </div>
    );
}
export default UserOwnedCommunities;