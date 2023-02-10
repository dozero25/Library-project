window.onload = () => {
    HeaderService.getInstance().loadHeader();

    SearchService.getInstance().clearBookList();
    SearchService.getInstance().loadSearchBooks();
    SearchService.getInstance().loadCategories();
    SearchService.getInstance().setMaxPage();
    
    ComponentEvent.getInstance().addClickEventCategoryCheckboxs();
    ComponentEvent.getInstance().addScrollEventPaging();
    ComponentEvent.getInstance().addClickEventSearchButton();

    SearchService.getInstance().onLoadSearch();
}

let maxPage = 0;

const searchObj = {
    page: 1,
    searchValue: null,
    categories: new Array(),
    count: 10
}

class SearchApi {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new SearchApi();
        }
        return this.#instance;
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

    getTotalCount() {
        let responseData = null;

        $.ajax({
            async: false,
            type: "get",
            url: "http://127.0.0.1:8000/api/search/totalcount",
            data: searchObj,
            dataType: "json",
            success: response => {
                responseData = response.data;
            },
            error: error => {
                console.log(error);
            }
        });

        return responseData;
    }

    searchBook() {
        let responseData = null;

        $.ajax({
            async: false,
            type: "get",
            url: "http://127.0.0.1:8000/api/search",
            data: searchObj,
            dataType: "json",
            success: response => {
                responseData = response.data;
            },
            error: error => {
                console.log(error);
            }
        });

        return responseData;
    }
    
    setLike(bookId) {
        let likeCount = -1; 

        $.ajax({
            async: false,
            type: "post",
            url: `http://127.0.0.1:8000/api/book/${bookId}/like`,
            dataType: "json",
            success : response => {
                likeCount = response.data;
            },
            error: error => {
                console.log(error);
            }
        });

        return likeCount;
    }

    setDisLike(bookId) {
        let likeCount = -1; 

        $.ajax({
            async: false,
            type: "delete",
            url: `http://127.0.0.1:8000/api/book/${bookId}/like`,
            dataType: "json",
            success : response => {
                likeCount = response.data;
            },
            error: error => {
                console.log(error);
            }
        });

        return likeCount;
    }

    rentalBook(bookId) {
        let responseData = false; 

        $.ajax({
            async: false,
            type: "post",
            url: `http://127.0.0.1:8000/api/rental/${bookId}`,
            dataType: "json",
            success : response => {
                responseData = response.data;
            },
            error: error => {
                console.log(error);
                alert(error.responseJSON.data.rentalCountError);
            }
        });

        return responseData;
    }

    returnBook(bookId) {
        let responseData = false; 

        $.ajax({
            async: false,
            type: "put",
            url: `http://127.0.0.1:8000/api/rental/${bookId}`,
            dataType: "json",
            success : response => {
                responseData = response.data;
            },
            error: error => {
                alert(error.responseJSON.data);
            }
        });

        return responseData;
    }

}


class SearchService {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new SearchService();
        }
        return this.#instance;
    }

    onLoadSearch() {
        const URLSearch = new URLSearchParams(location.search);
        
        if(URLSearch.has("searchValue")) { 
            const searchValue = URLSearch.get("searchValue");
            if(searchValue == "") {
                return;
            }
            const searchInput = document.querySelector(".search-input");
            searchInput.value = searchValue;
            const searchButton = document.querySelector(".search-button");
            searchButton.click();
        } 
    }

    setMaxPage() {
        const totalCount = SearchApi.getInstance().getTotalCount();
        maxPage = totalCount % 10 == 0 ? totalCount / 10 : Math.floor(totalCount / 10) + 1;
    }

    loadCategories() {
        const categoryList = document.querySelector(".category-list");
        categoryList.innerHTML = ``;
        
        const responseData = SearchApi.getInstance().getCategories();
        responseData.forEach(categoryObj => {
            categoryList.innerHTML += `
                <div class="category-item">
                    <input type="checkbox" class="category-checkbox" id="${categoryObj.category}" value="${categoryObj.category}">
                    <label for="${categoryObj.category}">${categoryObj.category}</label>
                </div>
            `;
        });
    }

    clearBookList(){
        const contentFlex = document.querySelector(".content-flex");
        contentFlex.innerHTML = "";
    }

    loadSearchBooks() {
        const responseData = SearchApi.getInstance().searchBook();
        const contentFlex = document.querySelector(".content-flex");
        const principal = PrincipalApi.getInstance().getPrincipal();

        const _bookButtons = document.querySelectorAll(".book-buttons");
        const bookButtonsLength = _bookButtons == null ? 0 : _bookButtons.length;

        responseData.forEach((data, index) => {
            contentFlex.innerHTML += `
            <div class="info-container">
                <div class="book-desc">
                    <div class="img-container">
                        <img src="http://127.0.0.1:8000/image/book/${data.saveName != null ? data.saveName : "noimg2.png"}" class="book-img">
                    </div>
                    <div class="like-info"><i class="fa-regular fa-thumbs-up"></i><span class="like-count">${data.likeCount != null ? data.likeCount : 0}</span></div>
                </div>
                <div class="book-info">
                    <input type= "hidden" class="book-id" value="${data.bookId}">
                    <div class="book-code">${data.bookCode}</div>
                    <h3 class="book-name">${data.bookName}</h3>
                    <div class="info-text book-author"><b>저자: </b>${data.author}</div>
                    <div class="info-text book-publisher"><b>출판사: </b>${data.publisher}</div>
                    <div class="info-text book-publication"><b>출판일: </b>${data.publicationDate}</div>
                    <div class="info-text book-category"><b>카테고리: </b>${data.category}</div>
                    <div class="book-buttons">
                        
                    </div>
                </div>
            </div>
            `;
            const bookButtons = document.querySelectorAll(".book-buttons");
            if(principal == null) {
                if(data.rentalDtlId != 0 && data.returnDate == null) {
                    bookButtons[bookButtonsLength + index].innerHTML = `
                        <button type="button" class="rental-buttons rental-button" disabled>대여중</button>
                    `;
                }else {
                    bookButtons[bookButtonsLength + index].innerHTML = `
                        <button type="button" class="rental-buttons rental-button" disabled>대여가능</button>
                    `;
                }
                bookButtons[bookButtonsLength + index].innerHTML += `
                    <button type="button" class="like-buttons like-button" disabled>추천</button>
                `;
            }else {
                if(data.rentalDtlId != 0 && data.returnDate == null && data.userId != principal.user.userId) {
                    bookButtons[bookButtonsLength + index].innerHTML = `
                        <button type="button" class="rental-buttons rental-button" disabled>대여중</button>
                    `;
                }else if(data.rentalDtlId != 0 && data.returnDate == null && data.userId == principal.user.userId){
                    bookButtons[bookButtonsLength + index].innerHTML = `
                        <button type="button" class="rental-buttons return-button">반납하기</button>
                    `;
                } else {
                    bookButtons[bookButtonsLength + index].innerHTML = `
                        <button type="button" class="rental-buttons rental-button">대여하기</button>
                    `;
                }
                if(data.likeId != 0){
                    bookButtons[bookButtonsLength + index].innerHTML += `
                        <button type="button" class="like-buttons dislike-button">추천취소</button>
                    `;
                } else {
                    bookButtons[bookButtonsLength + index].innerHTML += `
                        <button type="button" class="like-buttons like-button">추천</button>
                `;
                }
                ComponentEvent.getInstance().adddClickEventRentalButtons();
                ComponentEvent.getInstance().addClickEventLikeButtons();
            }
        })
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

    addClickEventCategoryCheckboxs() {
        const checkboxs = document.querySelectorAll(".category-checkbox");
        
        checkboxs.forEach(checkbox => {
            checkbox.onclick = () => {
                if(checkbox.checked) {
                    searchObj.categories.push(checkbox.value);
                }else {
                    const index = searchObj.categories.indexOf(checkbox.value)
                    searchObj.categories.splice(index, 1);
                }
                document.querySelector(".search-button").click();

            }
        }); 
    }

    addScrollEventPaging() {
        const html = document.querySelector("html");
        const body = document.querySelector("body");

        body.onscroll = () => {
            const scrollPosition = body.offsetHeight-html.clientHeight-html.scrollTop;

            if(scrollPosition < 250 && searchObj.page < maxPage) {
                searchObj.page++;
                SearchService.getInstance().loadSearchBooks();
            }
        }
    }

    addClickEventSearchButton() {
        const searchButton = document.querySelector(".search-button");
        const searchInput = document.querySelector(".search-input");

        searchButton.onclick = () => {
            searchObj.searchValue = searchInput.value;
            searchObj.page = 1;
            window.scrollTo(0, 0);
            SearchService.getInstance().clearBookList();
            SearchService.getInstance().setMaxPage();
            SearchService.getInstance().loadSearchBooks();
        }

        searchInput.onkeyup = () => {
            if(window.event.keyCode == 13) {
                searchButton.click();
            }
        }
    }
    addClickEventLikeButtons() {
        const likeButtons = document.querySelectorAll(".like-buttons");
        const bookIds = document.querySelectorAll(".book-id");
        const likeCounts = document.querySelectorAll(".like-count");
        likeButtons.forEach((button, index) => {
            button.onclick = () => {
                if(button.classList.contains("like-button")){
                    const likeCount = SearchApi.getInstance().setLike(bookIds[index].value);
                    if(likeCount != -1){
                        likeCounts[index].textContent = likeCount;
                        button.classList.remove("like-button");
                        button.classList.add("dislike-button");
                        button.textContent = "추천취소";
                    }
                }else {
                    const likeCount = SearchApi.getInstance().setDisLike(bookIds[index].value);
                    if(likeCount != -1){
                        likeCounts[index].textContent = likeCount;
                        button.classList.remove("dislike-button");
                        button.classList.add("like-button");
                        button.textContent = "추천";
                    }
                }
            }
        });
    }

    adddClickEventRentalButtons() {
        const rentalButtons = document.querySelectorAll(".rental-buttons");
        const bookIds = document.querySelectorAll(".book-id");

        rentalButtons.forEach((button, index) => {
            button.onclick = ()  => {
                if(button.classList.contains("rental-button") && button.disabled == false) {
                    const flag = SearchApi.getInstance().rentalBook(bookIds[index].value);
                    if(flag) {
                        button.classList.remove("rental-button");
                        button.classList.add("return-button");
                        button.textContent = "반납하기";
                    } 
                }else if(button.classList.contains("return-button")) {
                    const flag = SearchApi.getInstance().returnBook(bookIds[index].value);
                    if(flag) {
                        button.classList.remove("return-button");
                        button.classList.add("rental-button");
                        button.textContent = "대여하기";
                    }
                }
            }
        });
    }
}