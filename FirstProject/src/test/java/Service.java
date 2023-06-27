public class Service {


    public String getListUsers() {
        return "https://reqres.in/api/users?page=2";
    }

    public String getSingleUser() {
        return "https://reqres.in/api/users/2";
    }

    public String getSingleUserNotFound() {
        return "https://reqres.in/api/users/23";
    }

    public String getListResource() {
        return "https://reqres.in/api/unknown";
    }

    public String getSingleResource() {
        return "https://reqres.in/api/unknown/2";
    }

    public String getSingleResourceNotFound() {
        return "https://reqres.in/api/unknown/23";
    }

    public String postCreate() {
        return "https://reqres.in/api/users";
    }

    public String putUpdate() {
        return "https://reqres.in/api/users/2";
    }

    public String patchUpdate() {
        return "https://reqres.in/api/users/2";
    }

    public String delete() {
        return "https://reqres.in/api/users/2";
    }

    public String postRegisterSuccessful() {
        return "https://reqres.in/api/register";
    }

    public String postRegisterUnsuccessful() {
        return "https://reqres.in/api/register";
    }

    public String postLoginSuccessful() {
        return "https://reqres.in/api/login";
    }

    public String postLoginUnsuccessful() {
        return "https://reqres.in/api/login";
    }

    public String getDelayedResponse() {
        return "https://reqres.in/api/users?delay=3";
    }


}
