import './login.css';
const Login = () =>{
    return (
        <div className = "login">
            <div className = "loginWrapper">
                <form action="">
                    <h1>Login Here</h1>
                    <input type="text" placeholder="username" required/>
                    <input type="password" placeholder='password' required/>
                    <div className="remember-forget">
                        <label><input type="checkbox"/>   Remember me</label>
                        <a href="forgotPassword.jsx">Forgot Password?</a>
                    </div>
                    <button>Login</button>
                    <div className="register">
                        <p>Don't have an account?<a href="register.jsx">Register here</a></p>
                    </div>
                </form>
            </div>

        </div>
    )
}

export default Login;