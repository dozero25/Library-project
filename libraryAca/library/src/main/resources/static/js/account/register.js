window.onload = () => {
    RegisterEvent.getInstance().addRegisterSubmitOnclickEvent();
}

class RegisterApi {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new RegisterApi();
        }
        return this.#instance;
    }

    register(user) {
        $.ajax({
            async: false,
            type: "post",
            url: "/api/account/register",
            contentType: "application/json",
            data: JSON.stringify(user),
            dataType: "json",
            success: response => {
                console.log(response);
                alert("회원가입 완료. 로그인 페이지로 이동합니다.");
                location.replace("/account/login");
            },
            error: error => {
                console.log(error);
                RegisterService.getInstance().setErrorMessage(error.responseJSON.data);
            }
        });
    }
}


class RegisterService {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new RegisterService();
        }
        return this.#instance;
    }

    setErrorMessage(errors) {
        const registerError = document.querySelectorAll(".register-error");

        this.#clearErrorMessage();

        Object.keys(errors).forEach(error => {
            if(error == "username") {
                registerError[0].textContent = errors[error];
            }else if(error == "password") {
                registerError[1].textContent = errors[error];
            }else if(error == "repassword") {
                registerError[2].textContent = errors[error];
            }else if(error == "name") {
                registerError[3].textContent = errors[error];
            }else if(error == "email") {
                registerError[4].textContent = errors[error];
            }
        });
    }

    #clearErrorMessage() {
        const registerError = document.querySelectorAll(".register-error");
        registerError.forEach(error => {
            error.textContent = "";
        });
    }
}


class RegisterEvent {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new RegisterEvent();
        }
        return this.#instance;
    }

    addRegisterSubmitOnclickEvent() {
        const registerSubmit = document.querySelector(".register-submit");

        registerSubmit.onclick = () => {
            const usernameValue = document.querySelectorAll(".register-inputs")[0].value;
            const passwordValue = document.querySelectorAll(".register-inputs")[1].value;
            const repasswordValue = document.querySelectorAll(".register-inputs")[2].value;
            const nameValue = document.querySelectorAll(".register-inputs")[3].value;
            const emailValue = document.querySelectorAll(".register-inputs")[4].value;

            const user = new User(usernameValue, passwordValue, repasswordValue, nameValue, emailValue);

            RegisterApi.getInstance().register(user);
        }
    }
}

class User {
    username = null;
    password = null;
    repassword = null;
    name = null;
    email = null;

    constructor(username, password, repassword, name, email) {
        this.username = username;
        this.password = password;
        this.repassword = repassword;
        this.name = name;
        this.email = email;
    }
}