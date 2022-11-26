package com.database.atypon.Node.services.authentication;

import com.database.atypon.Node.model.User;
import com.database.atypon.Node.utils.PathBuilder;
import com.database.atypon.Node.utils.Token;
import com.database.atypon.Node.utils.file_operations.fileReader.FileReader;
import com.database.atypon.Node.utils.response.Response;
import com.database.atypon.Node.utils.response.ResponseType;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.io.File;

@Service
public class AuthenticationService {

    public Response authenticateUser(User user) {
        String pathToInfo = PathBuilder.getPathToMainInfo();

        try{
            FileReader fileReader = new FileReader(new File(pathToInfo));
            fileReader.read();
            JSONObject jsonObject = new JSONObject(fileReader.getContent());

            JSONObject users = jsonObject.getJSONObject("users");
            JSONObject userObject = users.getJSONObject(user.getUsername());
            if (userObject == null)
                return new Response(ResponseType.ERROR, "User not found");
            if (!userObject.getString("password").equals(user.getPassword()))
                return new Response(ResponseType.ERROR, "Wrong password");
            user.setRole(userObject.getString("role"));
            return new Response(ResponseType.SUCCESS, "Login successful", user);
        }catch (Exception e){
            return new Response(ResponseType.ERROR, "User not found");
        }
    }
    public Cookie buildTokenCookie(String token){
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60);
        return cookie;
    }
    public String generateToken(User user) throws Exception{
        if(user.getRole() == null)
            throw new Exception("User role is null");

        return user.getRole();
    }
    private boolean validateToken(String token){
        return !token.isEmpty();
    }
    public boolean isAdminToken(String token){
        if(!validateToken(token))
            return false;
        return token.equals(Token.ADMIN) || token.equals(Token.INTERNAL);
    }
    public boolean isUserToken(String token){
        if(!validateToken(token))
            return false;
        return token.equals(Token.USER) || isAdminToken(token);
    }

    public boolean isInternalToken(String token) {
        if(!validateToken(token))
            return false;
        return token.equals(Token.INTERNAL);
    }
}
