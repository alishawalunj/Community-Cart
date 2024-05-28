import './login.css';
const Login = () =>{
    return (
        <div className = "login">
            <div className = "loginWrapper">
                <form action="">
                    <input type="text" placeholder="username"/>
                    <input type="password" placeholder='password'/>
                    <button>Login</button>
                </form>
            </div>

        </div>
    )
}

export default Login;