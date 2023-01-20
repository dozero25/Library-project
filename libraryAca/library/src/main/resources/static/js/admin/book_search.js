window.onload = () => {

}

let searchObj = {
    page : 1,
    category : "",
    searchValue : "",
    order : "bookId",
    limit : "Y",
    count : 20
}

class BookSearchApi {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new BookSearchApi();
        }
        return this.#instance;
    }

    getBookList(searchObj) {
        let returnData = null;

        $.ajax({
            async: false,
            type: "get",
            url: "http://127.0.0.1:8000/api/admin/books",
            data: searchObj,
            dataType: "json",
            success : response => {
                returnData = response.data;
            },
            error: error => {
                console.log(error);
            }
        });

        return returnData;
    }
}

class BookService {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new BookService();
        }
        return this.#instance;
    }

    loadBookList() {

    }
}