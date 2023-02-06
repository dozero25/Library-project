window.onload = () => {
    SearchService.getInstance().loadCategories();
    
    ComponentEvent.getInstance().addClickEventCategoryCheckboxs();
}

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

}

class SearchService {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new SearchService();
        }
        return this.#instance;
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

            }
        }); 
    }
}