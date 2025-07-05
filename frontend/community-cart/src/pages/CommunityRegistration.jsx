import { React, useState } from 'react';

const CommunityRegistration = (onCommunityRegistrationclick) =>{

    const currentTime = new Date();
    const formattedTime = currentTime.toLocaleString();


    const [formData, setFormData] = useState({
        communityName:'',
        communityDescription:'',
        owner:'',
        createdOn:''
    });
    const handleChange = (e) =>{
        const { name, value } = e.target;
        setFormData((prevFormData) => ({
        ...prevFormData,[name]: value
        }));
    };

    const handleSubmit = (e) =>{
        e.preventDefault();
        alert(`
        Below Community created successfully!
        Community Name: ${formData.communityName}
        Community Description: ${formData.communityDescription}
        Owner: ${formData.owner}
        Created On: ${ formattedTime}`);
    }

    return(
            <div className='min-h-screen flex items-center justify-center'>
                <div className="w-1/2 flex flex-col">
                    <h1 className= "text-4xl bold flex justify-center items-center">Community Details</h1>
                    <form onSubmit={handleSubmit}>
                        <div className="mb-4">
                            <label htmlFor="communityName" className="block text-gray-700 font-bold mb-2">Community Name</label>
                            <input type="text" id="communityName" name="communityName" value={formData.communityName} onChange={handleChange} className="border rounded-lg px-4 py-2 w-full" required />    

                            <label htmlFor="communityDescription" className="block text-gray-700 font-bold mb-2">Community Description</label>
                            <textarea id="communityDescription" name="communityDescription" value={formData.communityDescription} onChange={handleChange} className="border rounded-lg px-4 py-2 w-full"  rows="3"required />

                            <label htmlFor="owner" className="block text-gray-700 font-bold mb-2">Owner</label>
                            <input type="text" id="owner" name="owner" value={formData.owner} onChange={handleChange} className="border rounded-lg px-4 py-2 w-full" required />    
                    
                            <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 mt-4 rounded-full items-center justify-center">Submit</button> 
                        </div>
                    </form>
                </div>
            </div>
    )
}

export default CommunityRegistration;