import { useState } from 'react';
import { useCommunity } from '../hooks/useCommunity';

const CommunityRegistration = () =>{

    const currentTime = new Date();
    const formattedTime = currentTime.toLocaleString();
    const userId = localStorage.getItem('userId');
    const [ image, setImage ] = useState();
    const [previewImage, setPreviewImage] = useState(null);
    const { createCommunity, uploadCommunityImage } = useCommunity();

    const [formData, setFormData] = useState({
        name:'',
        description:'',
        owner: userId ||'',
        createdOn: currentTime ||'',
    });

    const handleChange = (e) =>{
        const { name, value } = e.target;
        setFormData((prevFormData) => ({
        ...prevFormData,[name]: value
        }));
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if(file){
            setImage(file);
            const reader = new FileReader();
            reader.onload = () => {
                setPreviewImage(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            const createdCommunityResponse = await createCommunity(formData);
            if(!createdCommunityResponse || !createdCommunityResponse.communityId){
                alert("Error in creating community");
                return;
            }
            const communityId = createdCommunityResponse.communityId;
            if(image){
                const formDataToSend = new FormData();
                formDataToSend.append("file", image);
                try{
                    await uploadCommunityImage(communityId, formDataToSend);
                }catch(error){
                    console.log("Error uploading image", error);
                    alert("Failed to upload image");
                }
            }
            alert('Community created successfully!');
            setFormData({
                name: '',
                description: '',
                owner: userId || '',
                createdOn: formattedTime,
            });
            setImage(null);
            setPreviewImage(null);
        }catch(error){
            console.log("Error ", error);
            alert("Failed to create community");
        }
    }

    return (
    <div className="min-h-screen flex items-center bg-gradient-to-bl from-purple-600 via-pink-500 to-indigo-500 hover:from-indigo-500 via-pink-500 to-purple-600 justify-center">
      <div className="w-1/2 flex flex-col">
        <h1 className="text-4xl font-bold flex justify-center items-center mb-6">
          New Community Details
        </h1>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="name" className="block text-gray-700 font-bold mb-2">
              Community Name
            </label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              className="border rounded-lg px-4 py-2 w-full"
              required
            />

            <label htmlFor="description" className="block text-gray-700 font-bold mb-2 mt-4">
              Community Description
            </label>
            <textarea
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              className="border rounded-lg px-4 py-2 w-full"
              rows="3"
              required
            />

            <label htmlFor="photo" className="block text-gray-700 font-bold mb-2 mt-4">
              Community Image
            </label>
            <input
              type="file"
              accept="image/*"
              onChange={handleImageChange}
              className="border rounded-lg px-4 py-2 w-full"
            />

            {previewImage && (
              <div className="mt-4 flex justify-center">
                <img
                  src={previewImage}
                  alt="Preview"
                  className="w-32 h-32 object-cover rounded-full border"
                />
              </div>
            )}

            <button
              type="submit"
              className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 mt-6 rounded-full w-full"
            >
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CommunityRegistration;