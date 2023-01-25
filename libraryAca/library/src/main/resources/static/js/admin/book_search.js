window.onload = () => {
    BookService.getInstance().loadBookList();
    BookService.getInstance().loadCategories();
    ComponentEvent.getInstance().addClickEventSearchButton();

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
                console.log(response);
                returnData = response.data;
            },
            error: error => {
                console.log(error);
            }
        });

        return returnData;
    }

    getCategories() {
        let returnData = null;
        
        $.ajax({
            async: false,
            type: "get",
            url: "http://127.0.0.1:8000/api/admin/categories",
            dataType: "json",
            success : response => {
                console.log(response);
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
        const responseData = BookSearchApi.getInstance().getBookList(searchObj);

        const bookListBody = document.querySelector(".content-table tbody");
        bookListBody.innerHTML = "";

        responseData.forEach((data, index) => {
            bookListBody.innerHTML += `
                <tr>
                    <td><input type="checkbox"></td>
                    <td>${data.bookId}</td>
                    <td>${data.bookCode}</td>
                    <td>${data.bookName}</td>
                    <td>${data.author}</td>
                    <td>${data.publisher}</td>
                    <td>${data.publicationDate}</td>
                    <td>${data.category}</td>
                    <td>${data.rentalStatus == "Y" ? "대여중" : "대여가능"}</td>
                    <td ><i class="fa-solid fa-square-pen"></i></td>
                    <td><i class="fa-solid fa-square-minus"></i></td>
                </tr>
            `;
        });
    }

    loadCategories() {
        const responseData = BookSearchApi.getInstance().getCategories();

        const categorySelect = document.querySelector(".category-select");
        categorySelect.innerHTML = `<option value="">전체조회</option>`;

        responseData.forEach(data => {
            categorySelect.innerHTML += `
                <option value="${data.category}">${data.category}</option>
            `;
        });
    }
}

class ComponentEvent {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ComponentEvent();
        }
        return this.#instance;
    }

    addClickEventSearchButton() {
        const categorySelect = document.querySelector(".category-select");
        const searchInput = document.querySelector(".search-input");
        const searchButton = document.querySelector(".search-button");

        searchButton.onclick  = () => {
            searchObj.category = categorySelect.value;
            searchObj.searchValue = searchInput.value;
            
            BookService.getInstance().loadBookList();
        }

        searchInput.onkeyup = () => {
            if(window.event.keyCode == 13) {
                searchButton.click();
            }
        }
    }
}