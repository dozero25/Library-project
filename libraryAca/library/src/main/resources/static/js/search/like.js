class LikeApi {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new LikeApi();
        }
        return this.#instance;
    }
}

class LikeService {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new LikeService();
        }
        return this.#instance;
    }
}