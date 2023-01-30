window.onload = () => {
    ComponentEvent.getInstance().addClickEventRegisterButton();
    ComponentEvent.getInstance().addClickEventImgAddButton();
    ComponentEvent.getInstance().addChangeEventImgFile();
}

const fileObj = {
    files: new Array()
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

    addClickEventRegisterButton() {
        const registerButton = document.querySelector(".register-button");

        registerButton.onclick = () => {
            if(confirm("도서 이미지를 등록하시겠습니까?")) {
                const imgAddButton = document.querySelector(".img-add-button");
                const imgRegisterButton = document.querySelector(".img-register-button");
    
                imgAddButton.disabled = false;
                imgRegisterButton.disabled = false;
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
                ImgFileService.getInstance().getImgPreview();
                imgFile.value = null;
            }
        }
    }
}