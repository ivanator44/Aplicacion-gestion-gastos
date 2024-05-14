package extras;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author ivana
 */
public class Utils {
    // Métodos para comprobar que los campos son correctos
    
    /**
     *  A password is considered valid if follows an accepted email syntax:
     *  name@domain.com
     * @param email String which contains the email to check  
     * @return  True if the email is valid. False otherwise.  
     */
    
    public static Boolean checkName (String name){
        if (name == null){
          return false; 
        }
        
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        
        return matcher.matches();
    }
    
    public static Boolean checkSurname (String apellido){
        if (apellido == null){
            return true;
        }
        if (apellido.equals("")){
            return true;
        }
        
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(apellido);
        
        return matcher.matches();
    }
    
    public static Boolean checkNickname (String nickname){
        if (nickname == null){
          return false; 
        }
        
        String regex = "^[a-zA-Z0-9_\\-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nickname);
        
        return matcher.matches();
    }
    
    public static Boolean checkEmail (String email){   
        if (email == null){
          return false; 
        }
        // Regex to check valid email. 
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+(?:com|es|org|net|edu|gov|mil|info|biz)$";
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex);
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static Boolean checkPassword(String password){    
        // If the password is empty , return false 
        if (password == null) { return false; } 
        // Caracterñiasticas de la contraseña 
        String regex = "^(?=.*[A-Za-z0-9])(?=.*[@#$%^&+=_-])(?=\\S+$).{8,15}$";
        // Compilar reGex 
        Pattern pattern = Pattern.compile(regex); 
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    }   
    
    public static Boolean checkPasswordRep(String password, String passwordRep){     
        // If the password is empty , return false 
        if (password == null) { 
            return false; 
        } 
        return password.equals(passwordRep);
    } 
}
