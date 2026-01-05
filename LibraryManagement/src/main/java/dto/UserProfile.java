package dto;

public class UserProfile {
    String profilepath;
    public String getProfilePicPath(){
        return profilepath;
    }


    public static void main(String[] args) {
        UserDto presentUser = new UserDto();
        System.out.println(presentUser.getProfilePicPath());
        System.out.println("./images/ben.jpg");

    }
}
