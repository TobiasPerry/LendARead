import LoginView from "../views/user/LogIn.tsx";
import {useContext} from "react";
import {AuthContext} from "../contexts/authContext.tsx";

const RequireAuth = ({ children, sameUser }) => {
   const {isLoggedIn} = useContext(AuthContext);

   if (!isLoggedIn)
      return <LoginView redirect={false} />;

   if(sameUser) {
      
   }

   return children;
};

export default RequireAuth;