import AdsAxios from '../apis/AdsAxios';

export const login = async function (credentials) {
    try{
        let response = await AdsAxios.post("/users/login", credentials);
        let token = response.data;
        window.localStorage.setItem("token", token);
        window.location.reload();
    }catch (error) {
        alert("Could not log in!");
    }
};

export const logout = function() {
    window.localStorage.removeItem("token");
    window.location.reload();
}