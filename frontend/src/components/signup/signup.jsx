import react from "react";
import "./signup.css";
import { Link } from "react-router-dom";

const signup = () => {
    return (
        <div className="signup">
            <div className="signupWrapper">
                <form action="">
                    <h1>Let's Register</h1>
                    <input type="email" placeholder="Email" required/>
                    <input type="text" placeholder="Username" required/>
                    <input type ="password" placeholder="Password" required/>
                    <input type ="password" placeholder="Confirm Password" required/>
                    <button>Sign Up!!</button>
                    <div className="login">
                        <p>Already have an account?<Link to = "/login">Login Here</Link></p>
                    </div>
                </form>
            </div>
            
        </div>
    )
}