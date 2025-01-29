import { React, useState } from 'react';

const CommunityRegistration = (onCommunityRegistrationclick) =>{

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
        alert(`Community Name: ${formData.communityName}
        Community Description: ${formData.communityDescription}
        Owner: ${formData.owner}
        Created On: ${formData.createdOn}`);
    }

    return(
        <div>
            <h1 className= "text-4xl bold flex justify-center items-center">Community Details</h1>
            <div className="">
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label htmlFor="communityName" className="block text-gray-700 font-bold mb-2">Community Name</label>
                        <input type="text" id="communityName" name="communityName" value={formData.communityName} onChange={handleChange} className="border rounded-lg px-4 py-2 w-full" required />    

                        <label htmlFor="communityDescription" className="block text-gray-700 font-bold mb-2">Community Description</label>
                        <input type="text" id="communityDescription" name="communityDescription" value={formData.communityDescription} onChange={handleChange} className="border rounded-lg px-4 py-2 w-full" required />

                        <label htmlFor="owner" className="block text-gray-700 font-bold mb-2">Owner</label>
                        <input type="text" id="owner" name="owner" value={formData.owner} onChange={handleChange} className="border rounded-lg px-4 py-2 w-full" required />    
                        
                    
                        <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">Submit</button> 

                    </div>
                </form>
            </div>
        </div>
    )
}

export default CommunityRegistration