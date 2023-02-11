window.onload = () => {
    HeaderService.getInstance().loadHeader();
    ComponentEvent.getInstance().addClickEventSearchButton();
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
        const searchButton = document.querySelector(".search-button");
        const searchInput = document.querySelector(".search-input");
        searchButton.onclick = () => {
            location.href = `http://localhost:8000/search?searchValue=${searchInput.value}`;
        }
        searchInput.onkeyup = () => {
            if(window.event.keyCode == 13) {
                searchButton.click();
            }
        }
    }
}