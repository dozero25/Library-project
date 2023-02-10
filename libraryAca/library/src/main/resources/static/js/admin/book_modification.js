window.onload = () => {
    BookModificationService.getInstance().setBookCode();
    BookModificationService.getInstance().loadCategories();
    BookModificationService.getInstance().loadBookAndImageData();
    
    ComponentEvent.getInstance().addClickEventModificationButton();
    ComponentEvent.getInstance().addClickEventImgAddButton();
    ComponentEvent.getInstance().addChangeEventImgFile();
    ComponentEvent.getInstance().addClickEventImgModificationButton();
    ComponentEvent.getInstance().addClickEventImgCancelButton();
}

const bookObj = {
    bookCode: "",
    bookName: "",
    author: "",
    publisher: "",
    publicationDate: "",
    category: ""
}

const imgObj = {
    imageId : null,
    bookCode: null,
    saveName: null,
    originName: null
}

const fileObj = {
    files: new Array(),
    formData: new FormData()
}

class BookModificationApi {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new BookModificationApi();
        }
        return this.#instance;
    }

    getBookAndImage() {
        let responseData = null;

        $.ajax({
            async: false,
            type: "get",
            url: `http://localhost:8000/api/admin/book/${bookObj.bookCode}`,
            dataType: "json",
            success: response => {
                responseData = response.data;
            },
            error:error => {
                console.log(error);
            }
        });

        return responseData;
    }

    getCategories() {
        let responseData = null;

        $.ajax({
            async: false,
            type: "get",
            url: "http://localhost:8000/api/admin/categories",
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

    modifyBook() {
        let successFlag = false;

        $.ajax({
            async: false,
            type: "put",
            url: `http://localhost:8000/api/admin/book/${bookObj.bookCode}`,
            contentType: "application/json",
            data: JSON.stringify(bookObj),
            dataType: "json",
            success : response => {
                successFlag = true;
            },
            error: error => {
                console.log(error);
                BookModificationService.getInstance().setErrors(error.responseJSON.data);
            }
        });
        
        return successFlag;
    }

    removeImg() {
        let successFlag = false;

        $.ajax({
            async: false,
            type: "delete",
            url: `http://localhost:8000/api/admin/book/${bookObj.bookCode}/image/${imgObj.imageId}`,
            dataType: "json",
            success: response => {
                successFlag = true;
            },
            error: error => {
                console.log(error);
            }
        });

        return successFlag;
    }

    registerImg() {
        $.ajax({
            async: false,
            type:"post",
            url: `http://localhost:8000/api/admin/book/${bookObj.bookCode}/images`,
            encType: "multipart/form-data",
            contentType: false,
            processData: false,   
            data: fileObj.formData,
            dataType: "json",
            success: response => {
                alert("도서 이미지 수정 완료.");
                location.reload();
            },
            error: error => {
                console.log(error);
            }
        });
    }
}

class BookModificationService {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new BookModificationService();
        }
        return this.#instance;
    }

    setBookCode() {
        const URLSearch = new URLSearchParams(location.search);
        bookObj.bookCode = URLSearch.get("bookCode");

    }

    setBookObjValues() {
        const modificationInputs = document.querySelectorAll(".modification-input");

        bookObj.bookCode = modificationInputs[0].value;
        bookObj.bookName = modificationInputs[1].value;
        bookObj.author = modificationInputs[2].value;
        bookObj.publisher = modificationInputs[3].value;
        bookObj.publicationDate = modificationInputs[4].value;
        bookObj.category = modificationInputs[5].value;
    }

    loadBookAndImageData() {
        const responseData = BookModificationApi.getInstance().getBookAndImage();

        if(responseData.bookMst == null){
            alert("해당 도서코드는 등록되지 않은 코드입니다.");
            history.back();
        }

        const modificationInputs = document.querySelectorAll(".modification-input");
        modificationInputs[0].value = responseData.bookMst.bookCode;
        modificationInputs[1].value = responseData.bookMst.bookName;
        modificationInputs[2].value = responseData.bookMst.author;
        modificationInputs[3].value = responseData.bookMst.publisher;
        modificationInputs[4].value = responseData.bookMst.publicationDate;
        modificationInputs[5].value = responseData.bookMst.category;

        if(responseData.bookImage != null) {
            imgObj.imageId = responseData.bookImage.imageId;
            imgObj.bookCode = responseData.bookImage.bookCode;
            imgObj.saveName = responseData.bookImage.saveName;
            imgObj.originName = responseData.bookImage.originName;

            const bookImg = document.querySelector(".book-img");
            bookImg.src = `http://localhost:8000/image/book/${responseData.bookImage.saveName}`;
        }
    }

    loadCategories() {
        const responseData = BookModificationApi.getInstance().getCategories();

        const categorySelect = document.querySelector(".category-select");
        categorySelect.innerHTML = `<option value="">전체조회</option>`;

        responseData.forEach(data => {
            categorySelect.innerHTML += `
                <option value="${data.category}">${data.category}</option>
            `;
        });
    }

    setErrors(errors) {
        const errorMessages = document.querySelectorAll(".error-message");
        this.clearErrors();

        Object.keys(errors).forEach(key => {
            if(key == "bookCode") {
                errorMessages[0].innerHTML = errors[key];
            }else if(key == "bookName") {
                errorMessages[1].innerHTML = errors[key];
            }else if(key == "category") {
                errorMessages[5].innerHTML = errors[key];
            }
        })
    }

    clearErrors() {
        const errorMessages = document.querySelectorAll(".error-message");
        errorMessages.forEach(error => {
            error.innerHTML = "";
        }) 
    }
    
}

class ImgFileService {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ImgFileService();
        }
        return this.#instance;
    }

    getImgPreview() {
        const bookImg = document.querySelector(".book-img");

        const reader = new FileReader();

        reader.onload = (e) => {
            bookImg.src = e.target.result;
        }

        reader.readAsDataURL(fileObj.files[0]);
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

    addClickEventModificationButton() {
        const modificationButton = document.querySelector(".modification-button");

        modificationButton.onclick = () => {

            BookModificationService.getInstance().setBookObjValues();
            const successFlag = BookModificationApi.getInstance().modifyBook();

            if(!successFlag) {
                return;
            }

            BookModificationService.getInstance().clearErrors();
            
            if(confirm("도서 이미지를 수정하시겠습니까?")) {
                const imgAddButton = document.querySelector(".img-add-button");
                const imgCancelButton = document.querySelector(".img-cancel-button");
    
                imgAddButton.disabled = false;
                imgCancelButton.disabled = false;
            } else{
                location.reload();
            }
        }  
    }

    addClickEventImgAddButton() {
        const imgFile = document.querySelector(".img-file");
        const addButton = document.querySelector(".img-add-button");

        addButton.onclick = () => {
            imgFile.click();
        }
    }

    addChangeEventImgFile() {
        const imgFile = document.querySelector(".img-file");
        imgFile.onchange = () => {
            const formData = new FormData(document.querySelector(".img-form"));
            let changeFlag = false;

            fileObj.files.pop();

            formData.forEach(value => {
                if(value.size != 0) {
                    fileObj.files.push(value);
                    changeFlag = true;
                }
            });

            if(changeFlag) {
                const imgModificationButton = document.querySelector(".img-modification-button");
                imgModificationButton.disabled = false;

                ImgFileService.getInstance().getImgPreview();
                imgFile.value = null;
            }
        }
    }

    addClickEventImgModificationButton() {
        const imgModificationButton = document.querySelector(".img-modification-button");

        imgModificationButton.onclick = () => {
            fileObj.formData.append("files", fileObj.files[0]);

            let successFlag = true;

            if(imgObj.imageId != null) {
                successFlag = BookModificationApi.getInstance().removeImg();
            }
            if(successFlag) {
                BookModificationApi.getInstance().registerImg();
            }
        }
    }

    addClickEventImgCancelButton() {
        const imgCancelButton = document.querySelector(".img-cancel-button");

        imgCancelButton.onclick = () => {
            if(confirm("정말로 이미지 수정을 취소하시겠습니까?")) {
                location.reload();
            }
        }
    }
}