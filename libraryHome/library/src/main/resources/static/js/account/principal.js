class PrincipalApi {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new PrincipalApi();
        }
        return this.#instance;
    }

    getPrincipal() {
        let responseData = null;

        $.ajax({
            async: false,
            type: "get",
            url: "http://localhost:8000/api/account/principal",
            dataType: "json",
            success : response => {
                responseData = response.data;
            },
            error : error => {
                console.log(error);
            }
        });
        
        return responseData;
    }
}